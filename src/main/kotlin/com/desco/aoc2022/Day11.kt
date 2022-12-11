package com.desco.aoc2022

import com.desco.Day

object Day11: Day(11, 2022) {

    private val allMonkeys = mutableListOf<Monkey>()
    private val startingItemsRegex = "Starting items: ((?:\\d+(?:, )?)+)".toRegex()
    private val operationRegex = "Operation: new = old ([*+]) (old|\\d+)".toRegex()
    private val testRegex = "Test: divisible by (\\d+)".toRegex()
    private val ifRegex = "If (true|false): throw to monkey (\\d)".toRegex()

    init {
        var startingItems = mutableListOf<Long>()
        var operation: (Long) -> Long = { 0 }
        var test = 0
        var ifTrue = 0
        var ifFalse = 0
        for (s in inputList) {
            if (s.isNotEmpty()) {
                startingItemsRegex.find(s)?.let {
                    startingItems = it.groupValues[1].split(", ").map { it.toLong() }.toMutableList()
                } ?: operationRegex.find(s)?.let {
                    val (op, number) = it.destructured
                    when (op) {
                        "+" -> operation = { it + if (number == "old") it else (number.toLong()) }
                        "*" -> operation = { it * if (number == "old") it else (number.toLong()) }
                    }
                } ?: testRegex.find(s)?.let {
                    test = it.groupValues[1].toInt()
                } ?: ifRegex.find(s)?.let {
                    val (result, monkey) = it.destructured
                    when (result) {
                        "true" -> ifTrue = monkey.toInt()
                        "false" -> ifFalse = monkey.toInt()
                    }
                }
            } else {
                allMonkeys.add(Monkey(startingItems, operation, test, ifTrue, ifFalse))
            }
        }
        // For the last monkey
        allMonkeys.add(Monkey(startingItems, operation, test, ifTrue, ifFalse))
    }

    override fun partOne(): Any {

        repeat(20) {
            for (monkey in allMonkeys) {
                repeat(monkey.items.size) {
                    val item = monkey.operation(monkey.items.removeFirst()) / 3
                    if (item % monkey.test == 0L) {
                        allMonkeys[monkey.ifTrue].items.add(item)
                    } else {
                        allMonkeys[monkey.ifFalse].items.add(item)
                    }
                    monkey.inspects++
                }
            }
        }

        return allMonkeys.sortedBy { -it.inspects }.take(2).map { it.inspects }.reduce(Int::times)
    }

    override fun partTwo(): Any {

        val lcm = allMonkeys.map { it.test }.reduce(Int::times)

        repeat(10000) {
            for (monkey in allMonkeys) {
                repeat(monkey.items.size) {
                    val item = monkey.operation(monkey.items.removeFirst()) % lcm

                    if (item % monkey.test == 0L) {
                        allMonkeys[monkey.ifTrue].items.add(item)
                    } else {
                        allMonkeys[monkey.ifFalse].items.add(item)
                    }
                    monkey.inspects++
                }
            }
        }

        return allMonkeys.sortedBy { -it.inspects }.take(2).map { it.inspects.toLong() }.reduce(Long::times)
    }


    data class Monkey(var items: MutableList<Long>, val operation: (Long) -> Long, val test: Int, val ifTrue: Int, val ifFalse: Int, var inspects: Int = 0)
}