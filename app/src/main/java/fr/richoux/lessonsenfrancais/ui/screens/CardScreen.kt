package fr.richoux.lessonsenfrancais.ui.screens

import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.richoux.lessonsenfrancais.R
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme

private const val TAG = "CardScreen"

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

    Text(
        text = text.uppercase(),
        color = MaterialTheme.colors.secondary,
        fontSize = textFontSize
    )
    Text(
        text = text,
        color = MaterialTheme.colors.secondary,
        fontSize = textFontSize
    )
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
    onHomeClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val modifier = Modifier.fillMaxHeight()

    Log.d(TAG, "Card $soundText with sound number $soundID")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy((-20).dp)
                )
                {
                    DisplayCard( soundText )
                }
            }
            else -> {
                Row(
                    modifier = modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.Bottom
                )
                {
                    DisplayCard( soundText )
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
                weightSoundButton = 0.75f
                paddingSoundButton = 20
            }
            else -> {
                weightSoundButton = 1f
                paddingSoundButton = 5
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(weightSoundButton),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { mp.start() },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(paddingSoundButton.dp),
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
                )
            }
        }

        ////////////////////
        // Navigation bar //
        ////////////////////
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
}

@Preview(showBackground = true)
@Composable
fun CardScreenPreview() {
    LesSonsEnFrançaisTheme {
        CardScreen( "a", 2131755008 )
    }
}