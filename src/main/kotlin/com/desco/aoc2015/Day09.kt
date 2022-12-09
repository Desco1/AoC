package com.desco.aoc2015

import com.desco.Day

object Day09: Day(9, 2015) {

    private val locRegex = "([a-zA-Z]+) to ([a-zA-Z]+) = (\\d+)".toRegex()
    private val locations = mutableListOf<String>()
    private val distances = mutableMapOf<Pair<String, String>, Int>()
    private val testInput = setOf(
        "London to Dublin = 464",
        "London to Belfast = 518",
        "Dublin to Belfast = 141"
    )

    override fun partOne(): Any {
        for (s in inputList) {
            locRegex.matchEntire(s)?.let {
                val (l1, l2, distance) = it.destructured
                if (l1 !in locations) {
                    locations.add(l1)
                }
                if (l2 !in locations) {
                    locations.add(l2)
                }
                distances[l1 to l2] = distance.toInt()
                distances[l2 to l1] = distance.toInt()
            }
        }

        return allPermutations(locations).minOf {
            it.zipWithNext().sumOf {
                distances[it]!!
            }
        }
    }

    override fun partTwo(): Any {
        for (s in inputList) {
            locRegex.matchEntire(s)?.let {
                val (l1, l2, distance) = it.destructured
                if (l1 !in locations) {
                    locations.add(l1)
                }
                if (l2 !in locations) {
                    locations.add(l2)
                }
                distances[l1 to l2] = distance.toInt()
                distances[l2 to l1] = distance.toInt()
            }
        }

        return allPermutations(locations).maxOf {
            it.zipWithNext().sumOf {
                distances[it]!!
            }
        }
    }

    private fun allPermutations(set: List<String>): List<List<String>> {
        if (set.size == 1) return listOf(set.toList())
        val perms = mutableListOf<List<String>>()
        val toInsert = set[0]
        for (perm in allPermutations(set.drop(1))) {
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, toInsert)
                perms.add(newPerm)
            }
        }
        return perms
    }
}