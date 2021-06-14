package org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder

import io.mockk.every
import io.mockk.mockk
import org.dk.selenk.AutomationType
import org.dk.selenk.SelenKConfig
import org.dk.selenk.klocator.attribute.Id
import org.dk.selenk.klocator.attribute.Name
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.XpathPredicateBuilder
import org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder.XpathLocatorBuilder.Companion.xpath
import org.dk.selenk.klocator.widget.Button
import org.dk.selenk.klocator.widget.StaticText
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import org.openqa.selenium.By

class XpathLocatorBuilderTest {

    private val expectedLocatorString: (AutomationType) -> By = {
        By.xpath("//${Button.widget()}[${Name.asPredicateString(XpathPredicateBuilder())} = testText " +
            "or contains(${Id.asPredicateString(XpathPredicateBuilder())}, testId)]" +
            "/following-sibling::${StaticText.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} = testId1]" +
            "/preceding-sibling::${StaticText.widget()}[contains(${Id.asPredicateString(XpathPredicateBuilder())}, testId2)]" +
            "/${Button.widget()}[not(contains(${Id.asPredicateString(XpathPredicateBuilder())}, testId2))]")
    }
    private val xpath: (AutomationType) -> By = {
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

        val xpath = xpath(config.automationType)
        assertThat(xpath, `is`(expectedLocatorString(config.automationType)))
    }

    @Test
    fun testXpathLocatorOnUiAutomator2() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.UiAutomator2

        val xpath = xpath(config.automationType)
        assertThat(xpath, `is`(expectedLocatorString(config.automationType)))
    }

    @Test
    fun testXpathLocatorOnXcUiTest() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.XcUiTest

        val xpath = xpath(config.automationType)
        assertThat(xpath, `is`(expectedLocatorString(config.automationType)))
    }
}
