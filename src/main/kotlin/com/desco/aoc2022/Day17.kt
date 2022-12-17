package com.desco.aoc2022

import com.desco.Day

object Day17: Day(17, 2022) {

    private val floor = Array(7) { mutableListOf<Char>() }
    private var wind = 0

    override fun partOne(): Any {
        repeat(2022) {
            throwRock(it)
        }

        return floor.maxOf { it.size }
    }

    override fun partTwo(): Any {
        var i = 0
        val results = mutableListOf<Pair<Int, Int>>()
        val repeatList = mutableListOf<Pair<Int, Int>>()

        // Load up preliminary values
        while (i < 100000) {
            throwRock(i++)
            if (i % RockType.values().size == 0) {
                if (results.isNotEmpty()) {
                    repeatList.add(i - results.last().first to floor.maxOf { it.size } - results.last().second)
                }
                results.add(i to floor.maxOf { it.size })
            }
        }

        var repeat = -1 to -1
        var first = -1
        // Look for a repeat
        for (j in RockType.values().size .. 10000 step RockType.values().size) {
            val value = repeatList.drop(100).chunked(j).windowed(2).firstOrNull { (one, two) -> one == two }
            if (value != null) {
                val list = value.first()
                repeat = list.sumOf { it.first } to list.sumOf { it.second }
                first = j * RockType.values().size
                break
            }
        }

        if (repeat == -1 to -1 || first == -1) throw RuntimeException()

        // With the repeat, simulate
        val remainder = (1000000000000L - first) % repeat.first

        floor.forEach { it.clear() }
        wind = 0

        repeat(first + remainder.toInt()) {
            throwRock(it)
        }

        return (1000000000000L - first) / repeat.first * repeat.second + floor.maxOf { it.size }
    }

    private fun throwRock(i: Int) {
        val rock = Rock(2, floor.maxOf { it.size } + 3, RockType.values()[i % 5])

        while (true) {
            val dir = try {
                inputString[wind++]
            } catch (e: StringIndexOutOfBoundsException) {
                wind = 0
                inputString[wind++]
            }

            when (dir) {
                '>' -> rock.x++
                '<' -> rock.x--
                else -> throw RuntimeException()
            }

            val preY = rock.y--
            if (preY == rock.y) {
                rock.layers.forEachIndexed { y, s ->
                    s.forEachIndexed { x, c ->
                        val actualX = rock.x + x
                        val list = floor[actualX]
                        while (list.size <= rock.y + rock.tall - 1) {
                            list.add(' ')
                        }
                        if (c == '#') {
                            list[rock.y + y] = c
                        }
                    }
                }
                break
            }
        }
    }

    private class Rock(x: Int, y: Int, private val type: RockType) {

        var x = x
            set(value) {
                if (value < 0 || value > 7 - wide) return
                layers.forEachIndexed { y, s ->
                    val actualY = this.y + y
                    s.forEachIndexed { x, c ->
                        val actualX = value + x
                        if (c == '#') {
                            val list = floor[actualX]
                            if (list.size > actualY && list[actualY] == '#') {
                                return
                            }
                        }
                    }
                }
                field = value
            }

        var y = y
            set(value) {
                if (value < 0) {
                    return
                }
                layers.forEachIndexed { y, s ->
                    val actualY = value + y
                    s.forEachIndexed { x, c ->
                        val actualX = this.x + x
                        if (c == '#') {
                            val list = floor[actualX]
                            if (list.size > actualY && list[actualY] == '#') {
                                return
                            }
                        }
                    }
                }
                field = value
            }

        val layers: Array<out String>
            get() = type.layers

        val wide = layers.first().length
        val tall = layers.size
    }

    private enum class RockType(vararg val layers: String) {
        HORIZONTAL("####"),
        PLUS(" # ", "###", " # "),
        CORNER("###", "  #", "  #"),
        VERTICAL("#", "#", "#", "#"),
        BLOCK("##", "##"),;
    }
}