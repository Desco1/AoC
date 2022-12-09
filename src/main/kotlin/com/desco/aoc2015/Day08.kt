package com.desco.aoc2015

import com.desco.Day

object Day08: Day(8, 2015) {

    private val hexRegex = "\\\\x[\\da-f]{2}".toRegex()
    private val testInput = setOf(
        "\"\"",
        "\"abc\"",
        "\"aaa\\\"aaa\"",
        "\"\\x27\""
    )

    override fun partOne(): Any {
        return inputList.sumOf {
            it.length - it.replace("\\\\", "y").replace("\\\"", "y").replace(hexRegex, "y").length + 2
        }
    }

    override fun partTwo(): Any {
        return inputList.sumOf {
            it.replace("\\", "yy").replace("\"", "yy").length + 2 - it.length
        }
    }
}