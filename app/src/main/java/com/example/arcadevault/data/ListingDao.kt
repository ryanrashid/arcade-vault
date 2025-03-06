package com.example.arcadevault.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ListingDao {

    // Insert a listing
    @Insert
    suspend fun insertListing(listing: ListingEntity)

    // Insert multiple listings
    @Insert
    suspend fun insertListings(listings: List<ListingEntity>)

    // Get all listings - we will return a PagingSource here
    @Query("SELECT * FROM listings ORDER BY id DESC")
    fun getAllListings(): PagingSource<Int, ListingEntity> // PagingSource to load pages

    // Get a listing by title
    @Query("SELECT * FROM listings WHERE title = :title")
    fun getListingByTitle(title: String): PagingSource<Int, ListingEntity>
}