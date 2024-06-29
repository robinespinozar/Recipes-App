package com.raerossi.recipesapp.ui.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raerossi.recipesapp.domain.model.Recipe
import com.raerossi.recipesapp.domain.usecases.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _filterValue = MutableLiveData<String>()
    val filterValue: LiveData<String> = _filterValue

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                _recipes.value = getRecipesUseCase()
            } catch (e: Exception) {
                _uiState.update { it.copy(showErrorDialog = true , messageError = e.message.toString()) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun getFilterRecipes(filterValue: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                _filterValue.value = filterValue
               // _recipes.value = getFilteredRecipesUseCase(filterValue.trim())
            } catch (e: Exception) {
                _uiState.update { it.copy(showErrorDialog = true , messageError = e.message.toString()) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun hideErrorDialog() {
        _uiState.update { it.copy(showErrorDialog = false) }
    }
}