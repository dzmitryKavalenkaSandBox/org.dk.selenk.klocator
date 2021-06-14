package org.dk.selenk.klocator.locatorbuilder

import org.openqa.selenium.By

abstract class LocatorBuilder {

    protected var stringBuilder: StringBuilder = StringBuilder()

    fun getLocatorString() = "$stringBuilder"

    abstract fun getLocator(): By
}
