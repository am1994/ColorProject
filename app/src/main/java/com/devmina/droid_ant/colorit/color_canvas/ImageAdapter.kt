package com.devmina.droid_ant.colorit.color_canvas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devmina.droid_ant.colorit.databinding.ImageItemBinding
import com.devmina.droid_ant.colorit.model.ListofImages


class ImageAdapter (val onClick: onClickListener): ListAdapter<ListofImages, ImageAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener{
            onClick.clickListener(item)
        }
        holder.bind(item)

    }


    class onClickListener(val clickListener: (image:ListofImages) ->Unit){
        fun onClick(image:ListofImages)=clickListener(image)
    }

    class ViewHolder (private val binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ListofImages) {
            binding.imageChild = item
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<ListofImages>() {
        override fun areItemsTheSame(oldItem: ListofImages, newItem: ListofImages): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ListofImages, newItem: ListofImages): Boolean {
            return oldItem.image == newItem.image
        }
    }
}
