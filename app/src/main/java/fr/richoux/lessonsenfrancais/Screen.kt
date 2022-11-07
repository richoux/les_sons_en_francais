package fr.richoux.lessonsenfrancais

sealed class Screen(val route: String) {
    object Home: Screen( "home" )
    object CardScreen: Screen( "card_screen" )
}
