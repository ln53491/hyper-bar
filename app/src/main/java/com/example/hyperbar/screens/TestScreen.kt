package com.example.hyperbar.screens

import android.app.Activity
import android.content.res.Resources
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hyperbar.data.DataModel
import com.example.hyperbar.data.fromItemScreen
import com.example.hyperbar.data.tableBool
import com.example.hyperbar.data.tables


@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalAnimationApi
@Composable
fun TestScreen(){

    val activity = (LocalContext.current as? Activity)
    BackHandler() {
        if(tableBool.value == true){
            Router.navigateTo(Screen.MainScreen)
        }
        else{
            activity?.finish()
        }
    }

    fromItemScreen = false

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
    ) {
        var tableNum by remember {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current

            TextField(
                placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Table Number",
                            style = ArchivoTypography.body2.copy(fontSize = 18.sp)
                        )
                },
                textStyle = LocalTextStyle.current
                    .copy(textAlign = TextAlign.Center)
                    .copy(fontFamily = Archivo)
                    .copy(fontWeight = FontWeight.Medium)
                    .copy(fontSize = 18.sp),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(175.dp)
                    .align(Alignment.CenterHorizontally),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                value = tableNum,
                onValueChange = { value ->
                    if (value.length <= 2) {
                        tableNum = value.filter { it.isDigit() }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (tableNum != "" && tableNum.toInt() in tables) {
                        tableBool.refreshTableNum(tableNum.toInt())
                        tableBool.updateCameFromIntro(true)
                        keyboardController?.hide()
                        Router.navigateTo(Screen.MainScreen)
                    }
                })
            )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(175.dp)
                .height(40.dp)
                .background(Color.Transparent)
                .align(Alignment.CenterHorizontally),
            onClick = {
                    if (tableNum != "" && tableNum.toInt() in tables) {
                        tableBool.refreshTableNum(tableNum.toInt())
                        tableBool.updateCameFromIntro(true)
                        keyboardController?.hide()
                        Router.navigateTo(Screen.MainScreen)
                    }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF22CC22))
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    color = Color.White,
                    text = "Confirm",
                    style = ArchivoTypography.body2.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(175.dp)
                .height(40.dp)
                .background(Color.Transparent)
                .align(Alignment.CenterHorizontally),
            onClick = {
                tableNum = ""
                tableBool.refreshTableNum(0)
                keyboardController?.hide()
                tableBool.updateCameFromIntro(true)
                Router.navigateTo(Screen.MainScreen)
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF2222))
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    color = Color.White,
                    text = "Enter Without Ordering",
                    style = ArchivoTypography.body2.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}