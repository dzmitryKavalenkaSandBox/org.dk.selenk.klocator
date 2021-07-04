package org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder

import io.mockk.every
import io.mockk.mockk
import org.dk.selenk.common.AutomationType
import org.dk.selenk.common.SelenKConfig
import org.dk.selenk.common.attribute.Id
import org.dk.selenk.common.attribute.Name
import org.dk.selenk.common.attribute.Text
import org.dk.selenk.common.widget.general.Button
import org.dk.selenk.common.widget.general.Image
import org.dk.selenk.common.widget.general.StaticText
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.PredicateBuilder.Companion.asPredicateString
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.XpathFunction
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.XpathPredicateBuilder
import org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder.XpathLocatorBuilder.Companion.xpath
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

class XpathLocatorBuilderTest {

    private val expectedLocatorString: (AutomationType) -> By = {
        By.xpath("//${Button.widget()}[${Name.asPredicateString(XpathPredicateBuilder())} = 'testText' " +
            "or contains(${Id.asPredicateString(XpathPredicateBuilder())}, 'testId')]" +
            "/following-sibling::${StaticText.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} = 'testId1']" +
            "/preceding-sibling::${StaticText.widget()}[contains(${Id.asPredicateString(XpathPredicateBuilder())}, 'testId2')]" +
            "/${Button.widget()}[not(contains(${Id.asPredicateString(XpathPredicateBuilder())}, 'testId2'))] | " +
            "//${Button.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} = '1 - 2' and ${Text.asPredicateString(XpathPredicateBuilder())} = '-3 + 2']" +
            "/${Image.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} != 'testId3']" +
            "/${Image.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} < '2']" +
            "/${Image.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} <= '2']" +
            "/${Image.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} > '2']" +
            "/${Image.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} >= '2']" +
            "/${Image.widget()}[${Id.asPredicateString(XpathPredicateBuilder())} >= '1 mod 2']" +
            "/${Image.widget()}[${Text.asPredicateString(XpathPredicateBuilder())} = 'testText']" +
            "/${Image.widget()}[contains(${Text.asPredicateString(XpathPredicateBuilder())}, 'testText')][0]" +
            "//${Image.widget()}" +
            "/ancestor::${Image.widget()}" +
            "/ancestor::${Image.widget()}[${Text.asPredicateString(XpathPredicateBuilder())} = 'testText']" +
            "/parent::${Image.widget()}[${Text.asPredicateString(XpathPredicateBuilder())} = 'testText']"
        )
    }
    private val xpathLocator: (AutomationType) -> By = {
        xpath {
            relative(Button) { Name isEquals "testText" or { Id contains "testId" } }
            followingSibling(StaticText) { Id isEquals "testId1" }
            precedingSibling(StaticText) { Id contains "testId2" }
            nextAbsolute(Button) { not { Id contains "testId2" } }
            or {
                relative(Button) { Id isEquals { 1 minus 2 } and { Text isEquals { -3 plus 2 } } }
                nextAbsolute(Image) { Id isNotEquals "testId3" }
                nextAbsolute(Image) { Id lessThen "2" }
                nextAbsolute(Image) { Id lessOrEqual "2" }
                nextAbsolute(Image) { Id greaterThen "2" }
                nextAbsolute(Image) { Id greaterOrEqual "2" }
                nextAbsolute(Image) { Id greaterOrEqual { 1 modulus 2 } }
                nextAbsolute(Image) { Text isEquals "testText" }
                nextAbsolute(Image) { Text contains "testText" }
                withIndex(0)
                nextRelative(Image)
                ancestor(Image)
                ancestor(Image) { Text isEquals "testText" }
                parent(Image) { Text isEquals "testText" }
            }
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
