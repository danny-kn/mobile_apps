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
interface InstructionDao {
    @Insert
    suspend fun insertInstructions(instructions: List<Instruction>)

    @Update
    suspend fun updateInstructions(instructions: List<Instruction>)

    @Delete
    suspend fun deleteInstructions(instructions: List<Instruction>)

    @Query("SELECT * FROM instructions WHERE recipe_id = :recipeId")
    suspend fun getInstructionsByRecipeId(recipeId: UUID): List<Instruction>

    @Query("DELETE FROM instructions WHERE recipe_id = :recipeId")
    suspend fun deleteInstructionsByRecipeId(recipeId: UUID)
}
