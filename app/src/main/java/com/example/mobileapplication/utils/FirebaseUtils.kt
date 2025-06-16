package com.example.mobileapplication.utils

import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.auth
//import com.google.firebase.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseUtils {
    private val auth: FirebaseAuth by lazy {
        // import issue when using Firebase.auth? something something firebase.auth.auth not correct
        FirebaseAuth.getInstance()
    }

    fun getCurrentUser() = auth.currentUser

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.getIdToken(true)
                        ?.addOnSuccessListener { result ->
                            onResult(true, result.token)
                        }
                } else {
                    onResult(false, task.exception?.message ?: "Login Failed")
                }
            }
    }

    fun registerUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    auth.currentUser?.getIdToken(true)
                        ?.addOnSuccessListener { result ->
                            onResult(true, result.token)
                        }
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message ?: "Registration Failed")
                }
            }
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    suspend fun getIdToken(forceRefresh: Boolean = false): String? {
        return auth.currentUser?.getIdToken(forceRefresh)?.await()?.token
    }

    fun signOut() { auth.signOut() }
}
