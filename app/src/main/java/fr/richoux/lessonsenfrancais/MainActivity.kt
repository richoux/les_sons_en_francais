package fr.richoux.lessonsenfrancais

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.richoux.lessonsenfrancais.ui.AppViewModel
import fr.richoux.lessonsenfrancais.ui.Navigation
import fr.richoux.lessonsenfrancais.ui.OptionsData
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainActivity : ComponentActivity() {

    private val TAG = "Main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val preferences: SharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
            val darkTheme: Boolean = preferences.getBoolean("darkMode", isSystemInDarkTheme())
            val dyslexia: Boolean = preferences.getBoolean("dyslexia", false)
            //val appViewModel = AppViewModel( preferences, isSystemInDarkTheme() )
            Log.d(TAG, "Main")
            LesSonsEnFrançaisTheme(
                darkTheme = darkTheme, //appViewModel.isDarkMode()
                dyslexia = dyslexia //appViewModel.isDyslexia()
            ) {
                //Navigation(appViewModel)
                Navigation(preferences)
            }
        }
    }
}
