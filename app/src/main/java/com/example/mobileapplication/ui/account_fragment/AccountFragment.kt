// https://developer.android.com/reference/android/view/View
// https://developer.android.com/guide/fragments/transactions

package com.example.mobileapplication.ui.account_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mobileapplication.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AccountFragment: Fragment() {
    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)

        viewModel.isUserLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn) {
                renderProfile(v)
            } else {
                renderSignIn(v)
            }
        }
    }

    private fun renderSignIn(v: View) {
        val tabLayout = v.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = v.findViewById<ViewPager2>(R.id.view_pager)
        val profileContainer = v.findViewById<ViewGroup>(R.id.profile_container)

        tabLayout.visibility = View.VISIBLE
        viewPager.visibility = View.VISIBLE
        profileContainer.visibility = View.GONE

        viewPager.adapter = AccountPageAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = "SIGN IN"
                1 -> tab.text = "SIGN UP"
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(pos: Int) {
                viewModel.setSelectedTabIndex(pos)
            }
        })
    }

    private fun renderProfile(v: View) {
        val tabLayout = v.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = v.findViewById<ViewPager2>(R.id.view_pager)
        val profileContainer = v.findViewById<ViewGroup>(R.id.profile_container)

        tabLayout.visibility = View.GONE
        viewPager.visibility = View.GONE
        profileContainer.visibility = View.VISIBLE

        if (childFragmentManager.findFragmentById(R.id.profile_container) !is ProfileFragment) {
            childFragmentManager.beginTransaction()
                .replace(R.id.profile_container, ProfileFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment Lifecycle", "AccountFragment: onDestroyView()")
    }
}
