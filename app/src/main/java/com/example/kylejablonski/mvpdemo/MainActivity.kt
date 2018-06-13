package com.example.kylejablonski.mvpdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.design.widget.Snackbar
import android.widget.Toast
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity(), Contract.View, ChipGroup.OnCheckedChangeListener {

    private lateinit var presenter: Contract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, MainModel())
        presenter.start()

        chipGroup.setOnCheckedChangeListener(this@MainActivity)

        fabComputePrice.setOnClickListener {
            val checkAmount = editTextCheckAmount.text.toString()
            presenter.computeTotalPrice(checkAmount)
        }
    }

    override fun displayTipOptions(percentages: Array<Int>) {
        for (index in percentages.indices) {
            val chip = Chip(chipGroup.context)
            chip.chipText = "${percentages[index]}%"
            chip.isClickable = true
            chip.isCheckable = true
            chipGroup.addView(chip)
        }
    }

    override fun selectDefaultTipOption() {
        chipGroup.check(chipGroup.getChildAt(3).id)
    }

    override fun displayInitialPrice(total: Double) {
        appCompatTextViewTotal.text = NumberFormat.getCurrencyInstance().format(total)
    }

    override fun displayFinalPriceWithTip(total: Double, tipAmount: Double) {
        appCompatTextViewTotal.text = NumberFormat.getCurrencyInstance().format(total)
        appCompatTextViewTip.text = String.format(Locale.getDefault(), getString(R.string.format_tip_amount), NumberFormat.getCurrencyInstance().format(tipAmount))
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.error_price_cannot_be_empty), Toast.LENGTH_SHORT).show()
    }

    override fun onCheckedChanged(p0: ChipGroup, p1: Int) {
        // subtract 1 since views start at index 1
        presenter.changeTipPercentage(p0.checkedChipId - 1)
    }
}
