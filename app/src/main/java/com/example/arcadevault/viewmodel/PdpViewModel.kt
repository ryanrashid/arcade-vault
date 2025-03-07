package com.example.arcadevault.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcadevault.data.ListingEntity
import com.example.arcadevault.data.ListingRepository
import kotlinx.coroutines.launch

class PdpViewModel(private val repository: ListingRepository) : ViewModel() {

    private val _listing = MutableLiveData<ListingEntity>()
    val listing: LiveData<ListingEntity> = _listing

    fun loadListingFromId(id: Int) {
        viewModelScope.launch {
            _listing.postValue(repository.getListingFromId(id))
        }
    }
}