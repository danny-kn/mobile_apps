// https://developer.android.com/training/data-storage/room/accessing-data

package com.example.mobileapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.lifecycle.LiveData
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import java.util.UUID

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipeId")
    suspend fun getRecipeById(recipeId: UUID): Recipe?

    @Query("DELETE FROM recipes WHERE recipe_id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)
}
