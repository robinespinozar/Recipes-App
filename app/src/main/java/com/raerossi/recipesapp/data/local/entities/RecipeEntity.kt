package com.raerossi.recipesapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raerossi.recipesapp.data.local.Tables

@Entity(tableName = Tables.RECIPE)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ingredients") val ingredients: List<String>,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "level") val level: String,
    @ColumnInfo(name = "stars") val stars: Double,
    @ColumnInfo(name = "preparation") val preparation: String
)