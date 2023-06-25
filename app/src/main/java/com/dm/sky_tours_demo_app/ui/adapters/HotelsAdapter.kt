package com.dm.sky_tours_demo_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dm.sky_tours_demo_app.databinding.HotelItemNewBinding
import com.dm.sky_tours_demo_app.domain.models.Hotel
import com.squareup.picasso.Picasso

//TODO(доделать)
class HotelsAdapter : ListAdapter<Hotel, HotelsViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelsViewHolder {
        val binding = HotelItemNewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = HotelsViewHolder(binding)

        return holder
    }

    override fun onBindViewHolder(holder: HotelsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HotelsViewHolder(
    private val binding: HotelItemNewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Hotel) {
        with(binding) {
            Picasso.get().load(item.thumb).into(hotelsPreview)
            addressField.text = item.location.address
            hotelName.text = item.name
        }
    }
}

private val itemComparator = object : DiffUtil.ItemCallback<Hotel>() {
    override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
        return oldItem.location == newItem.location
    }

    override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
        return oldItem == newItem
    }
}
