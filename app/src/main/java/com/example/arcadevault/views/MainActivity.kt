package com.example.arcadevault.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arcadevault.R
import com.example.arcadevault.adapter.ListingAdapter
import com.example.arcadevault.data.AppDatabase
import com.example.arcadevault.data.ListingRepository
import com.example.arcadevault.viewmodel.ListingViewModel
import com.example.arcadevault.viewmodel.ListingViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ListingAdapter
    private val viewModel: ListingViewModel by viewModels {
        // Initialize the database and repository directly in the Activity
        val database = AppDatabase.getDatabase(this)
        val repository = ListingRepository(database.listingDao())
        ListingViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        // Set up RecyclerView and adapter
        adapter = ListingAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.listings_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Observe the listings using collectLatest
        lifecycleScope.launch {
            viewModel.getAllListings().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        // Set up the fragment transaction
        findViewById<Button>(R.id.addListingButton).setOnClickListener {
            showAddListingFragment()
        }
    }

    private fun showAddListingFragment() {
        val fragment = AddListingFragment()
        fragment.show(supportFragmentManager, fragment.tag)
    }
}