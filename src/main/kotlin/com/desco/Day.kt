package com.desco

abstract class Day(val day: Int, val year: Int) {

    protected val inputList: List<String> by lazy { InputReader.getInputAsList(day, year) }
    protected val inputString: String by lazy { InputReader.getInputAsString(day, year) }

    abstract fun partOne(): Any

    abstract fun partTwo(): Any

    fun both() {
        println("First: ${this.partOne()}")
        println("Second: ${this.partTwo()}")
    }

    fun first() {
        println("First: ${this.partOne()}")
    }

    fun second() {
        println("Second: ${this.partTwo()}")
    }

}