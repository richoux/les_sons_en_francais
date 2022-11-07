package com.example.lessonsenfrancais

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.lessonsenfrancais.ui.AppViewModel
import com.example.lessonsenfrancais.ui.Navigation
import com.example.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LesSonsEnFrançaisTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation(appViewModel = AppViewModel(this))
//                    Home(appViewModel = AppViewModel(this))
                }
            }
        }
    }
}
