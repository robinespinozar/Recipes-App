package com.raerossi.retotecnico.ui.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object WelcomeScreen : Screen("welcome_screen")
    object HomeScreen: Screen("home_screen")
    object DetailScreen: Screen("detail_screen/{id}")
}