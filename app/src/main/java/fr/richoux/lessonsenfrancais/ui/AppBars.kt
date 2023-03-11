package fr.richoux.lessonsenfrancais.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import fr.richoux.lessonsenfrancais.R
import fr.richoux.lessonsenfrancais.ui.theme.*

fun customShape() =  object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f,0f,595f /* width */, 900f /* height */))
    }
}

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector?,
    val isTextCursive: Boolean = false,
    val isSwitch: Boolean = false,
    val onSwitchSwitched: (Boolean) -> Unit = {},
    val switchValue: Boolean = false
)

@Composable
fun TopBar (
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.secondaryVariant,
        elevation = 3.dp,
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Ouvre le menu glissant"
                )
            }
        }
    )
}

@Composable
fun DrawerMenuShape(
    items: List<MenuItem>,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primary),
    itemTextStyle: TextStyle = TextStyle(
        fontSize = 18.sp,
        color = BackgroundTwitter
    ),
    onItemClick: (MenuItem) -> Unit
) {
    LazyColumn(modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .clickable {
                        onItemClick(item)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if(item.isSwitch) {
                    Button(
                        onClick = { item.onSwitchSwitched(!item.switchValue) },
                        shape = customShape(),
                        modifier = Modifier
                            .size(200.dp,50.dp)
                            .background(MaterialTheme.colors.primary)
                    ) {
                        val fontFamily: FontFamily = when(item.isTextCursive) {
                            false -> {
                                Mulish
                            }
                            else -> {
                                FontFamily.Cursive
                            }
                        }
                        val fontSize: TextUnit = when(item.isTextCursive) {
                            false -> {
                                18.sp
                            }
                            else -> {
                                24.sp
                            }
                        }
                        if(item.switchValue) {
                            Text(
                                text = "\u2705",
                                modifier = Modifier
                                    .background(MaterialTheme.colors.primary),
                                textAlign = TextAlign.Left
                            )
                        }
                        else{
                            Text(
                                text = "\u274C",
                                modifier = Modifier
                                    .background(MaterialTheme.colors.primary),
                                textAlign = TextAlign.Left
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.title,
                            style = itemTextStyle,
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary)
                                .fillMaxWidth(),
                            fontFamily = fontFamily,
                            fontSize = fontSize,
                            textAlign = TextAlign.Left
                        )
                    }
                }
                else {
                    item.icon?.let {
                        Button(
                            onClick = {onItemClick(item)},
                            shape = customShape(),
                            modifier = Modifier.background(MaterialTheme.colors.primary).size(200.dp,50.dp)
                        ) {
                            Icon(
                                imageVector = it,
                                contentDescription = item.contentDescription,
                                modifier = Modifier,
                                tint = BackgroundTwitter
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = item.title,
                                style = itemTextStyle,
                                modifier = Modifier
                                    .background(MaterialTheme.colors.primary)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
            Divider(color = Black, thickness = 1.dp)
        }
    }
}

@Composable
fun DrawerMenu(
    onHomeClicked: () -> Unit = {},
    appViewModel: AppViewModel
) {
    DrawerMenuShape(
        items = listOf(
            MenuItem(
                id = "home",
                title = "Liste des sons",
                contentDescription = "Retour sur la liste des sons",
                icon = Icons.Default.List
            ),
            MenuItem(
                id = "uppercase",
                title = "MAJUSCULES",
                contentDescription = "Afficher les lettres majuscules",
                icon = null,
                isSwitch = true,
                onSwitchSwitched = {
                    appViewModel.updateUppercase(it)
                },
                switchValue = appViewModel.isUppercase()
            ),
            MenuItem(
                id = "lowercase",
                title = "minuscules",
                contentDescription = "Afficher les lettres minuscules",
                icon = null,
                isSwitch = true,
                onSwitchSwitched = {
                    appViewModel.updateLowercase(it)
                },
                switchValue = appViewModel.isLowercase()
            ),
            MenuItem(
                id = "cursive",
                title = "cursives",
                contentDescription = "Afficher les lettres cursives",
                icon = null,
                isTextCursive = true,
                isSwitch = true,
                onSwitchSwitched = {
                    appViewModel.updateCursive(it)
                },
                switchValue = appViewModel.isCursive()
            ),
            MenuItem(
                id = "darkMode",
                title = "Thème sombre",
                contentDescription = "Passe du thème clair au thème sombre",
                icon = null,
                isSwitch = true,
                onSwitchSwitched = {
                    appViewModel.updateDarkTheme(it)
                },
                switchValue = appViewModel.isDarkTheme()
            )
        ),
        onItemClick = {
            onHomeClicked()
        }
    )
}

@Composable
fun BottomBar(
    content: @Composable RowScope.() -> Unit = {}
) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.secondaryVariant,
        elevation = 3.dp,
        content = content,
        modifier = Modifier.height(80.dp),
    )
}

