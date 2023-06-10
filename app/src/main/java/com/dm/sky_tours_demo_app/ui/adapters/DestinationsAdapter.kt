package com.dm.sky_tours_demo_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dm.sky_tours_demo_app.databinding.DestinationsListNewBinding
import com.dm.sky_tours_demo_app.domain.models.City

class DestinationsAdapter(private val getCity: (City) -> Unit) :
    ListAdapter<City, DestinationsAdapter.DestinationsViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationsViewHolder {
        val binding = DestinationsListNewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = DestinationsViewHolder(binding)

        binding.cityContainer.setOnClickListener {
            val currentCity = currentList[holder.adapterPosition]

            getCity(currentCity)
        }

        return holder
    }

    override fun onBindViewHolder(holder: DestinationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DestinationsViewHolder(
        private val binding: DestinationsListNewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: City) {
            binding.cityName.text = item.name
        }
    }
}

private val itemComparator = object : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}
