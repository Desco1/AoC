package com.desco.aoc2022

import com.desco.Day
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import kotlin.math.min

object Day13: Day(13, 2022) {

    override fun partOne(): Any {
        return inputList.asSequence().filter { it.isNotEmpty() }.map { JsonParser.parseString(it).asJsonArray }.chunked(2).mapIndexed { index, it ->
            for (i in 0 until min(it[0].size(), it[1].size())) {
                compare(it[0][i], it[1][i])?.let {
                    if (it) {
                        return@mapIndexed index + 1
                    } else {
                        return@mapIndexed 0
                    }
                }
            }
            if (it[0].size() < it[1].size()) {
                return@mapIndexed index + 1
            }
            return@mapIndexed 0
        }.sum()
    }

    override fun partTwo(): Any {
        val packets = inputList.filter { it.isNotEmpty() }.map { JsonParser.parseString(it).asJsonArray }.toMutableList()
        val decod2 = JsonArray().apply { this.add(JsonArray().apply { this.add(JsonPrimitive(2)) }) }
        val decod6 = JsonArray().apply { this.add(JsonArray().apply { this.add(JsonPrimitive(6)) }) }
        packets.add(decod2)
        packets.add(decod6)
        packets.sortWith { a1, a2 ->
            compare(a1, a2)?.let {
                if (it) {
                    return@sortWith -1
                } else {
                    return@sortWith 1
                }
            }
            0
        }
        return (packets.indexOf(decod2) + 1) * (packets.indexOf(decod6) + 1)
    }

    private fun compare(first: JsonElement, second: JsonElement): Boolean? {
        if (first is JsonPrimitive && second is JsonPrimitive) {
            return if (first.asInt < second.asInt) {
                true
            } else if (first.asInt == second.asInt) {
                null
            } else {
                false
            }
        } else if (first is JsonArray && second is JsonArray) {
            for (i in 0 until min(first.size(), second.size())) {
                compare(first[i], second[i])?.let {
                    return it
                }
            }
            return if (first.size() < second.size()) {
                true
            } else if (first.size() > second.size()) {
                false
            } else {
                null
            }
        } else if (first.isJsonPrimitive && second.isJsonArray) {
            return compare(JsonArray().apply { this.add(first) }, second)
        } else if (first.isJsonArray && second.isJsonPrimitive) {
            return compare(first, JsonArray().apply { this.add(second) })
        } else throw RuntimeException()
    }
}