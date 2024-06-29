package com.raerossi.recipesapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.raerossi.recipesapp.data.local.Tables

@Entity(tableName = Tables.RECIPE)
data class RecipeEntity (
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "id") val id: Int = 0,
   @ColumnInfo(name = "idRecipe") val idRecipe: Int,
   @ColumnInfo(name = "name") val name: String,
   @ColumnInfo(name = "imageUrl") val imageUrl: String?,
   @ColumnInfo(name = "time") val time: String,
   @ColumnInfo(name = "level") val level: String,
   @ColumnInfo(name = "stars") val stars: Double,
   @ColumnInfo(name = "preparation") val preparation: String
//
   // @Ignore val ingredients: List<IngredientEntity> = emptyList()
)