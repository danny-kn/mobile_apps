package com.example.mobileapplication.ui.recipes_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RecipesFragmentViewModel : ViewModel() {

    private val _selectedTabIndex = MutableLiveData<Int>()
    val selectedTabIndex: LiveData<Int> = _selectedTabIndex

    fun setSelectedTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }
}