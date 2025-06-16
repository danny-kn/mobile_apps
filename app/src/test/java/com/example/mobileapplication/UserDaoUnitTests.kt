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
class UserDaoUnitTests {
    private lateinit var userDao: UserDao
    private lateinit var db: RecipeDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java).build()
        userDao = db.userDao()
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

    @Test // test case 1: testing the insert user functionality.
    @Throws(Exception::class)
    fun insertUser() = runBlocking {
        val user = createTestUser()
        userDao.insertUser(user)
        val obtainedUser = userDao.findUserById(user.userId)
        assertNotNull(obtainedUser)
        assertThat(obtainedUser?.userId, equalTo(user.userId))
        assertThat(obtainedUser?.username, equalTo(user.username))
        assertThat(obtainedUser?.displayName, equalTo(user.displayName))
        assertThat(obtainedUser?.profileImageUrl, equalTo(user.profileImageUrl))
        assertThat(obtainedUser?.firebaseId, equalTo(user.firebaseId))
    }

    @Test // test case 2: testing the update user functionality.
    @Throws(Exception::class)
    fun updateUser() = runBlocking {
        val user = createTestUser()
        userDao.insertUser(user)
        val updatedUser = User(
            userId = user.userId,
            username = "updated_username",
            displayName = "updated_display_name",
            profileImageUrl = "updated_profile_image_url",
            firebaseId = user.firebaseId
        )
        userDao.updateUser(updatedUser)
        val obtainedUser = userDao.findUserById(user.userId)
        assertNotNull(obtainedUser)
        assertThat(obtainedUser?.userId, equalTo(user.userId))
        assertThat(obtainedUser?.username, equalTo("updated_username"))
        assertThat(obtainedUser?.displayName, equalTo("updated_display_name"))
        assertThat(obtainedUser?.profileImageUrl, equalTo("updated_profile_image_url"))
        assertThat(obtainedUser?.firebaseId, equalTo(user.firebaseId))
    }

    @Test // test case 3: testing the delete user functionality.
    @Throws(Exception::class)
    fun deleteUser() = runBlocking {
        val user = createTestUser()
        userDao.insertUser(user)
        userDao.deleteUser(user)
        val obtainedUser = userDao.findUserById(user.userId)
        assertNull(obtainedUser)
    }
}
