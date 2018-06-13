package com.example.kylejablonski.mvpdemo

class MainPresenter(
        private val view: Contract.View,
        private val model: MainModel
) : Contract.Presenter {

    override fun start() {
        view.displayTipOptions(model.tipOptions)
        view.selectDefaultTipOption()
        view.displayInitialPrice(model.checkAmount)
    }

    override fun changeTipPercentage(position: Int) {
        model.setTipOption(position)
        if (position < 0) {
            view.selectDefaultTipOption()
        }
    }

    override fun computeTotalPrice(billPrice: String) {
        val finalPrice = model.computeTotalPrice(billPrice)
        if (finalPrice == 0.0) {
            view.displayError()
        } else {
            view.displayFinalPriceWithTip(finalPrice, model.tipAmount)
        }
    }
}