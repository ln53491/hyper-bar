package com.example.hyperbar.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.hyperbar.R

val Archivo = FontFamily(
    Font(R.font.archivo_black, weight = FontWeight.Black),
    Font(R.font.archivo_bold, weight = FontWeight.Bold),
    Font(R.font.archivo_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.archivo_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.archivo_light, weight = FontWeight.Light),
    Font(R.font.archivo_medium, weight = FontWeight.Medium),
    Font(R.font.archivo_regular, weight = FontWeight.Normal),
    Font(R.font.archivo_semibold, weight = FontWeight.SemiBold),
    Font(R.font.archivo_thin, weight = FontWeight.Thin)
)

// Set of Material typography styles to start with
val ArchivoTypography = Typography(
    body1 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    h4 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    h5 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    h6 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = Archivo,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        textAlign = TextAlign.Center
    )




    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)