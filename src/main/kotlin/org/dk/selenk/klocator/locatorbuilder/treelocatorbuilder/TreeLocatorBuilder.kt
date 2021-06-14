package org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder

import org.dk.selenk.klocator.locatorbuilder.LocatorBuilder
import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.PredicateBuilder
import org.dk.selenk.klocator.widget.Widget

abstract class TreeLocatorBuilder<PREDICATE_BUILDER : PredicateBuilder<PREDICATE_BUILDER>> : LocatorBuilder() {

    protected abstract val nextAbsolute: String
    protected abstract val nextRelative: String

    protected abstract fun wrapPredicate(predicateString: String): String

    abstract fun locatorString(locatorStringBuilder: PREDICATE_BUILDER.() -> Unit): String

    open fun relative(widget: Widget, performTask: PREDICATE_BUILDER.() -> Unit = {}) = apply {
        stringBuilder.append("$nextRelative${widget.widget()}")
        stringBuilder.append(locatorString(performTask))
    }

    fun nextAbsolute(widget: Widget, performTask: PREDICATE_BUILDER.() -> Unit = {}) = apply {
        stringBuilder.append("$nextAbsolute${widget.widget()}")
        stringBuilder.append(locatorString(performTask))
    }

    fun nextRelative(widget: Widget, performTask: PREDICATE_BUILDER.() -> Unit = {}) = apply {
        stringBuilder.append("$nextRelative${widget.widget()}")
        stringBuilder.append(locatorString(performTask))
    }

    fun withIndex(index: Int) = apply {
        stringBuilder.append("[$index]")
    }
}
