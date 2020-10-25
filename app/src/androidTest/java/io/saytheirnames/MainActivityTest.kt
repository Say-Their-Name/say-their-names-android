package io.saytheirnames

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import io.saytheirnames.activity.MainActivity
import org.junit.Test


class MainActivityTest {

    @Test
    fun shouldShowActivityInView() {
        launchActivity()

        onView(withId(R.id.container)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowBottomNavigationView() {
        launchActivity()

        onView(withId(R.id.nav_view)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowNavigationHostFragment() {
        launchActivity()

        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }
    
    private fun launchActivity() = ActivityScenario.launch(MainActivity::class.java)
}