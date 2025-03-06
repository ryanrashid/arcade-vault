package com.example.arcadevault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.arcadevault.data.ListingEntity
import com.example.arcadevault.data.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListingViewModel(private val repository: ListingRepository) : ViewModel() {

    // Insert a listing
    fun insertListing(listing: ListingEntity) {
        viewModelScope.launch {
            repository.insertListing(listing)
        }
    }

    // Get all listings as a Flow of PagingData
    fun getAllListings(): Flow<PagingData<ListingEntity>> {
        return repository.getAllListings().cachedIn(viewModelScope) // Cache the data in the ViewModel scope
    }

    // Get listings by title as a Flow of PagingData
    fun getListingByTitle(title: String): Flow<PagingData<ListingEntity>> {
        return repository.getListingByTitle(title)
            .cachedIn(viewModelScope) // Cache the data in the ViewModel scope
    }
}