package com.example.mobileapplication.db

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val instructionDao: InstructionDao,
    private val ingredientDao: IngredientDao,
    ) {
    suspend fun insertRecipe(recipe: Recipe): Boolean {
        return withContext(Dispatchers.IO) {
            val existingRecipe = recipeDao.getRecipeById(recipe.recipeId)

            if (existingRecipe == null) {
                recipeDao.insertRecipe(recipe)
                Log.d("Recipe Test", "Recipe inserted: ${recipe.recipeId}")
                true
            } else {
                Log.d("Recipe Test", "Duplicate recipe, not inserting: ${recipe.recipeId}")
                false
            }
        }
    }

    suspend fun insertRecipeWithContent(recipe: Recipe, recipe2: ApiRecipe): Boolean {
        return withContext(Dispatchers.IO) {
            val existingRecipe = recipeDao.getRecipeById(recipe.recipeId)

            if (existingRecipe == null) {
                recipeDao.insertRecipe(recipe)
                Log.d("Recipe Test", "Recipe inserted: ${recipe.recipeId}")
                val instructionsWithRecipeId = recipe2.instructions.map { instruction -> Instruction(
                    recipeId = recipe.recipeId,
                    step = instruction.step,
                    value = instruction.value
                )
                }
                instructionDao.insertInstructions(instructionsWithRecipeId)
                Log.d("Recipe Test", "Instructions inserted: ${recipe.recipeId}")
                val ingredientsWithRecipeId = recipe2.ingredients.map {ingredient -> Ingredient(
                    recipeId = recipe.recipeId,
                    index = ingredient.index,
                    name = ingredient.name,
                    unit = ingredient.unit,
                    amount = ingredient.amount
                )}
                ingredientDao.insertIngredients(ingredientsWithRecipeId)
                Log.d("Recipe Test", "Ingredients inserted: ${recipe.recipeId}")
                true
            } else {
                Log.d("Recipe Test", "Duplicate recipe, not inserting: ${recipe.recipeId}")
                false
            }
        }
    }
    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    fun getAllRecipes(): LiveData<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }

    suspend fun getRecipeById(recipeId: UUID): Recipe? {
        return recipeDao.getRecipeById(recipeId)
    }

    suspend fun getIngredientsByRecipeId(recipeId: UUID): List<Ingredient> {
        return ingredientDao.getIngredientsByRecipeId(recipeId)
    }

    suspend fun getInstructionsByRecipeId(recipeId: UUID): List<Instruction> {
        return instructionDao.getInstructionsByRecipeId(recipeId)
    }
}
