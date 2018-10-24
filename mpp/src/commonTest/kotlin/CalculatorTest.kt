package houtbecke.rs.mpp

import houtbecke.rs.mpp.Calculator
import houtbecke.rs.mpp.Factory
import kotlin.test.Test
import kotlin.test.assertEquals

open class CalculatorTest {

    @Test
    fun testSum() {
        assertEquals(3, Calculator.sum(1, 2))
    }

    @Test
    fun testFactory() {
        val product = Factory.create(mapOf("user" to "jetbrains"))
        assertEquals(product.user, "jetbrains")
    }
}

