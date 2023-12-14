package com.example.android.shoppingapp.data.local

import androidx.room.TypeConverter
import com.example.android.shoppingapp.data.models.Rating;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken;

class RatingConverter {
    @TypeConverter
    fun fromRating(rating: Rating): String {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRating(json: String): Rating {
        val type = object : TypeToken<Rating>() {}.type
        return Gson().fromJson(json, type)
    }

}