package com.desco.aoc2022

import com.desco.Day

object Day06: Day(6, 2022) {

    override fun partOne(): Any {
        return inputString.windowed(4, 1, false).find {
            it.groupBy { it }.size == 4
        }.run { inputString.indexOf(this!!) + 4 }
    }

    override fun partTwo(): Any {
        return inputString.windowed(14, 1, false).find {
            it.groupBy { it }.size == 14
        }.run { inputString.indexOf(this!!) + 14 }
    }
}