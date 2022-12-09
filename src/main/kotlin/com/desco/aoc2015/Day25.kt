package com.desco.aoc2015

import com.desco.Day

object Day25: Day(25, 2015) {

    private val input = "Enter the code at row (\\d+), column (\\d+)\\.".toRegex().find(inputString)?.run {
        val (row, column) = this.destructured
        row.toInt() to column.toInt()
    }
    private var init = 20151125L
    private val factor = 252533L
    private val divider = 33554393L

    override fun partOne(): Any {

        var times = 0
        var prev = 0
        repeat(input!!.first + input.second - 2) {
            prev++
            times += prev
        }

        times += input.second - 1

        println("Times: $times")

        repeat(times) {
            init = init.run {
                this * factor % divider
            }
        }
        return init
    }

    override fun partTwo(): Any {
        TODO("Not yet implemented")
    }
}