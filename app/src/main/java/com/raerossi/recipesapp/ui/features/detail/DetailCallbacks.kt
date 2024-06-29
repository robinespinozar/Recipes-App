package com.raerossi.recipesapp.ui.features.detail

import android.graphics.drawable.Drawable

data class DetailCallbacks (
    val onBackClick : () -> Unit,
    val onErrorDialogClick: () -> Unit,
    val onSetBackgroundColor: (Drawable) -> Unit
)