package com.github.richoux.les_sons_en_francais.ui

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.github.richoux.les_sons_en_francais.ui.theme.BackgroundTwitter
import com.github.richoux.les_sons_en_francais.ui.theme.Black
import com.github.richoux.les_sons_en_francais.ui.theme.Mulish
import com.github.richoux.les_sons_en_francais.R

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector?,
    val hasIcon: Boolean = false,
    val isTextCursive: Boolean = false,
    val isSwitch: Boolean = false,
    val onSwitchSwitched: (Boolean) -> Unit = {},
    val switchValue: Boolean = false,
    val onClick: () -> Unit = {}
)

@Composable
fun TopBar (
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
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
                    Button(
                        onClick = when( item.hasIcon ) {
                            true -> {
                                {onItemClick(item)}
                            }
                            else -> {
                                {item.onClick()}
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(MaterialTheme.colors.primary)
                    ) {
                        if( item.hasIcon ) {
                            item.icon?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = item.contentDescription,
                                    modifier = Modifier,
                                    tint = BackgroundTwitter
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                        else {
                            Spacer(modifier = Modifier.width(32.dp))
                        }
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
            Divider(color = Black, thickness = 1.dp)
        }
    }
}

@Composable
fun DrawerMenu(
    appViewModel: AppViewModel,
    onHomeClicked: () -> Unit = {},
    onAboutClicked: () -> Unit = {}
) {
    DrawerMenuShape(
        items = listOf(
            MenuItem(
                id = "home",
                title = "Liste des sons",
                contentDescription = "Retour sur la liste des sons",
                icon = Icons.Default.List,
                hasIcon = true
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
            ),
            MenuItem(
                id = "about",
                title = "À propos",
                contentDescription = "À propos de cette application",
                icon = null,
                onClick = { onAboutClicked() }
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

