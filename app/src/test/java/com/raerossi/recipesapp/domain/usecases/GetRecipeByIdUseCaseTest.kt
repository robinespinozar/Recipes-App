package com.raerossi.recipesapp.domain.usecases

import com.raerossi.recipesapp.data.RecipeRepository
import com.raerossi.recipesapp.domain.model.Recipe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetRecipeByIdUseCaseTest{

    @RelaxedMockK
    private lateinit var repository: RecipeRepository

    lateinit var getRecipeByIdUseCase: GetRecipeByIdUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRecipeByIdUseCase = GetRecipeByIdUseCase(repository)
    }

    @Test
    fun `when getRecipeById returns correct recipe when id exists`() = runBlocking {
        val id = 1
        val expectedRecipe = Recipe(id = 1, name = "Test Recipe")
        coEvery { repository.getRecipeById(id) } returns expectedRecipe

        val response = getRecipeByIdUseCase(id)

        assertEquals(expectedRecipe, response)
    }
}