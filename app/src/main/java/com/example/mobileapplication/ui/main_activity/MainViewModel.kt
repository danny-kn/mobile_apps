package com.example.mobileapplication.ui.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _selectedTabId = MutableLiveData<Int>()
    val selectedTabId: LiveData<Int> get() = _selectedTabId

    fun setSelectedTab(tabId: Int) {
        _selectedTabId.value = tabId
    }
}
