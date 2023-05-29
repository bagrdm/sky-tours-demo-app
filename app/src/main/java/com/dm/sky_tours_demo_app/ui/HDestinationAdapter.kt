package com.dm.sky_tours_demo_app.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dm.sky_tours_demo_app.R

class HDestinationAdapter(
    private val destList: Array<String>,
    private val locateList: Array<String>,
) : RecyclerView.Adapter<HDestinationAdapter.MyViewHolder>(),
    RecyclerView.OnItemTouchListener {
    val dest = R.id.hotels_search_city

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val largeTextView: TextView = itemView.findViewById(R.id.cities_field)
        val smallTextView: TextView = itemView.findViewById(R.id.locate_field)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.destinations_list, parent, false)
        return MyViewHolder(itemView)
        Log.d("MyLog", "holder")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView.text = destList[position]
        holder.smallTextView.text = locateList[position]
        holder.largeTextView.text.toString()
        holder.smallTextView.text.toString()
        holder.itemView.setOnClickListener { view ->
            if (position >= 0) {
                val cityValue = holder.largeTextView.text.toString()
                val locateValue = holder.smallTextView.text.toString()
                Log.d("Listener", cityValue + locateValue)
            }
            return@setOnClickListener
        }
    }


    override fun getItemCount() = destList.size
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        TODO("Not yet implemented")
    }
}
