package com.example.android.shoppingapp.screens.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingapp.data.ProductListCategory
import com.example.android.shoppingapp.data.models.ProductsItem
import com.example.android.shoppingapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartScreenState(
    var isLoading: Boolean = true,
    var allProducts: List<ProductsItem> = emptyList(),
)

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: ProductsRepository) : ViewModel() {

    var cartScreenState = mutableStateOf(CartScreenState())
        private set

    fun getProducts() {
        cartScreenState.value = cartScreenState.value.copy(isLoading = true)
        repository.getProducts(viewModelScope, ProductListCategory.AllCategories) { products ->
            viewModelScope.launch(Dispatchers.Main) {
                cartScreenState.value =
                    cartScreenState.value.copy(isLoading = false, allProducts = products)
            }
        }
    }

    fun updateProduct(product: ProductsItem) {
        repository.updateProduct(viewModelScope, product = product) {
            getProducts()
        }
    }

    init {
        getProducts()
    }
}