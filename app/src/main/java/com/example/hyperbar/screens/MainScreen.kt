package com.example.hyperbar.screens

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.example.hyperbar.R
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.roundToInt

fun addToCart(id: Int) {
    Log.d("MainActivity", id.toString())
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    categories: State<List<CategoryNew>>,
    todaysSelection: State<List<TodaysSelection>>,
    products: State<List<Product>>,
    orders: State<List<Order>>,
    images: Map<Long, AsyncImagePainter>
) {
    val activity = (LocalContext.current as? Activity)
    BackHandler() {
        activity?.finish()
    }

    var visibility by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    LaunchedEffect(key1 = true) {
        delay(5000L)
        visibility = false
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MainScreenBack(
            categories,
            todaysSelection,
            products,
            orders,
            images
        )
        if (tableBool.cameFromIntro || ordered == true) {
            AnimatedVisibility(
                visible = visibility,
                enter = fadeIn(
                    initialAlpha = 0.0f,
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 75.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Row(
                        modifier = Modifier
                            .height(35.dp)
                            .clip(shape = RoundedCornerShape(size = 5.dp))
                            .background(Color(red = 255, green = 140, blue = 0, alpha = 255)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (tableBool.tableNum != 0 && fromItemScreen == false && ordered == false) {
                            Text(
                                modifier = Modifier.padding(start = 15.dp),
                                text = "Your table number is:  ",
                                style = ArchivoTypography.h4.copy(fontSize = 16.sp),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            );
                            Text(
                                modifier = Modifier.padding(end = 15.dp),
                                text = "${tableBool.tableNum}",
                                style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            );
                        }
                        if (tableBool.tableNum == 0 && fromItemScreen == false && ordered == false) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp),
                                text = "You are currently in non-ordering mode.",
                                style = ArchivoTypography.h4,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            );
                        }
                        if(ordered == true && orderSuccessful == true){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp),
                                text = "Order successful!",
                                style = ArchivoTypography.h4,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            );
                            orderSuccessful = false
                            ordered = false
                        }
                        if(ordered == true && orderSuccessful == false){
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp),
                                text = "Order failed! Check your connection.",
                                style = ArchivoTypography.h4,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            );
                            orderSuccessful = false
                            ordered = false
                        }
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun MainScreenBack(
    categories: State<List<CategoryNew>>,
    todaysSelection: State<List<TodaysSelection>>,
    products: State<List<Product>>,
    orders: State<List<Order>>,
    images: Map<Long, AsyncImagePainter>
) {
    val configuration = LocalConfiguration.current
    val orientation by remember {
        mutableStateOf(
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    "Landscape"
                }
                else -> {
                    "Portrait"
                }
            }
        )
    }

    val fabHeight = 72.dp //FabSize+Padding
    val fabHeightPx = with(LocalDensity.current) { fabHeight.roundToPx().toFloat() }
    val fabOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = fabOffsetHeightPx.value + delta
                fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    var orderTypeLocal by remember {mutableStateOf(orderType)}

    val focusRequester = remember { FocusRequester() }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
            TopBarMain()
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                var selectedOption by remember {
                    mutableStateOf(categories.value[selectedSaved])
                }

                val onSelectionChange = { text: CategoryNew ->
                    var sel = 0
                    for(cat in categories.value){
                        if(cat == text){
                            selectedSaved = sel
                        }
                        sel++
                    }
                    selectedOption = text
                }

                if(currentProductFirstTimeBool == true){
                    currentProduct = products.value[todaysSelection.value[0].id.toInt()-1]
                    currentProductFirstTimeBool = false
                }

                var currentImage by remember { mutableStateOf(
                    currentProduct
                ) }
                if (orientation == "Portrait") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                            .height(300.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                    ) {
//                        if(currentProductFirstTimeBool == true){
//                            currentProduct = products.value[todaysSelection.value[0].id.toInt()-1]
//                            currentProductFirstTimeBool = false
//                        }
//
//                        var currentImage by remember { mutableStateOf(
//                            currentProduct
//                        ) }
                        var direction by remember { mutableStateOf(-1) }

                        Crossfade(
                            targetState = currentImage,
                            animationSpec = tween(400)
                        ) { currentImage ->
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(currentImage.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.no_image),
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color(0xaa000000)),
                                        startY = 300f
                                    )
                                )
                        ) {
                        }

                        Crossfade(
                            targetState = currentImage,
                            animationSpec = tween(400)
                        ) { currentImage ->
                            Box(
                                contentAlignment = Alignment.BottomStart,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                                    .clickable {
                                        currentItem = currentImage
                                        Router.navigateTo(Screen.ItemScreen)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "today's selection:",
                                        style = ArchivoTypography.h4,
                                        color = Color.White
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = currentImage.name,
                                            style = ArchivoTypography.h5,
                                            color = Color.White
                                        )
                                                if (savedCurrencyState.first == "EUR") {
                                                    var price = currentImage.priceEur.toString().split(".")[1]
                                                    Text(
                                                        text = if (price.length == 1) {
                                                            "€" + currentImage.priceEur.toString() + "0"
                                                        } else {
                                                            "€" + currentImage.priceEur.toString()
                                                        },
                                                        style = ArchivoTypography.h5,
                                                        color = Color.White
                                                    )
                                                } else {
                                                    Text(
                                                        text = currentImage.priceHrk.toString() + " kn",
                                                        style = ArchivoTypography.h5,
                                                        color = Color.White
                                                    )
                                                }

                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    currentItem = currentImage
                                    Router.navigateTo(Screen.ItemScreen)
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDrag = { change, dragAmount ->
                                            change.consumeAllChanges()

                                            val (x, y) = dragAmount
                                            if (abs(x) > abs(y)) {
                                                when {
                                                    x > 0 -> {
                                                        direction = 0
                                                    }
                                                    x < 0 -> {
                                                        direction = 1
                                                    }
                                                }
                                            } else {
                                                when {
                                                    y > 0 -> {
                                                        direction = 2
                                                    }
                                                    y < 0 -> {
                                                        direction = 3
                                                    }
                                                }
                                            }
                                        },
                                        onDragEnd = {
                                            when (direction) {
                                                1 -> {
                                                    var x: TodaysSelection? = null
                                                    for(y in todaysSelection.value){
                                                        if(y.id == currentImage.id){
                                                            x = y
                                                        }
                                                    }
                                                    if (x != null && todaysSelection.value.indexOf(x) + 1 < todaysSelection.value.size) {
                                                        currentImage = products.value[todaysSelection.value[todaysSelection.value.indexOf(x)+1].id.toInt()-1]
                                                        currentProduct = currentImage
                                                    }
                                                }
                                                0 -> {
                                                    var x: TodaysSelection? = null
                                                    for(y in todaysSelection.value){
                                                        if(y.id == currentImage.id){
                                                            x = y
                                                        }
                                                    }
                                                    if (x != null && todaysSelection.value.indexOf(x) - 1 >= 0) {
                                                        currentImage = products.value[todaysSelection.value[todaysSelection.value.indexOf(x)-1].id.toInt()-1]
                                                        currentProduct = currentImage
                                                    }
                                                }
                                                2 -> {
                                                }
                                                3 -> {
                                                }
                                            }
                                        }
                                    )
                                }
                        )
                    }
                }

                var orderBool by remember { mutableStateOf(false) }
                var filterBool by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, top = 15.dp, end = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp
                                )
                            )
                            .clickable {
                                filterBool = true
                            }
                            .background(
                                Color.White
                            )
                            .padding(
                                vertical = 5.dp,
                                horizontal = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box() {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Filled.List, contentDescription = "Filter")
                                Text(
                                    text = "Filter By",
                                    style = ArchivoTypography.h6,
                                    color = if (!filterBool) {
                                        Color.Black
                                    } else {
                                        Color(red = 209, green = 99, blue = 0)
                                    },
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(start = 5.dp)
                                );
                            }
                            DropdownMenu(
                                expanded = filterBool,
                                onDismissRequest = { filterBool = false },
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(
                                            size = 15.dp
                                        )
                                    )
                                    .background(Color.White)
                            ) {
                                DropdownMenuItem(onClick = {
                                    filterBool = false
                                }) {
                                    Text("unnamed")
                                }
                                Divider()
                                DropdownMenuItem(onClick = {
                                    filterBool = false
                                }) {
                                    Text("unnamed")
                                }
                                Divider()
                                DropdownMenuItem(onClick = {
                                    filterBool = false
                                }) {
                                    Text("unnamed")
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp
                                )
                            )
                            .shadow(elevation = 2.dp)
                            .clickable {
                                orderBool = true
                            }
                            .background(
                                Color.White
                            )
                            .padding(
                                vertical = 5.dp,
                                horizontal = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box() {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Order")
                                Text(
                                    text = "Order By",
                                    style = ArchivoTypography.h6,
                                    color = if (!orderBool) {
                                        Color.Black
                                    } else {
                                        Color(red = 209, green = 99, blue = 0)
                                    },
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(start = 5.dp)
                                );
                            }
                            DropdownMenu(
                                expanded = orderBool,
                                onDismissRequest = { orderBool = false },
                                modifier = Modifier
                                    .clip(
                                        shape = RoundedCornerShape(
                                            size = 15.dp
                                        )
                                    )
                                    .background(Color.White)
                            ) {
                                DropdownMenuItem(onClick = {
                                    orderBool = false
                                    orderType = 0
                                    orderTypeLocal = 0
                                }) {
                                    Text("Price: Low to High")
                                }
                                Divider()
                                DropdownMenuItem(onClick = {
                                    orderBool = false
                                    orderType = 1
                                    orderTypeLocal = 1
                                }) {
                                    Text("Price: High to Low")
                                }
                                Divider()
                                DropdownMenuItem(onClick = {
                                    orderBool = false
                                    orderType = 2
                                    orderTypeLocal = 2
                                }) {
                                    Text("Name")
                                }
                                Divider()
                                DropdownMenuItem(onClick = {
                                    orderBool = false
                                    orderType = -1
                                    orderTypeLocal = -1
                                }) {
                                    Text("Default")
                                }
                            }
                        }

                    }
                }


                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    categories.value.forEach { text ->
                        Text(
                            text = text.name,
                            style = ArchivoTypography.h3,
                            color = if (text != selectedOption) {
                                Color.Black
                            } else {
                                Color(red = 209, green = 99, blue = 0)
                            },
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clip(
                                    shape = RoundedCornerShape(
                                        size = 12.dp
                                    )
                                )
                                .clickable {
                                    onSelectionChange(text)
                                }
                                .background(
                                    if (text != selectedOption) {
                                        Color.White
                                    } else {
                                        Color(0x77DDDDDD)
                                    }
                                )
                                .padding(
                                    vertical = 5.dp,
                                    horizontal = 10.dp
                                )
                        );
                        if (text.id.toInt() != categories.value.size) {
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }

                Crossfade(
                    targetState = selectedOption,
                    animationSpec = tween(400)
                ) { selectedOption ->
                    val productsCategory = mutableListOf<Product>()
                    for (product in products.value) {
                        if (product.categoryId == selectedOption.id) {
                            productsCategory.add(product)
                        }
                    }
                    if(orderTypeLocal == 2){
                        var productsCategory = productsCategory.sortBy { it.name }
                    }
                    if(orderTypeLocal == 1){
                        var productsCategory = productsCategory.sortByDescending { it.priceEur }
                    }
                    if(orderTypeLocal == 0){
                        var productsCategory = productsCategory.sortBy { it.priceEur }
                    }
                    if(orderTypeLocal == -1){
                        var productsCategory = productsCategory.sortBy { it.id }
                    }
                    if (orientation == "Portrait") {
                        DrinkGrid(productsCategory, 2, images)
                    } else {
                        DrinkGrid(productsCategory, 3, images)
                    }
                }
            }
        },
        bottomBar = {
            BottomBarMain(fabOffsetHeightPx)
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DrinkGrid(
    list: MutableList<Product>,
    num: Int,
    images: Map<Long, AsyncImagePainter>
) {
    val cel = ceil(list.size.toDouble() / num).toInt()
    var i = 0

    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxSize()
    ) {
        repeat(cel) { index ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                repeat(num) {
                    if (i < list.size) {
                        CardDrink(item = list[i], num, images)
                        ++i
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CardDrink(
    item: Product,
    num: Int,
    images: Map<Long, AsyncImagePainter>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Card(
        modifier = Modifier
            .width((screenWidth - 45.dp - (15.dp * (num - 2))) / num)
            .height(210.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.no_image),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            currentItem = item
                            Router.navigateTo(Screen.ItemScreen)
                        }
                        )
                )
                if(tableBool.tableNum != 0){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                currentItem = item
                                Router.navigateTo(Screen.ItemScreen)
                            }
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0x55000000), Color.Transparent),
                                    endY = 175f
                                )
                            ),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconButton(
                            onClick = {
                                if (tableBool.tableNum != 0) {
                                    var count = -1
                                    for (element in cartItems) {
                                        if (element.item == item) {
                                            count = cartItems.indexOf(element)
                                        }
                                    }
                                    if (count == -1) {
                                        var newCartItem = CartItem(item, 1)
                                        cartItems.add(newCartItem)
                                    } else {
                                        if (cartItems.get(count).quantity < 9) {
                                            cartItems.get(count).quantity =
                                                cartItems.get(count).quantity + 1
                                        }
                                    }
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.AddCircle,
                                contentDescription = "Add",
                                modifier = Modifier
                                    .padding(7.dp)
                                    .size(35.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            currentItem = item
                            Router.navigateTo(Screen.ItemScreen)
                        }
                        .padding(top = 7.dp, start = 10.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                            if (savedCurrencyState.first == "EUR") {
                                var price = item.priceEur.toString().split(".")[1]
                                Text(
                                    text = if (price.length == 1) {
                                        "€" + item.priceEur.toString() + "0"
                                    } else {
                                        "€" + item.priceEur.toString()
                                    },
                                    style = ArchivoTypography.subtitle1,
                                    color = Color.Black
                                )
                            } else {
                                Text(
                                    text = item.priceHrk.toString() + " kn",
                                    style = ArchivoTypography.subtitle1,
                                    color = Color.Black
                                )
                            }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, bottom = 7.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        item.name,
                        style = ArchivoTypography.h6,
                        color = Color.Black
                    )
                }
            }
        }


    }
}


@Composable
fun BottomBarMain(
    fabOffsetHeightPx: MutableState<Float>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset {
                IntOffset(
                    x = 0,
                    y = -fabOffsetHeightPx.value.roundToInt() - 15.dp.roundToPx()
                )
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 15.dp))
                .background(Color(0xE1FFCE93))
                .clickable {
                    if (tableBool.tableNum != 0) {
                        null
                    } else {
                        tableBool.update(true)
                        waiterFromMain = false
                        waiter = false
                        Router.navigateTo(Screen.TestScreen)
                    }
                },
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            if (tableBool.tableNum != 0) {
                IconButton(
                    onClick = {
                        if(cartItems.size != 0){
                            triggerDoneOuter = false
                            stateOfTheBox = BoxState.Expanded
                            triggerOrder = true
                        }
                        Router.navigateTo(Screen.CartScreen)
                    }
                ) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Send",
                        modifier = Modifier
                            .padding(10.dp),
                        tint = Color(0xFF555555)
                    )
                }
            } else {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp),
                    tint = Color(0x00555555)
                )
            }

            IconButton(
                onClick = {
                    tableBool.update(true)
                    waiterFromMain = false
                    waiter = false

                    cartItems.clear()
                    Router.navigateTo(Screen.TestScreen)
                }
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(10.dp),
                    tint = Color(0xFF555555)
                )
            }
            if (tableBool.tableNum != 0) {
                IconButton(
                    onClick = {

                        Router.navigateTo(Screen.PastOrdersScreen)

                    }
                ) {
                    Icon(
                        Icons.Filled.History,
                        contentDescription = "Refresh",
                        modifier = Modifier
                            .padding(10.dp),
                        tint = Color(0xFF555555)
                    )
                }
            } else {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp),
                    tint = Color(0x00555555)
                )
            }

            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}

@Composable
fun TopBarMain() {
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
                    Router.navigateTo(Screen.SettingsScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF555555)
                )
            }
            Text(
                text = "HYPER BAR",
                style = ArchivoTypography.h1
            )
            if (tableBool.tableNum != 0) {
                IconButton(
                    onClick = {
                        Router.navigateTo(Screen.CartScreen)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = Color(0xFF555555)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "",
                    tint = Color(0x00555555)
                )
            }
        }
    }
}