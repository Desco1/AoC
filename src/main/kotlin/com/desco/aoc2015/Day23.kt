package com.desco.aoc2015

import com.desco.Day

object Day23: Day(23, 2015) {

    private val registerMap = mutableMapOf(
        "a" to 0,
        "b" to 0
    )
    private val hlfRegex = "hlf ([ab])".toRegex()
    private val tplRegex = "tpl ([ab])".toRegex()
    private val incRegex = "inc ([ab])".toRegex()
    private val jmpRegex = "jmp ([+-]\\d+)".toRegex()
    private val jieRegex = "jie ([ab]), ([+-]\\d+)".toRegex()
    private val jioRegex = "jio ([ab]), ([+-]\\d+)".toRegex()

    override fun partOne(): Any {
        var index = 0
        while (true) {
            try {
                val instruction = inputList[index]

                hlfRegex.matchEntire(instruction)?.let {
                    val (register) = it.destructured
                    registerMap[register] = registerMap[register]!! / 2
                    index++
                } ?: tplRegex.matchEntire(instruction)?.let {
                    val (register) = it.destructured
                    registerMap[register] = registerMap[register]!! * 3
                    index++
                } ?: incRegex.matchEntire(instruction)?.let {
                    val (register) = it.destructured
                    registerMap[register] = registerMap[register]!! + 1
                    index++
                } ?: jmpRegex.matchEntire(instruction)?.let {
                    val (offset) = it.destructured
                    index += offset.toInt()
                } ?: jieRegex.matchEntire(instruction)?.let {
                    val (register, offset) = it.destructured
                    if (registerMap[register]!! % 2 == 0) {
                        index += offset.toInt()
                    } else {
                        index++
                    }
                } ?: jioRegex.matchEntire(instruction)?.let {
                    val (register, offset) = it.destructured
                    if (registerMap[register]!! == 1) {
                        index += offset.toInt()
                    } else {
                        index++
                    }
                } ?: return instruction
            } catch (e: IndexOutOfBoundsException) {
                return registerMap["b"]!!
            }
        }
    }

    override fun partTwo(): Any {
        registerMap["a"] = 1
        return partOne()
    }
}