package com.example.mobileapplication.utils

import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.db.Recipe
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/recommendations")
    suspend fun getDiscoveryRecipes(@Header("Authorization") token: String): List<ApiRecipe>
    @GET("recipes/")
    suspend fun getTrendingRecipes(@Query("numResults") numResults: Int, @Query("page") page: Int): List<ApiRecipe>
    @GET("recipes/")
    suspend fun getSearchRecipes(@Query("searchTerm") searchTerm: String, @Query("numResults") numResults: Int, @Query("page") page: Int): List<ApiRecipe>
}
