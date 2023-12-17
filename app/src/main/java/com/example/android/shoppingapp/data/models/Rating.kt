package com.example.android.shoppingapp.data.models


import com.google.gson.annotations.SerializedName


//model class for Rating class which is a sub class fo product-items
data class Rating(
    @SerializedName("count")
    val count: Int,
    @SerializedName("rate")
    val rate: Double
)