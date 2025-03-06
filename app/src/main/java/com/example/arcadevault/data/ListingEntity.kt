package com.example.arcadevault.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listings") // Table name will be 'listings'
data class ListingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // Automatically generated ID for each listing
    val title: String?,
    val price: Double?,
    val image: String?  // Assuming you're storing the image as a URL or base64 encoded string
)
