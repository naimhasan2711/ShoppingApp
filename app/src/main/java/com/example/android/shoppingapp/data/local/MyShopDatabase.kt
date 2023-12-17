package com.example.android.shoppingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.shoppingapp.data.models.ProductsItem

//This is an abstract database class for product related local database
@Database(entities = [ProductsItem::class], version = 3)
@TypeConverters(RatingConverter::class)
abstract class MyShopDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
}