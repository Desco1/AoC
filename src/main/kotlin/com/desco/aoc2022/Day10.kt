package com.desco.aoc2022

import com.desco.Day
import kotlin.math.floor

object Day10: Day(10, 2022) {

    override fun partOne(): Any {
        return listOf(20, 60, 100, 140, 180, 220).sumOf { it * findSignal(it) }
    }

    override fun partTwo(): Any {
        val screen = Array(6) { Array(40) { "░░" } }

        repeat(240) {
            val reg = findSignal(it + 1)
            if (reg in it % 40 - 1 .. it % 40 + 1) {
                screen[floor(it / 40.0).toInt()][it % 40] = "██"
            }
        }

        // This is just so the letters are clearer to read
        // Also the initial \n to not have the first row be shifted right
        return "\n${screen.joinToString("\n") { it.joinToString("") }}"
    }

    private fun findSignal(cycle: Int): Int {
        var reg = 1
        var i = 1
        for (s in inputList) {
            if (i == cycle) break
            if (s.startsWith("addx ")) {
                i++
                if (i == cycle) break
                reg += s.split(" ").last().toInt()
            }
            i++
        }
        return reg
    }
}