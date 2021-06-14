package org.dk.selenk.klocator.widget

import io.mockk.every
import io.mockk.mockk
import org.dk.selenk.AutomationType
import org.dk.selenk.SelenKConfig
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.EasyMock2Matchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class ButtonTest: BaseWidgetTest(Button) {

    @Test
    fun `assert widget on XCUITest`() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.XcUiTest

        assertThat(widget.widget(), `is`(widget.XCUITEST))
    }

    @Test
    fun `assert widget on uiautomator2`() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.UiAutomator2

        assertThat(widget.widget(), `is`(widget.UIAUTOMATOR2))
    }

    @Test
    fun `assert widget on web`() {
        val config: SelenKConfig = mockk()

        every { config.automationType } returns AutomationType.Web

        assertThat(widget.widget(), `is`(widget.WEB))
    }
}
