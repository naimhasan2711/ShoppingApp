package com.example.android.shoppingapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.shoppingapp.data.models.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAllOrders(): List<Order>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order)
}