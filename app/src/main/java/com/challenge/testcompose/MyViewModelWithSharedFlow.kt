package com.challenge.testcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModelWithSharedFlow : ViewModel() {
    private var _counter = MutableStateFlow(0)
    var counter = _counter.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    var isLoading = _isLoading.asStateFlow()

    private var _eventFlow = MutableSharedFlow<String>()
    var eventFlow: SharedFlow<String> = _eventFlow.asSharedFlow()

    fun incrementCounter() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000)
            if ((1..5).random() == 3) {
                _eventFlow.emit("Failed to increment counter!")
            } else {
                _counter.value += 1
                _eventFlow.emit("Counter incremented")
            }
            _isLoading.value = false
        }
    }
}