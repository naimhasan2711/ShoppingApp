package com.example.android.shoppingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.shoppingapp.data.models.Order
import com.example.android.shoppingapp.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
This view model is implemented for inserting and fetching data
for order history related data in the local database
 */
@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    val orderList: LiveData<List<Order>> = orderRepository.allOrders

    init {
        getAllOrders()
    }

    private fun getAllOrders() {
        orderRepository.getAllOrders()
    }

    fun addOrder(order: Order) {
        orderRepository.addOrder(order, completion = {
            getAllOrders()
        })
    }
}