package com.example.arcadevault.viewmodel

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.arcadevault.data.ListingEntity
import com.example.arcadevault.data.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListingViewModel(private val repository: ListingRepository) : ViewModel() {

    val uploadedImages: MutableLiveData<List<Uri>> = MutableLiveData()

    fun addListing(title: String, price: Double) {
        // Insert new listing with images into the database
        viewModelScope.launch {
            repository.insertListing(title, price, uploadedImages.value)
        }
    }

    // Get all listings as a Flow of PagingData
    fun getAllListings(): Flow<PagingData<ListingEntity>> {
        return repository.getAllListings().cachedIn(viewModelScope) // Cache the data in the ViewModel scope
    }

    fun pickImages(launcher: ActivityResultLauncher<String>) {
        // Notify repository to handle image picking
        repository.pickImages(launcher)
    }

    fun setSelectedImages(uris: List<Uri>) {
        uploadedImages.value = uris
    }
}