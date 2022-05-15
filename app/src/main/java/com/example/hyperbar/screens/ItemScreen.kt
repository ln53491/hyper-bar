package com.example.hyperbar.screens

import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import com.example.hyperbar.R
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.ArchivoTypography

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
                                Text(
                                    text = selectedProduct.name,
                                    style = ArchivoTypography.h5,
                                    color = Color.Black
                                )
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
                                    Text(
                                        text = "€" + selectedProduct.priceEur.toString() + "0",
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
            horizontalArrangement = Arrangement.Start
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