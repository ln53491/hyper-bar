package com.example.hyperbar.data

import Repository
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DataModel(val repository : Repository) : ViewModel() {

    var categories : Flow<List<CategoryNew>> = MutableStateFlow(emptyList())
    var todaysSelection : Flow<List<TodaysSelection>> = MutableStateFlow(emptyList())
    var products : Flow<List<Product>> = MutableStateFlow(emptyList())
    var orders : Flow<List<Order>> = MutableStateFlow(emptyList())
    var waiters : Flow<List<Waiter>> = MutableStateFlow(emptyList())
    var tables : Flow<List<Table>> = MutableStateFlow(emptyList())
    var keysOrders : Flow<List<String>> = MutableStateFlow(emptyList())

    fun addOrder(order: Order) = repository.addOrder(order)
    fun updateOrderWaiterId(orderKey: String, waiterId: Long) = repository.updateOrderWaiterId(orderKey, waiterId)
    fun updateOrderDone(orderKey: String) = repository.updateOrderDone(orderKey)

    init {
        viewModelScope.launch {
            categories = repository.getCategories()
            todaysSelection = repository.getTodaysSelection()
            products = repository.getProducts()
            orders = repository.getOrders()
            waiters = repository.getWaiters()
            tables = repository.getTables()
            keysOrders = repository.getOrdersKeys()
        }
    }
}