package com.desco.aoc2016

import com.desco.Day

fun main() = Day04.second()

object Day04: Day(4, 2016) {

    private val roomRegex = "([a-z-]+)-(\\d+)\\[([a-z]{5})]".toRegex()
    private val chars = ('a' .. 'z').toList()

    override fun partOne(): Any {
        return inputList.sumOf {
            roomRegex.matchEntire(it)?.let {
                val (encrypted, id, checksum) = it.destructured
                val testChecksum = encrypted.split("-").joinToString("")
                    .groupBy { it }.map { (k, v) -> k to v.size }
                    .sortedWith(compareBy<Pair<Char, Int>> { it.second }.thenByDescending { it.first })
                    .reversed()
                    .take(5).map { it.first }
                if (testChecksum.joinToString("") == checksum) {
                    return@sumOf id.toInt()
                }
                return@sumOf 0
            }
            0
        }
    }

    override fun partTwo(): Any {
        return inputList.forEach {
            roomRegex.matchEntire(it)?.let {
                val (encrypted, id, checksum) = it.destructured
                val testChecksum = encrypted.split("-").joinToString("")
                    .groupBy { it }.map { (k, v) -> k to v.size }
                    .sortedWith(compareBy<Pair<Char, Int>> { it.second }.thenByDescending { it.first })
                    .reversed()
                    .take(5).map { it.first }
                if (testChecksum.joinToString("") == checksum) {
                    println("${encrypted.map {
                        if (it == '-') {
                            ' '
                        } else {
                            val i = chars.indexOf(it)
                            chars[(i + id.toInt()) % chars.size]
                        }
                    }.joinToString("")} - $id")
                }
            }
        }
    }
}