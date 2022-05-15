package com.example.hyperbar.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

sealed class Screen() {
    object IntroScreen : Screen()
    object TestScreen : Screen()
    object MainScreen : Screen()
    object SettingsScreen : Screen()
    object CartScreen : Screen()
    object ItemScreen : Screen()
}

object Router {
    var currentScreen: Screen by mutableStateOf(Screen.IntroScreen)

    fun navigateTo(destination: Screen) {
        currentScreen = destination
    }
}