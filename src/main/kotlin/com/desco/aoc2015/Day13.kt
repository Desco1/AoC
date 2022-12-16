package com.desco.aoc2015

import com.desco.Day

object Day13: Day(13, 2015) {

    private val instRegex = "([A-Za-z]+) would (gain|lose) (\\d+) happiness units by sitting next to ([A-Za-z]+)\\.".toRegex()
    private val personSet = mutableSetOf<String>()
    private val infoMap = mutableMapOf<Pair<String, String>, Int>()

    init {
        inputList.forEach { s ->
            instRegex.matchEntire(s)?.let {
                val (first, gainLose, amount, second) = it.destructured
                val mult = if (gainLose == "gain") 1 else -1
                infoMap[first to second] = mult * amount.toInt()
                personSet.add(first)
                personSet.add(second)
            }
        }
    }

    override fun partOne(): Any {
        return allPermutations(personSet.toList()).maxOf {
            it.windowedWithLoop(2, 1).sumOf {
                infoMap[it[0] to it[1]]!! + infoMap[it[1] to it[0]]!!
            }
        }
    }

    override fun partTwo(): Any {
        personSet.forEach {
            infoMap[it to "Me"] = 0
            infoMap["Me" to it] = 0
        }
        personSet.add("Me")

        return partOne()
    }

    private fun allPermutations(list: List<String>): List<List<String>> {
        if (list.size == 1) return listOf(list)
        val perms = mutableListOf<List<String>>()
        val toInsert = list.first()
        for (perm in allPermutations(list.drop(1))) {
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, toInsert)
                perms.add(newPerm)
            }
        }
        return perms
    }

    private fun <T> Iterable<T>.windowedWithLoop(size: Int, step: Int): List<List<T>> {
        val list = this
        return list.windowed(size, step, true).map {
            if (it.size == size) {
                it
            } else {
                it.toMutableList() + list.take(size - it.size)
            }
        }
    }
}