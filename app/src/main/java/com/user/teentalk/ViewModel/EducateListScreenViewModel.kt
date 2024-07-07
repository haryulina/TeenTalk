package com.user.teentalk.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.user.teentalk.Data.Model.Educate.Educate
import com.user.teentalk.Data.Model.Educate.EducateRepository
import com.user.teentalk.ui.state.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EducateListScreenViewModel (private val repository: EducateRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UIState<List<Educate>>> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState<List<Educate>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    init {
        search("") // Load initial data
    }

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchEducate(_query.value)
            .catch {
                _uiState.value = UIState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UIState.Success(it)
            }
    }
}