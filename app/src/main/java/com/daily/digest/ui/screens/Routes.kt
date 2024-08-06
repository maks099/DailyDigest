package com.daily.digest.ui.screens

sealed class Routes(val route: String) {
    object Main : Routes("main")
}