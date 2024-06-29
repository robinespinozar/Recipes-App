package com.raerossi.recipesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raerossi.recipesapp.data.local.Tables
import com.raerossi.recipesapp.data.local.entities.IngredientEntity
import com.raerossi.recipesapp.data.local.entities.RecipeEntity

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRecipes(listRecipes: List<RecipeEntity>)

    @Query("Delete from " + Tables.RECIPE)
    suspend fun deleteAllRecipes()

    @Query("Select * from " + Tables.RECIPE + " where idRecipe =:idRecipe")
    suspend fun getRecipeById(idRecipe: Int): RecipeEntity

    @Query("Select * from " + Tables.RECIPE + " where lower(name) like '%' || :name || '%'")
    suspend fun getFilteredRecipes(name: String): List<RecipeEntity>
}
