package com.example.mobileapplication.utils

// https://medium.com/@pritam.karmahapatra/retrofit-in-android-with-kotlin-9af9f66a54a8

import android.content.Context
import android.util.Log
import com.example.mobileapplication.R
import com.example.mobileapplication.db.ApiRecipe
import com.example.mobileapplication.db.Recipe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RecipeApiClient {

    private lateinit var BASE_URL : String

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Set to NONE in production
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private lateinit var retrofit : Retrofit
    private lateinit var apiService: RecipeApiService

    fun init(context: Context) {
        // Need to use init so we can pass a context to get the resource string
        BASE_URL = context.getString(R.string.api_url)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(RecipeApiService::class.java)

        Log.d("Recipe Api", "Successfully initialized the recipe API client")
    }

    private fun ensureInitialized() {
        if (!::BASE_URL.isInitialized) {
            throw IllegalStateException("RecipeApiClient is not initialized. Call init(context) first.")
        }
    }

    suspend fun getDiscoveryRecipes(): List<ApiRecipe> {
        ensureInitialized()

        val idToken = FirebaseUtils.getIdToken()
        val request = try {
            apiService.getDiscoveryRecipes("Bearer $idToken")
        } catch (e: HttpException) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        } catch (e: IOException) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        } catch (e: Exception) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        }

        return request
    }

    suspend fun getTrendingRecipes(numResults: Int, page: Int): List<ApiRecipe> {
        ensureInitialized()

        val request = try {
            apiService.getTrendingRecipes(numResults, page)
        } catch (e: HttpException) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        } catch (e: IOException) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        } catch (e: Exception) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        }
        Log.i("Recipe Api", "$request")
        return request;
    }

    suspend fun getSearchRecipes(searchTerm: String, numResults: Int, page: Int): List<ApiRecipe> {
        ensureInitialized()

        val request = try {
            apiService.getSearchRecipes(searchTerm, numResults, page)
        } catch (e: HttpException) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        } catch (e: IOException) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        } catch (e: Exception) {
            Log.e("Recipe Api", e.toString())
            emptyList()
        }

        return request;
    }
}