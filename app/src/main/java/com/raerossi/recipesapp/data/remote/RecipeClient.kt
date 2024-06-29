package com.raerossi.recipesapp.data.remote

import com.raerossi.recipesapp.data.remote.response.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface RecipeClient {

    @GET("recetas")
    suspend fun getRecipes(): Response<RecipeResponse>
}