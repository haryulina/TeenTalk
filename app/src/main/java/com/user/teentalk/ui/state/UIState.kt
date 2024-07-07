package com.user.teentalk.ui.state

sealed class UIState<out T: Any?> {
    object Loading : UIState<Nothing>()
    data class Success<out T: Any>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}
