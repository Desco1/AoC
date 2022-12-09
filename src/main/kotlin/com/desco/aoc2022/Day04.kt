package com.desco.aoc2022

import com.desco.Day
import kotlin.math.max

object Day04: Day(4, 2022) {

    private val input = inputList

    override fun partOne(): Any {
        return inputList.count {
            val (i1, i2, i3, i4) = it.split(",").joinToString("-").split("-")
            val first = i1.toInt() .. i2.toInt()
            val second = i3.toInt() .. i4.toInt()
            val union = first.union(second)
            union.size == max(first.count(), second.count())
        }
    }

    override fun partTwo(): Any {
        return inputList.count {
            val (i1, i2, i3, i4) = it.split(",").joinToString("-").split("-")
            val first = i1.toInt() .. i2.toInt()
            val second = i3.toInt() .. i4.toInt()
            val union = first.union(second)
            union.size != first.count() + second.count()
        }
    }
}