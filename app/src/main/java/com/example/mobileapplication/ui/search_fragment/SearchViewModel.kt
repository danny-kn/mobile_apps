package com.example.mobileapplication.ui.discover_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.utils.RecipeApiClient
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<ApiRecipe>>(emptyList())
    val recipes: LiveData<List<ApiRecipe>> = _recipes

    private var _pageSize = 20
    private var _currentPage = 1
    private var lastSearch = ""

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun searchRecipes(query: String?) {
        if (_loading.value == true) return // Prevent multiple simultaneous loads

        _loading.value = true
        viewModelScope.launch {
            try {
                val actualQuery = query ?: lastSearch

                if (actualQuery != lastSearch) {
                    _currentPage = 1
                    _recipes.postValue(emptyList()) // clear old results
                }

                val result = RecipeApiClient.getSearchRecipes(actualQuery, _pageSize, _currentPage)

                // Only update state if the call was successful
                lastSearch = actualQuery
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

    fun retryLastQuery() {
        searchRecipes(null)
    }
}
