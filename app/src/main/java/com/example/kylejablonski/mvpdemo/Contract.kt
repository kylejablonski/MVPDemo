package com.example.kylejablonski.mvpdemo


interface Contract {

    interface View {

        fun displayTipOptions(percentages: Array<Int>)

        fun selectDefaultTipOption()

        fun displayInitialPrice(total: Double)

        fun displayFinalPriceWithTip(total: Double, tipAmount: Double)

        fun displayError()
    }

    interface Presenter {

        fun start()

        fun changeTipPercentage(position: Int)

        fun computeTotalPrice(billPrice: String)


    }
}
