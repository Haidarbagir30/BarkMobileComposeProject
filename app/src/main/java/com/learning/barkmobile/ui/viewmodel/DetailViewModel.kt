package com.learning.barkmobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.barkmobile.Data.AnimalRepository
import com.learning.barkmobile.model.Animal
import com.learning.barkmobile.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AnimalRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Animal>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Animal>>
        get() = _uiState

    fun getAnimalById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getAnimalById(id))
    }


    fun updateAnimal(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAnimal(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getAnimalById(id)
            }
    }

}