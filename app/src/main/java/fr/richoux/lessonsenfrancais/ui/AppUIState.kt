package fr.richoux.lessonsenfrancais.ui

import fr.richoux.lessonsenfrancais.R
import java.time.LocalDateTime
import kotlin.random.Random

data class AppUIState(
    val index: Int = 0,
    val soundID: Int = R.raw.o,
    val soundText: String = "o",
    val lastRoute: String = "home",
    val startDate: LocalDateTime = LocalDateTime.now(),
    val random: Random? = null
)