package com.dm.sky_tours_demo_app.ui.fragments.search_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.databinding.FragmentSearchBinding
import com.dm.sky_tours_demo_app.ui.fragments.cars_fragment.CarsFragment
import com.dm.sky_tours_demo_app.ui.fragments.flight_fragment.FlightFragment
import com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment.HotelsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        val hotels = HotelsFragment()
        val flight = FlightFragment()
        val cars = CarsFragment()

        val fm = childFragmentManager
        val container = R.id.fragment_container

        fun switch(fragment: Fragment) {
            fm.commit {
                replace(container, fragment)
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> switch(hotels)
                    1 -> switch(flight)
                    2 -> switch(cars)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        fun replace(fragment: Fragment) {
            fm.commit {
                replace(container, fragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
