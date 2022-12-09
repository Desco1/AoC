package com.desco.aoc2022

import com.desco.Day

object Day03: Day(3, 2022) {

    private val chars = ('a' .. 'z') + ('A' .. 'Z')

    override fun partOne(): Any {
        return inputList.sumOf {
            val (first, second) = arrayOf(it.substring(0, it.length / 2), it.substring(it.length / 2))
            first.filter { it in second }.groupBy { it }.keys.sumOf {
                chars.indexOf(it) + 1
            }
        }
    }

    override fun partTwo(): Any {
        return inputList.chunked(3).sumOf {
            val (first, second, third) = it
            first.filter { it in second && it in third }.groupBy { it }.keys.sumOf {
                chars.indexOf(it) + 1
            }
        }
    }
}