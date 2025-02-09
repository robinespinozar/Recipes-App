package com.raerossi.recipesapp.domain.usecases

import com.raerossi.recipesapp.data.RecipeRepository
import com.raerossi.recipesapp.domain.model.Recipe
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): List<Recipe> {
        val recipes = repository.getRecipesFromApi()

        return if (recipes.isEmpty()) {
            val recipesDB = repository.getFilteredRecipes("") // with filterValue = "" (get all recipes in database)
            return recipesDB
        } else {
            repository.clearAllRecipes()
            repository.insertAllRecipes(recipes)
            return recipes
        }


        // val recipes = repository.getRecipesFromApi()
        // repository.clearAllRecipes()
        // repository.insertAllRecipes(recipes)
        // return recipes
    }
}