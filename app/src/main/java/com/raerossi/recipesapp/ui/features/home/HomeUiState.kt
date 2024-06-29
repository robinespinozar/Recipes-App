package com.raerossi.recipesapp.ui.features.home

data class HomeUiState (
    val isLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val messageError: String = ""
)