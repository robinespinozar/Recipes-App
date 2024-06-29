package com.raerossi.recipesapp.data.mappers

import com.raerossi.recipesapp.data.local.entities.IngredientEntity
import com.raerossi.recipesapp.data.remote.model.IngredientModel
import com.raerossi.recipesapp.domain.model.Ingredient

fun IngredientModel.toDomain() = Ingredient(
    id = this.id,
    idRecipe = this.idRecipe,
    name = this.name,
    quantity = this.quantity
)

fun Ingredient.toDataBase() = IngredientEntity(
    id = this.id,
    idRecipe = this.idRecipe,
    name = this.name,
    quantity = this.quantity
)

fun IngredientEntity.toDomain() = Ingredient(
    id = this.id,
    idRecipe = this.idRecipe,
    name = this.name,
    quantity = this.quantity
)