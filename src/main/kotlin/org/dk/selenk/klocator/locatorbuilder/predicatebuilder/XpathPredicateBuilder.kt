package org.dk.selenk.klocator.locatorbuilder.predicatebuilder

import org.dk.selenk.klocator.attribute.Attribute

class XpathPredicateBuilder : PredicateBuilder<XpathPredicateBuilder>() {

    companion object {
        inline fun predicate(performTask: XpathPredicateBuilder.() -> Unit): String {
            val builder = XpathPredicateBuilder()
            builder.performTask()
            return builder.build()
        }
    }

    override infix fun <T> Attribute.isEquals(value: T): XpathPredicateBuilder {
        stringBuilder.append("${asPredicateString(this@XpathPredicateBuilder)} = $value")
        return this@XpathPredicateBuilder
    }

    override infix fun <T> Attribute.contains(value: T): XpathPredicateBuilder {
        stringBuilder.append("contains(${asPredicateString(this@XpathPredicateBuilder)}, $value)")
        return this@XpathPredicateBuilder
    }

    override infix fun and(predicateBuilder: XpathPredicateBuilder.() -> Unit) = apply {
        stringBuilder.append(" and ")
        stringBuilder.append(predicate(predicateBuilder))
    }

    override fun not(predicateBuilder: XpathPredicateBuilder.() -> Unit) = apply {
        stringBuilder.append("not(${predicate(predicateBuilder)})")
    }

    override infix fun or(predicateBuilder: XpathPredicateBuilder.() -> Unit) = apply {
        stringBuilder.append(" or ")
        stringBuilder.append(predicate(predicateBuilder))
    }
}
