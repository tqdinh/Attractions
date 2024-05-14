package com.example.attractions.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.attractions.R
import com.example.attractions.databinding.CustomAttractionViewBinding
import com.example.data.entity.Attraction
import com.example.data.entity.AttractionPlace

class AttractionAdapter(
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<AttractionPlace, AttractionAdapter.EventViewHolder>(object :
    DiffUtil.ItemCallback<AttractionPlace>() {
    override fun areItemsTheSame(
        oldItem: AttractionPlace,
        newItem: AttractionPlace
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AttractionPlace,
        newItem: AttractionPlace
    ): Boolean {
        return oldItem.id == newItem.id

    }
}) {

    interface OnItemClickListener {
        fun onItemClick(item: Attraction)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding =
            CustomAttractionViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)


//        val windowManager = parent.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        val widthPixels = displayMetrics.widthPixels
//
//
//        val layoutParams = ViewGroup.LayoutParams(widthPixels, (widthPixels * 0.8).toInt())
//        binding.root.layoutParams = layoutParams

        return ActiveEventViewHolder(binding, parent.context)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position), position)

    }

    inner class ActiveEventViewHolder(
        binding: CustomAttractionViewBinding,
        context: Context,

        ) :
        EventViewHolder(binding, onItemClickListener) {

        override fun bind(item: AttractionPlace, position: Int) {
            super.bind(item, position)
        }


    }

    open class EventViewHolder(

        val binding: CustomAttractionViewBinding,
        private val onItemClickListener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        open fun bind(item: AttractionPlace, position: Int) {
            binding.placeName.text = item.name
            binding.placeIntro.text = item.introduction
            bindPhoto(item.avatar, binding.ivAvatar)
        }


        private fun bindPhoto(url: String, imageView: ImageView) {
            Glide.with(itemView.context)
                .load(url)
                .placeholder(R.drawable.img_placeholder)
//                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

}