package com.example.mobileapplication.ui.account_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.utils.FirebaseUtils
import kotlinx.coroutines.launch

class AccountViewModel: ViewModel() {
    private val _selectedTabIndex = MutableLiveData<Int>()
    val selectedTabIndex: LiveData<Int> = _selectedTabIndex

    private val _signInResult = MutableLiveData<Result<String>>()
    val signInResult: LiveData<Result<String>> = _signInResult

    private val _signUpResult = MutableLiveData<Result<String>>()
    val signUpResult: LiveData<Result<String>> = _signUpResult

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    init {
        if (FirebaseUtils.isLoggedIn()) {
            _isUserLoggedIn.value = true
        } else {
            _isUserLoggedIn.value = false
        }
    }

    fun setSelectedTabIndex(idx: Int) {
        _selectedTabIndex.value = idx
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            FirebaseUtils.loginUser(email, password) { success, token ->
                if (success) {
                    _signInResult.postValue(Result.success(token ?: "no token."))
                    _isUserLoggedIn.value = true
                } else {
                    _signInResult.postValue(Result.failure(Exception(token ?: "sign in failed.")))
                }
            }
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            FirebaseUtils.registerUser(email, password) { success, token ->
                if (success) {
                    _signUpResult.postValue(Result.success(token ?: "no token."))
                    _isUserLoggedIn.value = true
                } else {
                    _signUpResult.postValue(Result.failure(Exception(token ?: "sign up failed.")))
                }
            }
        }
    }

    fun isLoggedIn(): Boolean { return FirebaseUtils.isLoggedIn() }

    fun signOut() {
        FirebaseUtils.signOut()
        _isUserLoggedIn.value = false
    }
}
