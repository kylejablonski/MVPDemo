package com.example.kylejablonski.mvpdemo

import org.junit.runner.RunWith
import org.junit.runners.Suite
import kotlin.reflect.KClass

@RunWith(Suite::class)
@Suite.SuiteClasses(
        MainModelUnitTest::class,
        MainPresenterIntegrationTest::class
)
class MainTestSuite