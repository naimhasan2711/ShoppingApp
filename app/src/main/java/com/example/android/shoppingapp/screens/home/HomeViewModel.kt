package com.example.android.shoppingapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingapp.data.ProductListCategory
import com.example.android.shoppingapp.data.models.ProductsItem
import com.example.android.shoppingapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenState(
    var isLoading: Boolean = false,
    var products: List<ProductsItem> = emptyList(),
    var productListCategory: ProductListCategory = ProductListCategory.AllCategories
)

/*
This viewmodel is responsible for fetching data from data source
like remote or local about product related data
like showing all product list,
product list category
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState get() = _homeScreenState.asStateFlow()

    fun getProducts(productListCategory: ProductListCategory) {
        repository.getProducts(viewModelScope, productListCategory) { products ->
            viewModelScope.launch {
                _homeScreenState.value = homeScreenState.value.copy(
                    products = products,
                    isLoading = false,
                    productListCategory = productListCategory
                )
            }
        }
    }

    fun updateProduct(
        product: ProductsItem,
        productListCategory: ProductListCategory
    ) = repository.updateProduct(viewModelScope, product) {
        getProducts(productListCategory = productListCategory)
    }

    init {
        _homeScreenState.value = homeScreenState.value.copy(isLoading = true)
        getProducts(productListCategory = ProductListCategory.AllCategories)
    }
}