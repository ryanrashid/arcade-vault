package com.example.arcadevault.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ListingDao {

    @Insert
    suspend fun insertListing(listing: ListingEntity): Long // Returns the row ID of the inserted item

    @Insert
    suspend fun insertListings(listings: List<ListingEntity>): List<Long> // Returns List of row IDs for the inserted items

    @Query("SELECT * FROM listings ORDER BY id DESC")
    fun getAllListings(): PagingSource<Int, ListingEntity>

    @Query("SELECT * FROM listings WHERE id = :listingId")
    suspend fun getListingFromId(listingId: Int): ListingEntity?
}
