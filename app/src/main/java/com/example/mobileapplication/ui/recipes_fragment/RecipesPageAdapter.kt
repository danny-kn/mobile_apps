package com.example.mobileapplication.ui.recipes_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mobileapplication.ui.discover_fragment.DiscoverFragment
import com.example.mobileapplication.ui.search_fragment.SearchFragment
import com.example.mobileapplication.ui.trending_fragment.TrendingFragment

// https://developer.android.com/reference/androidx/viewpager2/adapter/FragmentStateAdapter
class RecipesPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrendingFragment()
            1 -> DiscoverFragment()
            2 -> SearchFragment()
            else -> TrendingFragment()
        }
    }
}