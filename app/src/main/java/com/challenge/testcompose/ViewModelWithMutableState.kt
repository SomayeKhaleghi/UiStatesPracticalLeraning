package com.challenge.testcompose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class ViewModelWithMutableState: ViewModel() {

    private var _counter = mutableIntStateOf(0)
    var  counter:State<Int>  = _counter

    fun  increaseCounter()
    {
        _counter.intValue++
    }
}