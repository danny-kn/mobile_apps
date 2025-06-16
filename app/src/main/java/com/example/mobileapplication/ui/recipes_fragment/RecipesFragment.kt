package com.example.mobileapplication.ui.recipes_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.mobileapplication.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RecipesFragment : Fragment() {

    private val viewModel: RecipesFragmentViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.setSelectedTabIndex(position)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("Lifecycle", "RecipesFragment - onCreateView");

        val view = inflater.inflate(R.layout.recipes_fragment, container, false)

        // Initialize TabLayout and ViewPager2
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)

        val adapter = RecipesPageAdapter(requireActivity())
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Trending"
                1 -> tab.text = "Discover"
                2 -> tab.text = "Search"
            }
        }.attach()

//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                viewModel.setSelectedTabIndex(position)
//            }
//        })

        viewPager.registerOnPageChangeCallback(pageChangeCallback)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewPager.unregisterOnPageChangeCallback(pageChangeCallback)
        Log.d("Lifecycle", "RecipeFragment - onDestroyView")
    }


}