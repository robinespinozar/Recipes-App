package com.raerossi.recipesapp.domain.usecases

import com.raerossi.recipesapp.data.RecipeRepository
import com.raerossi.recipesapp.domain.model.Recipe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetFilteredRecipesUseCaseTest{
    @RelaxedMockK
    private lateinit var repository: RecipeRepository

    lateinit var getFilteredRecipesUseCase: GetFilteredRecipesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getFilteredRecipesUseCase = GetFilteredRecipesUseCase(repository)
    }

    @Test
    fun `when the use case returns correct filtered recipes when filter's value matches`(): Unit = runBlocking {
        //Given - Arrange
        val filterValue = "test"
        val expectedRecipes = listOf(Recipe(id = 1, name = "Test Recipe"))
        coEvery { repository.getFilteredRecipes(filterValue) } returns expectedRecipes

        //When - Act
        val response  = getFilteredRecipesUseCase(filterValue)

        //Then - Assert
        assertEquals(expectedRecipes, response)
    }

    @Test
    fun `when getFilteredRecipes returns empty list when filter does not match`() = runBlocking {
        //Given - Arrange
        val filterValue = "nonexistent"
        val expectedRecipes = emptyList<Recipe>()
        coEvery { repository.getFilteredRecipes(filterValue) } returns expectedRecipes

        //When - Act
        val actualRecipes = getFilteredRecipesUseCase(filterValue)

        //Then - Assert
        assertEquals(expectedRecipes, actualRecipes)
    }
}