package com.example.arcadevault.modules

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arcadevault.R
import com.example.arcadevault.adapter.ImageAdapter
import com.example.arcadevault.data.AppDatabase
import com.example.arcadevault.data.ListingEntity
import com.example.arcadevault.data.ListingRepository
import com.example.arcadevault.viewmodel.AppViewModelFactory
import com.example.arcadevault.viewmodel.PdpViewModel

class ProductDetailsPageActivity : AppCompatActivity() {

    private val viewModel: PdpViewModel by viewModels {
        val database = AppDatabase.getDatabase(this)
        val repository = ListingRepository(database.listingDao())
        AppViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdp)
        viewModel.loadListingFromId(intent.getIntExtra("listing_id", -1))
        val imageCarouselRecyclerView = findViewById<RecyclerView>(R.id.image_carousel_recycler_view)
        val titleTextView = findViewById<TextView>(R.id.title_text_view)
        val priceTextView = findViewById<TextView>(R.id.price_text_view)
        viewModel.listing.observe(this, Observer { listing: ListingEntity ->
            titleTextView.text = listing.title
            priceTextView.text = listing.price.toString()
            imageCarouselRecyclerView.apply {
                val imageAdapter = ImageAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = imageAdapter
                imageAdapter.submitList(listing.images)
            }
        })
    }

}