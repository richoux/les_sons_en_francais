package fr.richoux.lessonsenfrancais.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.richoux.lessonsenfrancais.R
import fr.richoux.lessonsenfrancais.ui.theme.BackgroundTwitter

fun customShape() =  object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f,0f,400f /* width */, 150f /* height */))
    }
}

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
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
        .fillMaxHeight()
        .background(MaterialTheme.colors.primaryVariant),
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
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                    modifier = Modifier,
                    tint = BackgroundTwitter
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DrawerMenu(
    onHomeClicked: () -> Unit = {}
) {
    DrawerMenuShape(
        items = listOf(
            MenuItem(
                id = "home",
                title = "Accueil",
                contentDescription = "Aller à l'écran-titre",
                icon = Icons.Default.Home
            ),
//                    MenuItem(
//                        id = "settings",
//                        title = "Options",
//                        contentDescription = "Pour gérer les options",
//                        icon = Icons.Default.Settings
//                    )
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

