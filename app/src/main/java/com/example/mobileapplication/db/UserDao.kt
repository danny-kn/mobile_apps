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
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun getUserById(userId: Int): LiveData<User>

    @Query("DELETE FROM users WHERE user_id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("SELECT * FROM users WHERE user_id = :userId LIMIT 1")
    suspend fun findUserById(userId: UUID): User?

    @Query("SELECT user_id FROM users WHERE username = 'user'")
    suspend fun getUserUUID(): UUID?

    @Query("SELECT user_id FROM users WHERE username = 'epicurious'")
    suspend fun getDbUUID(): UUID?


}
