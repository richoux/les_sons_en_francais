package fr.richoux.lessonsenfrancais.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.richoux.lessonsenfrancais.ui.AppViewModel
import fr.richoux.lessonsenfrancais.ui.DrawerMenu
import fr.richoux.lessonsenfrancais.ui.TopBar
import fr.richoux.lessonsenfrancais.ui.customShape
import fr.richoux.lessonsenfrancais.ui.theme.LesSonsEnFrançaisTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onSimpleSelected: (context: Context) -> Unit = {},
    onComplexSelected: (context: Context) -> Unit = {},
    onHomeClicked: () -> Unit = {},
    appViewModel: AppViewModel
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
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(20.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
                    onClick = {
                        onSimpleSelected(context)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.secondary
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
                        .weight(1f)
                        .padding(20.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(5.dp),
                    border = BorderStroke(5.dp, MaterialTheme.colors.secondary),
                    onClick = {
                        onComplexSelected(context)
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.secondary
                    )
                ) {
                    Text(
                        text = "Sons complexes\n(ou, en, ail, ...)",
                        color = MaterialTheme.colors.secondary,
                        fontSize = 36.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LesSonsEnFrançaisTheme {
        HomeScreen(appViewModel = viewModel())
    }
}