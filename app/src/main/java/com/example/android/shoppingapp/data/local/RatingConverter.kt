package com.example.android.shoppingapp.data.local

import androidx.room.TypeConverter
import com.example.android.shoppingapp.data.models.Rating;
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken;

/*
As we cant store custome data into our localdb, we need to convert it
Like we have a Rating class which is not primitive, custom class
So we need to convert that class
This class convert Rating into Gson and vise verse
 */
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