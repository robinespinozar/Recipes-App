package com.raerossi.recipesapp.ui.features.home

import com.raerossi.recipesapp.domain.model.Recipe

data class HomeCallbacks(
    val onRecipeClick: (Recipe) -> Unit,
    val onSearchTextChanged: (String) -> Unit,
    val onErrorDialogClick: () -> Unit
)
