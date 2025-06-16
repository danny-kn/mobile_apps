package com.example.mobileapplication.ui.trending_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.mobileapplication.R
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.mobileapplication.ui.recipe_list_component.RecipeListView

class TrendingFragment: Fragment() {

    private var recipeListView: RecipeListView? = null
    private val viewModel: TrendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "TrendingFragment: onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("Lifecycle", "TrendingFragment: onCreateView()")
        return inflater.inflate(R.layout.trending_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeListView = view.findViewById(R.id.trendingRecipes)

        recipeListView?.setOnLoadMoreListener {
            viewModel.loadRecipes()
        }

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipeListView?.setRecipes(recipes)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Log.e("Recipe Error", error)
        }

        viewModel.loadRecipes()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "TrendingFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "TrendingFragment: onResume()")
        recipeListView?.restartAnimations()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "TrendingFragment: onPause()")
        recipeListView?.clearAnimations()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "TrendingFragment: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "TrendingFragment: onDestroy()")
    }

    override fun onDestroyView() {
        recipeListView?.clearAnimations()
        recipeListView?.setOnLoadMoreListener{}
        recipeListView = null
        super.onDestroyView()
        Log.d("Lifecycle", "TrendingFragment: onDestroyView()")
    }
}
