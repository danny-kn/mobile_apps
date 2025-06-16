package com.example.mobileapplication.ui.trending_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.db.Recipe
import com.example.mobileapplication.utils.RecipeApiClient
import kotlinx.coroutines.launch

class TrendingViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<ApiRecipe>>(emptyList())
    val recipes: LiveData<List<ApiRecipe>> = _recipes

    private var _pageSize = 20
    private var _currentPage = 1

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadRecipes() {
        if (_loading.value == true) return // Prevent multiple simultaneous loads

        _loading.value = true
        viewModelScope.launch {
            try {

                val result = RecipeApiClient.getTrendingRecipes(_pageSize, _currentPage)

                _currentPage++

                val current = _recipes.value.orEmpty()
                _recipes.postValue(current + result)
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching recipes: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

}

