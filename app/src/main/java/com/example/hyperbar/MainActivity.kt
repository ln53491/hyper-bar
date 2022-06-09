package com.example.hyperbar

import Repository
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.*
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.hyperbar.data.*
import com.example.hyperbar.screens.*
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import org.koin.core.context.startKoin
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
//    private lateinit var worker: WorkManager

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: DataModel = getViewModel()
            val categories: State<List<CategoryNew>> =
                viewModel.categories.collectAsState(initial = emptyList())
            val todaysSelection: State<List<TodaysSelection>> =
                viewModel.todaysSelection.collectAsState(initial = emptyList())
            val products: State<List<Product>> =
                viewModel.products.collectAsState(initial = emptyList())
            val orders: State<List<Order>> = viewModel.orders.collectAsState(initial = emptyList())
            val tables: State<List<Table>> = viewModel.tables.collectAsState(initial = emptyList())
            val waiters: State<List<Waiter>> =
                viewModel.waiters.collectAsState(initial = emptyList())
            val orderKeys: State<List<String>> =
                viewModel.keysOrders.collectAsState(initial = emptyList())

            val images = products.value.map {
                it.id to
                        rememberAsyncImagePainter(it.imageUrl)
            }.toMap()

            if (waiterFlag == false) {
                var sessionOrdersLocal by remember { mutableStateOf(sessionOrders) }
                var sessionOrdersDoneLocal by remember { mutableStateOf(sessionOrdersDone) }
                for (order in orders.value) {
                    if (order.orderId in sessionOrdersLocal && order.waiterId.toInt() != 0) {
                        sessionOrders.remove(order.orderId)
                    }
                    if (order.orderId in sessionOrdersDoneLocal && order.done.toInt() == 1) {
                        sessionOrdersDone.remove(order.orderId)
                    }
                }
            }

            Crossfade(targetState = Router.currentScreen) { state ->
                when (state) {
                    Screen.IntroScreen -> IntroScreen()
                    Screen.TestScreen -> TestScreen(waiters, tables)
                    Screen.MainScreen -> MainScreen(
                        categories,
                        todaysSelection,
                        products,
                        orders,
                        images,
                        viewModel
                    )
                    Screen.SettingsScreen -> SettingsScreen()
                    Screen.CartScreen -> CartScreen(orders, viewModel)
                    Screen.ItemScreen -> ItemScreen(categories, products)
                    Screen.WaiterScreen -> WaiterScreen(
                        orders,
                        products,
                        waiters,
                        orderKeys,
                        viewModel
                    )
                    Screen.HistoryScreen -> HistoryScreen(
                        orders,
                        waiters,
                        products,
                        orderKeys,
                        viewModel
                    )
                    Screen.PastOrdersScreen -> PastOrdersScreen(viewModel)
                }
            }
        }
    }
}