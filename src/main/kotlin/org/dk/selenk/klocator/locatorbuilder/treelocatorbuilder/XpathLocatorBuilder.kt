package org.dk.selenk.klocator.locatorbuilder.treelocatorbuilder

import org.dk.selenk.klocator.locatorbuilder.predicatebuilder.XpathPredicateBuilder
import org.dk.selenk.klocator.widget.Widget
import org.openqa.selenium.By

class XpathLocatorBuilder : TreeLocatorBuilder<XpathPredicateBuilder>() {

    override val nextAbsolute: String = "/"
    override val nextRelative: String = "//"

    companion object {
        inline fun xpath(performTask: XpathLocatorBuilder.() -> Unit): By {
            val builder = XpathLocatorBuilder()
            builder.performTask()
            return builder.getLocator()
        }
    }

    override fun wrapPredicate(predicateString: String): String = if (predicateString.isNotEmpty()) "[$predicateString]" else predicateString

    override fun locatorString(locatorStringBuilder: XpathPredicateBuilder.() -> Unit): String {
        val builder = XpathPredicateBuilder()
        builder.locatorStringBuilder()
        return wrapPredicate(builder.build())
    }

    fun parent(widget: Widget, performTask: XpathPredicateBuilder.() -> Unit) = apply {
        parent(widget)
        stringBuilder.append(locatorString(performTask))
    }

    fun parent(widget: Widget) = apply {
        stringBuilder.append("/parent::${widget.widget()}")
    }

    fun followingSibling(widget: Widget, performTask: XpathPredicateBuilder.() -> Unit = {}) = apply {
        stringBuilder.append("/following-sibling::${widget.widget()}")
        stringBuilder.append(locatorString(performTask))
    }

    fun precedingSibling(widget: Widget, performTask: XpathPredicateBuilder.() -> Unit) = apply {
        precedingSibling(widget)
        stringBuilder.append(locatorString(performTask))
    }

    fun precedingSibling(widget: Widget) = apply {
        stringBuilder.append("/preceding-sibling::${widget.widget()}")
    }

    fun ancestor(widget: Widget, performTask: XpathPredicateBuilder.() -> Unit) = apply {
        ancestor(widget)
        stringBuilder.append(locatorString(performTask))
    }

    fun ancestor(widget: Widget) = apply {
        stringBuilder.append("/ancestor::${widget.widget()}")
    }

    fun or(performTask: XpathPredicateBuilder.() -> Unit) = apply {
        stringBuilder.append(" | ")
        stringBuilder.append(locatorString(performTask))
    }

    override fun getLocator(): By = By.xpath(getLocatorString())
}
