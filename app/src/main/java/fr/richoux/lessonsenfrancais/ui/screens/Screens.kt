package fr.richoux.lessonsenfrancais.ui.screens

sealed class Screens(val route: String) {
    object HomeScreen: Screens( "home" )
    object CardScreen: Screens( "card_screen" )
}
