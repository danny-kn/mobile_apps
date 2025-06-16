// https://developer.android.com/training/data-storage/room/accessing-data

package com.example.mobileapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.lifecycle.LiveData
import androidx.room.Query
import java.util.UUID

@Dao
interface IngredientDao {
    @Insert
    suspend fun insertIngredients(ingredients: List<Ingredient>)

    @Update
    suspend fun updateIngredients(ingredients: List<Ingredient>)

    @Delete
    suspend fun deleteIngredients(ingredients: List<Ingredient>)

    @Query("SELECT * FROM ingredients WHERE recipe_id = :recipeId")
    suspend fun getIngredientsByRecipeId(recipeId: UUID): List<Ingredient>

    @Query("DELETE FROM ingredients WHERE recipe_id = :recipeId")
    suspend fun deleteIngredientsByRecipeId(recipeId: UUID)
}
