package com.challenge.testcompose.ui.theme

sealed class UiState {
data object  Loading:UiState()
data object  Success:UiState()
data class  Error(var message: String):UiState()
}