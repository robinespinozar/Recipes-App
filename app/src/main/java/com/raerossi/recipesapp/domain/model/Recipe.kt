package com.raerossi.recipesapp.domain.model

data class Recipe(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String? = "",
    val time: String = "",
    val level: String = "",
    var ingredients: List<Ingredient> = emptyList(),
    val stars: Double = 0.0,
    val preparation: String = ""
)