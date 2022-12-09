package com.desco.aoc2015

import com.desco.Day
import kotlin.math.min

object Day20: Day(20, 2015) {

    private val presentNumber = inputString.toInt()

    override fun partOne(): Any {

        val houses = Array(presentNumber / 10 + 3) { 0 }

        for (i in 1 .. presentNumber / 10) {
            for (j in i .. presentNumber / 10 step i) {
                houses[j] += i * 10
            }
        }

        for (i in houses.indices) {
            if (houses[i] >= presentNumber) {
                return i
            }
        }
        return Unit
    }

    override fun partTwo(): Any {

        val houses = Array(presentNumber / 10 + 3) { 0 }

        for (i in 1 .. presentNumber / 10) {
            for (j in i .. min(presentNumber / 10, 50 * i) step i) {
                houses[j] += i * 11
            }
        }

        for (i in houses.indices) {
            if (houses[i] >= presentNumber) {
                return i
            }
        }
        return Unit
    }
}