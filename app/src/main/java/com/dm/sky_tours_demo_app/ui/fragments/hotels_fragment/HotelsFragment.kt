package com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment

import android.os.Bundle
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
import com.dm.sky_tours_demo_app.ui.adapters.DestinationsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HotelsFragment : Fragment(R.layout.fragment_hotels_new) {

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
                    binding.inputDestinations.setText(it)
                }
            }
        }
    }

    private fun getCityName(cityName: String) {
        viewModel.setCurrentCity(cityName)
        viewModel.clearCities()
        binding.inputDestinations.clearFocus()
    }

    private fun setupRV() {
        destinationsAdapter = DestinationsAdapter(::getCityName)

        val decorator =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL).apply {
                val drawable = ResourcesCompat
                    .getDrawable(resources, R.drawable.decorator, requireActivity().theme) ?: return

                setDrawable(drawable)
            }

        with(binding) {
            destinationsRv.addItemDecoration(decorator)
            destinationsRv.adapter = destinationsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
