package com.example.android.shoppingapp.data.remote.api

import com.example.android.shoppingapp.data.models.ProductsItem
import retrofit2.Call
import retrofit2.http.GET


//this is an interface for fetching data from the remote server, in our case fakeapi
interface ShoppingApi {
    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }

    @GET("products")
    fun getProducts(): Call<List<ProductsItem>?>
}