package com.desco.aoc2015

import com.desco.Day

object Day17: Day(17, 2015) {

    private val containers = inputList.map { it.toInt() }

    override fun partOne(): Any {

        return List(containers.size) { combinationWithRepetition(it) }.sumOf {
            it.count {
                it.sum() == 150
            }
        }
    }

    override fun partTwo(): Any {
        for (i in containers.indices) {
            val count = combinationWithRepetition(i).count { it.sum() == 150 }
            if (count != 0) {
                return count
            }
        }
        return Unit
    }

    private fun combinationWithRepetition(size: Int): List<List<Int>> {
        val allCombinations = mutableListOf<MutableList<Int>>()
        val combination = IntArray(size)

        fun generate(k: Int) {
            if (k >= size) {
                allCombinations.add(MutableList(size) { containers[combination[it]] })
            } else {
                for (j in containers.indices) {
                    if (k == 0 || j > combination[k - 1]) {
                        combination[k] = j
                        generate(k + 1)
                    }
                }
            }
        }

        generate(0)
        return allCombinations
    }
}