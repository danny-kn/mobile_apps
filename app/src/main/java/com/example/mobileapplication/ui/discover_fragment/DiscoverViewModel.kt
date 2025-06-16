package com.example.mobileapplication.ui.discover_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.db.Recipe
import com.example.mobileapplication.utils.RecipeApiClient
import kotlinx.coroutines.launch

class DiscoverViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<ApiRecipe>>()
    val recipes: LiveData<List<ApiRecipe>> = _recipes

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun refreshRecipes() {
        viewModelScope.launch {
            try {
                val result = RecipeApiClient.getDiscoveryRecipes()
                if (result.isNotEmpty()) {
                    _recipes.postValue(result)
                } else {
                    _errorMessage.postValue("No recipes found.")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching recipes: ${e.message}")
            }
        }
    }
}
