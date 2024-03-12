package com.learning.barkmobile.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.barkmobile.Data.AnimalRepository
import com.learning.barkmobile.model.Animal
import com.learning.barkmobile.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AnimalRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Animal>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Animal>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchAnimal(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateAnimal(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAnimal(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}