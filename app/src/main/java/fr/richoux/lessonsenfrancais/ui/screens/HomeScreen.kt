package fr.richoux.lessonsenfrancais.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.richoux.lessonsenfrancais.R
import fr.richoux.lessonsenfrancais.ui.AppViewModel
import fr.richoux.lessonsenfrancais.ui.DrawerMenu
import fr.richoux.lessonsenfrancais.ui.TopBar
import fr.richoux.lessonsenfrancais.ui.customShape
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    uppercase: Boolean,
    lowercase: Boolean,
    cursive: Boolean,
    onSelected: (Context, Int) -> Unit = {it, index -> Unit},
    onHomeClicked: () -> Unit = {}
){
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val allSounds = context.getResources().getStringArray(R.array.sounds)

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
                onHomeClicked = onHomeClicked,
                appViewModel = appViewModel
            )
        },
        drawerShape = customShape(),
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                items(allSounds.size) {
                        sound ->
                    var text = allSounds[sound]
                    when (text) {
                        "doo" -> text = "do"
                        "cca" -> text = "ça"
                        "ee" -> text = "é"
                        "eee" -> text = "è"
                        "eeee" -> text = "ê"
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        shape = RectangleShape,
                        contentPadding = PaddingValues(5.dp),
                        border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
                        onClick = {
                            onSelected(context, sound)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = MaterialTheme.colors.background,
                            contentColor = MaterialTheme.colors.secondary
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        )
                        {
                            when( uppercase ) {
                                true -> {
                                    Text(
                                        text = text.uppercase(),
                                        color = MaterialTheme.colors.secondary,
                                        fontSize = 36.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            when( lowercase ) {
                                true -> {
                                    Text(
                                        text = text,
                                        color = MaterialTheme.colors.secondary,
                                        fontSize = 36.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                else -> {
                                    when( uppercase ) {
                                        false ->{
                                            when( cursive ) {
                                                false -> {
                                                    Text(
                                                        text = text,
                                                        color = MaterialTheme.colors.secondary,
                                                        fontSize = 36.sp,
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            when( cursive ) {
                                true -> {
                                    Text(
                                        text = text,
                                        color = MaterialTheme.colors.secondary,
                                        fontFamily = FontFamily.Cursive,
                                        fontSize = 36.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LesSonsEnFrançaisTheme {
        HomeScreen(appViewModel = viewModel(),true,true,true)
    }
}