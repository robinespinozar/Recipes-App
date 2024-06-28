package com.raerossi.recipesapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class RecipeModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ingredients") val ingredients: List<IngredientModel>,
    @SerializedName("image") val imageUrl: String?,
    @SerializedName("time") val time: String,
    @SerializedName("level") val level: String,
    @SerializedName("stars") val stars: Double,
    @SerializedName("preparation") val preparation: String
)