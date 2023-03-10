package fr.richoux.lessonsenfrancais.ui

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.richoux.lessonsenfrancais.ui.screens.CardScreen
import fr.richoux.lessonsenfrancais.ui.screens.Screens
import fr.richoux.lessonsenfrancais.ui.screens.HomeScreen
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme
import androidx.lifecycle.viewmodel.compose.viewModel

private const val TAG = "Navigation"

@Composable
fun Navigation(
    preferences: SharedPreferences,
    appViewModel: AppViewModel = AppViewModel(preferences)
) {
    val navController = rememberNavController()
    val uiState by appViewModel.uiState.collectAsState()
    val options by appViewModel.options.collectAsState()

    NavHost(navController = navController, startDestination = appViewModel.getLastRoute()) {
        composable(route = Screens.HomeScreen.route) {
            LesSonsEnFrançaisTheme(
                darkTheme = options.darkMode
            ) {
                Log.d(TAG, "HomeScreen - index=${uiState.index}, selectCards=${uiState.selectCards}, soundText=${uiState.soundText}, startDate=${uiState.startDate}")

                HomeScreen(
                    appViewModel = appViewModel,
                    onSimpleSelected = {
                        appViewModel.simpleCards(it)
                        navController.navigate(Screens.CardScreen.route)
                    },
                    onComplexSelected = {
                        appViewModel.complexCards(it)
                        navController.navigate(Screens.CardScreen.route)
                    },
                    onHomeClicked = {
                        navController.navigate(Screens.HomeScreen.route)
                    }
                )
            }
        }
        composable(route = Screens.CardScreen.route) {
            LesSonsEnFrançaisTheme(
                darkTheme = options.darkMode
            ) {
                Log.d(
                    TAG,
                    "CardScreen - index=${uiState.index}, selectCards=${uiState.selectCards}, soundText=${uiState.soundText}, startDate=${uiState.startDate}"
                )
                CardScreen(
                    appViewModel = appViewModel,
                    soundText = uiState.soundText,
                    soundID = uiState.soundID,
                    onPreviousClicked = {
                        appViewModel.previousCard(it)
                    },
                    onNextClicked = {
                        appViewModel.nextCard(it)
                    },
                    onRandomClicked = {
                        appViewModel.randomCard(it)
                    },
                    onHomeClicked = {
                        navController.navigate(Screens.HomeScreen.route)
                    }
                )
            }
        }
    }
}