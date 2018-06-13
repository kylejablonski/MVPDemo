package com.example.kylejablonski.mvpdemo

class MainModel {

    val tipOptions = arrayOf(5, 10, 15, 20, 22)
    var defaultTipIndex = 3
    private var selectedTipOption = tipOptions[defaultTipIndex]

    var checkAmount = 0.00
    var totalAmount = 0.00
    var tipAmount = 0.00

    fun setTipOption(position: Int) {

        // default tip to lowest option
        selectedTipOption = if (position < 0 || position >= tipOptions.size) {
            tipOptions[defaultTipIndex]
        } else {
            tipOptions[position]
        }
    }

    fun computeTotalPrice(price: String?): Double {
        if(price.isNullOrEmpty()){
            return  0.0
        }
        val doublePrice = price!!.toDouble()
        if (doublePrice < 0) {
            checkAmount = 0.00
        }
        checkAmount = doublePrice

        tipAmount = checkAmount * (selectedTipOption.toDouble() / 100)

        totalAmount = checkAmount + tipAmount

        return totalAmount
    }
}