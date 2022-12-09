package com.desco.aoc2015

import com.desco.Day

object Day05: Day(5, 2015) {

    override fun partOne(): Any {
        val badStrings = setOf("ab", "cd", "pq", "xy")
        val vowels = setOf('a', 'e', 'i', 'o', 'u')

        return inputList.count { s ->
            if (badStrings.any { it in s }) {
                false
            } else {
                if (s.count { it in vowels } < 3) {
                    return@count false
                }

                return@count s.zipWithNext { a, b -> a == b }.any { it }
            }

        }
    }

    override fun partTwo(): Any {

        return inputList.count { s ->
            if (s.zipWithNext().any {(c1, c2) ->
                s.contains("$c1$c2") && s.replaceFirst("$c1$c2", "  ").contains("$c1$c2") }) {
                s.forEachIndexed { i, c ->
                    try {
                        val otherChar = s[i + 2]
                        if (c == otherChar) {
                            return@count true
                        }
                    } catch (e: Exception) {
                        return@count false
                    }
                }
            }
            return@count false
        }
    }
}