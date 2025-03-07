package com.example.arcadevault.data

import android.net.Uri
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import androidx.core.net.toUri

class Converters {

    // Convert List<Uri>? to JSON String
    @TypeConverter
    fun fromUriList(uriList: List<Uri>?): String {
        if (uriList == null) return "" // Return an empty string for null values
        Log.d("Converters", "Converting Uri list to JSON: ${uriList.joinToString()}")
        val uriStrings = uriList.map { it.toString() } // Convert each Uri to a String
        return Gson().toJson(uriStrings)
    }


    // Convert JSON String to List<Uri>?
    @TypeConverter
    fun toUriList(uriListString: String?): List<Uri>? {
        if (uriListString.isNullOrEmpty()) return null // Return null for empty or null strings
        Log.d("Converters", "Converting JSON to Uri list: $uriListString")
        val uriStrings = Gson().fromJson(uriListString, Array<String>::class.java)
        return uriStrings?.map { it.toUri() } // Convert each string to a Uri object
    }

}


