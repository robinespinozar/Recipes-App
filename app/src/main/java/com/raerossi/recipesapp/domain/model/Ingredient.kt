package com.raerossi.recipesapp.domain.model

import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: Int,
    val idRecipe: Int,
    val name: String,
    val quantity: String
)