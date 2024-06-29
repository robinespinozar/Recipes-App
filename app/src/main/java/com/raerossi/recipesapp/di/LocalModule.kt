package com.raerossi.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.raerossi.recipesapp.data.local.RecipeDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
   private const val RECIPE_DATA_BASE = "recipe_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): RecipeDataBase {
        return Room.databaseBuilder(context, RecipeDataBase::class.java, RECIPE_DATA_BASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: RecipeDataBase) = database.getRecipeDao()

    @Singleton
    @Provides
    fun provideIngredientDao(database: RecipeDataBase) = database.getIngredientDao()
}