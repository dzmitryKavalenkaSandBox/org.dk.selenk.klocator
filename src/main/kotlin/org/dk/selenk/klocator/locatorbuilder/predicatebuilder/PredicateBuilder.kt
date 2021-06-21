package org.dk.selenk.klocator.locatorbuilder.predicatebuilder

import org.dk.selenk.common.AutomationType
import org.dk.selenk.common.SelenKConfig
import org.dk.selenk.common.attribute.Attribute
import java.lang.StringBuilder

abstract class PredicateBuilder<PREDICATE_BUILDER : PredicateBuilder<PREDICATE_BUILDER>> {

    companion object {
        fun Attribute.asPredicateString(predicateBuilder: PredicateBuilder<*>): String = when {
            predicateBuilder is XpathPredicateBuilder && SelenKConfig.automationType == AutomationType.XcUiTest -> attribute()
            else -> "@${attribute()}"
        }
    }

    protected val stringBuilder: StringBuilder = StringBuilder()

    infix abstract fun <T> Attribute.isEquals(value: T): XpathPredicateBuilder

    infix abstract fun <T> Attribute.contains(value: T): XpathPredicateBuilder

    abstract infix fun and(predicateBuilder: PREDICATE_BUILDER.() -> Unit): PREDICATE_BUILDER

    abstract infix fun or(predicateBuilder: PREDICATE_BUILDER.() -> Unit): PREDICATE_BUILDER

    abstract fun not(predicateBuilder: PREDICATE_BUILDER.() -> Unit): PREDICATE_BUILDER

    open fun build() = "$stringBuilder"
}
