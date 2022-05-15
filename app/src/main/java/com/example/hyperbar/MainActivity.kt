package com.example.hyperbar

import Repository
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
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

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel : DataModel = getViewModel()
            val categories: State<List<CategoryNew>> =
                viewModel.categories.collectAsState(initial = emptyList())
            val todaysSelection: State<List<TodaysSelection>> =
                viewModel.todaysSelection.collectAsState(initial = emptyList())
            val products: State<List<Product>> =
                viewModel.products.collectAsState(initial = emptyList())
            val orders: State<List<Order>> = viewModel.orders.collectAsState(initial = emptyList())

            val images = products.value.map { it.id to
                    rememberAsyncImagePainter(it.imageUrl)
            }.toMap()

            Surface(color = MaterialTheme.colors.background) {
                when (Router.currentScreen) {
                    Screen.IntroScreen -> IntroScreen()
                    Screen.TestScreen -> TestScreen()
                    Screen.MainScreen -> MainScreen(categories, todaysSelection, products, orders, images)
                    Screen.SettingsScreen -> SettingsScreen()
                    Screen.CartScreen -> CartScreen(orders, viewModel)
                    Screen.ItemScreen -> ItemScreen(categories, products)
                }
            }
        }
    }
}