package com.desco.aoc2015

import com.desco.Day
import kotlin.math.min

object Day14: Day(14, 2015) {

    private val reindeers = mutableListOf<Reindeer>()
    private val reindeerRegex = "([A-Za-z]+) can fly (\\d+) km\\/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.".toRegex()

    override fun partOne(): Any {
        inputList.forEach {
            reindeerRegex.matchEntire(it)?.let {
                val (name, speed, duration, restDuration) = it.destructured
                reindeers.add(Reindeer(name, speed.toInt(), duration.toInt(), restDuration.toInt()))
            }
        }

        println(reindeers)

        return reindeers.maxOf { it.toDistance(2503) }
    }

    override fun partTwo(): Any {
        inputList.forEach {
            reindeerRegex.matchEntire(it)?.let {
                val (name, speed, duration, restDuration) = it.destructured
                reindeers.add(Reindeer(name, speed.toInt(), duration.toInt(), restDuration.toInt()))
            }
        }

        repeat(2503) { i ->
            reindeers.maxBy {
                it.toDistance(i + 1)
            }.points++
        }

        return reindeers.maxOf { it.points }
    }

    data class Reindeer(val name: String, val speed: Int, val duration: Int, val restDuration: Int) {

        var points = 0

        fun toDistance(raceSeconds: Int): Int {
            val distance = raceSeconds / (duration + restDuration) * speed * duration
            val usefulSeconds = min(raceSeconds % (duration + restDuration), duration)
            return distance + usefulSeconds * speed
        }
    }
}