package fr.richoux.lessonsenfrancais.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.richoux.lessonsenfrancais.Screen

@Composable
fun Navigation( appViewModel: AppViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            Home(appViewModel = appViewModel, navController = navController)
        }
        composable(route = Screen.CardScreen.route) {
            CardScreen(appViewModel = appViewModel, navController = navController)
        }
    }
}