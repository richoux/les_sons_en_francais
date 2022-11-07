package com.example.lessonsenfrancais.ui

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lessonsenfrancais.R
import com.example.lessonsenfrancais.Screen
import com.example.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme

private const val TAG = "AppScreen"

@Composable
fun Home(
    appViewModel: AppViewModel,
    navController: NavController
){
//    val appUIState by appViewModel.uiState.collectAsState()
    appViewModel.initDarkLightMode( isSystemInDarkTheme() )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp),
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            onClick = {
                appViewModel.simpleCards()
                navController.navigate(Screen.CardScreen.route)
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.surface
            )
        ) {
            Text(
                text = "Sons simples\n(a, po, bi, ...)",
                color = MaterialTheme.colors.secondary,
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp),
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            onClick = {
                appViewModel.complexCards()
                navController.navigate(Screen.CardScreen.route)
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.surface
            )
        ) {
            Text(
                text = "Sons complexes\n(ou, en, ail, ...)",
                color = MaterialTheme.colors.secondary,
                fontSize = 36.sp,
                textAlign = TextAlign.Center
            )
        }

//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp)
//                .padding(20.dp),
//            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
//            onClick = {
//                Log.d(TAG, "Dark theme button pushed")
//                Log.d(TAG, "Previously, Dark theme = ${appUIState.darkMode}")
//                appViewModel.mustSwichMode()
//            }
//        ) {
//            Text(
//                text = "Dark/Light mode ",
//                color = MaterialTheme.colors.secondary,
//                fontSize = 24.sp,
//                textAlign = TextAlign.Center
//            )
//            Image(
//                painter = painterResource(id = R.drawable.icon_dark_light),
//                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
//                contentDescription = "",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .size(40.dp)
//            )
//        }
//        if( appUIState.mustSwitchMode ) {
//            Log.d(TAG, "Switch mode on")
//            appViewModel.switchMode()
//            Log.d(TAG, "Dark theme on? ${appUIState.darkMode}")
//            SelectTheme( darkTheme = appUIState.darkMode ) {
//                Home( appViewModel )
//            }
//        }

//        if( appUIState.selectCards > 0 )
//        {
//            Log.d(TAG, "Select cards, select=${appUIState.selectCards}")
//            AppScreen(appViewModel)
//        }
    }
}

@Composable
fun CardScreen(
    appViewModel: AppViewModel = AppViewModel(LocalContext.current),
    navController: NavController
) {
    val context = LocalContext.current
    val appUIState by appViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        CardDisplay( appUIState.soundText )
        SoundMaker(context, appUIState.soundID)
        BottomBar(appViewModel, navController)
    }
}

@Composable
fun CardDisplay(soundText: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        var textFontSize = 128.sp
        var text = soundText
        if( text == "ouille" || soundText == "euille" )
            textFontSize = 100.sp
        else if( text == "doo" )
            text = "do"
        else if( text == "cca" )
            text = "ça"
        else if( text == "ee" )
            text = "é"
        else if( text == "eee" )
            text = "è / ê"

        Text(
            text = text,
            color = MaterialTheme.colors.secondary,
            fontSize = textFontSize
        )
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colors.secondary,
            fontSize = textFontSize
        )
    }
}

@Composable
fun SoundMaker(context: Context, soundID: Int) {
    val mp: MediaPlayer = MediaPlayer.create(context, soundID)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { mp.start() },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .padding(40.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(5.dp),
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.surface
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.speaker),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(52.dp)
//                .clip(RoundedCornerShape(16.dp))
//                .background(Color.DarkGray)
            )
        }
    }
}

@Composable
fun BottomBar(
    appViewModel: AppViewModel,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            onClick = {
                appViewModel.previousCard()
            },
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .fillMaxHeight()
                .padding(10.dp),
            contentPadding = PaddingValues(5.dp),
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.surface
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .background(MaterialTheme.colors.background),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
            )
        }
        Button(
            onClick = { navController.navigate(Screen.Home.route) },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .padding(15.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(5.dp),
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.secondary
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_home),
                contentDescription = "",
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colors.background),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
            )
        }
        Button(
            onClick = {
                appViewModel.nextCard()
            },
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight()
                .padding(10.dp),
//                .pointerInput(Unit){
//                    detectTapGestures(
//                        onLongPress = {
//                            appViewModel.nextCard()
//                        }
//                    )
//                },
            contentPadding = PaddingValues(5.dp),
            border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.secondary
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "",
                modifier = Modifier
                    .size(128.dp)
                    .background(MaterialTheme.colors.background),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LesSonsEnFrançaisTheme() {
        val appViewModel: AppViewModel = AppViewModel(LocalContext.current)
        val navController = rememberNavController()
//        Home(appViewModel, navController)
        CardScreen(appViewModel, navController)
    }
}