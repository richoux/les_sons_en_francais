package com.github.richoux.les_sons_en_francais.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.richoux.les_sons_en_francais.ui.AppViewModel
import com.github.richoux.les_sons_en_francais.ui.DrawerMenu
import com.github.richoux.les_sons_en_francais.ui.TopBar
import kotlinx.coroutines.launch
import com.github.richoux.les_sons_en_francais.R

// from https://stackoverflow.com/questions/68611320/remember-lazycolumn-scroll-position-jetpack-compose
private val SaveMap = mutableMapOf<String, KeyParams>()

private data class KeyParams(
    val params: String = "",
    val index: Int,
    val scrollOffset: Int
)

@Composable
fun rememberForeverLazyListState(
    key: String,
    params: String = "",
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    val scrollState = rememberSaveable(saver = LazyListState.Saver) {
        var savedValue = SaveMap[key]
        if (savedValue?.params != params) savedValue = null
        val savedIndex = savedValue?.index ?: initialFirstVisibleItemIndex
        val savedOffset = savedValue?.scrollOffset ?: initialFirstVisibleItemScrollOffset
        LazyListState(
            savedIndex,
            savedOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            val lastIndex = scrollState.firstVisibleItemIndex
            val lastOffset = scrollState.firstVisibleItemScrollOffset
            SaveMap[key] = KeyParams(params, lastIndex, lastOffset)
        }
    }
    return scrollState
}

@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    uppercase: Boolean,
    lowercase: Boolean,
    cursive: Boolean,
    onSelected: (Context, Int) -> Unit = {_, _ -> Unit},
    onHomeClicked: () -> Unit = {},
    onAboutClicked: () -> Unit = {}
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
                appViewModel = appViewModel,
                onHomeClicked = onHomeClicked,
                onAboutClicked = onAboutClicked
            )
        },
        content = { innerPadding ->
            LazyColumn(
                state = rememberForeverLazyListState(key = "HomeScreen"),
                modifier = Modifier.fillMaxHeight().padding(innerPadding).padding(WindowInsets.systemBars.asPaddingValues()),
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
                            if( uppercase ) {
                                Text(
                                    text = text.uppercase(),
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 36.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            if( lowercase ) {
                                Text(
                                    text = text,
                                    color = MaterialTheme.colors.secondary,
                                    fontSize = 36.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                            else
                            {
                                if( !uppercase && !cursive) {
                                    // if everything is off, force lowercase
                                    Text(
                                        text = text,
                                        color = MaterialTheme.colors.secondary,
                                        fontSize = 36.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            if( cursive ) {
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
    )
}
