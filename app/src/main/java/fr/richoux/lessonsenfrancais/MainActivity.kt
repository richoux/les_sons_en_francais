package fr.richoux.lessonsenfrancais

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import fr.richoux.lessonsenfrancais.ui.Navigation
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme

class MainActivity : ComponentActivity() {
    private val TAG = "Main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val preferences: SharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)
            val darkTheme: Boolean = preferences.getBoolean("darkMode", isSystemInDarkTheme())
            Log.d(TAG, "Main")
            LesSonsEnFrançaisTheme(
                darkTheme = darkTheme
            ) {
                Navigation(preferences)
            }
        }
    }
}
