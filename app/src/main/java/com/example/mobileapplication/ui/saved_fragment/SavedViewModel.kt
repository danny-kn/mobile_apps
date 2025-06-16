package com.example.mobileapplication.ui.account_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SavedViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Settings Fragment."
    }
    val text: LiveData<String> = _text
}
