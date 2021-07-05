package org.dk.selenk.klocator.locatorbuilder.predicatebuilder

import org.dk.selenk.common.attribute.Attribute
import org.dk.selenk.common.widget.Widget

@Suppress("ClassName")
open class iOSNativePredicateBuilder : PredicateBuilder<iOSNativePredicateBuilder>() {

    companion object {
        inline fun predicate(performTask: iOSNativePredicateBuilder.() -> Unit): String {
            val builder = iOSNativePredicateBuilder()
            builder.performTask()
            return builder.build().replace("\\s+".toRegex(), " ")
        }
    }

    override fun <T> Attribute.isEquals(value: T): iOSNativePredicateBuilder {
        stringBuilder.append("${asPredicateString(this@iOSNativePredicateBuilder)} == ${value.escapeStringValue()}")
        return this@iOSNativePredicateBuilder
    }

    override fun <T> Attribute.contains(value: T): iOSNativePredicateBuilder {
        stringBuilder.append("${asPredicateString(this@iOSNativePredicateBuilder)} CONTAINS ${value.escapeStringValue()}")
        return this@iOSNativePredicateBuilder
    }

    infix fun <T> Attribute.beginsWith(value: T): iOSNativePredicateBuilder {
        stringBuilder.append("${asPredicateString(this@iOSNativePredicateBuilder)} BEGINSWITH ${value.escapeStringValue()}")
        return this@iOSNativePredicateBuilder
    }

    infix fun <T> Attribute.endsWith(value: T): iOSNativePredicateBuilder {
        stringBuilder.append("${asPredicateString(this@iOSNativePredicateBuilder)} ENDSWITH ${value.escapeStringValue()}")
        return this@iOSNativePredicateBuilder
    }

    infix fun <T> Attribute.beginsWithIgnoreCase(value: T): iOSNativePredicateBuilder {
        stringBuilder.append("${asPredicateString(this@iOSNativePredicateBuilder)} BEGINSWITH[c] ${value.escapeStringValue()}")
        return this@iOSNativePredicateBuilder
    }

    infix fun <T> Attribute.inPredicate(value: T): iOSNativePredicateBuilder {
        stringBuilder.append("${asPredicateString(this@iOSNativePredicateBuilder)} IN {${value.escapeStringValue()}}")
        return this@iOSNativePredicateBuilder
    }

    override fun and(predicateBuilder: iOSNativePredicateBuilder.() -> Unit) = apply {
        stringBuilder.append(" AND ")
        stringBuilder.append(predicate(predicateBuilder))
    }

    override fun or(predicateBuilder: iOSNativePredicateBuilder.() -> Unit) = apply {
        stringBuilder.append(" OR ")
        stringBuilder.append(predicate(predicateBuilder))
    }

    override fun not(predicateBuilder: iOSNativePredicateBuilder.() -> Unit) = apply {
        stringBuilder.append(" NOT ${predicate(predicateBuilder)}")
    }

    fun typeEquals(widget: Widget) = apply {
        stringBuilder.append("type == \"${widget.widget()}\"")
    }

    private fun <R> R.escapeStringValue() = if (this is String) "\"$this\"" else this

}
