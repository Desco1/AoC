package com.desco.aoc2015

import com.desco.Day

object Day16: Day(16, 2015) {

    private val items = setOf(
        "children: (\\d+)",
        "cats: (\\d+)",
        "samoyeds: (\\d+)",
        "pomeranians: (\\d+)",
        "akitas: (\\d+)",
        "vizslas: (\\d+)",
        "goldfish: (\\d+)",
        "trees: (\\d+)",
        "cars: (\\d+)",
        "perfumes: (\\d+)"
    ).map { it.toRegex() }
    private val aunts = mutableMapOf<Int, MutableMap<String, Int>>()

    override fun partOne(): Any {
        inputList.forEachIndexed { i, s ->
            val itemMap = mutableMapOf<String, Int>()
            items.forEach {
                it.find(s)?.let {
                    val (item, amount) = it.value.split(" ")
                    itemMap[item] = amount.toInt()
                }
            }
            aunts[i + 1] = itemMap
        }

        val solution = mapOf(
            "children:" to 3,
            "cats:" to 7,
            "samoyeds:" to 2,
            "pomeranians:" to 3,
            "akitas:" to 0,
            "vizslas:" to 0,
            "goldfish:" to 5,
            "trees:" to 3,
            "cars:" to 2,
            "perfumes:" to 1
        )

        aunts.entries.find { (_, items) ->
            solution.forEach { (item, amount) ->
                if (item in items && (items[item] ?: -1) > amount) {
                    return@find false
                }
            }
            true
        }?.let {
            return it.key
        }
        return 0
    }

    override fun partTwo(): Any {
        inputList.forEachIndexed { i, s ->
            val itemMap = mutableMapOf<String, Int>()
            items.forEach {
                it.find(s)?.let {
                    val (item, amount) = it.value.split(" ")
                    itemMap[item] = amount.toInt()
                }
            }
            aunts[i + 1] = itemMap
        }

        val solution = mapOf(
            "children:" to 3,
            "cats:" to 7,
            "samoyeds:" to 2,
            "pomeranians:" to 3,
            "akitas:" to 0,
            "vizslas:" to 0,
            "goldfish:" to 5,
            "trees:" to 3,
            "cars:" to 2,
            "perfumes:" to 1
        )

        aunts.entries.find { (_, items) ->
            solution.forEach { (item, amount) ->
                if (item in items) {
                    when (item) {
                        "cats:", "trees:" -> {
                            if (items[item]!! <= amount) {
                                return@find false
                            }
                        }
                        "pomeranians:", "goldfish:" -> {
                            if (items[item]!! >= amount) {
                                return@find false
                            }
                        }
                        else -> {
                            if (items[item]!! != amount) {
                                return@find false
                            }
                        }
                    }
                }
            }
            true
        }?.let {
            return it.key
        }
        return 0
    }
}