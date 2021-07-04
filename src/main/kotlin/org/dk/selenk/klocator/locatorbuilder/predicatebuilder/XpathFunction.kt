package org.dk.selenk.klocator.locatorbuilder.predicatebuilder

class XpathFunction {

    infix fun Int.minus(value: Int): String {
        return "$this - $value"
    }

    infix fun Int.plus(value: Int): String {
        return "$this + $value"
    }

    infix fun Int.modulus(value: Int): String {
        return "$this mod $value"
    }
}
