// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/defining-data

package com.example.mobileapplication.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "ingredients",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["recipe_id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["recipe_id", "index"]
)

data class Ingredient(
    @ColumnInfo(name = "recipe_id") val recipeId: UUID,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Float?,
    @ColumnInfo(name = "unit") val unit: String?
) : Serializable
