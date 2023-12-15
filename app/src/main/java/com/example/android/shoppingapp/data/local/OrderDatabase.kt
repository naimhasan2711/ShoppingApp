package com.example.android.shoppingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.shoppingapp.data.models.Order

@Database(entities = [Order::class], version = 1)
@TypeConverters(OrderConverter::class)
abstract class OrderDatabase:RoomDatabase() {
    abstract fun orderDAO(): OrderDao
}