package com.desco.aoc2016

import com.desco.Day

fun main() = Day01.second()

object Day01: Day(1, 2016) {

    private val instrRegex = "([RL])(\\d+)".toRegex()

    override fun partOne(): Any {
        var currentView = View.NORTH
        var currentCoords = 0 to 0
        for (it in instrRegex.findAll(inputString)) {
            val (type, amount) = it.destructured
            currentView = currentView.rotateDirection(type).also {
                val (x, y) = it.rotateCoord(amount.toInt())
                currentCoords = currentCoords.first + x to currentCoords.second + y
            }
        }
        return currentCoords
    }

    override fun partTwo(): Any {
        var currentView = View.NORTH
        var currentCoords = 0 to 0
        val locations = mutableSetOf(currentCoords)
        for (it in instrRegex.findAll(inputString)) {
            val (type, amount) = it.destructured
            currentView = currentView.rotateDirection(type).also {
                val (x, y) = it.rotateCoord(1)
                repeat(amount.toInt()) {
                    currentCoords = currentCoords.first + x to currentCoords.second + y
                    if (!locations.add(currentCoords)) {
                        return currentCoords
                    }
                }
            }
        }
        return currentCoords
    }

    private enum class View {
        NORTH,
        EAST,
        SOUTH,
        WEST;

        fun rotateCoord(int: Int): Pair<Int, Int> {
            return when (this) {
                NORTH -> 0 to int
                EAST -> int to 0
                SOUTH -> 0 to -int
                WEST -> -int to 0
            }
        }

        fun rotateDirection(dir: String): View {
            return if (dir == "R") {
                View.values().getOrElse(View.values().indexOf(this) + 1) { NORTH }
            } else {
                View.values().getOrElse(View.values().indexOf(this) - 1) { WEST }
            }
        }
    }
}
