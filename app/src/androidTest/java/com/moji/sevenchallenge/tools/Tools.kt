package com.moji.sevenchallenge.tools


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.moji.sevenchallenge.R
import org.hamcrest.Matchers.allOf


fun waitFor(secs: Float) {
    try {
        Thread.sleep((secs*1000).toLong())
    } catch (ex: Exception) {

    }

}

fun waitFor(maxSecs: Int, runnable: () -> Unit) {
    val startTime = System.currentTimeMillis()
    while (true) {
        try {
            runnable()
            break
        } catch (ex: AssertionError) {
            // keep going util time out or run with no exception
        }catch (ex: NoMatchingViewException){
            // keep going util time out or run with no exception
        }

        val currentTime = System.currentTimeMillis()
        if (currentTime - startTime > maxSecs * 1000) {
            runnable()
        }
    }
}

fun waitForLoader() {
    waitFor(SHORT_DELAY)
    waitFor(MAX_LOADER_WAIT) {
        onView(allOf(withId(R.id.loaderLayout), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).check(doesNotExist())
    }
    waitFor(SHORT_DELAY)
}

