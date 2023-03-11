package fr.richoux.lessonsenfrancais.ui.screens

import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.richoux.lessonsenfrancais.ui.AppViewModel
import fr.richoux.lessonsenfrancais.ui.DrawerMenu
import fr.richoux.lessonsenfrancais.ui.TopBar
import fr.richoux.lessonsenfrancais.ui.customShape
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    appViewModel: AppViewModel,
    onHomeClicked: () -> Unit = {}
){
    val context = LocalContext.current
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
                onHomeClicked = onHomeClicked,
                appViewModel = appViewModel
            )
        },
        drawerShape = customShape(),
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = "Muf"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    LesSonsEnFrançaisTheme {
        AboutScreen(appViewModel = viewModel())
    }
}