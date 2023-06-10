package com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.databinding.FragmentHotelsNewBinding
import com.dm.sky_tours_demo_app.domain.models.City
import com.dm.sky_tours_demo_app.ui.adapters.DestinationsAdapter
import com.dm.sky_tours_demo_app.ui.fragments.hotelslist_fragment.HotelsListFragment
import com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment.RoomsFragment
import com.dm.sky_tours_demo_app.ui.fragments.switchFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import limitRange
import java.text.SimpleDateFormat

@AndroidEntryPoint
class HotelsFragment : Fragment() {

    private val viewModel: HotelsViewModel by viewModels()

    private var _binding: FragmentHotelsNewBinding? = null
    private val binding get() = _binding!!

    private lateinit var destinationsAdapter: DestinationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelsNewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupRV()
        setupCitiesObserver()
        setupTextChangedListener()
        setupCityNameObserver()
        setupDatePickerDialog()
        setupNavigationButtonsListeners()
    }

    private fun setupTextChangedListener() {
        binding.inputDestinations.doAfterTextChanged { text ->
            val query = text.toString()

            if (query.length >= 3) {
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.getCities(query)
                    }
                }
            } else {
                viewModel.clearCities()
            }
        }
    }

    private fun setupCitiesObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cities.collect {
                    if (it.isNotEmpty()) {
                        destinationsAdapter.submitList(it)
                        withContext(Dispatchers.Main) {
                            binding.mainGroup.isGone = true
                            binding.destinationsRv.isVisible = true
                        }
                    } else {
                        destinationsAdapter.submitList(emptyList())
                        withContext(Dispatchers.Main) {
                            binding.mainGroup.isVisible = true
                            binding.destinationsRv.isGone = true
                        }
                    }
                }
            }
        }
    }

    private fun setupCityNameObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentCity.collect {
                    binding.inputDestinations.setText(it.name)
                }
            }
        }
    }

    private fun getCity(city: City) {
        viewModel.setCurrentCity(city)
        viewModel.clearCities()
        binding.inputDestinations.clearFocus()
    }

    private fun setupRV() {
        destinationsAdapter = DestinationsAdapter(::getCity)

        val decorator =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                val drawable = ResourcesCompat
                    .getDrawable(
                        resources,
                        R.drawable.decorator,
                        requireActivity().theme
                    ) ?: return
                setDrawable(drawable)
            }

        with(binding) {
            destinationsRv.addItemDecoration(decorator)
            destinationsRv.adapter = destinationsAdapter
        }
    }

    // TODO(Что-то сделать с этим полотном)
    private fun setupDatePickerDialog() = with(binding) {
        fromToContainer.setOnClickListener {
            val builderRange = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(com.dm.sky_tours_demo_app.R.style.ThemeOverlay_App_MaterialCalendar)
                .setTitleText(com.dm.sky_tours_demo_app.R.string.choosedayofdp)
            builderRange.setCalendarConstraints(limitRange().build())
            val pickerRange = builderRange.build()
            pickerRange.show(childFragmentManager, pickerRange.toString())
            pickerRange.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val firstData = it.first // первая дата
                val secondData = it.second // вторая дата
                var dateFormatter = SimpleDateFormat("dd-MM-yyyy") /* формат даты */
                val fdate = dateFormatter.format(firstData)
                val sdate = dateFormatter.format(secondData)
                val textfrom = fieldFrom //вывод в поле
                val textto = fieldTo
                textfrom.text = fdate!!  //вывод в поле
                textto.text = sdate!!
                Log.d("MyLog", "$fdate - date of departure is selected")
                Log.d("MyLog", "$sdate - date of arrived is selected")
                dateFormatter = SimpleDateFormat("yyyyMMdd") /* формат даты для запроса*/
                val f2date = dateFormatter.format(firstData)
                val s2date = dateFormatter.format(secondData)
                val dates = "$f2date|$s2date"
//                dataViewModel.hDates.value = dates
                Log.d("DATES", dates)
            }
            // Setting up the event for when cancelled is clicked
            pickerRange.addOnNegativeButtonClickListener {
                Log.d("MyLog", "${pickerRange.headerText} is cancelled")
            }
            // Setting up the event for when back button is pressed
            pickerRange.addOnCancelListener {
                Log.d("MyLog", "Date Picker Cancelled")
            }
        }
    }

    private fun setupNavigationButtonsListeners() {

        val fm = parentFragmentManager
        val container = R.id.fragment_container

        with(binding) {

            hotelsSearchButton.setOnClickListener {
                switchFragment(fm, container, HotelsListFragment())
            }

            hiddenButton.setOnClickListener {
                switchFragment(fm, container, RoomsFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
