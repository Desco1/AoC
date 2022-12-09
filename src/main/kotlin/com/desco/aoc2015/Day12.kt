package com.desco.aoc2015

import com.desco.Day
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive

object Day12: Day(12, 2015) {

    override fun partOne(): Any {
        val obj = JsonParser.parseString(inputString).asJsonObject
        return getSumOfObject(obj)
    }

    override fun partTwo(): Any {
        val obj = JsonParser.parseString(inputString).asJsonObject
        return getSumOfObjectSecond(obj)
    }

    private fun getSumOfObject(obj: JsonObject): Int {
        return obj.entrySet().sumOf { (k, v) ->
            if (v is JsonArray) {
                getSumOfArray(v)
            } else if (v is JsonObject) {
                getSumOfObject(v)
            } else if (v is JsonPrimitive && v.isNumber) {
                v.asInt
            } else {
                0
            }
        }
    }

    private fun getSumOfArray(arr: JsonArray): Int {
        return arr.sumOf { v ->
            if (v is JsonArray) {
                getSumOfArray(v)
            } else if (v is JsonObject) {
                getSumOfObjectSecond(v)
            } else if (v is JsonPrimitive && v.isNumber) {
                v.asInt
            } else {
                0
            }
        }
    }

    private fun getSumOfObjectSecond(obj: JsonObject): Int {
        return obj.entrySet().sumOf { (k, v) ->
            if (v is JsonArray) {
                getSumOfArray(v)
            } else if (v is JsonObject) {
                getSumOfObjectSecond(v)
            } else if (v is JsonPrimitive && v.isNumber) {
                v.asInt
            } else if (v is JsonPrimitive && v.isString && v.asString == "red") {
                return 0
            } else {
                0
            }
        }
    }
}