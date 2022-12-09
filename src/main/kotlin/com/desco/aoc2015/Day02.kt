package com.desco.aoc2015

import com.desco.Day

object Day02: Day(2, 2015) {

    override fun partOne(): Any {
        return inputList.sumOf {
            val (first, second, third) = it.split("x").map { it.toInt() }
            val max = maxOf(first, second, third)
            return@sumOf 2*first*second + 2*second*third + 2*third*first + first*second*third/max
        }
    }

    override fun partTwo(): Any {
        return inputList.sumOf {
            val (first, second, third) = it.split("x").map { it.toInt() }
            val max = maxOf(first, second, third)
            return@sumOf first*second*third + 2 * (first + second + third) - 2 * max
        }
    }
}