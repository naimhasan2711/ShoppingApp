package com.example.android.shoppingapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.android.shoppingapp.data.local.OrderDao
import com.example.android.shoppingapp.data.models.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderRepository(private val dao: OrderDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allOrders = MutableLiveData<List<Order>>()
    fun getAllOrders() {
        coroutineScope.launch(Dispatchers.IO) {
            allOrders.postValue(dao.getAllOrders())
        }
    }

    fun addOrder(order: Order, completion: () -> Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.insertOrder(order)
            completion()
        }
    }
}