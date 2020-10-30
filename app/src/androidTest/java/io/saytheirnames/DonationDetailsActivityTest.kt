package io.saytheirnames

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.saytheirnames.activity.DonationDetailsActivity
import org.junit.Test

class DonationDetailsActivityTest {

    @Test
    fun shouldShowActivityInView() {
        launchActivity()

        onView(withId(R.id.donations_container)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowToolbarView() {
        launchActivity()

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.close)).check(matches(isDisplayed()))
        onView(withId(R.id.textView2)).check(matches(isDisplayed()))
        onView(withId(R.id.shareDonation)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowNavigationHostFragment() {
        launchActivity()

        onView(withId(R.id.frameLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.blurImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.actual_image)).check(matches(isDisplayed()))
    }

    private fun launchActivity() = ActivityScenario.launch(DonationDetailsActivity::class.java)
}