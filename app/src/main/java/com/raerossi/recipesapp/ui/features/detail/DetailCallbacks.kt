package com.raerossi.recipesapp.ui.features.detail

data class DetailCallbacks (
    val onBackClick : () -> Unit,
    val onErrorDialogClick: () -> Unit
)