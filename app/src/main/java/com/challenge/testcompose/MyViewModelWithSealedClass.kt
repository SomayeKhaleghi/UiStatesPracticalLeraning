package com.challenge.testcompose

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.testcompose.ui.theme.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch

class MyViewModelWithSealedClass:ViewModel() {
    private   var  _counter  = MutableStateFlow(0)
    var counter  = _counter.asStateFlow()

    private   var  _uiEvent  = MutableSharedFlow<UiState>()
    var uiEvent  = _uiEvent.asSharedFlow()

    fun increament()
    {

        viewModelScope.launch {
            _uiEvent.emit(UiState.Loading)

            if((1..5).random() ==3)
            {
              _uiEvent.emit(UiState.Error("Error"))
            }
            else
            {
                _counter.value++
                _uiEvent.emit(UiState.FetchValue)
            }
        }
    }
}