package com.moji.sevenchallenge

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.moji.sevenchallenge.extensions.mockNetworkAvailabilityToFalse
import com.moji.sevenchallenge.tools.POPULAR_CATEGORY
import com.moji.sevenchallenge.tools.waitForLoader
import com.moji.sevenchallenge.ui.activities.MainActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityEspressoTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun main_activity_check_no_network_and_swipe_to_refresh_and_snackbar_functionality() {
        waitForLoader()

        onView(withTagValue(Matchers.equalTo(POPULAR_CATEGORY)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.txtTitle)).check(matches(isDisplayed()))

        pressBack()

        onView(withText(R.string.app_name)).check(matches(isDisplayed()))

        mockNetworkAvailabilityToFalse = true

        onView(withId(R.id.swipeRefreshLayout)).perform(swipeDown())

        waitForLoader()

        onView(withText(R.string.error_no_internet_connection))
            .check(matches(isDisplayed()))

        mockNetworkAvailabilityToFalse = false

        onView(withText(R.string.try_again))
            .perform(click())

        waitForLoader()

        onView(withText(R.string.error_no_internet_connection))
            .check(doesNotExist())

        onView(withTagValue(Matchers.equalTo(POPULAR_CATEGORY)))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(R.id.txtGenre)).check(matches(isDisplayed()))

    }
}