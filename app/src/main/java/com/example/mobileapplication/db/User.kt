// https://developer.android.com/training/data-storage/room
// https://developer.android.com/training/data-storage/room/defining-data

package com.example.mobileapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: UUID,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "display_name") val displayName: String,
    @ColumnInfo(name = "profile_image_url") val profileImageUrl: String?,
    @ColumnInfo(name = "firebase_id") val firebaseId: String
) : Serializable
