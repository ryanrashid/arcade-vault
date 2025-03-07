package com.example.arcadevault

import com.example.arcadevault.data.ListingEntity
import androidx.core.net.toUri

object Utils {

    fun generateFakeListings(count: Int): List<ListingEntity> {
        val listings = mutableListOf<ListingEntity>()
        for (i in 1..count) {
            val listing = ListingEntity(
                title = "Prize $i",
                price = (10..50).random().toDouble(), // Random price between 10 and 50
                images = listOf("https://en.wikipedia.org/wiki/File:Example.jpg".toUri())
            )
            listings.add(listing)
        }
        return listings
    }
}