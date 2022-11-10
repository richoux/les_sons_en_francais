package fr.richoux.lessonsenfrancais.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import fr.richoux.lessonsenfrancais.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random
import kotlin.random.nextInt

class AppViewModel(darkMode: Boolean = false) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

//    val simpleSounds: Array<String> = context.getResources().getStringArray(R.array.simple)
//    val numberSimpleCards: Int = simpleSounds.size
//    val complexSounds: Array<String> = context.getResources().getStringArray(R.array.complex)
//    val numberComplexCards: Int = complexSounds.size

    init {
        _uiState.value = AppUIState(darkMode = darkMode)
    }

    fun updateCard(context: Context, newIndex: Int) {
        if (_uiState.value.selectCards == 1) {
            val text: String = context.getResources().getStringArray(R.array.simple)[newIndex]
            _uiState.update { currentState ->
                currentState.copy(
                    index = newIndex,
                    indexSimple = newIndex,
                    soundID = context.getResources().getIdentifier(
                        text,
                        "raw",
                        context.getPackageName()
                    ),
                    soundText = text
                )
            }
        }
        else if (_uiState.value.selectCards == 2) {
            val text: String = context.getResources().getStringArray(R.array.complex)[newIndex]
            _uiState.update { currentState ->
                currentState.copy(
                    index = newIndex,
                    indexComplex = newIndex,
                    soundID = context.getResources().getIdentifier(
                        text,
                        "raw",
                        context.getPackageName()
                    ),
                    soundText = text
                )
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

        _uiState.update { currentState ->
            currentState.copy(
                index = index,
                soundID = context.getResources().getIdentifier(
                    text,
                    "raw",
                    context.getPackageName()
                ),
                soundText = text,
                selectCards = selection
            )
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

    fun initDarkLightMode(darkMode: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                darkMode = darkMode
            )
        }
    }

    fun getLastRoute(): String {
        return _uiState.value.lastRoute
    }

    fun mustSwichMode() {
        _uiState.update { currentState ->
            currentState.copy(
                mustSwitchMode = true
            )
        }
    }

    fun switchMode() {
        _uiState.update { currentState ->
            currentState.copy(
                darkMode = !_uiState.value.darkMode,
                mustSwitchMode = false
            )
        }
    }
}
