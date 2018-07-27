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
import android.support.test.espresso.action.Press
import android.support.test.espresso.action.CoordinatesProvider
import android.support.test.espresso.action.Tap
import android.support.test.espresso.action.GeneralClickAction
import android.support.test.espresso.ViewAction
import android.view.InputDevice


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
    fun viewsDisplayAsExpected() {
        onView(withText("Tip Calculator")).check(matches(isDisplayed()))
        onView(withHint("Enter check amount")).check(matches(isDisplayed()))
        onView(withText("Choose a tip")).check(matches(isDisplayed()))
        onView(withText("Tip: $0.00")).check(matches(isDisplayed()))
        onView(withId(R.id.chipGroup)).check(matches(hasChildCount(5)))
    }

    @Test
    fun enter100AsPriceShouldDisplay120ForA20PercentTip() {
        onView(withId(R.id.editTextCheckAmount))
                .perform(typeText("100"), closeSoftKeyboard())

        val (viewTargetY, viewTargetX) = getTargetXAndY()

        // perform the action
        onView(withId(R.id.keyboardLayout)).perform(clickXY(viewTargetX.toInt(), viewTargetY))
        onView(withText("Tip: $20.00")).check(matches(isDisplayed()))
        onView(withText("Total: $120.00")).check(matches(isDisplayed()))
    }

    @Test
    fun select10PercentTipShouldDisplay110For100Dollars() {
        onView(withId(R.id.editTextCheckAmount))
                .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(2)).perform(click())
        onView(withId(2)).check(matches(isSelected()))

        val (viewTargetY, viewTargetX) = getTargetXAndY()

        // perform the action
        onView(withId(R.id.keyboardLayout)).perform(clickXY(viewTargetX.toInt(), viewTargetY))
        onView(withText("Tip: $10.00")).check(matches(isDisplayed()))
        onView(withText("Total: $110.00")).check(matches(isDisplayed()))

    }

    private fun getTargetXAndY(): Pair<Int, Double> {
        var viewHeight = 0
        var viewWidth = 0
        var viewPosX = 0F
        val viewMatcher = onView(withId(R.id.keyboardLayout))
        viewMatcher.check { view, _ ->
            viewWidth = view.width
            viewHeight = view.height
            viewPosX = view.x
        }

        val keyboardKeyWidthPercent = 0.333
        val keyboardRowsCount = 3

        // use the bounds to determine the middle of the button we want to press
        val keyboardButtonWidthQuarter = (viewWidth * keyboardKeyWidthPercent) / 4
        val keyboardButtonHeightHalf = (viewHeight / keyboardRowsCount) / 2

        /*
            use View's bounds to determine the position of X & Y to press
            the keyboard button center
          */
        val viewTargetY = viewHeight - keyboardButtonHeightHalf
        val viewTargetX = (viewPosX + viewWidth) - keyboardButtonWidthQuarter
        return Pair(viewTargetY, viewTargetX)
    }

    private fun clickXY(x: Int, y: Int): ViewAction {
        return GeneralClickAction(
                Tap.SINGLE,
                CoordinatesProvider { view ->
                    val screenPos = IntArray(2)
                    view.getLocationOnScreen(screenPos)

                    val screenX = (screenPos[0] + x).toFloat()
                    val screenY = (screenPos[1] + y).toFloat()

                    floatArrayOf(screenX, screenY)
                },
                Press.FINGER, InputDevice.SOURCE_KEYBOARD, 0)
    }
}
