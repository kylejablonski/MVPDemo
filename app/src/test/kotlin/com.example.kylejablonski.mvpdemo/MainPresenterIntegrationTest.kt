package com.example.kylejablonski.mvpdemo

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * This class tests the integration between the view and Model
 * within the MainPresenter class.
 */
@ExtendWith(MockKExtension::class)
class MainPresenterIntegrationTest {

    @RelaxedMockK
    private lateinit var view: Contract.View

    @MockK(relaxUnitFun = true)
    private lateinit var model: MainModel

    // SUT
    @InjectMockKs
    private lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        // Mocking setup
        MockKAnnotations.init(this)
        every { model.tipOptions } returns arrayOf(5, 10, 15, 20, 22)
        every { model.totalAmount } returns 120.0
        every { model.tipAmount } returns 20.0
        every { model.defaultTipIndex } returns 3
        every { model.checkAmount} returns 100.0

        presenter = MainPresenter(view, model)
    }

    @Test
    fun startShouldCallExpectedMethodsWithExpectedValues(){

        presenter.start()


        verify {
            view.displayTipOptions(arrayOf(5, 10, 15, 20, 22))
            view.selectDefaultTipOption(3)
            view.displayInitialPrice(100.0)
        }
    }

    @Test
    fun changeTipPercentWithNegativeValueShouldCallSelectDefaultTipOption(){

        presenter.changeTipPercentage(-1)


        verify {
            view.selectDefaultTipOption(3)
        }
    }

    @Test
    fun computeTotalPriceShouldCallDisplayFinalPriceWithTip(){
        every { model.computeTotalPrice("100") } returns 120.0

        presenter.computeTotalPrice("100")

        verify{
            view.displayFinalPriceWithTip(120.0, 20.0)
        }
    }

    @Test
    fun computeTotalPriceWithZeroShouldCallDisplayError(){
        every { model.computeTotalPrice("0") } returns 0.0

        presenter.computeTotalPrice("0")

        verify{
            view.displayError()
        }
    }
}