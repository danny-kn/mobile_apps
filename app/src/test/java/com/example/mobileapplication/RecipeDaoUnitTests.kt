// src_01: https://developer.android.com/training/data-storage/room/testing-db
// src_02: https://medium.com/@wambuinjumbi/unit-testing-in-android-room-361bf56b69c5
// src_03: https://stackoverflow.com/questions/75917684/android-room-database-junit5-test

package com.example.mobileapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobileapplication.db.User
import com.example.mobileapplication.db.UserDao
import com.example.mobileapplication.db.Recipe
import com.example.mobileapplication.db.RecipeDao
import com.example.mobileapplication.db.RecipeDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class RecipeDaoUnitTests {
    private lateinit var userDao: UserDao
    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RecipeDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java).build()
        userDao = db.userDao()
        recipeDao = db.recipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    private fun createTestUser(): User {
        val user = User(
            userId = UUID.randomUUID(),
            username = "test_username",
            displayName = "test_display_name",
            profileImageUrl = "test_profile_image_url",
            firebaseId = "test_firebase_id"
        )
        return user
    }

    private fun createTestRecipe(authorId: UUID): Recipe {
        val recipe = Recipe(
            recipeId = UUID.randomUUID(),
            title = "test_title",
            description = "test_description",
            imageId = "test_image_id",
            serves = 1,
            difficulty = 1,
            prepTime = 1.0f,
            authorId = authorId
        )
        return recipe
    }

    @Test // test case 1: testing the insert recipe functionality.
    @Throws(Exception::class)
    fun insertRecipe() = runBlocking {
        val user = createTestUser()
        userDao.insertUser(user)
        val recipe = createTestRecipe(user.userId)
        recipeDao.insertRecipe(recipe)
        val obtainedRecipe = recipeDao.getRecipeById(recipe.recipeId)
        assertNotNull(obtainedRecipe)
        assertThat(obtainedRecipe?.recipeId, equalTo(recipe.recipeId))
        assertThat(obtainedRecipe?.title, equalTo(recipe.title))
        assertThat(obtainedRecipe?.description, equalTo(recipe.description))
        assertThat(obtainedRecipe?.imageId, equalTo(recipe.imageId))
        assertThat(obtainedRecipe?.serves, equalTo(recipe.serves))
        assertThat(obtainedRecipe?.difficulty, equalTo(recipe.difficulty))
        assertThat(obtainedRecipe?.prepTime, equalTo(recipe.prepTime))
        assertThat(obtainedRecipe?.authorId, equalTo(recipe.authorId))
    }

    @Test // test case 2: testing the update recipe functionality.
    @Throws(Exception::class)
    fun updateRecipe() = runBlocking {
        val user = createTestUser()
        userDao.insertUser(user)
        val recipe = createTestRecipe(user.userId)
        recipeDao.insertRecipe(recipe)
        val updatedRecipe = Recipe(
            recipeId = recipe.recipeId,
            title = "updated_title",
            description = "updated_description",
            imageId = recipe.imageId,
            serves = recipe.serves,
            difficulty = recipe.difficulty,
            prepTime = recipe.prepTime,
            authorId = recipe.authorId
        )
        recipeDao.updateRecipe(updatedRecipe)
        val obtainedRecipe = recipeDao.getRecipeById(recipe.recipeId)
        assertNotNull(obtainedRecipe)
        assertThat(obtainedRecipe?.recipeId, equalTo(recipe.recipeId))
        assertThat(obtainedRecipe?.title, equalTo("updated_title"))
        assertThat(obtainedRecipe?.description, equalTo("updated_description"))
        assertThat(obtainedRecipe?.imageId, equalTo(recipe.imageId))
        assertThat(obtainedRecipe?.serves, equalTo(recipe.serves))
        assertThat(obtainedRecipe?.difficulty, equalTo(recipe.difficulty))
        assertThat(obtainedRecipe?.prepTime, equalTo(recipe.prepTime))
        assertThat(obtainedRecipe?.authorId, equalTo(recipe.authorId))
    }

    @Test // test case 3: testing the delete recipe functionality.
    @Throws(Exception::class)
    fun deleteRecipe() = runBlocking {
        val user = createTestUser()
        userDao.insertUser(user)
        val recipe = createTestRecipe(user.userId)
        recipeDao.insertRecipe(recipe)
        recipeDao.deleteRecipe(recipe)
        val obtainedRecipe = recipeDao.getRecipeById(recipe.recipeId)
        assertNull(obtainedRecipe)
    }
}
