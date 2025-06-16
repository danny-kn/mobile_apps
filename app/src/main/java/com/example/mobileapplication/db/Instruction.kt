// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/defining-data

package com.example.mobileapplication.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "instructions",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["recipe_id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["recipe_id", "step"]
)

data class Instruction(
    @ColumnInfo(name = "recipe_id") val recipeId: UUID,
    @ColumnInfo(name = "step") val step: Int,
    @ColumnInfo(name = "value") val value: String
) : Serializable
