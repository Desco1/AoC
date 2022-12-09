package com.desco.aoc2015

import com.desco.Day
import kotlin.math.floor

object Day06: Day(6, 2015) {

    val lightRegex = "(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex()

    override fun partOne(): Any {
        val lights = MutableList(1_000_000) { LightOne(floor(it / 1000F).toInt(), it % 1000, false) }

        for (s in inputList) {
            lightRegex.matchEntire(s)?.let {
                val (action, x0, y0, x1, y1) = it.destructured

                for (light in lights.filter { it.x in x0.toInt()..x1.toInt() && it.y in y0.toInt()..y1.toInt() }) {
                    when (action) {
                        "turn on" -> light.on = true
                        "turn off" -> light.on = false
                        "toggle" -> light.on = !light.on
                    }
                }
            } ?: run {
                println("This isn't supposed to happen: $s")
            }
        }
        return lights.count { it.on }
    }

    override fun partTwo(): Any {
        val lights = MutableList(1_000_000) { LightTwo(floor(it / 1000F).toInt(), it % 1000, 0) }

        for (s in inputList) {
            lightRegex.matchEntire(s)?.let {
                val (action, x0, y0, x1, y1) = it.destructured

                for (light in lights.filter { it.x in x0.toInt()..x1.toInt() && it.y in y0.toInt()..y1.toInt() }) {
                    when (action) {
                        "turn on" -> light.brightness++
                        "turn off" -> light.brightness = (light.brightness - 1).coerceAtLeast(0)
                        "toggle" -> light.brightness += 2
                    }
                }
            } ?: run {
                println("This isn't supposed to happen: $s")
            }
        }
        return lights.sumOf { it.brightness }
    }

    data class LightOne(val x: Int, val y: Int, var on: Boolean)

    data class LightTwo(val x: Int, val y: Int, var brightness: Int)
}