package com.example.android.shoppingapp.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingapp.data.models.ProductsItem
import com.example.android.shoppingapp.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailScreenState(var product: ProductsItem? = null)

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    var detailScreenState = mutableStateOf(DetailScreenState())
        private set

    fun getProductById(productId: Int) = repository.getProductById(productId, viewModelScope) {
        viewModelScope.launch(Dispatchers.Main) {
            detailScreenState.value = detailScreenState.value.copy(product = it)
        }
    }

    fun updateProduct(product: ProductsItem) {
        repository.updateProduct(viewModelScope, product) {
            getProductById(product.id)
        }
    }
}