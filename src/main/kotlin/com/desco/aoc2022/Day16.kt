package com.desco.aoc2022

import com.desco.Day
import java.util.Arrays
import kotlin.math.pow

object Day16: Day(16, 2022) {

    private val valveRegex = "Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z, ]+)".toRegex()
    private val valves = mutableMapOf<String, Pair<Int, Set<String>>>()
    private val usefulValves by lazy { valves.filter { it.value.first > 0 }.map { (k, v) -> k to v.first }.toMap() }

    init {
        for (s in inputList) {
            valveRegex.matchEntire(s)?.let {
                val (valve, flow, tunnels) = it.destructured
                valves[valve] = flow.toInt() to tunnels.split(", ").toSet()
            }
        }
    }

    override fun partOne(): Any {
        val distances = floydMarshall()
        val time = 30

        fun getPressure(starting: String, timeLeft: Int, openedValves: List<String>): Int {
            val possibleLocations = usefulValves.filter { it.key !in openedValves }
            return if (possibleLocations.isEmpty()) {
                0
            } else {
                possibleLocations.maxOf { (n, p) ->
                    val distance = distances[starting to n]!! + 1
                    if (timeLeft > distance) {
                        p * (timeLeft - distance) + getPressure(n, timeLeft - distances[starting to n]!! - 1, openedValves + n)
                    } else {
                        0
                    }
                }
            }
        }

        return getPressure("AA", time, mutableListOf())
    }

    override fun partTwo(): Any {
        val distances = floydMarshall()
        val time = 26

        fun getPressure(starting: String, timeLeft: Int, openedValves: List<String>): Pair<List<String>, Int> {
            val possibleLocations = usefulValves.filter { it.key !in openedValves }
            return if (possibleLocations.isEmpty()) {
                emptyList<String>() to 0
            } else {
                val result = possibleLocations.map { (n, p) ->
                    val distance = distances[starting to n]!! + 1
                    if (timeLeft > distance) {
                        val next = getPressure(n, timeLeft - distances[starting to n]!! - 1, openedValves + n)
                        (next.first + starting) to p * (timeLeft - distance) + next.second
                    } else {
                        emptyList<String>() to 0
                    }
                }
                result.maxBy { it.second }
            }
        }

        val human = getPressure("AA", time, mutableListOf())

        return human.second + getPressure("AA", time, human.first).second
    }

    private fun floydMarshall(): Map<Pair<String, String>, Int> {
        val distances = mutableMapOf<Pair<String, String>, Int>()

        for (valve in valves) {
            distances[valve.key to valve.key] = 0
            for (conn in valve.value.second) {
                distances[valve.key to conn] = 1
            }
        }

        for ((k, _) in valves) {
            for ((j, _) in valves) {
                for ((i, _) in valves) {
                    val ij = distances[i to j] ?: valves.size
                    val ik = distances[i to k] ?: valves.size
                    val kj = distances[k to j] ?: valves.size
                    if (ij > ik + kj) {
                        distances[i to j] = ik + kj
                    }
                }
            }
        }

        return distances
    }
}