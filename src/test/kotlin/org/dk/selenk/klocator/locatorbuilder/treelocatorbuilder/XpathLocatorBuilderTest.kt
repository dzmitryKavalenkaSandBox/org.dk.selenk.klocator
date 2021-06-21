package org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder

import io.mockk.every
import io.mockk.mockk
import org.dk.selenk.common.AutomationType
import org.dk.selenk.common.SelenKConfig
import org.dk.selenk.common.attribute.Id
import org.dk.selenk.common.attribute.Name
import org.dk.selenk.common.widget.Button
import org.dk.selenk.common.widget.StaticText
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.PredicateBuilder.Companion.asPredicateString
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.XpathPredicateBuilder
import org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder.XpathLocatorBuilder.Companion.xpath
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

class XpathLocatorBuilderTest {

    private val expectedLocatorString: (AutomationType) -> By = {
        By.xpath("//${Button.widget()}[${Name.asPredicateString(XpathPredicateBuilder())} = testText " +
            "or contains(${Id.asPredicateString(XpathPredicateBuilder())}, testId)]" +
            "/following-sibling::${StaticText.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} = testId1]" +
            "/preceding-sibling::${StaticText.widget()}[contains(${Id.asPredicateString(XpathPredicateBuilder())}, testId2)]" +
            "/${Button.widget()}[not(contains(${Id.asPredicateString(XpathPredicateBuilder())}, testId2))] | " +
            "//button[]")
    }
    private val xpathLocator: (AutomationType) -> By = {
        xpath {
            relative(Button) { Name isEquals "testText" or { Id contains "testId" } }
            followingSibling(StaticText) { Id isEquals "testId1" }
            precedingSibling(Button) { Id contains "testId2" }
            nextAbsolute(Button) { not { Id contains "testId2" } }
        }
    }

    @Test
    fun testXpathLocatorOnWeb() {
        val config: SelenKConfig = mockk()
        every { config.automationType } returns AutomationType.Web

        val xpath = xpathLocator(config.automationType)
        assertThat(xpath, `is`(expectedLocatorString(config.automationType)))
    }

    @Test
    fun testXpathLocatorOnUiAutomator2() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.UiAutomator2

        val xpath = xpathLocator(config.automationType)
        assertThat(xpath, `is`(expectedLocatorString(config.automationType)))
    }

    @Test
    fun testXpathLocatorOnXcUiTest() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.XcUiTest

        val xpath = xpathLocator(config.automationType)
        assertThat(xpath, `is`(expectedLocatorString(config.automationType)))
    }
}
