package com.example.hyperbar.screens

import android.content.res.Resources
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hyperbar.ui.theme.*
import com.example.hyperbar.R
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.NavHostController
import com.example.hyperbar.data.DataModel
import com.example.hyperbar.data.firstTime
import com.example.hyperbar.data.tableBool


@ExperimentalAnimationApi
@Composable
fun IntroScreen (){
    LaunchedEffect(key1 = true){
        delay(1500L)
        firstTime = true
        tableBool.update(false)
        tableBool.refreshTableNum(0)
        Router.navigateTo(Screen.TestScreen)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(red = 255, green = 180, blue = 20),
                        Color(red = 208, green = 99, blue = 0)
                    )
                )
            )
    ){
        Text(
            text = "HYPER BAR",
            style = ArchivoTypography.h2
        )
        Image(
            painter = painterResource(id = R.drawable.ic_icon_bw2),
            contentDescription = "Icon Image",
            modifier = Modifier.size(60.dp)
        )
    }
}