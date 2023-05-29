package com.dm.sky_tours_demo_app.ui.fragments.hotels_fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.databinding.FragmentHotelsBinding
import com.dm.sky_tours_demo_app.ui.AppTextWatcher
import com.dm.sky_tours_demo_app.ui.HDestinationAdapter
import com.google.android.material.textview.MaterialTextView

class HotelsFragment : Fragment(R.layout.fragment_hotels) {
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var adapter: HDestinationAdapter
    val city = arrayOf("Moscow", "Dublin", "Lida", "Riga", "Brest", "Minsk","Barselona","Rhodos",
        "Virginia","Los Angeles","Rio")

    val  geo = arrayOf("12423442", "24242342", "4234234", "432342423", "23234234", "423424","4234234","234234",
        "34324234","23423423424","423424234")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fieldListener()
        recyclerView()
    }



    @SuppressLint("SuspiciousIndentation")
    private fun fieldListener() = with(binding) {
        val destinationField = view?.findViewById(R.id.hotels_search_city) as MaterialTextView
        val hintField = view?.findViewById(R.id.destination_search_hint) as TextView
        val dest = destinationField.text.toString()
        Log.d("Hotels", "getData")
        destinationField.addTextChangedListener(
            AppTextWatcher {
                if (destinationField.text.length <= 3){
                    progressBar.isVisible = true
                    hotelsSearchConstraint.isVisible = true
                }
                if (destinationField.text.length >= 3) {
                    progressBar.isVisible = false
                    val math = 3 - destinationField.text?.length!!
                    hintField.text = "enter $math\n or more"
                    if (maybeField.visibility == View.GONE) {
                        maybeField.isVisible = true
                    }
                    if (destinationField.text.length >= 3 && destinationField.text.length <= 49) {
                        hintField.text = destinationField.text?.length.toString() + "/50"
                        val teal = "#119CA4"
                        val tealInt = Color.parseColor(teal)
                        hintField.setTextColor(tealInt)
                    } else if (destinationField.text.length >= 50) {
                        hintField.text = destinationField.text.length.toString() + "/50"
                        hintField.setTextColor(Color.RED)
                        Toast.makeText(
                            requireContext().applicationContext,
                            "You want to enter more characters than required!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        )
    }

    fun recyclerView() {
        val animation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
        binding.destinationList.setHasFixedSize(true)
        binding.destinationList.layoutManager = LinearLayoutManager(activity)
        binding.destinationList.adapter =
            context?.let {
                HDestinationAdapter(
                    city,
                    geo,)
            }
        binding.destinationList.startAnimation(animation)
    }
}