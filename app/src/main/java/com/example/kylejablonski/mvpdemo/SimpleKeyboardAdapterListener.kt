package com.example.kylejablonski.mvpdemo

import android.inputmethodservice.KeyboardView
import android.util.Log

/**
 * Adapter wrapping the functionality of the Keyboardview.OnKeyboardActionListener
 * for simplifying the required methods to implement within the app.
 */
interface SimpleKeyboardAdapterListener: KeyboardView.OnKeyboardActionListener {

    override fun swipeRight() {
        Log.d("SKAL", "swipeRight()")
    }

    override fun onPress(p0: Int) {
        Log.d("SKAL", "onPress($p0)")
    }

    override fun onRelease(p0: Int) {
        Log.d("SKAL", "onRelease($p0)")
    }

    override fun swipeLeft() {
        Log.d("SKAL", "swipeLeft()")
    }

    override fun swipeUp() {
        Log.d("SKAL", "swipeUp()")
    }

    override fun swipeDown() {
        Log.d("SKAL", "swipeDown()")
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        Log.d("SKAL", "onKey($primaryCode, $keyCodes)")
    }

    override fun onText(p0: CharSequence?) {
        Log.d("SKAL", "onText($p0)")
    }
}