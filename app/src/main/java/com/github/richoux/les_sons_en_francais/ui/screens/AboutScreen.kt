package com.github.richoux.les_sons_en_francais.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.richoux.les_sons_en_francais.ui.AppViewModel
import com.github.richoux.les_sons_en_francais.ui.DrawerMenu
import com.github.richoux.les_sons_en_francais.ui.TopBar
import com.github.richoux.les_sons_en_francais.ui.theme.BlueTwitter
import kotlinx.coroutines.launch

// from https://developer.android.com/jetpack/compose/text
@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(tag = "URL",
            annotation = "https://github.com/richoux/les_sons_en_francais")
        withStyle(style = SpanStyle(color = BlueTwitter,
            fontWeight = FontWeight.Bold)
        ) {
            append("https://github.com/richoux/les_sons_en_francais")
        }

        pop()
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset,
                end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}


@Composable
fun AboutScreen(
    appViewModel: AppViewModel,
    onHomeClicked: () -> Unit = {},
    onAboutClicked: () -> Unit = {}
){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues()),
            onMenuClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
        Scaffold(
            scaffoldState = scaffoldState,
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
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(WindowInsets.navigationBars.asPaddingValues())
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround,
                ){
                item {
                    Text(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 18.sp,
                        text = "Cette application a été développée pour ma fille au tout début de son apprentissage de la lecture en français, en dehors du programme scolaire en France. Le fait de pouvoir répéter les sons l'a aidé, et je me suis dit que ça pourrait être utile à d'autres parents."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    Text(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 18.sp,
                        text = "L'ordre des sons est par complexité croissante, tel qu'il est enseigné dans les ateliers de lecture de l'Association des Familles Franco-Japonaises au Japon."
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    Text(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 18.sp,
                        text = "L'application est gratuite, sans publicité, sans accès aux données du téléphone, ne requiert aucune autorisation particulière, et est open source sous licence GNU GPL v3. Le code source peut être trouvé sur sa page GitHub :"
                    )
                }
                item {
                    AnnotatedClickableText()
                }
            }
        }
    )
}
