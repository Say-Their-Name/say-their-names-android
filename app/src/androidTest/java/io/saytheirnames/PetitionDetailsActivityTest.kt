package io.saytheirnames

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.saytheirnames.activity.PetitionDetailsActivity
import org.junit.Test

class PetitionDetailsActivityTest {
    @Test
    fun shouldShowActivityInView() {
        launchActivity()

        onView(withId(R.id.petitions_container)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowToolbarView() {
        launchActivity()

        onView(withId(R.id.linearLayoutDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.imgShare)).check(matches(isDisplayed()))
        onView(withId(R.id.imgClose)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowNavigationHostFragment() {
        launchActivity()

        onView(withId(R.id.frameLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.blurImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.actual_image)).check(matches(isDisplayed()))
    }

    private fun launchActivity() = ActivityScenario.launch(PetitionDetailsActivity::class.java)
}