package com.raerossi.recipesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raerossi.recipesapp.data.local.Tables
import com.raerossi.recipesapp.data.local.entities.IngredientEntity
import com.raerossi.recipesapp.domain.model.Ingredient

@Dao
interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllIngredients(listIngredients: List<IngredientEntity>)

    @Query("Delete from " + Tables.INGREDIENT)
    suspend fun deleteAllIngredients()

    @Query("Select * from " + Tables.INGREDIENT + " where idRecipe =:idRecipe")
    suspend fun getIngredientsByRecipe(idRecipe: Int): List<IngredientEntity>
}