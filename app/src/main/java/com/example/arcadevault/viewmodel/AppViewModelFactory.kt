package com.example.arcadevault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.arcadevault.data.ListingRepository

class AppViewModelFactory(
    private val listingRepository: ListingRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListingViewModel::class.java) -> {
                ListingViewModel(listingRepository) as T
            }
            modelClass.isAssignableFrom(PdpViewModel::class.java) -> {
                PdpViewModel(listingRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
