package fr.richoux.lessonsenfrancais.ui

import java.time.LocalDateTime
import kotlin.random.Random

data class AppUIState(
    val index: Int = 0,
    var soundID: Int = 2131755008, // 'a' sound ID
    val soundText: String = "",
    val lastRoute: String = "home",
    val startDate: LocalDateTime = LocalDateTime.now(),
    val random: Random? = null
)