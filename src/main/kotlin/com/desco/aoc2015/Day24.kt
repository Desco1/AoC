package com.desco.aoc2015

import com.desco.Day

object Day24: Day(24, 2015) {

    private val presents = inputList.map { it.toInt() }.reversed()

    override fun partOne(): Any {
        val groups = getGroupsThatEqual(presents.sum() / 3, presents)
            .groupBy { it.size }
        val lists = groups[groups.keys.min()]!!

        return lists.map { it.map { it.toLong() } }.minOf { it.reduce { acc, l -> acc * l } }
    }

    override fun partTwo(): Any {
        val groups = getGroupsThatEqual(presents.sum() / 4, presents)
            .groupBy { it.size }
        val lists = groups[groups.keys.min()]!!

        return lists.map { it.map { it.toLong() } }.minOf { it.reduce { acc, l -> acc * l } }
    }

    private fun getGroupsThatEqual(equal: Int, valueList: List<Int> = presents.toMutableList()): List<List<Int>> {
        val masterList = mutableListOf<MutableList<Int>>()

        for ((index, value) in valueList.withIndex()) {
            if (value < equal) {
                if (index + 1 == valueList.size) {
                    break
                } else {
                    val groups = getGroupsThatEqual(equal - value, valueList.drop(index + 1))
                    if (groups.isNotEmpty()) {
                        for (j in groups) {
                            val iList = mutableListOf(value)
                            iList.addAll(j)
                            masterList.add(iList)
                        }
                    }
                }
            } else if (value == equal) {
                masterList.add(mutableListOf(value))
            }
        }

        return masterList
    }
}