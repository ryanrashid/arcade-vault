package com.example.arcadevault.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "listings")
@TypeConverters(Converters::class) // Add this annotation on ListingEntity to apply the TypeConverter to this class
data class ListingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val price: Double?,
    val images: List<Uri>? // This will be converted to/from JSON using the TypeConverter
)
