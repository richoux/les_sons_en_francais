package fr.richoux.lessonsenfrancais.ui.screens

import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.richoux.lessonsenfrancais.R
import fr.richoux.lessonsenfrancais.ui.*
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme
import kotlinx.coroutines.launch

@Composable
fun DisplayCard(
    soundText: String
) {
    val textFontSize = 80.sp
    var text = soundText
    when (text) {
        "doo" -> text = "do"
        "cca" -> text = "ça"
        "ee" -> text = "é"
        "eee" -> text = "è"
        "eeee" -> text = "ê"
    }

    val configuration = LocalConfiguration.current
    val spacerModifier: Modifier = when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Modifier.height((-20).dp)
        }
        else -> {
            Modifier.width(30.dp)
        }
    }

    Text(
        text = text.uppercase(),
        color = MaterialTheme.colors.secondary,
        fontSize = textFontSize
    )
    Spacer(modifier = spacerModifier)
    Text(
        text = text,
        color = MaterialTheme.colors.secondary,
        fontSize = textFontSize
    )
    Spacer(modifier = spacerModifier)
    Text(
        text = text,
        color = MaterialTheme.colors.secondary,
        fontSize = textFontSize,
        style = MaterialTheme.typography.body2
    )
//            Text(
//                text = text,
//                color = MaterialTheme.colors.secondary,
//                fontSize = textFontSize
//            )
}

@Composable
fun CardScreen(
    soundText: String,
    soundID: Int,
    onPreviousClicked: (context: Context) -> Unit = {},
    onNextClicked: (context: Context) -> Unit = {},
    onRandomClicked: (context: Context) -> Unit = {},
    onHomeClicked: () -> Unit = {}
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
            DrawerMenu(onHomeClicked)
        },
        drawerShape = customShape(),
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
        },
        content = {
            Column(
                modifier = modifier.fillMaxHeight(),
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
                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.SpaceAround// .Vertical.Top //.spacedBy((-20).dp)
                        )
                        {
                            DisplayCard(soundText)
                        }
                    }
                    else -> {
                        Row(
                            modifier = modifier
                                .weight(1f)
                                .padding(5.dp)
                                .paddingFromBaseline(top = 0.dp),
                            Arrangement.SpaceBetween,
//                            horizontalArrangement = Arrangement.SpaceAround, //.Horizontal.SpaceEvenly, //.spacedBy(30.dp)
                            verticalAlignment = Alignment.Bottom
                        )
                        {
                            DisplayCard(soundText)
                        }
                    }
                }

                //////////////////
                // Sound button //
                //////////////////
                val mp: MediaPlayer = MediaPlayer.create(context, soundID)
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
//                Column(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .weight(weightSoundButton),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
                    Button(
                        onClick = { mp.start() },
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
//                }

                ////////////////////
                // Navigation bar //
                ////////////////////


/*
        val weightNavigationBar: Float = when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                0.25f
            }
            else -> {
                0.5f
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(weightNavigationBar),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
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
                    onHomeClicked()
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
                    onNextClicked(context)
                },
                modifier = Modifier
                    .weight(0.33f)
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
    */
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun CardScreenPreview() {
//    LesSonsEnFrançaisTheme {
//        CardScreen( "a", 2131755008 )
//    }
//}