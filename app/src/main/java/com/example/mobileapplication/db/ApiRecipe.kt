package com.example.mobileapplication.db

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.UUID

// https://stackoverflow.com/questions/68522805/android-json-mapping-retrofit
data class ApiRecipe(
    val recipeId: UUID,
    val title: String,
    val imageId: String,
    val serves: Int?,
    val difficulty: Int,
    val prepTime: Float,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>,
    val author: User,
    val description: String
) : Serializable