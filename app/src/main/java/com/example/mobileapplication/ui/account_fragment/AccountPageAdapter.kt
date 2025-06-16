package com.example.mobileapplication.ui.account_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// https://developer.android.com/reference/androidx/viewpager2/adapter/FragmentStateAdapter
class AccountPageAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(idx: Int): Fragment {
        return when (idx) {
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> LoginFragment()
        }
    }
}
