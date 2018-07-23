package com.example.kylejablonski.mvpdemo

import android.support.test.InstrumentationRegistry

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*

import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import android.support.test.rule.ActivityTestRule
import org.junit.Rule



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityInstrumentedTest {

    @Rule
    @JvmField
    var mActivityRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.kylejablonski.mvpdemo", appContext.packageName)
    }

    @Test
    fun viewsDisplayAsExpected(){
        onView(withText("Tip Calculator")).check(matches(isDisplayed()))
        onView(withHint("Enter check amount")).check(matches(isDisplayed()))
        onView(withText("Choose a tip")).check(matches(isDisplayed()))
        onView(withText("$0.00")).check(matches(isDisplayed()))
        onView(withId(R.id.chipGroup)).check(matches(hasChildCount(5)))
    }

    @Test
    fun enter100AsPriceShouldDisplay120ForA20PercentTip(){
        onView(withId(R.id.editTextCheckAmount))
                .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.fabComputePrice))
                .perform(click())
        onView(withText("$120.00")).check(matches(isDisplayed()))

    }

    @Test
    fun select10PercentTipShouldDisplay110For100Dollars(){
        onView(withId(R.id.editTextCheckAmount))
                .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(2)).perform(click())
        onView(withId(2)).check(matches(isSelected()))
        onView(withId(R.id.fabComputePrice))
                .perform(click())
        onView(withText("$110.00")).check(matches(isDisplayed()))

    }
}
