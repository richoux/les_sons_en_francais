package fr.richoux.lessonsenfrancais.ui

import java.time.LocalDateTime
import kotlin.random.Random

data class AppUIState(
    val indexSimple: Int = 0,
    val indexComplex: Int = 0,
    val index: Int = 0,
    val selectCards: Int = 0,
    var soundID: Int = 2131755008, // 'a' sound ID
    val soundText: String = "",
    val darkMode: Boolean = false,
    val mustSwitchMode: Boolean = false,
    val lastRoute: String = "home",
    val startDate: LocalDateTime = LocalDateTime.now(),
    val random: Random? = null
)