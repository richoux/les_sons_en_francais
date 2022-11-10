package fr.richoux.lessonsenfrancais

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import fr.richoux.lessonsenfrancais.ui.AppViewModel
import fr.richoux.lessonsenfrancais.ui.Navigation
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LesSonsEnFrançaisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
//                    val appViewModel: AppViewModel = AppViewModel(isSystemInDarkTheme())
//                    Navigation(appViewModel = appViewModel)
                    Navigation()
                }
            }
        }
    }
}
