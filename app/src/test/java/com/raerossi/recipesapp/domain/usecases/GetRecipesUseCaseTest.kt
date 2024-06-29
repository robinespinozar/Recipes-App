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

class GetRecipesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: RecipeRepository

    lateinit var getRecipesUseCase: GetRecipesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRecipesUseCase = GetRecipesUseCase(repository)
    }

    @Test
    fun `when the repository returns correct recipes values`(): Unit = runBlocking {
        //Given - Arrange
        val expectedRecipes = listOf(Recipe( 1, "Recipe 1", "url.com", "1 hora", "medium"))
        coEvery { repository.getRecipesFromApi() } returns expectedRecipes

        //When - Act
        val response  = getRecipesUseCase()

        //Then - Assert
        coVerify { repository.getRecipesFromApi() }
        coVerify { repository.clearAllRecipes() }
        coVerify { repository.insertAllRecipes(expectedRecipes) }
        assertEquals(expectedRecipes, response)
    }

}