package com.desco.aoc2015

import com.desco.Day

object Day01: Day(1, 2015) {

    override fun partOne(): Any {
        return inputString.count { it == '(' } - inputString.count { it == ')'}
    }

    override fun partTwo(): Any {
        var h = 0
        inputString.forEachIndexed { i, c ->
            if (c == '(') {
                h++
            } else if (c == ')') {
                h--
            }
            if (h < 0) {
                return i + 1
            }
        }
        return Unit
    }

}