package com.raerossi.recipesapp.data

import com.raerossi.recipesapp.data.local.dao.IngredientDao
import com.raerossi.recipesapp.data.local.dao.RecipeDao
import com.raerossi.recipesapp.data.mappers.toDataBase
import com.raerossi.recipesapp.data.mappers.toDomain
import com.raerossi.recipesapp.data.remote.RecipeService
import com.raerossi.recipesapp.domain.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val api: RecipeService,
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao
) {
    suspend fun getRecipesFromApi(): List<Recipe> {
        val response = api.getRecipes()
        return response.map { it.toDomain() }
    }

    suspend fun insertAllRecipes(recipes: List<Recipe>) {
        insertAllIngredients(recipes)
        recipeDao.insertAllRecipes(recipes.map { it.toDataBase() })
    }

    private suspend fun insertAllIngredients(recipes: List<Recipe>){
        recipes.forEach {
            val idRecipe = it.id
            ingredientDao.insertAllIngredients(it.ingredients.map { ingredient ->
                ingredient.idRecipe = idRecipe
                ingredient.toDataBase()
            })
        }
    }

    suspend fun clearAllRecipes() {
        ingredientDao.deleteAllIngredients()
        recipeDao.deleteAllRecipes()
    }

    suspend fun getRecipeById(id: Int): Recipe {
        val recipe = recipeDao.getRecipeById(id).toDomain()
        recipe.ingredients = ingredientDao.getIngredientsByRecipe(id).map { it.toDomain() }
        return recipe
    }

    suspend fun getFilteredRecipes(filterValue: String): List<Recipe> {
        val recipes = recipeDao.getFilteredRecipes(filterValue).map { it.toDomain() }
        recipes.forEach { recipe ->
            recipe.ingredients = ingredientDao.getIngredientsByRecipe(recipe.id).map { it.toDomain() }
        }
        return recipes
    }
}