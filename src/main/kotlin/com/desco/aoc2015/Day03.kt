package com.desco.aoc2015

import com.desco.Day

object Day03: Day(3, 2015) {

    override fun partOne(): Any {
        val coordSet = mutableSetOf<Coords>()
        var x = 0
        var y = 0
        coordSet.add(Coords(0, 0))
        for (c in inputString) {
            when (c) {
                '>' -> x++
                '<' -> x--
                '^' -> y++
                'v' -> y--
            }
            coordSet.add(Coords(x, y))
        }
        return coordSet.size
    }

    override fun partTwo(): Any {
        val coordSet = mutableSetOf<Coords>()
        var x0 = 0
        var y0 = 0
        var x1 = 0
        var y1 = 0
        coordSet.add(Coords(0, 0))
        inputString.forEachIndexed { i, c ->
            if (i % 2 == 0) {
                when (c) {
                    '>' -> x0++
                    '<' -> x0--
                    '^' -> y0++
                    'v' -> y0--
                }
                coordSet.add(Coords(x0, y0))
            } else {
                when (c) {
                    '>' -> x1++
                    '<' -> x1--
                    '^' -> y1++
                    'v' -> y1--
                }
                coordSet.add(Coords(x1, y1))
            }
        }
        return coordSet.size
    }

    data class Coords(val x: Int, val y: Int)
}