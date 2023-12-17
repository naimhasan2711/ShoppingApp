package com.example.android.shoppingapp.data

//a sealed class containing list of category
sealed interface ProductListCategory {
    object AllCategories : ProductListCategory
    object Electronics : ProductListCategory
    object Jewelery : ProductListCategory
    object MenClothing : ProductListCategory
    object WomenClothing : ProductListCategory
}