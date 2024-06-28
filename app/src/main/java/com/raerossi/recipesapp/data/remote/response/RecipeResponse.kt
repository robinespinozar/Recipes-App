package com.raerossi.recipesapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.raerossi.recipesapp.data.remote.model.RecipeModel

data class RecipeResponse(
    @SerializedName("recipes") val recipes: List<RecipeModel>
)