package org.dk.selenk.klocator.locatorbuilder.predicatebuilder

import org.dk.selenk.klocator.attribute.Attribute
import java.lang.StringBuilder

abstract class PredicateBuilder<PREDICATE_BUILDER : PredicateBuilder<PREDICATE_BUILDER>> {

    protected val stringBuilder: StringBuilder = StringBuilder()

    infix abstract fun <T> Attribute.isEquals(value: T): XpathPredicateBuilder

    infix abstract fun <T> Attribute.contains(value: T): XpathPredicateBuilder

    abstract infix fun and(predicateBuilder: PREDICATE_BUILDER.() -> Unit): PREDICATE_BUILDER

    abstract infix fun or(predicateBuilder: PREDICATE_BUILDER.() -> Unit): PREDICATE_BUILDER

    abstract fun not(predicateBuilder: PREDICATE_BUILDER.() -> Unit): PREDICATE_BUILDER

    open fun build() = "$stringBuilder"
}
