package com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
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
import com.dm.sky_tours_demo_app.ui.adapters.HotelsAdapter
import com.dm.sky_tours_demo_app.ui.fragments.utils.createDatePicker
import com.dm.sky_tours_demo_app.ui.fragments.hotelslist_fragment.HotelsListFragment
import com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment.RoomsFragment
import com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment.RoomsFragment.Companion.ROOMS_DATA
import com.dm.sky_tours_demo_app.ui.fragments.utils.AppTextWatcher
import com.dm.sky_tours_demo_app.ui.fragments.utils.switchFragment
import com.dm.sky_tours_demo_app.ui.model.RoomsData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class HotelsFragment : Fragment() {

    private val viewModel: HotelsViewModel by viewModels()

    private var _binding: FragmentHotelsNewBinding? = null
    private val binding get() = _binding!!

    private lateinit var destinationsAdapter: DestinationsAdapter
    private lateinit var hotelsAdapter: HotelsAdapter

    //        TODO(need refactoring)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        TODO(need refactoring)
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val roomsData = bundle.getParcelable(ROOMS_DATA, RoomsData::class.java)

            roomsData?.let {

                viewModel.updateRooms(it)

                Toast.makeText(
                    requireContext(),
                    "Adults: ${it.adultCount}, Child: ${it.childCount}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelsNewBinding.inflate(inflater, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupRV()
        setupRoomsObserver()
        setupCitiesObserver()
        setupCityNameObserver()
        setupDateObserver()
        setupHotelsObserver()
        setupTextChangedListener()
        setupNavigationButtonsListeners()
        setupDatePickerDialog()
        setupLocationListener()
    }

    //    private fun setupTextChangedListener() {
//        binding.inputDestinations.doAfterTextChanged { text ->
//            val query = text.toString()
//            if (query.length >= 3) {
//                viewLifecycleOwner.lifecycleScope.launch {
//                    repeatOnLifecycle(Lifecycle.State.STARTED) {
//                        viewModel.getCities(query)
//                    }
//                }
//            } else {
//                viewModel.clearCities()
//            }
//        }
//    }

    //    TODO(need refactoring)
    private fun setupTextChangedListener() {
        binding.inputDestinations.addTextChangedListener(
            AppTextWatcher {
                val query = it.toString()
                if (query.length >= 3) {
                    binding.destinationsProgress.isVisible = true
                    binding.hotelsLocationIcon.isVisible = true
                    binding.textSearchNear.isVisible = true
                    binding.textDetectLocation.isVisible = true
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.fetchCities(query)
                        }
                    }
                } else {
                    binding.destinationsProgress.isGone = true
                    binding.textMaybe.isGone = true
                    viewModel.clearCities()
                }
            }
        )
    }

    //        TODO(need refactoring)
    private fun setupLocationListener() {
        binding.hotelsLocationIcon.setOnClickListener {
            detectLocation()
        }
    }

    //        TODO(need refactoring)
    private fun detectLocation() {
        try {
            getActualLocation()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    //        TODO(need refactoring)
    private fun getActualLocation() {

        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat
                .checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                Log.d("Hotels", it.latitude.toString())
//                binding.latitudeTextview.text = "${it.latitude}" // it.longitude is a Double
//                binding.longitudeTextview.text = "${it.longitude}" // tvLongitude is a TextView
            }
        }
    }

    //        TODO(need refactoring)
    private fun setupCitiesObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cities.collect {
                    if (it.isNotEmpty()) {
                        destinationsAdapter.submitList(it)
                        withContext(Dispatchers.Main) {
                            binding.mainGroup.isGone = true
                            binding.destinationsRv.isVisible = true
                            binding.textMaybe.isVisible = true
                        }
                    } else {
                        destinationsAdapter.submitList(emptyList())
                        withContext(Dispatchers.Main) {
                            binding.mainGroup.isVisible = true
                            binding.destinationsRv.isGone = true
                            binding.destinationsProgress.isGone = true
                            binding.hotelsLocationIcon.isGone = true
                            binding.textSearchNear.isGone = true
                            binding.textDetectLocation.isGone = true
                            binding.textMaybe.isGone = true
                        }
                    }
                }
            }
        }
    }

    private fun setupHotelsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hotels.collect {
                    if (it.isNotEmpty()) {
                        hotelsAdapter.submitList(it)
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

    private fun setupDateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.date.collect {
                    binding.fieldFrom.text = it.dateFrom
                    binding.fieldTo.text = it.dateTo
                }
            }
        }
    }

    private fun setupRoomsObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rooms.collect {
                    binding.adultCount.text = it.adultCount.toString()
                    binding.childrenCount.text = it.childCount.toString()
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
        hotelsAdapter = HotelsAdapter()
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

            hotelsRv.adapter = hotelsAdapter
        }
    }

    private fun setupDatePickerDialog() {

        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) /* формат даты */

        val datePickerDialog = createDatePicker(
            R.style.ThemeOverlay_App_MaterialCalendar,
            R.string.choosedayofar
        ).apply {
            addOnPositiveButtonClickListener {
                viewModel.updateFromToDate(
                    dateFormatter.format(it.first),
                    dateFormatter.format(it.second)
                )
            }
        }

        binding.fromToContainer.setOnClickListener {
            datePickerDialog.show(childFragmentManager, "")
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

            hotelsSearchButton.setOnClickListener {
                viewModel.fetchHotels()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {
        const val REQUEST_KEY = "qwerty"
    }
}
