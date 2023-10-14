package com.geekbrains.tests

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.Matcher

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    companion object {
        private const val TEST_STRING = "Test string"
    }

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }

    @Test
    fun activity_AssertNotNull_Test() {
        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

    @Test
    fun activity_IsResumed_Test() {
        scenario.onActivity {
            assertEquals(Lifecycle.State.RESUMED, it.lifecycle.currentState)
        }
    }

    @Test
    fun activityEditText_NotNull_Test() {
        onView(withId(R.id.searchEditText))
            .check(matches(withText("")))
    }

    @Test
    fun activityEditText_IsDisplayed_Test() {
        val assertion = matches(ViewMatchers.isDisplayed())
        onView(withId(R.id.searchEditText)).check(assertion)
    }

    @Test // или isCompletelyDisplayed()
    fun activityEditText_IsDisplayingAtLeast_Test() {
        val assertion = matches(ViewMatchers.isDisplayingAtLeast(100))
        onView(withId(R.id.searchEditText)).check(assertion)

    }

    @Test
    fun activityEditText_HasText_Test() {
        val assertionText = matches(withText(TEST_STRING))
        val assertionClear = matches(withText(""))
        onView(withId(R.id.searchEditText))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(TEST_STRING))
            .check(assertionText)
            .perform(ViewActions.clearText())
            .check(assertionClear)
    }

    @Test
    fun activityButton_IsDisplayed_Test(){
        val assertion = matches(isDisplayed())
        onView(withId(R.id.toDetailsActivityButton)).check(assertion)
    }

    @Test
    fun activityButton_WithEffectiveVisibility_Test(){
        val assertion = matches(withEffectiveVisibility(Visibility.VISIBLE))
        onView(withId(R.id.toDetailsActivityButton)).check(assertion)
    }

    @Test
    fun activityButton_Intent_Test(){
        onView(withId(R.id.toDetailsActivityButton))
            .perform(ViewActions.click())
        onView(withId(R.id.totalCountTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("Number of results: 0")))
    }

    @After
    fun close() {
        scenario.close()
    }
}
