package com.example.kylejablonski.mvpdemo

/**
 * Interface grouping for the MainPresenter and MainActivity
 */
interface Contract {

    interface View {

        /**
         * Displays the tip percent options within the view
         */
        fun displayTipOptions(percentages: Array<Int>)

        /**
         * Selects the default tip (i.e 20%) for the calculations
         */
        fun selectDefaultTipOption(index: Int)

        /**
         * Displays the initial price when text is cleared or app is loaded
         */
        fun displayInitialPrice(total: Double)

        /**
         * Displays the final price and the tip
         */
        fun displayFinalPriceWithTip(total: Double, tipAmount: Double)

        /**
         * Displays an error such as an empty price field
         */
        fun displayError()
    }

    interface Presenter {

        /**
         * Starts the presenter
         */
        fun start()

        /**
         * Changes the tip percentage based on selected position in ChipGroup
         */
        fun changeTipPercentage(position: Int)

        /**
         * Computes the final price using the value from the EditText
         */
        fun computeTotalPrice(billPrice: String)


    }
}
