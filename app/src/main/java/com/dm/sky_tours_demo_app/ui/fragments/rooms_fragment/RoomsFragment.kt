package com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.databinding.FragmentRoomsBinding
import com.dm.sky_tours_demo_app.ui.adapters.RoomsAdapter
import com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment.HotelsFragment.Companion.REQUEST_KEY
import com.dm.sky_tours_demo_app.ui.model.RoomsData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RoomsFragment : Fragment(), RoomClickListener {

    private val viewModel: RoomsViewModel by viewModels()

    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!

    private lateinit var roomsAdapter: RoomsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setupRV()
        setupObserver()
        setupClickListeners()
    }

    private fun setupClickListeners() {

        with(binding) {
            addRoomBtn.setOnClickListener {
                viewModel.addRoom()
            }

//            TODO(need refactoring)
            roomAcceptBtn.setOnClickListener {
                setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(
                        ROOMS_DATA to RoomsData(
                            viewModel.rooms.value.size,
                            viewModel.rooms.value.sumOf { it.adultCount },
                            viewModel.rooms.value.sumOf { it.childCount },
                            listOf()
                        )
                    )
                )

                parentFragmentManager.popBackStack()
            }

            roomCloseBtn.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupRV() {
        roomsAdapter = RoomsAdapter(this)

        binding.roomsRecycler.adapter = roomsAdapter
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rooms.collect {
                    val adultCount = it.sumOf { room -> room.adultCount }
                    val childCount = it.sumOf { room -> room.childCount }
                    val overallCount = adultCount + childCount

                    withContext(Dispatchers.Main) {
                        with(binding) {
                            adultCountText.text =
                                getString(R.string.room_adult_count, adultCount)

                            childCountText.text =
                                getString(R.string.room_child_count, childCount)

                            countOverallText.text =
                                getString(R.string.room_overall_count, overallCount)
                        }
                    }

                    roomsAdapter.submitList(it)
                }
            }
        }
    }

    override fun closeRoom(position: Int) {
        viewModel.removeRoom(position)
    }

    override fun addAdult(position: Int) {
        viewModel.addAdult(position)
    }

    override fun addChild(position: Int) {
        viewModel.addChild(position)
    }

    override fun removeAdult(position: Int) {
        viewModel.removeAdult(position)
    }

    override fun removeChild(position: Int) {
        viewModel.removeChild(position)
    }

    override fun clickOnContainer(position: Int) {
        viewModel.showDetails(position)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {
        const val ROOMS_DATA = "room_data"
    }
}
