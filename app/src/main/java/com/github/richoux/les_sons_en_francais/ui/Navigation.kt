package com.github.richoux.les_sons_en_francais.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.richoux.les_sons_en_francais.ui.screens.AboutScreen
import com.github.richoux.les_sons_en_francais.ui.screens.CardScreen
import com.github.richoux.les_sons_en_francais.ui.screens.HomeScreen
import com.github.richoux.les_sons_en_francais.ui.screens.Screens
import com.github.richoux.les_sons_en_francais.ui.theme.LesSonsEnFrançaisTheme

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
                Log.d(TAG, "Nav.HomeScreen - index=${uiState.index}, soundText=${uiState.soundText}, soundID=${uiState.soundID}, startDate=${uiState.startDate}")
                HomeScreen(
                    appViewModel = appViewModel,
                    uppercase = options.uppercase,
                    lowercase = options.lowercase,
                    cursive = options.cursive,
                    onSelected = { it: Context, index: Int ->
                        appViewModel.updateCard(it, index)
                        navController.navigate(Screens.CardScreen.route)
                    },
                    onHomeClicked = {
                        navController.navigate(Screens.HomeScreen.route)
                    },
                    onAboutClicked = {
                        navController.navigate(Screens.AboutScreen.route)
                    }
                )
            }
        }
        composable(route = Screens.AboutScreen.route) {
            LesSonsEnFrançaisTheme(
                darkTheme = options.darkMode
            ) {
                Log.d(TAG, "Nav.AboutScreen")
                AboutScreen(
                    appViewModel = appViewModel,
                    onHomeClicked = {
                        navController.navigate(Screens.HomeScreen.route)
                    },
                    onAboutClicked = {
                        navController.navigate(Screens.AboutScreen.route)
                    }
                )
            }
        }
        composable(route = Screens.CardScreen.route) {
            LesSonsEnFrançaisTheme(
                darkTheme = options.darkMode
            ) {
                Log.d(TAG,"Nav.CardScreen - index=${uiState.index}, soundText=${uiState.soundText}, soundID=${uiState.soundID}, startDate=${uiState.startDate}" )
                CardScreen(
                    appViewModel = appViewModel,
                    soundText = uiState.soundText,
                    soundID = uiState.soundID,
                    uppercase = options.uppercase,
                    lowercase = options.lowercase,
                    cursive = options.cursive,
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
                    },
                    onAboutClicked = {
                        navController.navigate(Screens.AboutScreen.route)
                    }
                )
            }
        }
    }
}