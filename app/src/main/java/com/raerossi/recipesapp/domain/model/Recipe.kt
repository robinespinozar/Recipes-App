package com.raerossi.recipesapp.domain.model

data class Recipe(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val time: String,
    val level: String,
    val ingredients: List<Ingredient>,
    val stars: Double,
    val preparation: String
)