package com.raerossi.recipesapp.data.mappers

import com.raerossi.recipesapp.data.local.entities.RecipeEntity
import com.raerossi.recipesapp.data.remote.model.RecipeModel
import com.raerossi.recipesapp.domain.model.Recipe

fun RecipeModel.toDomain() = Recipe(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    time = this.time,
    level = this.level,
    ingredients = this.ingredients.map { it.toDomain() },
    stars = this.stars,
    preparation = this.preparation
)

fun Recipe.toDataBase() = RecipeEntity(
    id = this.id,
    idRecipe = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    time = this.time,
    level = this.level,
    stars = this.stars,
    preparation = this.preparation
)

fun RecipeEntity.toDomain() = Recipe(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    time = this.time,
    level = this.level,
    stars = this.stars,
    preparation = this.preparation
)