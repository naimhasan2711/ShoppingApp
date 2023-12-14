package com.example.android.shoppingapp.data.local

import androidx.room.*
import com.example.android.shoppingapp.data.models.ProductsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product Where id=:productId")
    suspend fun getProductById(productId: Int): ProductsItem

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<ProductsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductsItem>)

    @Delete
    suspend fun deleteProduct(product: ProductsItem)

}