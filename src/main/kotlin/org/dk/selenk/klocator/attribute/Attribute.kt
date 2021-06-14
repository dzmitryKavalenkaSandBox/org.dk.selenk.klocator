package org.dk.selenk.klocator.attribute

import org.dk.selenk.AutomationType
import org.dk.selenk.SelenKConfig
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.PredicateBuilder
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.XpathPredicateBuilder

@Suppress("PropertyName")
abstract class Attribute {

    internal abstract val WEB: String
    internal abstract val XCUITEST: String
    internal abstract val UIAUTOMATOR2: String

    fun attribute(automationType: AutomationType = SelenKConfig.automationType): String =
        when (automationType) {
            is AutomationType.Web -> WEB
            is AutomationType.XcUiTest -> XCUITEST
            is AutomationType.UiAutomator2 -> UIAUTOMATOR2
        }

    fun asPredicateString(predicateBuilder: PredicateBuilder<*>): String = when {
        predicateBuilder is XpathPredicateBuilder && SelenKConfig.automationType == AutomationType.XcUiTest -> attribute()
        else -> "@${attribute()}"
    }
}
