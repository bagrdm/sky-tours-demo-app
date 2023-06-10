package com.dm.sky_tours_demo_app.ui.fragments.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.databinding.FragmentSearchBinding
import com.dm.sky_tours_demo_app.ui.fragments.FragmentsHolder
import com.dm.sky_tours_demo_app.ui.fragments.switchFragment
import com.dm.sky_tours_demo_app.ui.fragments.cars_fragment.CarsFragment
import com.dm.sky_tours_demo_app.ui.fragments.flight_fragment.FlightFragment
import com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment.HotelsFragment
import com.dm.sky_tours_demo_app.ui.fragments.hotelsnflight_fragment.HotelsNFlightFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val fragmentHolder by lazy { FragmentsHolder() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation() {

        val fm = childFragmentManager
        val container = R.id.fragment_container

        val hotels = getString(R.string.hotels)
        val flight = getString(R.string.flight)
        val cars = getString(R.string.cars)
        val hotelsnflight = getString(R.string.hotelnflight)

        fragmentHolder.apply {
            add(hotels, HotelsFragment())
            add(flight, FlightFragment())
            add(cars, CarsFragment())
            add(hotelsnflight, HotelsNFlightFragment())
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {

                    hotels -> switchFragment(
                        fm,
                        container,
                        fragmentHolder.get(hotels)
                    )

                    flight -> switchFragment(
                        fm,
                        container,
                        fragmentHolder.get(flight)
                    )

                    cars -> switchFragment(
                        fm,
                        container,
                        fragmentHolder.get(cars)
                    )

                    hotelsnflight -> switchFragment(
                        fm,
                        container,
                        fragmentHolder.get(hotelsnflight)
                    )
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentHolder.clear()
        _binding = null
    }
}
