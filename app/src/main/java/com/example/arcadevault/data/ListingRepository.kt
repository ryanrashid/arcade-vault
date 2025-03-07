package com.example.arcadevault.data

import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class ListingRepository(private val listingDao: ListingDao) {

    // Function to handle launching the file picker for multiple images
    fun pickImages(launcher: ActivityResultLauncher<String>) {
        // Launch the file picker from the repository
        launcher.launch("image/*")
    }

    // Insert a listing
    suspend fun insertListing(title: String, price: Double, images: List<Uri>?) {
        val listing = ListingEntity(title = title, price = price, images = images)
        try {
            listingDao.insertListing(listing)
        } catch (e: Exception) {
            Log.e("InsertError", "Error inserting listing", e)
        }
    }

    // Get all listings as a Flow of PagingData
    fun getAllListings(): Flow<PagingData<ListingEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Number of items to load at once
                enablePlaceholders = false // Disable placeholders for simplicity
            ),
            pagingSourceFactory = { listingDao.getAllListings() }
        ).flow
    }

    suspend fun getListingFromId(id: Int): ListingEntity? {
        return listingDao.getListingFromId(id)
    }
}