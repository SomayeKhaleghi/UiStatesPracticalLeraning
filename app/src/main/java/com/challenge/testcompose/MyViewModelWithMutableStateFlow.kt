package com.challenge.testcompose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModelWithMutableStateFlow: ViewModel() {

    private var _counter = MutableStateFlow(0)
    var  counter:StateFlow<Int>  = _counter

    fun  increaseCounter()
    {
        _counter.value++
    }
}