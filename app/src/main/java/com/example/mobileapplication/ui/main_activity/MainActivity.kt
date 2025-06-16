package com.example.mobileapplication.ui.main_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.ui.recipes_fragment.RecipesFragment
import com.example.mobileapplication.ui.account_fragment.AccountFragment
import com.example.mobileapplication.ui.create_fragment.CreateFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import androidx.activity.viewModels
import com.example.mobileapplication.R
import com.example.mobileapplication.utils.RecipeApiClient

class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        // Initialize the api endpoint
        RecipeApiClient.init(this)
        setContentView(R.layout.activity_main);

        Log.d("Activity Lifecycle", "MainActivity: onCreate()");

        // bottom navigation bar.
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation);

        if (bottomNavView != null) { // prevent null crash

            viewModel.selectedTabId.observe(this) { tabId ->
                bottomNavView.selectedItemId = tabId
            }
            // start on the recipes fragment.

            if (savedInstanceState == null) {
                replaceFragment(RecipesFragment(),"RecipesFragment")
                bottomNavView.selectedItemId = R.id.nav_recipes
            }

            bottomNavView.setOnItemSelectedListener { item ->
                val fragmentTag = when (item.itemId) {
                    R.id.nav_create -> "CreateFragment"
                    R.id.nav_account -> "AccountFragment"
                    else -> "RecipesFragment"
                }

                val fragment = when (fragmentTag) {
                    "CreateFragment" -> CreateFragment()
                    "AccountFragment" -> AccountFragment()
                    else -> RecipesFragment()
                }

                replaceFragment(fragment, fragmentTag)
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(tag)

        val transaction = fragmentManager.beginTransaction()

        fragmentManager.fragments.forEach { transaction.hide(it) }

        if (existingFragment != null) {
            if (tag == "RecipesFragment") {
                transaction.replace(R.id.fragment_container, fragment, tag)
            } else {
                transaction.show(existingFragment)
            }
        } else {
            transaction.add(R.id.fragment_container, fragment, tag)
        }

        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Activity Lifecycle", "MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity Lifecycle", "MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity Lifecycle", "MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity Lifecycle", "MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity Lifecycle", "MainActivity: onDestroy()")
    }
}
