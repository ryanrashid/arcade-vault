package com.example.arcadevault.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.arcadevault.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListingRepository(private val listingDao: ListingDao) {

    private fun insertFakeData() {
        CoroutineScope(Dispatchers.IO).launch {
            // Generate and insert fake data
            val fakeListings = Utils.generateFakeListings(1000)
            listingDao.insertListings(fakeListings)
        }
    }

    // Insert a listing
    suspend fun insertListing(listing: ListingEntity) {
        listingDao.insertListing(listing)
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

    // Get listings by title as a Flow of PagingData
    fun getListingByTitle(title: String): Flow<PagingData<ListingEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { listingDao.getListingByTitle(title) }
        ).flow
    }
}