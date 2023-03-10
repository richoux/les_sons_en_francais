package fr.richoux.lessonsenfrancais.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import fr.richoux.lessonsenfrancais.R
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random
import kotlinx.coroutines.flow.*

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
                indexSimple = preferences.getInt("indexSimple", 0),
                indexComplex = preferences.getInt("indexComplex", 0),
                index = preferences.getInt("index", 0),
                selectCards = preferences.getInt("selectCards", 0),
                soundID = preferences.getInt("soundID", 2131755008),
                soundText = it
            )
        }!!
        _options.value = OptionsData(
            darkMode = preferences.getBoolean("darkMode", false)
        )
        Log.d(TAG, "AppViewModel - index=${_uiState.value.index}, selectCards=${_uiState.value.selectCards}, soundText=${_uiState.value.soundText}, darkMode=${_options.value.darkMode}, startDate=${_uiState.value.startDate}")
    }

    fun updateCard(context: Context, newIndex: Int) {
        if (_uiState.value.selectCards == 1) {
            val text: String = context.getResources().getStringArray(R.array.simple)[newIndex]
            val id: Int = context.getResources().getIdentifier(text,"raw",context.getPackageName())
            _uiState.update { currentState ->
                currentState.copy(
                    index = newIndex,
                    indexSimple = newIndex,
                    soundID = id,
                    soundText = text
                )
            }
            _editor.apply {
                putInt("index", newIndex)
                putInt("indexSimple", newIndex)
                putInt("soundID", id)
                putString("soundText", text)
                apply()
            }
        }
        else if (_uiState.value.selectCards == 2) {
            val text: String = context.getResources().getStringArray(R.array.complex)[newIndex]
            val id: Int = context.getResources().getIdentifier(text,"raw",context.getPackageName())
            _uiState.update { currentState ->
                currentState.copy(
                    index = newIndex,
                    indexComplex = newIndex,
                    soundID = id,
                    soundText = text
                )
            }
            _editor.apply {
                putInt("index", newIndex)
                putInt("indexComplex", newIndex)
                putInt("soundID", id)
                putString("soundText", text)
                apply()
            }
        }
    }

    fun changeCardType(context: Context, selection: Int) {
        var text: String = ""
        var index: Int = 0
        val simpleSounds: Array<String> = context.getResources().getStringArray(R.array.simple)
        val complexSounds: Array<String> = context.getResources().getStringArray(R.array.complex)

        if(selection == 1) {
            index = _uiState.value.indexSimple
            text = context.getResources().getStringArray(R.array.simple)[index]
        }
        else if(selection == 2){
            index = _uiState.value.indexComplex
            text = context.getResources().getStringArray(R.array.complex)[index]
        }

        val id: Int = context.getResources().getIdentifier(text,"raw",context.getPackageName())

        _uiState.update { currentState ->
            currentState.copy(
                index = index,
                soundID = id,
                soundText = text,
                selectCards = selection
            )
        }

        _editor.apply {
            putInt("index", index)
            putInt("soundID", id)
            putString("soundText", text)
            putInt("selectCards", selection)
            apply()
        }
    }

    fun numberCards(context: Context) : Int{
        if(_uiState.value.selectCards == 1)
            return context.getResources().getStringArray(R.array.simple).size
        else
            return context.getResources().getStringArray(R.array.complex).size
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

    fun simpleCards(context: Context) {
        changeCardType(context,1)
    }

    fun complexCards(context: Context) {
        changeCardType(context, 2)
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
}
