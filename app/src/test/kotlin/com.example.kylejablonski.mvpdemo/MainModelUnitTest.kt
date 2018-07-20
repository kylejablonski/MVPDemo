package com.example.kylejablonski.mvpdemo

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


import org.junit.jupiter.api.TestInstance

/**
 * Local unit test, which will execute on the development machine (host).
 * This tests all the functionality of the MainModel class in the application.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainModelUnitTest {

    private val model = MainModel()

    @Test
    fun fivePercentTipForOneHundredDollarsShouldReturnOneHundredAndFiveDollars() {
        model.setTipOption(0)

        val price = model.computeTotalPrice("100")

        assertThat(price).isEqualTo(105.0)
    }

    @Test
    fun tenPercentTipForOneHundredDollarsShouldReturnOneHundredAndTenDollars() {
        model.setTipOption(1)

        val price = model.computeTotalPrice("100")

        assertThat(price).isEqualTo(110.0)
    }

    @Test
    fun fifteenPercentTipForOneHundredDollarsShouldReturnOneHundredAndFifteenDollars() {
        model.setTipOption(2)

        val price = model.computeTotalPrice("100")

        assertThat(price).isEqualTo(115.0)
    }

    @Test
    fun twentyPercentTipForOneHundredDollarsShouldReturnOneHundredAndTwentyDollars() {
        model.setTipOption(3)

        val price = model.computeTotalPrice("100")

        assertThat(price).isEqualTo(120.0)
    }

    @Test
    fun twentyTwoPercentTipForOneHundredDollarsShouldReturnOneHundredAndTwentyTwoDollars() {
        model.setTipOption(4)

        val price = model.computeTotalPrice("100")

        assertThat(price).isEqualTo(122.0)
    }

    @Test
    fun invalidTipOptionShouldReturnTotalPriceWithTwentyPercentTip() {
        model.setTipOption(6)

        val price = model.computeTotalPrice("100")

        assertThat(price).isEqualTo(120.0)
    }

    @Test
    fun twentyTwoPercentTipForOneHundredDollarsShouldYieldExpectedValues() {
        model.setTipOption(4)

        model.computeTotalPrice("100")

        assertThat(model.totalAmount).isEqualTo(122.0)
        assertThat(model.checkAmount).isEqualTo(100.0)
        assertThat(model.tipAmount).isEqualTo(22.0)
    }

    @Test
    fun nullPriceShouldReturnZeroTotal(){
        val total = model.computeTotalPrice(null)

        assertThat(total).isZero()
    }

    @Test
    fun negativePriceShouldReturnZeroCheckAmount(){
        model.computeTotalPrice("-1")

        assertThat(model.totalAmount).isEqualTo(0.0)
        assertThat(model.checkAmount).isEqualTo(0.0)
        assertThat(model.tipAmount).isEqualTo(0.0)

    }



}