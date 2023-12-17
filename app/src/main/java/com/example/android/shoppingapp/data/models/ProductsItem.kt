package com.example.android.shoppingapp.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//model class for single product item
@Entity(tableName = "product")
data class ProductsItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("title")
    val title: String,
    var addedToCart: Boolean = false,
)