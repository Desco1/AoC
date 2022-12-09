package com.desco.aoc2022

import com.desco.Day

object Day01: Day(1, 2022) {

    override fun partOne(): Any {
        val allAmounts = mutableListOf<Int>()
        var currentAmount = 0
        for (line in inputList) {
            if (line.isEmpty()) {
                allAmounts.add(currentAmount)
                currentAmount = 0
            } else {
                currentAmount += line.toInt()
            }
        }
        return allAmounts.max()
    }

    override fun partTwo(): Any {
        val allAmounts = mutableListOf<Int>()
        var currentAmount = 0
        for (line in inputList) {
            if (line.isEmpty()) {
                allAmounts.add(currentAmount)
                currentAmount = 0
            } else {
                currentAmount += line.toInt()
            }
        }
        return allAmounts.sortedBy { -it }.take(3).sum()
    }
}