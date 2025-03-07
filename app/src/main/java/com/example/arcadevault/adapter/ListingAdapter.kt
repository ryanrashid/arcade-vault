package com.example.arcadevault.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.arcadevault.R
import com.example.arcadevault.data.ListingEntity

class ListingAdapter : PagingDataAdapter<ListingEntity, ListingAdapter.ListingViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_listing, parent, false)
        return ListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = getItem(position)
        if (listing != null) {
            // Load image using Glide
            if (!listing.images.isNullOrEmpty()) {
                Glide.with(holder.itemView.context).load(listing.images.first()).into(holder.listingImageView)
            }
            holder.titleTextView.text = listing.title
            holder.priceTextView.text = listing.price?.toString() ?: "N/A"
        }
    }

    class ListingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val listingImageView: ImageView = itemView.findViewById(R.id.listing_image_view)
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ListingEntity>() {
            override fun areItemsTheSame(oldItem: ListingEntity, newItem: ListingEntity): Boolean {
                return oldItem.id == newItem.id // Compare by ID
            }

            override fun areContentsTheSame(oldItem: ListingEntity, newItem: ListingEntity): Boolean {
                return oldItem == newItem // Compare all fields
            }
        }
    }
}
