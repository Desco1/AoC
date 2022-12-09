package com.desco.aoc2015

import com.desco.Day

object Day18: Day(18, 2015) {

    private var lights = inputList.map { it.map { it == '#' }.toTypedArray() }.toTypedArray()

    override fun partOne(): Any {

        repeat(100) {
            lights = lights.mapIndexed outer@ { x, it ->
                it.mapIndexed { z, it ->
                    val neighbors = getNeighbours(x, z)
                    if (x == 0) {
                        if (z == 0 || z == 99) {
                            return@mapIndexed true
                        }
                    } else if (x == 99) {
                        if (z == 0 || z == 99) {
                            return@mapIndexed true
                        }
                    }

                    if (it) {
                        if (neighbors != 2 && neighbors != 3) {
                            return@mapIndexed false
                        }
                    } else {
                        if (neighbors == 3) {
                            return@mapIndexed true
                        }
                    }
                    it
                }.toTypedArray()
            }.toTypedArray()
        }

        return lights.sumOf { it.count { it } }
    }

    override fun partTwo(): Any {

        repeat(100) {
            lights = lights.mapIndexed outer@ { x, it ->
                it.mapIndexed { z, it ->
                    if (x == 0) {
                        if (z == 0 || z == 99) {
                            return@mapIndexed true
                        }
                    } else if (x == 99) {
                        if (z == 0 || z == 99) {
                            return@mapIndexed true
                        }
                    }
                    val neighbors = getNeighbours(x, z)
                    if (x == 0) {
                        if (z == 0 || z == 99) {
                            return@mapIndexed true
                        }
                    } else if (x == 99) {
                        if (z == 0 || z == 99) {
                            return@mapIndexed true
                        }
                    }

                    if (it) {
                        if (neighbors != 2 && neighbors != 3) {
                            return@mapIndexed false
                        }
                    } else {
                        if (neighbors == 3) {
                            return@mapIndexed true
                        }
                    }
                    it
                }.toTypedArray()
            }.toTypedArray()
        }

        return lights.sumOf { it.count { it } }
    }

    private fun getNeighbours(x: Int, y: Int): Int {
        val list = mutableListOf<Boolean>()
        for (i in x - 1 .. x + 1) {
            for (j in y - 1 .. y + 1) {
                if (i == x && j == y) {
                    continue
                } else {
                    list.add(lights.getOrNull(i)?.getOrNull(j) ?: false)
                }
            }
        }
        return list.count { it }
    }
}