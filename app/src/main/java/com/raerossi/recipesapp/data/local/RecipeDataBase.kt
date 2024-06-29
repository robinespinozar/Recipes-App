package com.raerossi.recipesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raerossi.recipesapp.data.local.dao.IngredientDao
import com.raerossi.recipesapp.data.local.dao.RecipeDao
import com.raerossi.recipesapp.data.local.entities.IngredientEntity
import com.raerossi.recipesapp.data.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, IngredientEntity::class],
    version = 3,
    exportSchema = false
)
abstract class RecipeDataBase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao
    abstract fun getIngredientDao(): IngredientDao
}