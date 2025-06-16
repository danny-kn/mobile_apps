package com.example.mobileapplication.ui.recipe_list_component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.R
import com.example.mobileapplication.db.ApiRecipe

// https://developer.android.com/reference/android/widget/FrameLayout
// want single child reusable component
// https://developer.android.com/develop/ui/views/layout/custom-views/create-view

@SuppressLint("ClickableViewAccessibility")
class RecipeListView(
    context: Context, attrs: AttributeSet? = null
): FrameLayout(context, attrs) {

    private val recyclerView: RecyclerView
    private var adapter: RecipeListAdapter? = null
    private var isLoading: Boolean = false
    private var loadMoreCallback: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.recipe_list, this, true)
        recyclerView = findViewById(R.id.recipeList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setOnTouchListener { _, _ -> true }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                val threshold = 5 // Load more when 5 items away from the bottom

                if (!isLoading && (firstVisibleItemPosition + visibleItemCount + threshold >= totalItemCount)) {
                    loadMoreCallback?.invoke()
                }
            }
        })
    }

    fun setOnLoadMoreListener(callback: () -> Unit) {
        loadMoreCallback = callback
    }

    fun setRecipes(recipes: List<ApiRecipe>) {
        recyclerView.setOnTouchListener(null)
        adapter?.setRecipes(recipes)
    }

    fun setLoading(isLoading: Boolean) {
        adapter?.isLoading = isLoading
    }

    fun restartAnimations() {
        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val holder = recyclerView.getChildViewHolder(child)
            if (holder is RecipeListAdapter.SkeletonViewHolder) {
                holder.bind()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (adapter == null) {
            adapter = RecipeListAdapter()
            recyclerView.adapter = adapter
        }
    }

    fun clearAnimations() {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i)?.clearAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        recyclerView.adapter = null
        adapter = null
        loadMoreCallback = null
    }
}