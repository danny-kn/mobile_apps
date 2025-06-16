package com.example.mobileapplication.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_session")
class SessionManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("firebase_id_token")
    }

    private val auth = FirebaseAuth.getInstance()

    suspend fun getToken(forceRefresh: Boolean = false): String? {
        val user = auth.currentUser ?: return null

        return try {
            val tokenResult = user.getIdToken(forceRefresh).await()
            val token = tokenResult.token
            token?.let {saveToken(it)}
            token
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveToken(token: String) {
        context.dataStore.edit {prefs ->
            prefs[TOKEN_KEY] = token
        }

    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun logout() {
        auth.signOut()
    }


}