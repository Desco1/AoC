package com.desco.aoc2022

import com.desco.Day
import kotlin.math.abs

object Day15: Day(15, 2022) {

    private val sensorRegex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()

    override fun partOne(): Any {
        val positionSet = mutableSetOf<Int>()
        val objects = mutableListOf<Pair<Int, Int>>()
        val row = 2000000
        for (s in inputList) {
            sensorRegex.matchEntire(s)?.let {
                val (sensorX, sensorY, beaconX, beaconY) = it.destructured.toList().map { it.toInt() }
                objects.add(sensorX to sensorY)
                objects.add(beaconX to beaconY)
                val distance = abs(beaconX - sensorX) + abs(beaconY - sensorY)
                val difference = if (sensorY <= row && sensorY + distance >= row) {
                    sensorY + distance - row
                } else if (sensorY >= row && sensorY - distance <= row) {
                    distance - sensorY + row
                } else {
                    return@let
                }
                positionSet.addAll(sensorX - difference .. sensorX + difference)
            }
        }
        positionSet.removeIf { col -> objects.any { it.first == col && it.second == row } }
        return positionSet.size
    }

    override fun partTwo(): Any {
        val sensors = mutableSetOf<Sensor>()
        for (s in inputList) {
            sensorRegex.matchEntire(s)?.let {
                val (sensorX, sensorY, beaconX, beaconY) = it.destructured.toList().map { it.toInt() }
                val distance = abs(beaconX - sensorX) + abs(beaconY - sensorY)
                sensors.add(Sensor(sensorX, sensorY, distance))
            }
        }
        val minX = sensors.minOf { it.x }
        val maxX = sensors.maxOf { it.x }
        val minY = sensors.minOf { it.y }
        val maxY = sensors.maxOf { it.y }

        for (x in minX .. maxX) {
            var y = minY
            loop@
            while (y <= maxY) {
                for (sensor in sensors) {
                    val distance = abs(sensor.x - x) + abs(sensor.y - y)
                    if (distance <= sensor.reach) {
                        y += if (y <= sensor.y) {
                            (sensor.y - y) * 2 + sensor.reach - distance + 1
                        } else {
                            y - sensor.y + sensor.reach - distance - 1
                        }
                        continue@loop
                    }
                }
                return x * 4000000L + y
            }
        }

        throw RuntimeException()
    }

    private data class Sensor(val x: Int, val y: Int, val reach: Int)
}