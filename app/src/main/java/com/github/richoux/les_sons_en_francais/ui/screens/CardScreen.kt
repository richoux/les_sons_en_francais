package com.github.richoux.les_sons_en_francais.ui.screens

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.richoux.les_sons_en_francais.PlayerFactory
import com.github.richoux.les_sons_en_francais.ui.*
import kotlinx.coroutines.launch
import com.github.richoux.les_sons_en_francais.R


private const val TAG = "CardScreen"

@Composable
fun DisplayCard(
    soundText: String,
    uppercase: Boolean,
    lowercase: Boolean,
    cursive: Boolean,
) {
    var text = soundText
    when (text) {
        "doo" -> text = "do"
        "cca" -> text = "ça"
        "ee" -> text = "é"
        "eee" -> text = "è"
        "eeee" -> text = "ê"
        "inn" -> text = "in"
    }

    val configuration = LocalConfiguration.current
    val spacerModifier: Modifier = when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Modifier.height(IntrinsicSize.Min)
        }
        else -> {
            Modifier.width(30.dp)
        }
    }

    if (uppercase) {
        Text(
            text = text.uppercase(),
            color = MaterialTheme.colors.secondary,
            fontSize = 70.sp
        )
        Spacer(modifier = spacerModifier)
    }
    if (lowercase) {
        Text(
            text = text,
            color = MaterialTheme.colors.secondary,
            fontSize = 70.sp
        )
        Spacer(modifier = spacerModifier)
    }
    if (cursive) {
        Text(
            text = text,
            color = MaterialTheme.colors.secondary,
            fontSize = 70.sp,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun CardScreen(
    appViewModel: AppViewModel,
    soundText: String,
    soundID: Int,
    uppercase: Boolean,
    lowercase: Boolean,
    cursive: Boolean,
    onPreviousClicked: (context: Context) -> Unit = {},
    onNextClicked: (context: Context) -> Unit = {},
    onRandomClicked: (context: Context) -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onAboutClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val modifier = Modifier.fillMaxHeight()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                onMenuClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerMenu(
                appViewModel = appViewModel,
                onHomeClicked = onHomeClicked,
                onAboutClicked = onAboutClicked
            )
        },
        bottomBar = {
            BottomBar()
            {
                Button(
                    onClick = {
                        onPreviousClicked(context)
                    },
                    modifier = Modifier
                        .weight(0.33f)
                        .fillMaxHeight()
                        .padding(10.dp),
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.secondary
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
                    onClick = {
                        onRandomClicked(context)
                    },
                    modifier = Modifier
                        .weight(0.33f)
                        .fillMaxHeight()
                        .padding(10.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.secondary
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.random),
                        contentDescription = "",
                        modifier = Modifier
                            .size(64.dp)
                            .background(MaterialTheme.colors.background),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                    )
                }
                Button(
                    onClick = {
                        onNextClicked(context)
                    },
                    modifier = Modifier
                        .weight(0.33f)
                        .fillMaxHeight()
                        .padding(10.dp),
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
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.fillMaxHeight().padding(innerPadding).padding(WindowInsets.systemBars.asPaddingValues()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                //////////////////
                // Display card //
                //////////////////
                when (configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        Column(
                            modifier = modifier
                                .weight(1f)
                                .padding(20.dp),
                            Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            DisplayCard(soundText,uppercase, lowercase, cursive)
                        }
                    }
                    else -> {
                        Row(
                            modifier = modifier
                                .weight(1f)
                                .padding(5.dp)
                                .paddingFromBaseline(top = 0.dp),
                            Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        )
                        {
                            DisplayCard(soundText,uppercase, lowercase, cursive)
                        }
                    }
                }

                //////////////////
                // Sound button //
                //////////////////
                Log.d(TAG, "CardScreen - context=${context}, soundID=${soundID}")
                //var mp: MediaPlayer = MediaPlayer.create(context, soundID)
                val weightSoundButton: Float
                val paddingSoundButton: Int
                when (configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        weightSoundButton = 1f
                        paddingSoundButton = 100
                    }
                    else -> {
                        weightSoundButton = 1f
                        paddingSoundButton = 90
                    }
                }
                Button(
                    onClick = {
                        val mp = PlayerFactory( context, soundID )//: MediaPlayer = MediaPlayer.create(context, soundID)
                        mp.start()
                        while(mp.isPlaying()) {}
                        mp.stop()
                        mp.reset()
                        mp.release()
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(weightSoundButton)
                        .padding(bottom = paddingSoundButton.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.secondary
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.speaker),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(52.dp)
                    )
                }
            }
        }
    )
}
