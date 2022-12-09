package com.desco.aoc2015

import com.desco.Day

object Day11: Day(11, 2015) {

    val letters = ('a' .. 'z').toList()

    override fun partOne(): Any {
        var input = inputString
        if (input.isValid()) return input
        do {
            input = input.next()
        } while (!input.isValid())

        return input
    }

    override fun partTwo(): Any {
        var input = partOne() as String
        do {
            input = input.next()
        } while (!input.isValid())

        return input
    }

    fun String.next(): String {
        var alsoNext: Boolean
        val output = this.reversed().toCharArray()
        for ((i, c) in this.reversed().withIndex()) {
            alsoNext = false
            val nextChar = try {
                letters[letters.indexOf(c) + 1]
            } catch (e: IndexOutOfBoundsException) {
                alsoNext = true
                letters[0]
            }
            output[i] = nextChar
            if (!alsoNext) {
                break
            }
        }
        return output.concatToString().reversed()
    }

    fun String.isValid(): Boolean {
        if ('i' in this || 'o' in this || 'l' in this) return false

        if (this.map { letters.indexOf(it) }.windowed(3, 1, false).any { it[0] + 1 == it[1] && it[1] == it[2] - 1 }) {
            return this.map { letters.indexOf(it) }.windowed(2, 1, false).filter { it[0] == it[1] }.groupBy {
                it.first()
            }.size > 1
        }
        return false
    }
}