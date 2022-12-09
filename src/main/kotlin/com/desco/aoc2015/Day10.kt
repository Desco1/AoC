package com.desco.aoc2015

import com.desco.Day

object Day10: Day(10, 2015) {

    override fun partOne(): Any {
        var input = inputString
        repeat(40) {
            input = input.getInChunks().joinToString(separator = "") {
                "${it.length}${it.first()}"
            }
        }
        return input.length
    }

    override fun partTwo(): Any {
        var input = inputString
        repeat(50) {
            input = input.getInChunks().joinToString(separator = "") {
                "${it.length}${it.first()}"
            }
        }
        return input.length
    }

    private fun String.getInChunks(): List<String> {
        if (this.length == 1) return listOf(this)
        val list = mutableListOf<String>()
        var chunk = ""
        for (c in this) {
            if (chunk.isNotEmpty() && chunk.first() != c) {
                list.add(chunk)
                chunk = ""
            }
            chunk += c
        }
        list.add(chunk)
        return list
    }
}