package com.desco.aoc2022

import com.desco.Day
import kotlin.math.sign

fun main() = Day09.second()

object Day09: Day(9, 2022) {

    private val movementRegex = "([UDLR]) (\\d+)".toRegex()

    override fun partOne(): Any {
        var hPos = 0 to 0
        var tPos = 0 to 0
        val tPositions = mutableSetOf(tPos)

        for (s in inputList) {
            movementRegex.matchEntire(s)?.let {
                val (dir, amount) = it.destructured
                repeat(amount.toInt()) {
                    when (dir) {
                        "U" -> hPos = hPos.first to hPos.second + 1
                        "D" -> hPos = hPos.first to hPos.second - 1
                        "L" -> hPos = hPos.first - 1 to hPos.second
                        "R" -> hPos = hPos.first + 1 to hPos.second
                    }
                    val diff = hPos.first - tPos.first to hPos.second - tPos.second
                    if (diff.first in -1 .. 1 && diff.second in -1 .. 1) {
                        return@repeat
                    }

                    tPos = tPos.first + diff.first.sign to tPos.second + diff.second.sign
                    tPositions.add(tPos)
                }
            }
        }
        return tPositions.size
    }

    override fun partTwo(): Any {
        val positions = MutableList(10) { 0 to 0 }
        val tPositions = mutableSetOf(0 to 0)

        for (s in inputList) {
            movementRegex.matchEntire(s)?.let {
                val (dir, amount) = it.destructured
                repeat(amount.toInt()) {
                    when (dir) {
                        "U" -> positions[0] = positions[0].first to positions[0].second + 1
                        "D" -> positions[0] = positions[0].first to positions[0].second - 1
                        "L" -> positions[0] = positions[0].first - 1 to positions[0].second
                        "R" -> positions[0] = positions[0].first + 1 to positions[0].second
                    }
                    for ((i, tail) in positions.drop(1).withIndex()) {
                        val head = positions[i - 1]
                        val diff = head.first - tail.first to head.second - tail.second
                        if (diff.first in -1 .. 1 && diff.second in -1 .. 1) {
                            break
                        }

                        positions[i] = tail.first + diff.first.sign to tail.second + diff.second.sign
                    }

                    tPositions.add(positions.last())
                }
            }
        }
        return tPositions.size
    }
}