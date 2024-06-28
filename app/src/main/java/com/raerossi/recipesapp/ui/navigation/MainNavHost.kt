package com.raerossi.retotecnico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raerossi.recipesapp.ui.features.detail.DetailScreen
import com.raerossi.recipesapp.ui.features.home.HomeScreen
import com.raerossi.recipesapp.ui.features.welcome.WelcomeScreen

@Composable
fun MainNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = Graph.MAIN,
        startDestination = Screen.WelcomeScreen.route
    ) {
        composable(route = Screen.WelcomeScreen.route) {
            WelcomeScreen(
                onStartClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.HomeScreen.route)
                }
            )
        }
        composable(route = Screen.HomeScreen.route){
            HomeScreen()
        }
        composable(route = Screen.DetailScreen.route){
            DetailScreen()
        }
    }
}
