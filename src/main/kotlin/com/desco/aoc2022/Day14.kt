package com.desco.aoc2022

import com.desco.Day
import kotlin.math.max
import kotlin.math.min

object Day14: Day(14, 2022) {

    private var map: Array<Array<Boolean>>
    private var offset: Int
    private val rockRegex = "(\\d+),(\\d+)".toRegex()
    // Not a perfect regex, but it's a way to have overlapping matches
    private val lineRegex = "(?=((\\d{3}),(\\d+) -> (\\d{3}),(\\d+)))".toRegex()

    init {
        val matches = inputList.map { rockRegex.findAll(it).toList() }.flatten()
        val left = matches.minOf { it.groupValues[1].toInt() }
        val right = matches.maxOf { it.groupValues[1].toInt() }
        val down = matches.maxOf { it.groupValues[2].toInt() }
        offset = 500 - left
        map = Array(down + 1) { Array(right - left + 1) { false } }

        for (s in inputList) {
            for (it in lineRegex.findAll(s)) {
                // First two groups ignored because they are lookahead's empty and the full match
                val (_, g1, g2, g3, g4) = it.destructured
                val firstX = g1.toInt() - 500
                val firstY = g2.toInt()
                val secondX = g3.toInt() - 500
                val secondY = g4.toInt()
                if (firstX == secondX) {
                    val minY = min(firstY, secondY)
                    val maxY = max(firstY, secondY)
                    for (y in minY .. maxY) {
                        map[y][firstX + offset] = true
                    }
                } else if (firstY == secondY) {
                    val minX = min(firstX, secondX)
                    val maxX = max(firstX, secondX)
                    for (x in minX .. maxX) {
                        map[firstY][x + offset] = true
                    }
                } else throw RuntimeException()
            }
        }
    }

    override fun partOne(): Any {
        var i = 0
        while (!dropSandFirst()) {
            i++
        }
        return i
    }

    override fun partTwo(): Any {
        // Add the floor and the empty layer
        map = Array(map.size + 2) { Array(map.first().size) { false } }.apply {
            map.forEachIndexed { y, arrZ ->
                arrZ.forEachIndexed { x, z ->
                    this[y][x] = z
                }
            }
        }
        map[map.size - 1] = Array(map.first().size) { true }

        var i = 0
        while (!dropSandSecond()) {
            i++
        }
        // One extra because the sand at 500,0 isn't actually placed
        return i + 1
    }

    private fun dropSandFirst(): Boolean {
        var x = offset
        var y = 0

        try {
            while (true) {
                while (!map[y][x]) {
                    y++
                }

                if (!map[y][x - 1]) {
                    x--
                    continue
                }

                if (!map[y][x + 1]) {
                    x++
                    continue
                }

                break
            }
        } catch (e: IndexOutOfBoundsException) {
            return true
        }

        map[y - 1][x] = true
        return false
    }

    private fun dropSandSecond(): Boolean {
        var x = offset
        var y = 0

        fun doTheThing() {
            while (true) {
                while (!map[y][x]) {
                    y++
                }

                if (!map[y][x - 1]) {
                    x--
                    continue
                }

                if (!map[y][x + 1]) {
                    x++
                    continue
                }

                break
            }
        }

        try {
            doTheThing()
        } catch (e: IndexOutOfBoundsException) {
            if (x < offset) {
                offset++
                // Expand left
                map = map.map { Array(it.size + 1) { false }
                    .apply {
                        it.forEachIndexed { i, z ->
                            this[i + 1] = z
                        }
                    }
                }.toTypedArray()
                // Make sure floor is still made out of floor
                map[map.size - 1] = Array(map.first().size) { true }
            } else if (x > offset) {
                // Expand right
                map = map.map { Array(it.size + 1) { false }
                    .apply {
                        it.forEachIndexed { i, z ->
                            this[i] = z
                        }
                    }
                }.toTypedArray()
                // Floor is indeed made out of floor
                map[map.size - 1] = Array(map.first().size) { true }
            }
            return dropSandSecond()
        }

        if (y == 1 && x == offset) {
            return true
        }
        map[y - 1][x] = true
        return false
    }
}