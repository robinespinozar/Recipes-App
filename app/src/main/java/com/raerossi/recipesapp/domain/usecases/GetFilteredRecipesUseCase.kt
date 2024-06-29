package com.raerossi.recipesapp.domain.usecases

import com.raerossi.recipesapp.data.RecipeRepository
import javax.inject.Inject

class GetFilteredRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
){
   // suspend operator fun invoke(filterValue: String) = repository.getFilteredRecipes(filterValue)
}