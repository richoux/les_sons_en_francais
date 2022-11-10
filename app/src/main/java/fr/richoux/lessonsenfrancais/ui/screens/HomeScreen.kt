package fr.richoux.lessonsenfrancais.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme

@Composable
fun HomeScreen(
    onSimpleSelected: (context: Context) -> Unit = {},
    onComplexSelected: (context: Context) -> Unit = {}
){
    val context = LocalContext.current
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
                onSimpleSelected(context)
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
                onComplexSelected(context)
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
//                appViewModel.mustSwitchMode()
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LesSonsEnFrançaisTheme {
        HomeScreen()
    }
}