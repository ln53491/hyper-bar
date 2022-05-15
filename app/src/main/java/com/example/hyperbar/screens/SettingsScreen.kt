package com.example.hyperbar.screens

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import android.provider.Settings
import android.widget.ToggleButton
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hyperbar.R
import com.example.hyperbar.data.*
import com.example.hyperbar.ui.theme.Archivo
import com.example.hyperbar.ui.theme.ArchivoTypography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen() {
    BackHandler() {
        Router.navigateTo(Screen.MainScreen)
    }
    tableBool.updateCameFromIntro(false)
    fromItemScreen = true

    val focusRequester = remember { FocusRequester() }
    val scaffoldState: ScaffoldState = rememberScaffoldState()

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
            TopBarSettings()
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val checkedState = remember { mutableStateOf(true) }
                val options = MyButton().options

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 1.dp)
                        .height(65.dp),
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
                        Text(
                            text = "Notifications",
                            style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(red = 255, green = 140, blue = 0),
                                uncheckedThumbColor = Color.White
                            )
                        )
                    }
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 15.dp)
                        .height(65.dp),
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
                        Text(
                            text = "Currency",
                            style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        ToggleButton(
                            options = options,
                            type = SelectionType.SINGLE,
                            onClick = {

                            }
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    color = Color(red = 175, green = 175, blue = 175)
                )

                ExpandableCard(
                    title = "Privacy Policy",
                    description = loremIpsum
                )
            }
        },
        bottomBar = {
            if (orientation == "Portrait") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(15.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "App Version 1.0.0",
                            style = ArchivoTypography.h3.copy(fontSize = 12.sp),
                            color = Color.Gray
                        )
                        Text(
                            text = "Date of Version 5/6/2022",
                            style = ArchivoTypography.h3.copy(fontSize = 12.sp),
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 15.dp
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 15.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = {
            expandedState = !expandedState
        },
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = ArchivoTypography.h6.copy(fontSize = 16.sp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
            if (expandedState) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    text = description,
                    style = ArchivoTypography.subtitle1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

enum class SelectionType {
    NONE,
    SINGLE,
    MULTIPLE,
}

data class ToggleButtonOption(
    val text: String,
    val iconRes: Int?,
)


@Composable
fun SelectionPill(
    option: ToggleButtonOption,
    selected: Boolean,
    onClick: (option: ToggleButtonOption) -> Unit = {}
) {
    Button(
        onClick = { onClick(option) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected) Color(
                red = 255,
                green = 140,
                blue = 0,
                alpha = 225
            ) else Color.White,
        ),
        shape = RoundedCornerShape(0),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        contentPadding = PaddingValues(0.dp),
//        modifier = Modifier.padding(0.dp, 5.dp),
    ) {
        Row(
            modifier = Modifier.padding(0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = option.text,
                color = if (selected) Color.White else Color.Black,
                modifier = Modifier.padding(0.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ToggleButton(
    options: Array<ToggleButtonOption>,
    modifier: Modifier = Modifier,
    type: SelectionType = SelectionType.SINGLE,
    onClick: (selectedOptions: Array<ToggleButtonOption>) -> Unit = {},
) {
    val state = remember { mutableStateMapOf<String, ToggleButtonOption>(savedCurrencyState) }

    OutlinedButton(
        onClick = { },
        border = BorderStroke(1.dp, Color.Gray),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
        contentPadding = PaddingValues(0.dp, 0.dp),
        modifier = modifier
            .padding(0.dp)
            .height(52.dp),
    ) {
        if (options.isEmpty()) {
            return@OutlinedButton
        }
        val onItemClick: (option: ToggleButtonOption) -> Unit = { option ->
            if (type == SelectionType.SINGLE) {
                options.forEach {
                    val key = it.text
                    if (key == option.text) {
                        savedCurrencyState = Pair<String, ToggleButtonOption>(key, option)
                        state[key] = option
                    } else {
                        state.remove(key)
                    }
                }
            } else {
                val key = option.text
                if (!state.contains(key)) {
                    state[key] = option
                } else {
                    state.remove(key)
                }
            }
            onClick(state.values.toTypedArray())
        }
        if (options.size == 1) {
            val option = options.first()
            SelectionPill(
                option = option,
                selected = state.contains(option.text),
                onClick = onItemClick,
            )
            return@OutlinedButton
        }
        val first = options.first()
        val last = options.last()
        val middle = options.slice(1..options.size - 2)
        SelectionPill(
            option = first,
            selected = state.contains(first.text),
            onClick = onItemClick,
        )
        VerticalDivider()
        middle.map { option ->
            SelectionPill(
                option = option,
                selected = state.contains(option.text),
                onClick = onItemClick,
            )

            VerticalDivider()
        }
        SelectionPill(
            option = last,
            selected = state.contains(last.text),
            onClick = onItemClick,
        )
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp),
        color = Color.Gray
    )
}


@Composable
fun TopBarSettings() {
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
                text = "Settings",
                style = ArchivoTypography.h5
            )
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}