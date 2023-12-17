package com.example.android.shoppingapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//model class for order
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    @SerializedName("id")
    val id: String = "",
    @SerializedName("item")
    var items: ArrayList<String> = ArrayList(),
    @SerializedName("total_amount")
    val total_amount: Double= 0.0,
    @SerializedName("order_date_time")
    val order_date_time: Long = 0L
)