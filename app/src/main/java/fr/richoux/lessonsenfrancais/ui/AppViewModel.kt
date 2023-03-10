package fr.richoux.lessonsenfrancais.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import fr.richoux.lessonsenfrancais.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

private const val TAG = "AppViewModel"

class AppViewModel(preferences: SharedPreferences) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    private val _options = MutableStateFlow(OptionsData())
    val options: StateFlow<OptionsData> = _options.asStateFlow()

    private val _editor = preferences.edit()

    init {
        _uiState.value = preferences.getString("soundText", "")?.let {
            AppUIState(
                index = preferences.getInt("index", 0),
                soundID = preferences.getInt("soundID", 2131755008),
                soundText = it
            )
        }!!
        _options.value = OptionsData(
            uppercase = preferences.getBoolean("uppercase", true),
            lowercase = preferences.getBoolean("lowercase", true),
            cursive = preferences.getBoolean("cursive", true),
            darkMode = preferences.getBoolean("darkMode", false)
        )
        Log.d(TAG, "AppViewModel - index=${_uiState.value.index}, soundText=${_uiState.value.soundText}, soundID=${_uiState.value.soundID}, darkMode=${_options.value.darkMode}, startDate=${_uiState.value.startDate}")
    }

    fun convertTextFile(textFile: String): String {
        var text = textFile
        when (text) {
            "aille" -> text = "ail"
            "ain" -> text = "in"
            "au" -> text = "o"
            "am" -> text = "an"
            "ca" -> text = "ka"
            "cca" -> text = "sa"
            "ce" -> text = "se"
            "ci" -> text = "si"
            "co" -> text = "ko"
            "cu" -> text = "ku"
            "eau" -> text = "o"
            "ee" -> text = "et"
            "eee" -> text = "ai"
            "eeee" -> text = "ai"
            "ei" -> text = "ai"
            "eille" -> text = "eil"
            "ein" -> text = "in"
            "em" -> text = "an"
            "en" -> text = "an"
            "er" -> text = "et"
            "est" -> text = "ai"
            "eu" -> text = "e"
            "euille" -> text = "euil"
            "im" -> text = "in"
            "y" -> text = "i"
            "py" -> text = "pi"
            "by" -> text = "bi"
            "ly" -> text = "li"
            "ty" -> text = "ti"
            "ry" -> text = "ri"
            "sy" -> text = "si"
            "vy" -> text = "vi"
            "ny" -> text = "ni"
            "my" -> text = "mi"
            "dy" -> text = "di"
            "zy" -> text = "zi"
            "ky" -> text = "ki"
            "gy" -> text = "gi"
            "cy" -> text = "si"
            "chy" -> text = "chi"
            "om" -> text = "on"
            "ouille" -> text = "ouil"
            "qua" -> text = "ka"
            "que" -> text = "ke"
            "qui" -> text = "ki"
            "quo" -> text = "ko"
            "un" -> text = "in"
        }
        return text
    }

    fun updateCard(context: Context, newIndex: Int) {
        val text: String = context.getResources().getStringArray(R.array.sounds)[newIndex]
        val id: Int = context.getResources().getIdentifier(convertTextFile(text),"raw",context.getPackageName())
        _uiState.update { currentState ->
            currentState.copy(
                index = newIndex,
                soundID = id,
                soundText = text
            )
        }
        _editor.apply {
            putInt("index", newIndex)
            putInt("soundID", id)
            putString("soundText", text)
            apply()
        }
        Log.d(TAG, "AppViewModel - soundText=${_uiState.value.soundText}, soundID=${_uiState.value.soundID}, startDate=${_uiState.value.startDate}")
    }

    fun numberCards(context: Context) : Int{
        return context.getResources().getStringArray(R.array.sounds).size
    }

    fun randomCard(context: Context) {
        if(_uiState.value.random == null) {
            val callDate = LocalDateTime.now()
            val diffSeconds = ChronoUnit.SECONDS.between(_uiState.value.startDate, callDate)
            _uiState.update { currentState ->
                currentState.copy(
                    random = Random(diffSeconds)
                )
            }
        }
        val numberCards: Int = numberCards(context)
        var newIndex: Int = _uiState.value.random!!.nextInt(0, numberCards)
        while(newIndex == _uiState.value.index)
            newIndex = _uiState.value.random!!.nextInt(0, numberCards)
        updateCard(context, newIndex)
    }

    fun previousCard(context: Context) {
        val newIndex: Int
        val numberCards: Int = numberCards(context)

        if (_uiState.value.index == 0)
            newIndex = numberCards - 1
        else
            newIndex = _uiState.value.index - 1

        updateCard(context, newIndex)
    }

    fun nextCard(context: Context) {
        val newIndex: Int
        val numberCards: Int = numberCards(context)

        if (_uiState.value.index == numberCards - 1)
            newIndex = 0
        else
            newIndex = _uiState.value.index + 1

        updateCard(context, newIndex)
    }

    fun getLastRoute(): String {
        return _uiState.value.lastRoute
    }

    fun isDarkTheme(): Boolean {
        return _options.value.darkMode
    }

    fun updateDarkTheme(newValue: Boolean) {
        _options.update { currentState ->
            currentState.copy(
                darkMode = newValue
            )
        }
        _editor.apply {
            putBoolean("darkMode", newValue)
            apply()
        }
    }

    fun isUppercase(): Boolean {
        return _options.value.uppercase
    }

    fun updateUppercase(newValue: Boolean) {
        _options.update { currentState ->
            currentState.copy(
                uppercase = newValue
            )
        }
        _editor.apply {
            putBoolean("uppercase", newValue)
            apply()
        }
    }

    fun isLowercase(): Boolean {
        return _options.value.lowercase
    }

    fun updateLowercase(newValue: Boolean) {
        _options.update { currentState ->
            currentState.copy(
                lowercase = newValue
            )
        }
        _editor.apply {
            putBoolean("lowercase", newValue)
            apply()
        }
    }

    fun isCursive(): Boolean {
        return _options.value.cursive
    }

    fun updateCursive(newValue: Boolean) {
        _options.update { currentState ->
            currentState.copy(
                cursive = newValue
            )
        }
        _editor.apply {
            putBoolean("cursive", newValue)
            apply()
        }
    }

}
