package com.example.arcadevault.modules

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arcadevault.R
import com.example.arcadevault.adapter.ListingAdapter
import com.example.arcadevault.data.AppDatabase
import com.example.arcadevault.data.ListingRepository
import com.example.arcadevault.viewmodel.AppViewModelFactory
import com.example.arcadevault.viewmodel.ListingViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExplorePageFragment : Fragment(R.layout.fragment_explore_page) {

    private lateinit var adapter: ListingAdapter
    private val viewModel: ListingViewModel by viewModels {
        // Initialize the database and repository directly in the Activity
        val database = AppDatabase.getDatabase(requireContext())
        val repository = ListingRepository(database.listingDao())
        AppViewModelFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up RecyclerView and adapter
        adapter = ListingAdapter() { id: Int ->
            val intent = Intent(requireContext(), ProductDetailsPageActivity::class.java)
            intent.putExtra("listing_id", id)
            startActivity(intent)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.listings_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // Observe the listings using collectLatest
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllListings().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        // Set up the fragment transaction
        view.findViewById<Button>(R.id.addListingButton).setOnClickListener {
            showAddListingFragment()
        }
    }

    private fun showAddListingFragment() {
        val fragment = AddListingFragment()
        fragment.show(parentFragmentManager, fragment.tag)
    }

}