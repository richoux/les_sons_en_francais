package fr.richoux.lessonsenfrancais.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.richoux.lessonsenfrancais.ui.screens.CardScreen
import fr.richoux.lessonsenfrancais.ui.screens.Screens
import fr.richoux.lessonsenfrancais.ui.screens.HomeScreen

@Composable
fun Navigation(appViewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState by appViewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = appViewModel.getLastRoute()) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(
                onSimpleSelected = {
                    appViewModel.simpleCards( it )
                    navController.navigate(Screens.CardScreen.route)
                },
                onComplexSelected = {
                    appViewModel.complexCards( it )
                    navController.navigate(Screens.CardScreen.route)
                }
            )
        }
        composable(route = Screens.CardScreen.route) {
            CardScreen(
                soundText = uiState.soundText,
                soundID = uiState.soundID,
                onPreviousClicked = {
                    appViewModel.previousCard(it)
                },
                onNextClicked = {
                    appViewModel.nextCard(it)
                },
                onHomeClicked = {
                    navController.navigate(Screens.HomeScreen.route)
                }
            )
        }
    }
}