// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/defining-data

package com.example.mobileapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.Index
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["author_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("author_id")]
)

data class Recipe (
    // https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.uuid/-uuid/
    // NOTE: originally tried with kotlin uuid but did not like the experimental aspect
    // Now using the Java one, no source because it was suggested by IDE
    @PrimaryKey @ColumnInfo(name = "recipe_id") val recipeId: UUID,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "image_id") val imageId: String?,
    @ColumnInfo(name = "serves") val serves: Int?,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "prep_time") val prepTime: Float,
    @ColumnInfo(name = "author_id") val authorId: UUID,
//    @Ignore var instructions: List<Instruction> = listOf()
) : Serializable
