package com.example.mobileapplication.ui.recipe_detail_activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.db.Recipe
import com.example.mobileapplication.db.RecipeDatabase
import com.example.mobileapplication.db.RecipeRepository
import com.example.mobileapplication.db.User
import com.example.mobileapplication.db.Ingredient
import com.example.mobileapplication.db.Instruction
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class RecipeDetailViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: RecipeRepository

    init {
        val recipeDao = RecipeDatabase.getInstance(app).recipeDao()
        val ingredientDao = RecipeDatabase.getInstance(app).ingredientDao()
        val instructionDao = RecipeDatabase.getInstance(app).instructionDao()
        repository = RecipeRepository(recipeDao, instructionDao, ingredientDao)
        ensureUserExists()
    }

    fun saveRecipe(recipe: ApiRecipe, onRecipeSaved: (Boolean) -> Unit) {
        val recipe2 = Recipe(
            recipeId = recipe.recipeId,
            title = recipe.title,
            description = recipe.description,
            serves = recipe.serves,
            difficulty = recipe.difficulty,
            prepTime = recipe.prepTime,
            authorId = getDBId(),
            imageId = recipe.imageId
        )
        Log.d("Save Recipe", "$recipe2")
        viewModelScope.launch {
            val wasInserted = repository.insertRecipeWithContent(recipe2, recipe)
            onRecipeSaved(wasInserted)
        }
    }

    fun loadSavedRecipeDetails(recipeId: UUID, onLoaded: (List<Ingredient>, List<Instruction>) -> Unit) {
        viewModelScope.launch {
            val ingredients = repository.getIngredientsByRecipeId(recipeId)
            val instructions = repository.getInstructionsByRecipeId(recipeId)
            onLoaded(ingredients, instructions)
        }
    }

    private fun ensureUserExists() {
        runBlocking {
            val userDao = RecipeDatabase.getInstance(getApplication()).userDao()
            val uuid = userDao.getDbUUID()
            if (uuid == null) {
                val user = User(
                    userId = UUID.randomUUID(),
                    username = "epicurious",
                    displayName = "Epicurious",
                    profileImageUrl = null,
                    firebaseId = "null"
                )
                userDao.insertUser(user)
            }
        }
    }

    private fun getDBId(): UUID {
        return runBlocking {
            val userDao = RecipeDatabase.getInstance(getApplication()).userDao()
            val uuid = userDao.getDbUUID()
            return@runBlocking if (uuid == null) {
                val newUUID = UUID.randomUUID()
                val user = User(
                    userId = newUUID,
                    username = "epicurious",
                    displayName = "Epicurious",
                    profileImageUrl = null,
                    firebaseId = "null"
                )
                userDao.insertUser(user)
                newUUID
            } else {
                uuid
            }
        }
    }
}