package com.example.hyperbar.screens

import android.content.ContentValues
import android.os.Handler
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartScreen(
    orders: State<List<Order>>,
    viewModel: DataModel
){
    BackHandler() {
        Router.navigateTo(Screen.MainScreen)
    }
    tableBool.updateCameFromIntro(false)
    fromItemScreen = true
    cartBool = true

    val focusRequester = remember { FocusRequester() }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var cartSizeBottom by remember { mutableStateOf(cartItems.size) }

//    for(element in cartItems){
//        for(el2 in allPrices){
//            if(element.item.id == el2.id){
//                sumEur = sumEur + el2.eurPrice*element.quantity
//            }
//            if(element.item.id == el2.id){
//                sumHrk = sumHrk + el2.hrkPrice*element.quantity
//            }
//        }
//    }
//    sumEur = (sumEur * 100.0).roundToInt() / 100.0
//    sumHrk = (sumHrk * 100.0).roundToInt() / 100.0

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

    var sumEurRem by remember{ mutableStateOf(sumEur) }
    var sumHrkRem by remember{ mutableStateOf(sumHrk) }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
            TopBarCart()
        },
        content = {

            var cartSize by remember { mutableStateOf(cartItems.size) }
            var cartList = remember { mutableStateListOf<CartItem>() }

            for(item in cartItems){
                cartList.add(item)
            }

            if(cartSize == 0){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
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
            if(cartSize != 0){

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
//                        .verticalScroll(rememberScrollState()),
                ){
                    itemsIndexed(cartList) { index, item ->
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
                                                sumEurRem = tmpSumEur
                                                sumHrkRem = tmpSumHrk

                                                if(cartItems.size == 0){
                                                    cartSize = 0
                                                    cartSizeBottom = 0
                                                }

                                                cartBool = false
//                                                cartSize = cartItems.size
//                                                cartSizeBottom = cartItems.size

//                                                Log.d(ContentValues.TAG, cartSize.toString() + " " + cartSizeBottom.toString())
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
                                                sumEurRem = tmpSumEur
                                                sumHrkRem = tmpSumHrk

//                                                cartSize = cartItems.size
                                            },
                                            modifier = Modifier.size(25.dp)
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
                                                sumEurRem = tmpSumEur
                                                sumHrkRem = tmpSumHrk

//                                                cartSize = cartItems.size
                                            },
                                            modifier = Modifier.size(25.dp)
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
                    }
                }
            }
        },
        bottomBar = {
            if(cartSizeBottom != 0){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    val configuration = LocalConfiguration.current
                    val screenWidth = configuration.screenWidthDp.dp
                    var cartList = remember { mutableStateListOf<CartItem>() }
                    for(item in cartItems){
                        cartList.add(item)
                    }

                    Card(
                        modifier = Modifier
                            .width((screenWidth - 45.dp)*2/3)
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 3.dp,
                        backgroundColor = Color.White
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxSize().padding(horizontal = 15.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ){
                            Text(
                                text = "Total: ",
                                style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            if(savedCurrencyState.first == "EUR"){
                                Text(
                                    text = "€$sumEurRem" + "0",
                                    style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                            if(savedCurrencyState.first == "HRK"){
                                Text(
                                    text = "$sumHrkRem kn",
                                    style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .width((screenWidth - 45.dp)*1/3)
                            .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 3.dp,
                        backgroundColor = Color(0xFF22CC22),
                        onClick = {

                            var hashmap = hashMapOf<String,Long>()

//                            var finalItems = mutableMapOf<String, Long>()
                            for(item in cartItems){
                                hashmap.put(item.item.id.toString()+ "_key", item.quantity.toLong())
                            }

                            var newOrderId = 0
                            var allOrderIds = mutableListOf<Long>()
                            for(order in orders.value){
                                allOrderIds.add(order.orderId)
                            }
                            if(allOrderIds.size == 0){
                                newOrderId = 1
                            }else{
                                allOrderIds.sort()
                                newOrderId = allOrderIds[allOrderIds.size-1].toInt()+1
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
                            cartItems = mutableListOf()
                            ordered = true
                            Handler().postDelayed({
                                for(or in orders.value){
                                    if(or.orderId.toInt() == newOrder.orderId.toInt()){
                                        Log.d(ContentValues.TAG, or.orderId.toString() + " " + newOrderId.toString())
                                        orderSuccessful = true
                                    }
                                }
                                Router.navigateTo(Screen.MainScreen)
                            }, 1000)
                        }
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ){
                            Text(
                                text = "Order",
                                style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                color = Color.White,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ItemCartCard(
    item: CartItem,
    cartList: MutableList<CartItem>
){
    var quantity by remember { mutableStateOf(item.quantity) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
            .height(75.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 3.dp
    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        for(element in cartList){
                            if(element.item == item.item){
                                cartList.remove(element)
                                cartItems.remove(element)
                                break
                            }
                        }
                        var tmpSumEur = 0.0
                        var tmpSumHrk = 0
                        for(element in cartItems){
                            tmpSumEur += element.item.priceEur*element.quantity
                            tmpSumHrk += element.item.priceHrk.toInt()*element.quantity.toInt()
                        }
                        tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                        sumEur = tmpSumEur
                        sumHrk = tmpSumHrk
//                        Log.d(ContentValues.TAG, quantity.toString())
                    },
                    modifier = Modifier.size(25.dp)
                ){
                    Card(
                        shape = CircleShape,
                        backgroundColor = Color.Red,
                        modifier = Modifier.size(25.dp)
                    ){
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
                ){
                    Text(
                        text = item.item.name,
                        style = ArchivoTypography.h6.copy(fontSize = 14.sp),
                        color = Color.Black,
                    )
                }

            }
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd){
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        if(quantity > 1){
                            quantity--
                            item.quantity = quantity
                        }
                        var tmpSumEur = 0.0
                        var tmpSumHrk = 0
                        for(element in cartItems){
                            tmpSumEur += element.item.priceEur*element.quantity
                            tmpSumHrk += element.item.priceHrk.toInt()*element.quantity.toInt()
                        }
                        tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                        sumEur = tmpSumEur
                        sumHrk = tmpSumHrk
                    },
                    modifier = Modifier.size(25.dp)
                ){
                    Card(
                        shape = CircleShape,
                        backgroundColor = Color(red = 255, green = 140, blue = 0, alpha = 225),
                        modifier = Modifier.size(25.dp)
                    ){
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
                        if(quantity < 9){
                            quantity++
                            item.quantity = quantity
                        }
                        var tmpSumEur = 0.0
                        var tmpSumHrk = 0
                        for(element in cartItems){
                            tmpSumEur += element.item.priceEur*element.quantity
                            tmpSumHrk += element.item.priceHrk.toInt()*element.quantity.toInt()
                        }
                        tmpSumEur = (tmpSumEur * 100.0).roundToInt() / 100.0
//                        sumHrk = (sumHrk * 100.0).roundToInt() / 100.0
                        sumEur = tmpSumEur
                        sumHrk = tmpSumHrk
                    },
                    modifier = Modifier.size(25.dp)
                ){
                    Card(
                        shape = CircleShape,
                        backgroundColor = Color(red = 255, green = 140, blue = 0, alpha = 225),
                        modifier = Modifier.size(25.dp)
                    ){
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
fun TopBarCart(){
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
                    Router.navigateTo(Screen.MainScreen)
                }
            ){
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