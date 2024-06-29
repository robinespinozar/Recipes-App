package com.raerossi.recipesapp.ui.features.detail

data class DetailUiState(
    val isLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val messageError: String = ""
)
