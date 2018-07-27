package com.example.kylejablonski.mvpdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.Toast
import java.text.NumberFormat
import java.util.*
import android.inputmethodservice.Keyboard
import android.text.*
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.example.kylejablonski.mvpdemo.R.id.appCompatTextViewTip
import com.example.kylejablonski.mvpdemo.R.id.appCompatTextViewTotal
import kotlinx.android.synthetic.main.activity_main.view.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager

/**
 * MainActivity for all UI in the application
 */
class MainActivity : AppCompatActivity(), Contract.View,
        ChipGroup.OnCheckedChangeListener, View.OnClickListener, SimpleKeyboardAdapterListener {

    private lateinit var presenter: Contract.Presenter
    private lateinit var keyboard: Keyboard
    private lateinit var inputConnection: InputConnection
    private var lastCheckedChild = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createKeyboard()

        presenter = MainPresenter(this, MainModel())
        presenter.start()

        chipGroup.setOnCheckedChangeListener(this@MainActivity)

        inputConnection = editTextCheckAmount.onCreateInputConnection(EditorInfo())
        editTextCheckAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.isEmpty()) {
                    appCompatTextViewTotal.text = NumberFormat.getCurrencyInstance().format(0)
                    appCompatTextViewTip.animate()
                            .alpha(0F)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    super.onAnimationEnd(animation)
                                    appCompatTextViewTip.clearAnimation()
                                    appCompatTextViewTip.alpha = 0F
                                }
                            })
                            .start()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun displayTipOptions(percentages: Array<Int>) {
        for (index in percentages.indices) {
            val chip = Chip(chipGroup.context)
            chip.id = index + 1
            chip.chipText = "${percentages[index]}%"
            chip.isClickable = true
            chip.isCheckable = true
            chipGroup.addView(chip)
        }
    }

    override fun selectDefaultTipOption(index: Int) {
        chipGroup.check(chipGroup.getChildAt(index).id)
    }

    override fun displayInitialPrice(total: Double) {
        appCompatTextViewTotal.text = String.format(Locale.getDefault(),
                getString(R.string.format_tip_amount),
                NumberFormat.getCurrencyInstance().format(total))
    }

    override fun displayFinalPriceWithTip(total: Double, tipAmount: Double) {

        appCompatTextViewTotal.text =
                String.format(Locale.getDefault(),
                        getString(R.string.format_tip_amount),
                        NumberFormat.getCurrencyInstance().format(tipAmount))

        appCompatTextViewTotal.animate()
                .scaleX(1.3F)
                .scaleY(1.3F)
                .setInterpolator(LinearInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        appCompatTextViewTotal.clearAnimation()

                        appCompatTextViewTotal.animate()
                                .scaleX(1F)
                                .scaleY(1F)
                                .setInterpolator(LinearInterpolator())
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator?) {
                                        super.onAnimationEnd(animation)
                                        appCompatTextViewTotal.clearAnimation()

                                        appCompatTextViewTip.animate()
                                                .alpha(1F)
                                                .setListener(object : AnimatorListenerAdapter() {
                                                    override fun onAnimationEnd(animation: Animator?) {
                                                        super.onAnimationEnd(animation)
                                                        appCompatTextViewTip.clearAnimation()
                                                        appCompatTextViewTip.alpha = 1F
                                                    }
                                                }).start()
                                    }
                                })
                    }
                }).start()
        appCompatTextViewTip.text =
                String.format(Locale.getDefault(),
                        getString(R.string.format_total_amount),
                        NumberFormat.getCurrencyInstance().format(total))


    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.error_price_cannot_be_empty), Toast.LENGTH_SHORT).show()
    }

    override fun onCheckedChanged(p0: ChipGroup, p1: Int) {
        // subtract 1 since views start at index 1
        val actualViewIndex = p0.checkedChipId - 1
        chipGroup.getChildAt(actualViewIndex)?.isSelected = true
        chipGroup.getChildAt(lastCheckedChild)?.isSelected = false
        lastCheckedChild = actualViewIndex
        presenter.changeTipPercentage(actualViewIndex)
    }

    override fun onClick(view: View) {
        if (view is AppCompatTextView) {
            val currentText = SpannableStringBuilder(editTextCheckAmount.text.toString())
            currentText.append(view.text.toString())
            editTextCheckAmount.text = currentText

        } else if (view is FloatingActionButton) {
            val checkAmount = editTextCheckAmount.text.toString()
            presenter.computeTotalPrice(checkAmount)
        }
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {

        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                val selectedText = inputConnection.getSelectedText(0)

                if (TextUtils.isEmpty(selectedText)) {
                    inputConnection.deleteSurroundingText(1, 0)
                } else {
                    inputConnection.commitText("", 1)
                }

                keyboardLayout.invalidateAllKeys()
            }
            Keyboard.KEYCODE_DONE -> {
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                presenter.computeTotalPrice(editTextCheckAmount.text.toString())
            }
            else -> {
                if (primaryCode == 46) {
                    inputConnection.commitText(".", 1)
                } else {
                    inputConnection.commitText(primaryCode.toString(), 1)
                }
            }
        }
    }

    private fun createKeyboard(){
        // Create the Keyboard
        keyboard = Keyboard(this, R.xml.keyboard_keys)
        // Attach the keyboard to the view
        keyboardLayout.keyboard = keyboard
        // Do not show the preview balloons
        keyboardLayout.isPreviewEnabled = false
        keyboardLayout.setOnKeyboardActionListener(this)
    }
}
