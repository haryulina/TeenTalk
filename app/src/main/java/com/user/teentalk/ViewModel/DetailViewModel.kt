package com.user.teentalk.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.teentalk.Data.Model.Educate.Educate
import com.user.teentalk.Data.Model.Educate.EducateRepository
import com.user.teentalk.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: EducateRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UIState<Educate>> =
        MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<Educate>>
        get() = _uiState

    fun getEducateById(id: Int) = viewModelScope.launch {
        _uiState.value = UIState.Loading
        _uiState.value = UIState.Success(repository.getEducateById(id))
    }
}