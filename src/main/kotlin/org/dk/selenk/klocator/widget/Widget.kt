package org.dk.selenk.klocator.widget

import org.dk.selenk.AutomationType
import org.dk.selenk.SelenKConfig

@Suppress("PropertyName")
abstract class Widget {

    internal abstract val WEB: String
    internal abstract val XCUITEST: String
    internal abstract val UIAUTOMATOR2: String

    fun widget(automationType: AutomationType = SelenKConfig.automationType): String =
        when (automationType) {
            is AutomationType.Web -> Button.WEB
            is AutomationType.XcUiTest -> Button.XCUITEST
            is AutomationType.UiAutomator2 -> Button.UIAUTOMATOR2
        }
}
