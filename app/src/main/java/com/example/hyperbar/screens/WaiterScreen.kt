package com.example.hyperbar.screens

import android.app.Activity
import android.content.ContentValues
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toIntRect
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun WaiterScreen(
    orders: State<List<Order>>,
    products: State<List<Product>>,
    waiters: State<List<Waiter>>,
    orderKeys: State<List<String>>,
    viewModel: DataModel
) {
    val activity = (LocalContext.current as? Activity)
    BackHandler() {
        activity?.finish()
    }

    var visibility by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    val focusRequester = remember { FocusRequester() }
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    var ordersLocal = mutableListOf<Order>()
    for(order in orders.value){
        if(order.waiterId.toInt() == 0){
            ordersLocal.add(order)
        }
    }
    var ordersAlreadyShown = mutableListOf<Order>()
    ordersAlreadyShown = emptyList<Order>().toMutableList()

    var waiterLogoutLocal by remember { mutableStateOf(waiterLogout) }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
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
//                            waiterLogout = true
//                            waiterLogoutLocal = true
                            waiterFlag = false
                            Router.navigateTo(Screen.TestScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color(0xFF555555)
                        )
                    }
                    Text(
                        text = "HYPER BAR",
                        style = ArchivoTypography.h1
                    )
                    IconButton(
                        onClick = {
                            Router.navigateTo(Screen.HistoryScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Active",
                            tint = Color(0xFF555555)
                        )
                    }
                }
            }
        },
        content = {
            if (ordersLocal.size == 0) {
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
                        text = "Looks like there are no orders...",
                        style = ArchivoTypography.h3,
                        color = Color.Gray
                    )
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(15.dp))
                        for (order in ordersLocal) {
                            if (order !in ordersAlreadyShown) {
                                OrderCard(order = order, products, orderKeys, viewModel, false)
                                ordersAlreadyShown.add(order)
                            }
                        }
                    }
                }
                if (waiterLogoutLocal == true) {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color(0x55888888)).clickable(
                                indication = null, // disable ripple effect
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { }
                            ),

                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(245.dp)
                                .height(170.dp),
                            shape = RoundedCornerShape(15.dp),
                            elevation = 10.dp,
                            backgroundColor = Color.White
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.height(100.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Logout?",
                                        style = ArchivoTypography.h5
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(40.dp)
                                            .background(Color.Transparent),
                                        onClick = {
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                0xFFFF2222
                                            )
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                color = Color.White,
                                                text = "Yes",
                                                style = ArchivoTypography.body2.copy(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                    }
                                    Button(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(40.dp)
                                            .background(Color.Transparent),
                                        onClick = {
                                            waiterLogout = false
                                            waiterLogoutLocal = false
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                0xFF22CC22
                                            )
                                        )
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                color = Color.White,
                                                text = "No",
                                                style = ArchivoTypography.body2.copy(
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OrderCard(
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
                if(order.done.toInt() == 1){
                    Color(red = 225, green = 225, blue = 225)
                } else{
                    Color.White
                },
        shape = RoundedCornerShape(15.dp),
        elevation = 3.dp
    ) {
        if (accepted == false || activated == true) {
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clickable {
                        if(activated == false){
                            accepted = true
                        }
                    },
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
                            text = "Table: " + order.tableNumber.toString(),
                            style = ArchivoTypography.h5,
                            color =
                                if(order.done.toInt() == 1){
                                    Color(red = 175, green = 175, blue = 175)
                                } else{
                                    Color(red = 255, green = 140, blue = 0)
                                }
                        )
                        Text(
                            text = "ID",
                            style = ArchivoTypography.h1,
                            color =
                            if(order.done.toInt() == 1){
                                Color(red = 175, green = 175, blue = 175)
                            } else{
                                Color.Black
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color =
                        if(order.done.toInt() == 1){
                            Color.White
                        } else{
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
                                if(order.done.toInt() == 1){
                                    Color(red = 175, green = 175, blue = 175)
                                } else{
                                    Color.Black
                                },
                                style = ArchivoTypography.h1,
//                            modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "$productId",
                                style = ArchivoTypography.h1,
//                            modifier = Modifier.fillMaxWidth()
                                color =
                                if(order.done.toInt() == 1){
                                    Color(red = 175, green = 175, blue = 175)
                                } else{
                                    Color(red = 255, green = 140, blue = 0)
                                }
                            )
                        }
                    }

                    if(activated == true){
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier =
                                if(order.done.toInt() == 1){
                                    Modifier.fillMaxWidth().height(40.dp)
                                } else{
                                    Modifier.fillMaxWidth()
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            val configuration = LocalConfiguration.current
                            val screenWidth = configuration.screenWidthDp.dp

                            if(order.done.toInt() == 1){
                                Text(
                                    color = Color(red = 175, green = 175, blue = 175),
                                    text = "FINISHED",
                                    style = ArchivoTypography.body2.copy(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            } else{

                                val selected = remember { mutableStateOf(false) }
                                val scale = animateFloatAsState(if (selected.value) 0.9f else 1f)

                                val selected2 = remember { mutableStateOf(false) }
                                val scale2 = animateFloatAsState(if (selected2.value) 0.9f else 1f)

                                Button(
                                    modifier = Modifier
                                        .width((screenWidth - 75.dp)/2)
                                        .height(40.dp)
                                        .background(Color.Transparent)
                                        .scale(scale.value)
                                        .pointerInteropFilter {
                                            when (it.action) {
                                                MotionEvent.ACTION_DOWN -> {
                                                    selected.value = true
                                                }

                                                MotionEvent.ACTION_UP  -> {
                                                    selected.value = false
                                                    Handler().postDelayed({
                                                        var key = ""
                                                        for(orderKey in orderKeys.value){
                                                            var splitted = orderKey.split("ĐĐĐ")
                                                            if(splitted[1].toLong() == order.orderId){
                                                                key = splitted[0]
                                                            }
                                                        }
                                                        if(key != ""){
                                                            viewModel.updateOrderDone(key)
                                                        }
                                                    }, 100)
                                                }

                                                MotionEvent.ACTION_CANCEL  -> {
                                                    selected.value = false }
                                            }
                                            true
                                        },
                                    onClick = {

                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF22CC22))
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            color = Color.White,
                                            text = "Done",
                                            style = ArchivoTypography.body2.copy(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                                Button(
                                    modifier = Modifier
                                        .width((screenWidth - 75.dp)/2)
                                        .height(40.dp)
                                        .background(Color.Transparent)
                                        .scale(scale2.value)
                                        .pointerInteropFilter {
                                            when (it.action) {
                                                MotionEvent.ACTION_DOWN -> {
                                                    selected2.value = true
                                                }

                                                MotionEvent.ACTION_UP  -> {
                                                    selected2.value = false
                                                    Handler().postDelayed({
                                                        var key = ""
                                                        for(orderKey in orderKeys.value){
                                                            var splitted = orderKey.split("ĐĐĐ")
                                                            if(splitted[1].toLong() == order.orderId){
                                                                key = splitted[0]
                                                            }
                                                        }
                                                        if(key != ""){
                                                            viewModel.updateOrderWaiterId(key, 0.toLong())
                                                        }
                                                        accepted = false
                                                    }, 100)
                                                }

                                                MotionEvent.ACTION_CANCEL  -> {
                                                    selected2.value = false }
                                            }
                                            true
                                        },
                                    onClick = {

                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF2222))
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            color = Color.White,
                                            text = "Remove",
                                            style = ArchivoTypography.body2.copy(
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {

            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0xFF22CC22)),
                contentPadding = PaddingValues(0.dp, 0.dp),
                modifier = Modifier
                    .padding(0.dp)
                    .height(with(LocalDensity.current) {
                        buttonSize.toDp()
                    }),

                ) {
                Button(
                    onClick = {
                        var key = ""
                        for(orderKey in orderKeys.value){
                            var splitted = orderKey.split("ĐĐĐ")
                            if(splitted[1].toLong() == order.orderId){
                                key = splitted[0]
                            }
                        }
                        if(key != ""){
                            viewModel.updateOrderWaiterId(key, waiterId.toLong())
                        }
                        accepted = false
                        Router.navigateTo(Screen.HistoryScreen)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF22CC22)
                    ),
                    modifier = Modifier.fillMaxHeight(),
                    shape = RoundedCornerShape(0),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Confirm",
                            color = Color.White,
                            style = ArchivoTypography.h5,
                            modifier = Modifier.padding(0.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
                VerticalDivider()
                Button(
                    onClick = {
                        accepted = false
                    },
                    modifier = Modifier.fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFFF2222)
                    ),
                    shape = RoundedCornerShape(0),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.White,
                            style = ArchivoTypography.h5,
                            modifier = Modifier.padding(0.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}