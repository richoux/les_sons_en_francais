package com.example.lessonsenfrancais

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    val card = SimpleCard(this)
                    card.Display()
                }
            }
        }
    }
}

class SimpleCard(val context: Context) {
    var index: Int

    init {
        index = 0
    }

    fun previousCard(numberCards: Int) {
        if (index == 0)
            index = numberCards - 1
        else
            index--
    }

    fun nextCard(numberCards: Int) {
        if (index == numberCards - 1)
            index = 0
        else
            index++
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Display() {
        val sounds = stringArrayResource(R.array.simple)
        val numberCards: Int = sounds.size
        var soundID by rememberSaveable { mutableStateOf(context.getResources().getIdentifier(
            sounds[index],
            "raw",
            context.getPackageName())
        ) }
        val mp: MediaPlayer = MediaPlayer.create(context, soundID)
        var soundText by rememberSaveable { mutableStateOf(sounds[index]) }

        Surface(color = Color.Black, onClick = { mp.start() }) {}

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                )
                {
                    var textFontSize = 128.sp
                    if( soundText == "ouille" || soundText == "euille" )
                        textFontSize = 100.sp
                    else if( soundText == "doo" )
                        soundText = "do"
                    else if( soundText == "cca" )
                        soundText = "ça"
                    else if( soundText == "ee" )
                        soundText = "é"
                    else if( soundText == "eee" )
                        soundText = "è / ê"

                    Text(
                        text = soundText,
                        color = Color.White,
                        fontSize = textFontSize
                    )
                    Text(
                        text = soundText.uppercase(),
                        color = Color.White,
                        fontSize = textFontSize
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.speaker),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(52.dp)
//                .clip(RoundedCornerShape(16.dp))
//                .background(Color.DarkGray)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        previousCard(numberCards)
                        soundText = sounds[index]
                        soundID = context.getResources().getIdentifier(
                            sounds[index],
                            "raw",
                            context.getPackageName())
                    },
                    shape = CircleShape,
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, Color.White),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "",
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.Black),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
//            Button(
//                onClick = { /* ... */ },
//                shape = CircleShape,
//                contentPadding = PaddingValues(5.dp),
//                colors = ButtonDefaults.textButtonColors(
//                    backgroundColor = Color.Black,
//                    contentColor = Color.White
//                )
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.ic_home),
//                    contentDescription = "",
//                    modifier = Modifier.size(64.dp).background(Color.Black),
//                    colorFilter = ColorFilter.tint(Color.White)
//                )
//            }
                Button(
                    onClick = {
                        nextCard(numberCards)
                        soundText = sounds[index]
                        soundID = context.getResources().getIdentifier(
                            sounds[index],
                            "raw",
                            context.getPackageName())
                    },
                    shape = CircleShape,
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, Color.White),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "",
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.Black),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        }
    }
}
