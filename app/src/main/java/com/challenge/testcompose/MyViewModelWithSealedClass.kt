package com.challenge.testcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.testcompose.ui.theme.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModelWithSealedClass:ViewModel() {
    private var _counter = MutableStateFlow(0)
    var counter = _counter.asStateFlow()

    private var _uiEvent  = MutableSharedFlow<UiState>()
    var uiEvent  = _uiEvent.asSharedFlow()

    fun increament()
    {
        viewModelScope.launch {
            _uiEvent.emit(UiState.Loading)

            delay(2000)

            if((1..5).random() ==3)
            {
              _uiEvent.emit(UiState.Error("Failed to increment counter!"))
            }
            else
            {
                _counter.value++
                _uiEvent.emit(UiState.Success)
            }
        }
    }
}