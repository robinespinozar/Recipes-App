package com.raerossi.recipesapp.ui.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*

import com.raerossi.recipesapp.domain.model.Recipe
import com.raerossi.recipesapp.domain.usecases.GetFilteredRecipesUseCase
import com.raerossi.recipesapp.domain.usecases.GetRecipesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var getRecipesUseCase: GetRecipesUseCase

    @RelaxedMockK
    private lateinit var getFilteredRecipesUseCase: GetFilteredRecipesUseCase

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var rule: MainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(getRecipesUseCase, getFilteredRecipesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when homeViewModel initializes with correct default values`() = runTest {
        coEvery { getRecipesUseCase() } returns emptyList()

        val recipes = viewModel.recipes.first()
        val uiState = viewModel.uiState.first()

        assertEquals(emptyList<Recipe>(), recipes)
        assertEquals(HomeUiState(), uiState)
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(val dispatcher: TestDispatcher = StandardTestDispatcher()): TestWatcher() {

    override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)
    override fun finished(description: Description?) = Dispatchers.resetMain()
}