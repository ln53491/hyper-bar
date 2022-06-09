package com.example.hyperbar.screens

import android.os.Handler
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography
import okhttp3.internal.wait

@Composable
fun PastOrdersScreen(
    viewModel: DataModel
) {

    BackHandler() {
        stateOfTheBox = BoxState.Collapsed
        Router.navigateTo(Screen.MainScreen)
    }
    tableBool.updateCameFromIntro(false)
    fromItemScreen = true
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val products: State<List<Product>> =
        viewModel.products.collectAsState(initial = emptyList())
    val orders: State<List<Order>> = viewModel.orders.collectAsState(initial = emptyList())
    val orderKeys: State<List<String>> = viewModel.keysOrders.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        for (order in orders.value) {
            if (order.done.toInt() != 0) {
                pastOrderOrders.add(order)
            }
        }
    }

    var completedOrders = mutableListOf<Order>()
    var completedOrdersShown = mutableListOf<Order>()
    for (order in pastOrderOrders) {
        for(order2 in orders.value){
            if(order2.orderId == order.orderId){
                completedOrders.add(order2)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
            PastOrdersTopBar()
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
                            OrderCardCustomer(
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
                            OrderCardCustomer(
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
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OrderCardCustomer(
    order: Order,
    products: State<List<Product>>,
    orderKeys: State<List<String>>,
    viewModel: DataModel,
    activated: Boolean
) {
    var buttonSize by remember { mutableStateOf(0) }
    var accepted by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
            .onGloballyPositioned { coordinates ->
                buttonSize = coordinates.size.height
            },
        backgroundColor =
        if (order.done.toInt() == 1) {
            Color(red = 225, green = 225, blue = 225)
        } else {
            if (order.waiterId.toInt() == 0) {
                Color.White
            } else {
                Color(red = 255, green = 245, blue = 120)
            }
        },
        shape = RoundedCornerShape(15.dp),
        elevation = 3.dp
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Order",
                        style = ArchivoTypography.h5,
                        color =
                        if (order.done.toInt() == 1) {
                            Color(red = 175, green = 175, blue = 175)
                        } else {
                            Color(red = 255, green = 140, blue = 0)
                        }
                    )

                    if (savedCurrencyState.first == "EUR") {
                        var price = order.totalPriceInEUR.toString().split(".")[1]
                        Text(
                            text = if (price.length == 1) {
                                "€" + order.totalPriceInEUR.toString() + "0"
                            } else {
                                "€" + order.totalPriceInEUR.toString()
                            },
                            style = ArchivoTypography.h1,
                            color =
                            if(order.done.toInt() == 1){
                                Color(red = 175, green = 175, blue = 175)
                            } else{
                                Color(red = 255, green = 140, blue = 0)
                            }
                        )
                    } else {
                        Text(
                            text = order.totalPriceInKN.toString() + " kn",
                            style = ArchivoTypography.h1,
                            color =
                            if(order.done.toInt() == 1){
                                Color(red = 175, green = 175, blue = 175)
                            } else{
                                Color(red = 255, green = 140, blue = 0)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color =
                    if (order.done.toInt() == 1) {
                        Color.White
                    } else {
                        Color(red = 200, green = 200, blue = 200)
                    },
                )

                Spacer(modifier = Modifier.height(5.dp))

                for (product in order.products) {
                    var productId = product.key.split("_").get(0).toLong()
                    var prodName = ""
                    for (prd in products.value) {
                        if (productId == prd.id) {
                            prodName = prd.name
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${product.value}x   " + prodName,
                            color =
                            if (order.done.toInt() == 1) {
                                Color(red = 175, green = 175, blue = 175)
                            } else {
                                Color.Black
                            },
                            style = ArchivoTypography.h1,
//                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    color =
                    if (order.done.toInt() == 1) {
                        Color.White
                    } else {
                        Color(red = 200, green = 200, blue = 200)
                    },
                )

                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier =
                    if (order.done.toInt() == 1) {
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val configuration = LocalConfiguration.current
                    val screenWidth = configuration.screenWidthDp.dp

                    if (order.done.toInt() == 1) {
                        Text(
                            color = Color(red = 175, green = 175, blue = 175),
                            text = "Order finished!",
                            style = ArchivoTypography.body2.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        if (order.waiterId.toInt() == 0) {
                            Text(
                                color = Color(red = 0, green = 0, blue = 0),
                                text = "Waiting for someone\nto take your order",
                                style = ArchivoTypography.body2.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                color = Color(red = 0, green = 0, blue = 0),
                                text = "Working on your order...",
                                style = ArchivoTypography.body2.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PastOrdersTopBar() {
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
                    stateOfTheBox = BoxState.Collapsed
                    Router.navigateTo(Screen.MainScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF555555)
                )
            }
            Text(
                text = "Past Orders",
                style = ArchivoTypography.h5
            )
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}