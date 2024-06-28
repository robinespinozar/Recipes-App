package com.raerossi.recipesapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class IngredientModel(
    @SerializedName("id") val id: Int,
    @SerializedName("idRecipe") val idRecipe: Int,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String
)
