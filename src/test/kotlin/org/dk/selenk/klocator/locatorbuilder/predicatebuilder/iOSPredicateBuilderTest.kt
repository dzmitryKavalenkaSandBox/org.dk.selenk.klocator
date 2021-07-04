package org.dk.selenk.klocator.locatorbuilder.predicatebuilder

import io.mockk.every
import io.mockk.mockk
import org.dk.selenk.common.AutomationType
import org.dk.selenk.common.SelenKConfig
import org.dk.selenk.common.attribute.Text
import org.dk.selenk.common.attribute.Visible
import org.dk.selenk.common.widget.general.Image
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.PredicateBuilder.Companion.asPredicateString
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.iOSNativePredicateBuilder.Companion.predicate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

@Suppress("ClassName")
class iOSPredicateBuilderTest {

    @Test
    fun `type predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\""

        val actualPredicate: String = predicate {
            typeEquals(Image)
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Test
    fun `type and predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" AND ${Text.asPredicateString(iOSNativePredicateBuilder())} == \"testText\""

        val actualPredicate: String = predicate {
            typeEquals(Image) and {
                Text isEquals "testText"
            }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Test
    fun `type or predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" OR ${Text.asPredicateString(iOSNativePredicateBuilder())} == \"testText\""

        val actualPredicate: String = predicate {
            typeEquals(Image) or {
                Text isEquals "testText"
            }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Test
    fun `type not predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" AND NOT ${Text.asPredicateString(iOSNativePredicateBuilder())} == \"testText\""

        val actualPredicate: String = predicate {
            typeEquals(Image) and {
                not { Text isEquals "testText" }
            }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Test
    fun `visible attribute predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" AND ${Visible.asPredicateString(iOSNativePredicateBuilder())} == 1"

        val actualPredicate: String = predicate {
            typeEquals(Image) and { Visible isEquals 1 }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `beginswith predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" AND ${Text.asPredicateString(iOSNativePredicateBuilder())} BEGINSWITH \"bla\""

        val actualPredicate: String = predicate {
            typeEquals(Image) and { Text beginsWith "bla" }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `beginswith ignore case predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" AND ${Text.asPredicateString(iOSNativePredicateBuilder())} BEGINSWITH[c] \"bla\""

        val actualPredicate: String = predicate {
            typeEquals(Image) and { Text beginsWithIgnoreCase "bla" }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `endswith ignore case predicate resolved correctly`() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.XcUiTest

        val expectedPredicateString = "type == \"${Image.widget()}\" AND ${Text.asPredicateString(iOSNativePredicateBuilder())} ENDSWITH \"bla\""

        val actualPredicate: String = predicate {
            typeEquals(Image) and { Text endsWith "bla" }
        }

        assertThat(expectedPredicateString, `is`(actualPredicate))
    }


}
