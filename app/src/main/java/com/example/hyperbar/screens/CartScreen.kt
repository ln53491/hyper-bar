package com.example.hyperbar.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hyperbar.R
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography
import okhttp3.internal.toImmutableMap
import kotlin.math.round
import kotlin.math.roundToInt

var sumEur = 0.00
var sumHrk = 0

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun CartScreen(
    orders: State<List<Order>>,
    viewModel: DataModel
) {
    BackHandler() {
        stateOfTheBox = BoxState.Collapsed
        Router.navigateTo(Screen.MainScreen)
    }
    tableBool.updateCameFromIntro(false)
    fromItemScreen = true
    cartBool = true

    val context = LocalContext.current

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var cartSizeBottom by remember { mutableStateOf(cartItems.size) }

    var tmpSumEur = 0.0
    var tmpSumHrk = 0
    for (element in cartItems) {
        tmpSumEur += element.item.priceEur * element.quantity
        tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
    }
    tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
    sumEur = tmpSumEur
    sumHrk = tmpSumHrk

    var sumEurRem by remember { mutableStateOf(sumEur) }
    var sumHrkRem by remember { mutableStateOf(sumHrk) }

    var triggerOrderLocal by remember { mutableStateOf(triggerOrder) }
    var triggerDone by remember { mutableStateOf(triggerDoneOuter) }

    var currentState by remember { mutableStateOf(stateOfTheBox) }
    val transition = updateTransition(currentState, label = "Box")

    val imageAlpha by transition.animateFloat(label = "alpha") { state ->
        when (state) {
            BoxState.Collapsed -> 0f
            BoxState.Expanded -> 1f
        }
    }

    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

//    val colorAnim by transition.animateColor(
//        transitionSpec = {
//            when {
//                BoxState.Expanded isTransitioningTo BoxState.Collapsed ->
//                    spring()
//                else ->
//                    tween(durationMillis = 500)
//            }
//        }, label = "color"
//    ) { state ->
//        when (state) {
//            BoxState.Collapsed -> MaterialTheme.colors.primary
//            BoxState.Expanded -> AndroidColor
//        }
//    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
            TopBarCart()
        },
        content = {

            var cartSize by remember { mutableStateOf(cartItems.size) }
            var cartList = remember { mutableStateListOf<CartItem>() }

//            if(triggerOrderLocal == false){
//
//            }

            LaunchedEffect(Unit){
                for (item in cartItems) {
                    cartList.add(item)
                }
            }

            if (cartSize == 0) {
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
                        text = "Looks like your cart is empty...",
                        style = ArchivoTypography.h3,
                        color = Color.Gray
                    )
                }
            }
            if (cartSize != 0) {
//                if(triggerOrderLocal == true) {
//                    cartList = remember { mutableStateListOf<CartItem>() }
//                }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
//                        .verticalScroll(rememberScrollState()),
                    ) {
                        itemsIndexed(cartList) {index, item ->
//                        ItemCartCard(item = item, cartList = cartList)
                            run {
                                var quantity by remember { mutableStateOf(item.quantity) }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                                        .height(75.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 3.dp
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            val selected3 = remember { mutableStateOf(false) }
                                            val scale3 = animateFloatAsState(if (selected3.value) 1.25f else 1f)
                                            IconButton(
                                                onClick = {
                                                },
                                                modifier = Modifier.size(25.dp).scale(scale3.value)
                                                    .pointerInteropFilter {
                                                        when (it.action) {
                                                            MotionEvent.ACTION_DOWN -> {
                                                                selected3.value = true
                                                            }

                                                            MotionEvent.ACTION_UP  -> {
                                                                selected3.value = false
                                                                for (element in cartList) {
                                                                    if (element.item == item.item) {
                                                                        cartList.remove(element)
                                                                        cartItems.remove(element)
                                                                        break
                                                                    }
                                                                }
                                                                var tmpSumEur = 0.0
                                                                var tmpSumHrk = 0
                                                                for (element in cartItems) {
                                                                    tmpSumEur += element.item.priceEur * element.quantity
                                                                    tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
                                                                }
                                                                tmpSumEur =
                                                                    (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                                                                sumEur = tmpSumEur
                                                                sumHrk = tmpSumHrk
                                                                sumEurRem = tmpSumEur
                                                                sumHrkRem = tmpSumHrk

                                                                if (cartItems.size == 0) {
                                                                    cartSize = 0
                                                                    cartSizeBottom = 0
                                                                }

                                                                cartBool = false}

                                                            MotionEvent.ACTION_CANCEL  -> {
                                                                selected3.value = false }
                                                        }
                                                        true
                                                    }
                                            ) {
                                                Card(
                                                    shape = CircleShape,
                                                    backgroundColor = Color.Red,
                                                    modifier = Modifier.size(25.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Close,
                                                        contentDescription = "Delete",
                                                        tint = Color.White
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))
                                            AsyncImage(
                                                model = ImageRequest.Builder(LocalContext.current)
                                                    .data(item.item.imageUrl)
                                                    .crossfade(true)
                                                    .build(),
                                                placeholder = painterResource(R.drawable.no_image),
                                                contentDescription = "Image",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .aspectRatio(1f)
                                                    .clip(shape = RoundedCornerShape(5.dp))
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .width(100.dp),
                                                contentAlignment = Alignment.CenterStart
                                            ) {
                                                Text(
                                                    text = item.item.name,
                                                    style = ArchivoTypography.h6.copy(fontSize = 14.sp),
                                                    color = Color.Black,
                                                )
                                            }

                                        }
                                    }
                                    Box(
                                        Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {

                                            val selected2 = remember { mutableStateOf(false) }
                                            val scale2 = animateFloatAsState(if (selected2.value) 0.8f else 1f)
                                            IconButton(
                                                onClick = {
                                                },
                                                modifier = Modifier.size(25.dp)
                                                    .scale(scale2.value)
                                                    .pointerInteropFilter {
                                                        when (it.action) {
                                                            MotionEvent.ACTION_DOWN -> {
                                                                selected2.value = true
                                                                if (quantity > 1) {
                                                                    quantity--
                                                                    item.quantity = quantity
                                                                }
                                                                var tmpSumEur = 0.0
                                                                var tmpSumHrk = 0
                                                                for (element in cartItems) {
                                                                    tmpSumEur += element.item.priceEur * element.quantity
                                                                    tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
                                                                }
                                                                tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
                                                                sumEur = tmpSumEur
                                                                sumHrk = tmpSumHrk
                                                                sumEurRem = tmpSumEur
                                                                sumHrkRem = tmpSumHrk}

                                                            MotionEvent.ACTION_UP  -> {
                                                                selected2.value = false }

                                                            MotionEvent.ACTION_CANCEL  -> {
                                                                selected2.value = false }
                                                        }
                                                        true
                                                    }
                                            ) {
                                                Card(
                                                    shape = CircleShape,
                                                    backgroundColor = Color(
                                                        red = 255,
                                                        green = 140,
                                                        blue = 0,
                                                        alpha = 225
                                                    ),
                                                    modifier = Modifier.size(25.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Remove,
                                                        contentDescription = "Remove",
                                                        tint = Color.White
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Text(
                                                text = quantity.toString(),
                                                style = ArchivoTypography.h6.copy(fontSize = 14.sp),
                                                color = Color.Black,
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            val selected = remember { mutableStateOf(false) }
                                            val scale = animateFloatAsState(if (selected.value) 0.8f else 1f)
                                            IconButton(
                                                onClick = {

                                                },
                                                modifier = Modifier
                                                    .size(25.dp)
                                                    .scale(scale.value)
                                                    .pointerInteropFilter {
                                                        when (it.action) {
                                                            MotionEvent.ACTION_DOWN -> {
                                                                selected.value = true
                                                                if (quantity < 9) {
                                                                    quantity++
                                                                    item.quantity = quantity
                                                                }
                                                                var tmpSumEur = 0.0
                                                                var tmpSumHrk = 0
                                                                for (element in cartItems) {
                                                                    tmpSumEur += element.item.priceEur * element.quantity
                                                                    tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
                                                                }
                                                                tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
                                                                sumEur = tmpSumEur
                                                                sumHrk = tmpSumHrk
                                                                sumEurRem = tmpSumEur
                                                                sumHrkRem = tmpSumHrk}

                                                            MotionEvent.ACTION_UP  -> {
                                                                selected.value = false }

                                                            MotionEvent.ACTION_CANCEL  -> {
                                                                selected.value = false }
                                                        }
                                                        true
                                                    }
                                            ) {
                                                Card(
                                                    shape = CircleShape,
                                                    backgroundColor = Color(
                                                        red = 255,
                                                        green = 140,
                                                        blue = 0,
                                                        alpha = 225
                                                    ),
                                                    modifier = Modifier.size(25.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Add,
                                                        contentDescription = "Add",
                                                        tint = Color.White
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (index == cartList.size - 1) {
                                Spacer(modifier = Modifier.height(105.dp))
                            }
                        }
                    }














                if (triggerOrderLocal == true) {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color(0x55777777))
                            .clickable(
                                indication = null, // disable ripple effect
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { }
                            ).alpha(imageAlpha),
                        contentAlignment = Alignment.Center
                    ) {
                        if(triggerDone == true){
                            CircularProgressIndicator(color = Color(red = 255, green = 140, blue = 0),
                            modifier = Modifier.size(80.dp))
                        } else {
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
                                            text = "Order now?",
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
                                                currentState = BoxState.Collapsed
                                                triggerOrder = false
                                                triggerOrderLocal = false
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
                                                    text = "No",
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
                                                triggerDone = true
                                                var hashmap = hashMapOf<String, Long>()

//                            var finalItems = mutableMapOf<String, Long>()
                                                for (item in cartItems) {
                                                    hashmap.put(
                                                        item.item.id.toString() + "_key",
                                                        item.quantity.toLong()
                                                    )
                                                }

                                                var newOrderId = 0
                                                var allOrderIds = mutableListOf<Long>()
                                                for (order in orders.value) {
                                                    allOrderIds.add(order.orderId)
                                                }
                                                if (allOrderIds.size == 0) {
                                                    newOrderId = 1
                                                } else {
                                                    allOrderIds.sort()
                                                    newOrderId =
                                                        allOrderIds[allOrderIds.size - 1].toInt() + 1
                                                }

//                            var finalMap = finalItems.toImmutableMap()
//                            var finalMapNew = HashMap(finalMap)

                                                var newOrder = Order(
                                                    done = 0,
                                                    orderId = newOrderId.toLong(),
                                                    products = hashmap,
                                                    tableNumber = tableBool.tableNum.toLong(),
                                                    totalPriceInEUR = sumEur,
                                                    totalPriceInKN = sumHrk.toLong(),
                                                    waiterId = 0
                                                )
                                                viewModel.addOrder(newOrder)
                                                sessionOrders.add(newOrderId.toLong())
                                                cartItems = mutableListOf()

                                                pastOrderOrders.add(newOrder)

                                                ordered = true

//                                            var progress by remember { mutableStateOf(0.1f) }
//                                            val animatedProgress = animateFloatAsState(
//                                                targetValue = progress,
//                                                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
//                                            ).value


                                                Handler().postDelayed({
                                                    for (or in orders.value) {
                                                        if (or.orderId.toInt() == newOrder.orderId.toInt()) {
                                                            Log.d(
                                                                ContentValues.TAG,
                                                                or.orderId.toString() + " " + newOrderId.toString()
                                                            )
                                                            orderSuccessful = true
                                                        }
                                                    }
                                                    triggerOrder = false
                                                    triggerOrderLocal = false
                                                    triggerDone = false
                                                    currentState = BoxState.Collapsed
                                                    stateOfTheBox = BoxState.Collapsed
                                                    Router.navigateTo(Screen.MainScreen)
                                                }, 1000)
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
                                                    text = "Yes",
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
            }
        },
        bottomBar = {
            if (cartSizeBottom != 0) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val configuration = LocalConfiguration.current
                        val screenWidth = configuration.screenWidthDp.dp
                        var cartList = remember { mutableStateListOf<CartItem>() }
                        for (item in cartItems) {
                            cartList.add(item)
                        }

                        Card(
                            modifier = Modifier
                                .width((screenWidth - 45.dp) * 2 / 3)
                                .height(50.dp),
                            shape = RoundedCornerShape(15.dp),
                            elevation = 3.dp,
//                            backgroundColor = Color(0x55777777)
                        ) {
                            Box(
                                modifier = if (triggerOrderLocal == false) {
                                    Modifier
                                        .fillMaxSize()
                                } else {
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color(0x55777777))
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 15.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween

                                ) {
                                    Text(
                                        text = "Total: ",
                                        style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                    if (savedCurrencyState.first == "EUR") {
                                        var price = sumEurRem.toString().split(".")[1]
                                        Text(
                                            text = if (price.length == 1) {
                                                "€$sumEurRem" + "0"
                                            } else {
                                                "€$sumEurRem"
                                            },
                                            style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    if (savedCurrencyState.first == "HRK") {
                                        Text(
                                            text = "$sumHrkRem kn",
                                            style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                        Card(
                            modifier = Modifier
                                .width((screenWidth - 45.dp) * 1 / 3)
                                .height(50.dp),
                            shape = RoundedCornerShape(15.dp),
                            elevation = 3.dp,
                            backgroundColor = if(triggerOrderLocal == false){
                                                                            Color(0xFF22CC22)
                                                                            } else{
                                                                                  Color(0xff22aa22)
                                                                                  },
                            onClick = {

                                currentState = BoxState.Expanded
                                triggerOrder = true
                                triggerOrderLocal = true



//                                val notificationId = 0
//                                val myBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_icon_bw)
//                                largeTextWithBigIconNotification(
//                                    context,
//                                    "Hyper Bar",
//                                    notificationId,
//                                    "Order Successful!",
//                                    "Your order has been sent to our personnel. We will notify you when it's ready.",
//                                    myBitmap
//                                )
//                            }, 1000)
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Text(
                                    text = "Order",
                                    style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                    color = if(triggerOrderLocal == true){
                                                                         Color(0xffcccccc)
                                                                         } else{
                                                                               Color.White
                                                                               },
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


enum class BoxState {
    Collapsed,
    Expanded
}

//@Composable
//fun CircularProgressIndicatorSample() {
//    var progress by remember { mutableStateOf(0.1f) }
//    val animatedProgress = animateFloatAsState(
//        targetValue = progress,
//        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
//    ).value
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Spacer(Modifier.height(30.dp))
////        Text("CircularProgressIndicator with undefined progress")
//        CircularProgressIndicator()
//        Spacer(Modifier.height(30.dp))
////        Text("CircularProgressIndicator with progress set by buttons")
//        CircularProgressIndicator(progress = animatedProgress)
//        Spacer(Modifier.height(30.dp))
//        OutlinedButton(
//            onClick = {
//                if (progress < 1f) progress += 0.1f
//            }
//        ) {
//            Text("Increase")
//        }
//
//        OutlinedButton(
//            onClick = {
//                if (progress > 0f) progress -= 0.1f
//            }
//        ) {
//            Text("Decrease")
//        }
//    }
//}


@Composable
fun ItemCartCard(
    item: CartItem,
    cartList: MutableList<CartItem>
) {
    var quantity by remember { mutableStateOf(item.quantity) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            .height(75.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 3.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        for (element in cartList) {
                            if (element.item == item.item) {
                                cartList.remove(element)
                                cartItems.remove(element)
                                break
                            }
                        }
                        var tmpSumEur = 0.0
                        var tmpSumHrk = 0
                        for (element in cartItems) {
                            tmpSumEur += element.item.priceEur * element.quantity
                            tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
                        }
                        tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                        sumEur = tmpSumEur
                        sumHrk = tmpSumHrk
//                        Log.d(ContentValues.TAG, quantity.toString())
                    },
                    modifier = Modifier.size(25.dp)
                ) {
                    Card(
                        shape = CircleShape,
                        backgroundColor = Color.Red,
                        modifier = Modifier.size(25.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.item.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.no_image),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(5.dp))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = item.item.name,
                        style = ArchivoTypography.h6.copy(fontSize = 14.sp),
                        color = Color.Black,
                    )
                }

            }
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (quantity > 1) {
                            quantity--
                            item.quantity = quantity
                        }
                        var tmpSumEur = 0.0
                        var tmpSumHrk = 0
                        for (element in cartItems) {
                            tmpSumEur += element.item.priceEur * element.quantity
                            tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
                        }
                        tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                        sumEur = tmpSumEur
                        sumHrk = tmpSumHrk
                    },
                    modifier = Modifier.size(25.dp)
                ) {
                    Card(
                        shape = CircleShape,
                        backgroundColor = Color(red = 255, green = 140, blue = 0, alpha = 225),
                        modifier = Modifier.size(25.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Remove",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = quantity.toString(),
                    style = ArchivoTypography.h6.copy(fontSize = 14.sp),
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = {
                        if (quantity < 9) {
                            quantity++
                            item.quantity = quantity
                        }
                        var tmpSumEur = 0.0
                        var tmpSumHrk = 0
                        for (element in cartItems) {
                            tmpSumEur += element.item.priceEur * element.quantity
                            tmpSumHrk += element.item.priceHrk.toInt() * element.quantity.toInt()
                        }
                        tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                        sumEur = tmpSumEur
                        sumHrk = tmpSumHrk
                    },
                    modifier = Modifier.size(25.dp)
                ) {
                    Card(
                        shape = CircleShape,
                        backgroundColor = Color(red = 255, green = 140, blue = 0, alpha = 225),
                        modifier = Modifier.size(25.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TopBarCart() {
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
                text = "Cart Items",
                style = ArchivoTypography.h5
            )
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}