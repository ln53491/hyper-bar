package com.example.hyperbar.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography
import kotlinx.coroutines.delay


@Composable
fun HistoryScreen(
    orders: State<List<Order>>,
    waiters: State<List<Waiter>>,
    products: State<List<Product>>,
    orderKeys: State<List<String>>,
    viewModel: DataModel
) {
    val activity = (LocalContext.current as? Activity)
    BackHandler() {
        Router.navigateTo(Screen.WaiterScreen)
    }

    var visibility by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    val focusRequester = remember { FocusRequester() }
    val scaffoldState: ScaffoldState = rememberScaffoldState()


    var ordersLocal = mutableListOf<Order>()
    var completedOrders = mutableListOf<Order>()
    var completedOrdersShown = mutableListOf<Order>()
    ordersLocal = orders.value.toMutableList()
    for (order in ordersLocal) {
        if (order.waiterId.toInt() != 0 && order.waiterId.toInt() == waiterId) {
            completedOrders.add(order)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
            HistoryBottomScreen()
        },
        content = {
            if (completedOrders.size == 0) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Oh, no!",
                        style = ArchivoTypography.h3,
                        color = Color.Gray
                    )
                    Text(
                        text = "Looks like there are no active orders...",
                        style = ArchivoTypography.h3,
                        color = Color.Gray
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    for (order in completedOrders) {
                        if (order !in completedOrdersShown && order.done.toInt() == 0) {
                            OrderCard(
                                order = order,
                                products,
                                orderKeys,
                                viewModel,
                                activated = true
                            )
                            completedOrdersShown.add(order)
                        }
                    }
                    for (order in completedOrders) {
                        if (order !in completedOrdersShown && order.done.toInt() == 1) {
                            OrderCard(
                                order = order,
                                products,
                                orderKeys,
                                viewModel,
                                activated = true
                            )
                            completedOrdersShown.add(order)
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun HistoryBottomScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(red = 255, green = 140, blue = 0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    Router.navigateTo(Screen.WaiterScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF555555)
                )
            }
            Text(
                text = "Active",
                style = ArchivoTypography.h5
            )
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}