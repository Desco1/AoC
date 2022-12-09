package com.desco.aoc2022

import com.desco.Day
import kotlin.math.max

object Day08: Day(8, 2022) {

    private val trees = inputList.map { it.toCharArray() }.toTypedArray()

    // These ain't good lol
    // Lily made me realize .all() exists but I forgot about it when I wrote these
    override fun partOne(): Any {
        var sum = 0
        for (j in 0 until trees.size) {
            for (i in 0 until trees.first().size) {
                val tree = trees[j][i].digitToInt()
                try {
                    for (y in j - 1 downTo  -1) {
                         if (trees[y][i].digitToInt() >= tree) {
                             break
                         }
                    }
                    for (y in j + 1 .. trees.size) {
                        if (trees[y][i].digitToInt() >= tree) {
                            break
                        }
                    }
                    for (x in i - 1 downTo  -1) {
                        if (trees[j][x].digitToInt() >= tree) {
                            break
                        }
                    }
                    for (x in i + 1 .. trees.first().size) {
                        if (trees[j][x].digitToInt() >= tree) {
                            break
                        }
                    }
                } catch (e: IndexOutOfBoundsException) {
                    sum++
                }
            }
        }
        return sum
    }

    override fun partTwo(): Any {
        var maxScore = 0
        for (j in 0 until trees.size) {
            for (i in 0 until trees.first().size) {
                val tree = trees[j][i].digitToInt()
                var up = 0
                for (y in j - 1 downTo 0) {
                    up++
                    if (trees[y][i].digitToInt() >= tree) {
                        break
                    }
                }
                var down = 0
                for (y in j + 1 until trees.size) {
                    down++
                    if (trees[y][i].digitToInt() >= tree) {
                        break
                    }
                }
                var left = 0
                for (x in i - 1 downTo 0) {
                    left++
                    if (trees[j][x].digitToInt() >= tree) {
                        break
                    }
                }
                var right = 0
                for (x in i + 1 until trees.first().size) {
                    right++
                    if (trees[j][x].digitToInt() >= tree) {
                        break
                    }
                }
                val score = up * down * left * right
                maxScore = max(maxScore, score)
            }
        }
        return maxScore
    }
}