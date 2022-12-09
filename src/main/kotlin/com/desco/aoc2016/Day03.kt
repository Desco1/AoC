package com.desco.aoc2016

import com.desco.Day

fun main() = Day03.second()

object Day03: Day(3, 2016) {

    override fun partOne(): Any {
        return inputList.count { s ->
            val (first, second, third) = s.split(" ").filter { it.isNotEmpty() }
            val list = mutableListOf(first.toInt(), second.toInt(), third.toInt())
            list.sortBy { it }
            list[0] + list[1] > list[2]
        }
    }

    override fun partTwo(): Any {
        val first = mutableListOf<Int>()
        val second = mutableListOf<Int>()
        val third = mutableListOf<Int>()
        inputList.forEach {
            val (one, two, three) = it.split(" ").filter { it.isNotEmpty() }
            first.add(one.toInt())
            second.add(two.toInt())
            third.add(three.toInt())
        }
        val c1 = first.chunked(3).count {
            val temp = it.sortedBy { it }
            temp[0] + temp[1] > temp[2]
        }
        val c2 = second.chunked(3).count {
            val temp = it.sortedBy { it }
            temp[0] + temp[1] > temp[2]
        }
        val c3 = third.chunked(3).count {
            val temp = it.sortedBy { it }
            temp[0] + temp[1] > temp[2]
        }

        return c1 + c2 + c3
    }
}