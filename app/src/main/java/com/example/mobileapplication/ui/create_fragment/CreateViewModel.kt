// https://developer.android.com/develop/ui/views/layout/recyclerview
// https://www.geeksforgeeks.org/android-recyclerview/
// https://www.youtube.com/watch?v=bOd3wO0uFr8
// https://www.youtube.com/watch?v=zCDB-OqOzfY
// https://github.com/chetanvaghela457/Android-Kotlin-DataBinding-WIth-BindingAdapter-Recyclerview-Binding
// https://github.com/emanuelnlopez/android-kotlin-crud

package com.example.mobileapplication.ui.create_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.db.Recipe
import com.example.mobileapplication.db.RecipeDatabase
import com.example.mobileapplication.db.RecipeRepository
import com.example.mobileapplication.db.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

class CreateViewModel(app: Application): AndroidViewModel(app) {
    private val repo: RecipeRepository
    val allRecipes: LiveData<List<Recipe>>
    var userId: UUID

    init {
        val recipeDao = RecipeDatabase.getInstance(app).recipeDao()
        val ingredientDao = RecipeDatabase.getInstance(app).ingredientDao()
        val instructionDao = RecipeDatabase.getInstance(app).instructionDao()
        val userDao = RecipeDatabase.getInstance(app).userDao()
        repo = RecipeRepository(recipeDao, instructionDao, ingredientDao)
        allRecipes = repo.getAllRecipes()
//        ensureUserExists()
        userId = getUserId2()
    }

    fun createRecipe(title: String, description: String?, serves: Int?, difficulty: Int, prepTime: Float) {
        val recipe = Recipe(
            recipeId = UUID.randomUUID(),
            title = title,
            description = description,
            serves = serves,
            difficulty = difficulty,
            prepTime = prepTime,
            authorId = userId,
            imageId = null
        )
        viewModelScope.launch {
            repo.insertRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            if (recipe.authorId == userId)
                repo.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repo.deleteRecipe(recipe)
        }
    }

//    private fun ensureUserExists() {
//        runBlocking {
//            val userDao = RecipeDatabase.getInstance(getApplication()).userDao()
//            val uuid = userDao.getUserUUID()
//            if (uuid == null) {
//                val user = User(
//                    userId = UUID.randomUUID(),
//                    username = "user",
//                    displayName = "user",
//                    profileImageUrl = null,
//                    firebaseId = "null"
//                )
//                userDao.insertUser(user)
//            }
//        }
//    }

    private fun getUserId2(): UUID {
        return runBlocking {
            val userDao = RecipeDatabase.getInstance(getApplication()).userDao()
            val uuid = userDao.getUserUUID()
            return@runBlocking if (uuid == null) {
                val newUUID = UUID.randomUUID()
                val user = User(
                    userId = newUUID,
                    username = "user",
                    displayName = "user",
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
