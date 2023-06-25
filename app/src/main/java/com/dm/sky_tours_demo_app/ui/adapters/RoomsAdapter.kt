package com.dm.sky_tours_demo_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dm.sky_tours_demo_app.R
import com.dm.sky_tours_demo_app.databinding.RoomListNewBinding
import com.dm.sky_tours_demo_app.ui.model.Room
import com.dm.sky_tours_demo_app.ui.fragments.rooms_fragment.RoomClickListener

class RoomsAdapter(private val clickListener: RoomClickListener) :
    ListAdapter<Room, RoomsViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val binding = RoomListNewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = RoomsViewHolder(binding)

        with(binding) {
            with(clickListener) {

                addAdultBtn.setOnClickListener {
                    addAdult(holder.adapterPosition)
                }

                addChildBtn.setOnClickListener {
                    addChild(holder.adapterPosition)
                }

                removeAdultBtn.setOnClickListener {
                    removeAdult(holder.adapterPosition)
                }

                removeChildBtn.setOnClickListener {
                    removeChild(holder.adapterPosition)
                }

                closeRoomItem.setOnClickListener {
                    closeRoom(holder.adapterPosition)
                }

                roomContainer.setOnClickListener {
                    clickOnContainer(holder.adapterPosition)
                }
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RoomsViewHolder(
    private val binding: RoomListNewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Room) {
        with(binding) {
            with(item) {
                adultCounter.text = adultCount.toString()
                childCounter.text = childCount.toString()
                roomNumber.text = itemView.context.getString(R.string.room_number, id + 1)
                collapseGroup.isGone = isCollapsed
            }
        }
    }
}

private val itemComparator = object : DiffUtil.ItemCallback<Room>() {
    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
        return oldItem == newItem
    }
}
