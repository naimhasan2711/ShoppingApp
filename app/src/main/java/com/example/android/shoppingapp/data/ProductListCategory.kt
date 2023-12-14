package com.example.android.shoppingapp.data

sealed interface ProductListCategory {
    object AllCategories : ProductListCategory
    object Electronics : ProductListCategory
    object Jewelery : ProductListCategory
    object MenClothing : ProductListCategory
    object WomenClothing : ProductListCategory
}