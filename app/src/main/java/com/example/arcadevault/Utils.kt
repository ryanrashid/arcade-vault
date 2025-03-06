package com.example.arcadevault

import com.example.arcadevault.data.ListingEntity

object Utils {

    fun generateFakeListings(count: Int): List<ListingEntity> {
        val listings = mutableListOf<ListingEntity>()
        for (i in 1..count) {
            val listing = ListingEntity(
                id = 0, // Room will auto-generate the ID
                title = "Prize $i",
                price = (10..50).random().toDouble(), // Random price between 10 and 50
                image = "https://example.com/image$i.jpg" // Fake image URL
            )
            listings.add(listing)
        }
        return listings
    }
}