package com.raerossi.recipesapp.domain.model

import com.google.gson.annotations.SerializedName

data class Ingredient(
    val id: Int = 0,
    var idRecipe: Int = 0,
    val name: String? = "",
    val quantity: String = ""
)