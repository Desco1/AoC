package com.desco.aoc2015

import com.desco.Day

object Day07: Day(7, 2015) {

    private val wireMap = mutableMapOf<String, Int>()
    private val commandSet = setOf(
        " AND ",
        " OR ",
        " LSHIFT ",
        " RSHIFT ",
        "NOT "
    )

    override fun partOne(): Any {

        while ("a" !in wireMap) {
            for (s in inputList) {
                val (command, wire) = s.split(" -> ")
                if (wire in wireMap) continue
                val (gate1, gate2) = getParams(command)
                if (gate2 == -1) continue
                with(command) {
                    if (contains(" AND ")) {
                        wireMap[wire] = gate1 and gate2
                    } else if (contains(" OR ")) {
                        wireMap[wire] = gate1 or gate2
                    } else if (contains(" LSHIFT ")) {
                        wireMap[wire] = gate1 shl  gate2
                    } else if (contains(" RSHIFT ")) {
                        wireMap[wire] = gate1 shr gate2
                    } else if (contains("NOT ")) {
                        wireMap[wire] = 65535 - gate1
                    } else {
                        wireMap[wire] = gate1
                    }
                }
            }
        }

        return wireMap["a"]!!
    }

    override fun partTwo(): Any {
        val b = partOne() as Int
        wireMap.clear()
        wireMap["b"] = b
        return partOne()
    }

    private fun getParams(command: String): Pair<Int, Int> {
        commandSet.find { it in command }?.let {
            if (it == "NOT ") {
                val g1 = command.replace(it, "")
                val input1 = if (g1.all { it.isDigit() }) {
                    g1.toInt()
                } else if (g1 in wireMap) {
                    wireMap[g1]!!
                } else {
                    return -1 to -1
                }
                return input1 to 0
            } else {
                val (g1, g2) = command.split(it)
                val input1 = if (g1.all { it.isDigit() }) {
                    g1.toInt()
                } else if (g1 in wireMap) {
                    wireMap[g1]!!
                } else {
                    return -1 to -1
                }
                val input2 = if (g2.all { it.isDigit() }) {
                    g2.toInt()
                } else if (g2 in wireMap) {
                    wireMap[g2]!!
                } else {
                    return -1 to -1
                }
                return input1 to input2
            }
        } ?: run {
            if (command.all { it.isDigit() }) {
                return command.toInt() to 0
            } else if (command in wireMap) {
                return wireMap[command]!! to 0
            } else {
                return -1 to -1
            }
        }
    }
}