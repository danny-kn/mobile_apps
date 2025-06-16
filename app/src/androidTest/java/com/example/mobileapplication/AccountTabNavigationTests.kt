// src_01: https://developer.android.com/training/testing/espresso
// src_02: https://medium.com/globant/get-started-with-espresso-kotlin-8aa5ca935489
// src_03: https://developer.android.com/training/testing/espresso/basics
// src_04: https://developer.android.com/training/testing/espresso/cheat-sheet
// src_05: https://developer.android.com/guide/fragments/test
// src_06: https://stackoverflow.com/questions/35471425/how-do-i-test-a-fragment-in-isolation-using-espresso
// src_07: https://www.browserstack.com/guide/test-fragement-using-espresso

package com.example.mobileapplication

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobileapplication.ui.account_fragment.AccountFragment
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA

@RunWith(AndroidJUnit4::class)
class AccountTabNavigationTests {

    private fun clickSignInTab() { // simulate clicking the sign in tab.
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(0))
    }

    private fun clickSignUpTab() { // simulate clicking the sign up tab.
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
    }

    // https://stackoverflow.com/questions/49626315/how-to-select-a-specific-tab-position-in-tab-layout-using-espresso-testing
    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object: ViewAction {
            override fun getDescription() = "tabIndex: $tabIndex"
            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))
            override fun perform(uiController: UiController, view: View) {
                (view as TabLayout).getTabAt(tabIndex)?.select()
            }
        }
    }

    @Test
    fun testSignIn() { // make sure the sign in tab displays the correct form and ui elements.
        val scenario = launchFragmentInContainer<AccountFragment>(themeResId=R.style.Theme_MobileApplication)
        clickSignInTab()
        onView(withId(R.id.login_form)).check(matches(isDisplayed()))
        onView(withId(R.id.email)).check(matches(isDisplayed()))
        onView(withId(R.id.password)).check(matches(isDisplayed()))
        onView(withId(R.id.signInButton)).check(matches(isDisplayed()))
    }

    @Test
    fun testSignUp() { // make sure the sign up tab displays the correct form and ui elements.
        val scenario = launchFragmentInContainer<AccountFragment>(themeResId=R.style.Theme_MobileApplication)
        clickSignUpTab()
        onView(withId(R.id.register_form)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.email), isDescendantOfA(withId(R.id.register_form)))).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.password), isDescendantOfA(withId(R.id.register_form)))).check(matches(isDisplayed()))
        onView(withId(R.id.confirmPassword)).check(matches(isDisplayed()))
        onView(withId(R.id.signUpButton)).check(matches(isDisplayed()))
    }

    @Test
    fun testSignInSignUp() {
        val scenario = launchFragmentInContainer<AccountFragment>(themeResId=R.style.Theme_MobileApplication)
        clickSignInTab()
        onView(withId(R.id.login_form)).check(matches(isDisplayed()))
        clickSignUpTab()
        onView(withId(R.id.register_form)).check(matches(isDisplayed()))
        clickSignInTab()
        onView(withId(R.id.login_form)).check(matches(isDisplayed()))
    }

    @Test
    fun testSignInAndSignOut() {
        launchFragmentInContainer<AccountFragment>(themeResId=R.style.Theme_MobileApplication)
        clickSignInTab()
        onView(withId(R.id.email)).perform(typeText("user@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("password"), closeSoftKeyboard())
        onView(withId(R.id.signInButton)).perform(click())
        onView(withId(R.id.email)).check(matches(withText("user@gmail.com")))
        // https://phase2online.com/2021/06/16/flaky-tests-on-android-with-espresso-handling-network-calls-in-android-ui-testing/
        Thread.sleep(3000) // not a good solution. will fix later.
        onView(withId(R.id.signOutButton)).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.login_form)).check(matches(isDisplayed()))
    }
}
