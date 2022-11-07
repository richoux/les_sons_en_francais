package fr.richoux.lessonsenfrancais.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import fr.richoux.lessonsenfrancais.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel(val context: Context) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUIState())
    val uiState: StateFlow<AppUIState> = _uiState.asStateFlow()

    val simpleSounds: Array<String> = context.getResources().getStringArray(R.array.simple)
    val numberSimpleCards: Int = simpleSounds.size
    val complexSounds: Array<String> = context.getResources().getStringArray(R.array.complex)
    val numberComplexCards: Int = complexSounds.size

    fun updateCard(newIndex: Int) {
        if (_uiState.value.selectCards == 1) {
            val text: String = simpleSounds[newIndex]
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
            val text: String = complexSounds[newIndex]
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

    fun changeCardType(selection: Int) {
        var text: String = ""
        var index: Int = 0

        if(selection == 1) {
            index = _uiState.value.indexSimple
            text = simpleSounds[index]
        }
        else if(selection == 2){
            index = _uiState.value.indexComplex
            text = complexSounds[index]
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

    fun numberCards() : Int{
        if(_uiState.value.selectCards == 1)
            return numberSimpleCards
        else
            return numberComplexCards
    }

    fun previousCard() {
        val newIndex: Int
        val numberCards: Int = numberCards()

        if (_uiState.value.index == 0)
            newIndex = numberCards - 1
        else
            newIndex = _uiState.value.index - 1

        updateCard(newIndex)
    }

    fun nextCard() {
        val newIndex: Int
        val numberCards: Int = numberCards()

        if (_uiState.value.index == numberCards - 1)
            newIndex = 0
        else
            newIndex = _uiState.value.index + 1

        updateCard(newIndex)
    }

    fun simpleCards(){
        changeCardType(1)
    }

    fun complexCards(){
        changeCardType(2)
    }

    fun initDarkLightMode(darkMode: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                darkMode = darkMode
            )
        }
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
