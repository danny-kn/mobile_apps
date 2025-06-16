package com.example.mobileapplication.ui.search_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.mobileapplication.R
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.mobileapplication.ui.discover_fragment.SearchViewModel
import com.example.mobileapplication.ui.recipe_list_component.RecipeListView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchFragment: Fragment() {

    private var recipeListView: RecipeListView? = null
    private lateinit var searchContainer: TextInputLayout
    private lateinit var searchField: TextInputEditText
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SearchFragment: onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("Lifecycle", "SearchFragment: onCreateView()")
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeListView = view.findViewById(R.id.searchRecipes)
        searchContainer = view.findViewById(R.id.searchContainer)
        searchField = view.findViewById(R.id.searchField)

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeListView?.setRecipes(recipes)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Log.e("Recipe Error", error)
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            recipeListView?.setLoading(isLoading)
        }

        // Default search for fun
        viewModel.searchRecipes("chicken")

        searchContainer.setEndIconOnClickListener {
            val query = searchField.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchRecipes(query)
            } else {
                Log.e("Search", "Search query is empty.")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "SearchFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "SearchFragment: onResume()")
        recipeListView?.restartAnimations()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "SearchFragment: onPause()")
        recipeListView?.clearAnimations()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "SearchFragment: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "SearchFragment: onDestroy()")
    }

    override fun onDestroyView() {
        recipeListView?.clearAnimations()
        recipeListView?.setOnLoadMoreListener{}
        recipeListView = null
        super.onDestroyView()
        Log.d("Lifecycle", "SearchFragment: onDestroyView()")
    }
}
