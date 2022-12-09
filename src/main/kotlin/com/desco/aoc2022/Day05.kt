package com.desco.aoc2022

import com.desco.Day

object Day05: Day(5, 2022) {

    private val instructionRegex = "move (\\d+) from (\\d) to (\\d)".toRegex()
    private val crateStacks = arrayOf<MutableList<String>>(
        mutableListOf(), // 1
        mutableListOf(), // 2
        mutableListOf(), // 3
        mutableListOf(), // 4
        mutableListOf(), // 5
        mutableListOf(), // 6
        mutableListOf(), // 7
        mutableListOf(), // 8
        mutableListOf()  // 9
    )

    override fun partOne(): Any {
        val crates = mutableListOf<String>()
        var instructions = false
        for (s in inputList) {
            if (s.isEmpty()) {
                crates.reverse()
                val numbers = crates.first().split(" ").filter { it.isNotEmpty() }.map { crates.first().indexOf(it) }
                for (crate in crates.drop(1)) {
                    for ((i, number) in numbers.withIndex()) {
                        if (crate[number].isLetter()) {
                            crateStacks[i].add(crate[number].toString())
                        }
                    }
                }
                instructions = true
            } else {
                if (instructions) {
                    instructionRegex.matchEntire(s)?.let {
                        val (g1, g2, g3) = it.destructured
                        val amount = g1.toInt()
                        val from = g2.toInt() - 1
                        val to = g3.toInt() - 1
                        repeat(amount) {
                            crateStacks[to].add(crateStacks[from].removeLast())
                        }
                    }
                } else {
                    crates.add(s)
                }
            }
        }

        return crateStacks.joinToString("") { it.last() }
    }

    override fun partTwo(): Any {
        val crates = mutableListOf<String>()
        var instructions = false
        for (s in inputList) {
            if (s.isEmpty()) {
                crates.reverse()
                val numbers = crates.first().split(" ").filter { it.isNotEmpty() }.map { crates.first().indexOf(it) }
                for (crate in crates.drop(1)) {
                    for ((i, number) in numbers.withIndex()) {
                        if (crate[number].isLetter()) {
                            crateStacks[i].add(crate[number].toString())
                        }
                    }
                }
                instructions = true
            } else {
                if (instructions) {
                    instructionRegex.matchEntire(s)?.let {
                        val (g1, g2, g3) = it.destructured
                        val amount = g1.toInt()
                        val from = g2.toInt() - 1
                        val to = g3.toInt() - 1
                        crateStacks[to].addAll(crateStacks[from].takeLast(amount))
                        crateStacks[from] = crateStacks[from].dropLast(amount).toMutableList()
                    }
                } else {
                    crates.add(s)
                }
            }
        }

        return crateStacks.joinToString("") { it.last() }
    }
}