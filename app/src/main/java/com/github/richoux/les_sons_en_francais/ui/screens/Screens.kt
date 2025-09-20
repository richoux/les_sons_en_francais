package com.github.richoux.les_sons_en_francais.ui.screens

sealed class Screens(val route: String) {
    object HomeScreen: Screens( "home" )
    object CardScreen: Screens( "card_screen" )
    object AboutScreen: Screens( "about" )
}
