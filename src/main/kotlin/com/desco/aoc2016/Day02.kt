package com.desco.aoc2016

import com.desco.Day

fun main() = Day02.second()

object Day02: Day(2, 2016) {

    private val buttonArrayFirst = arrayOf(
        arrayOf(1, 2, 3),
        arrayOf(4, 5, 6),
        arrayOf(7, 8, 9)
    )

    private val buttonArraySecond = arrayOf(
        arrayOf("", "", "1", "", ""),
        arrayOf("", "2", "3", "4", ""),
        arrayOf("5", "6", "7", "8", "9"),
        arrayOf("", "A", "B", "C", ""),
        arrayOf("", "", "D", "", "")
    )

    override fun partOne(): Any {
        var x = 1
        var y = 1
        val buttons = mutableListOf<Int>()
        for (s in inputList) {
            for (c in s) {
                when (c)  {
                    'U' -> x--
                    'D' -> x++
                    'L' -> y--
                    'R' -> y++
                }
                x = x.coerceIn(0, 2)
                y = y.coerceIn(0, 2)
            }
            buttons.add(buttonArrayFirst[x][y])
        }
        return buttons.joinToString("")
    }

    override fun partTwo(): Any {
        var x = 2
        var y = 0
        val buttons = mutableListOf<String>()
        for (s in inputList) {
            for (c in s) {
                when (c)  {
                    'U' -> {
                        x--
                        try {
                            if (buttonArraySecond[x][y] == "") {
                                x++
                            }
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            x++
                        }
                    }
                    'D' -> {
                        x++
                        try {
                            if (buttonArraySecond[x][y] == "") {
                                x--
                            }
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            x--
                        }
                    }
                    'L' -> {
                        y--
                        try {
                            if (buttonArraySecond[x][y] == "") {
                                y++
                            }
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            y++
                        }
                    }
                    'R' -> {
                        y++
                        try {
                            if (buttonArraySecond[x][y] == "") {
                                y--
                            }
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            y--
                        }
                    }
                }
            }
            buttons.add(buttonArraySecond[x][y])
        }
        return buttons.joinToString("")
    }
}