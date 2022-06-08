package com.example.hyperbar.screens

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.example.hyperbar.R
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ItemScreen(
    categories: State<List<CategoryNew>>,
    products: State<List<Product>>
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    BackHandler() {
        Router.navigateTo(Screen.MainScreen)
    }

    var selectedProduct: Product? = null
    for (product in products.value) {
        if (currentItem.id == product.id) {
            selectedProduct = product
        }
    }

    fromItemScreen = true
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

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color(0xffFFF2E5),
        topBar = {
            TopItemBar()
        },
        content = {
            if (selectedProduct != null) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                ) {
                    if (orientation == "Portrait") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                                .height(300.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(selectedProduct?.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.no_image),
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            if (tableBool.tableNum != 0) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0x55000000),
                                                    Color.Transparent
                                                ),
                                                endY = 175f
                                            )
                                        ),
                                    contentAlignment = Alignment.TopEnd
                                ) {

                                    val selectedAdd = remember { mutableStateOf(false) }
                                    val scaleAdd =
                                        animateFloatAsState(if (selectedAdd.value) 0.9f else 1f)
                                    IconButton(
                                        modifier = Modifier
                                            .scale(scaleAdd.value)
                                            .pointerInteropFilter {
                                                when (it.action) {
                                                    MotionEvent.ACTION_DOWN -> {
                                                        selectedAdd.value = true
                                                    }

                                                    MotionEvent.ACTION_UP -> {
                                                        selectedAdd.value = false
                                                        if (tableBool.tableNum != 0) {
                                                            var count = -1
                                                            for (element in cartItems) {
                                                                if (element.item == selectedProduct) {
                                                                    count =
                                                                        cartItems.indexOf(element)
                                                                }
                                                            }
                                                            if (count == -1) {
                                                                var newCartItem =
                                                                    CartItem(selectedProduct, 1)
                                                                cartItems.add(newCartItem)
                                                            } else {
                                                                if (cartItems.get(count).quantity < 9) {
                                                                    cartItems.get(count).quantity =
                                                                        cartItems.get(count).quantity + 1
                                                                }
                                                            }
                                                        }
                                                    }

                                                    MotionEvent.ACTION_CANCEL -> {
                                                        selectedAdd.value = false
                                                    }
                                                }
                                                true
                                            },
                                        onClick = {

                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.AddCircle,
                                            contentDescription = "Add",
                                            modifier = Modifier
                                                .padding(3.dp)
                                                .size(100.dp),
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 15.dp, end = 15.dp),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 3.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                if (selectedProduct.name.length >= 19) {
                                    Text(
                                        text = selectedProduct.name,
                                        style = ArchivoTypography.h5.copy(fontSize = 16.sp),
                                        color = Color.Black
                                    )
                                } else {
                                    Text(
                                        text = selectedProduct.name,
                                        style = ArchivoTypography.h5,
                                        color = Color.Black
                                    )
                                }
                                for (category in categories.value) {
                                    if (category.id == selectedProduct.categoryId) {
                                        Text(
                                            text = category.name,
                                            style = ArchivoTypography.subtitle1,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                if (savedCurrencyState.first == "EUR") {
                                    var price = selectedProduct.priceEur.toString().split(".")[1]
                                    Text(
                                        text = if (price.length == 1) {
                                            "€" + selectedProduct.priceEur.toString() + "0"
                                        } else {
                                            "€" + selectedProduct.priceEur.toString()
                                        },
                                        style = ArchivoTypography.h5,
                                        color = Color.Black
                                    )
                                } else {
                                    Text(
                                        text = selectedProduct.priceHrk.toString() + " kn",
                                        style = ArchivoTypography.h5,
                                        color = Color.Black
                                    )
                                }
                                Text(
                                    text = selectedProduct.servingSize.toString() + "L",
                                    style = ArchivoTypography.subtitle1,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 15.dp),
                        shape = RoundedCornerShape(15.dp),
                        elevation = 3.dp
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            text = loremIpsum,
                            //text = selectedProduct.description
                            style = ArchivoTypography.subtitle1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TopItemBar() {
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
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF555555)
                )
            }
            Text(
                text = "Item",
                style = ArchivoTypography.h5
            )
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}