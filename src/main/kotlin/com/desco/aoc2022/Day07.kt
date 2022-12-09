package com.desco.aoc2022

import com.desco.Day

object Day07: Day(7, 2022) {

    private val cdRegex = "\\\$ cd ([a-z.]+)".toRegex()
    private val fileRegex = "(\\d+) ([a-z.]+)".toRegex()

    private val allFiles = mutableMapOf<String, Int>()
    private val allDirs = mutableSetOf<String>()

    override fun partOne(): Any {
        var currentDir = "/"
        allDirs.add(currentDir)
        inputList.forEach { s ->
            cdRegex.matchEntire(s)?.let {
                val (dir) = it.destructured
                if (dir == "..") {
                    currentDir = currentDir.split("/").dropLast(2).joinToString("/") + "/"
                } else {
                    currentDir += "$dir/"
                    allDirs.add(currentDir)
                }
            } ?: fileRegex.matchEntire(s)?.let {
                val (size, name) = it.destructured
                allFiles[currentDir + name] = size.toInt()
            }
        }

        return allDirs.map { dir -> allFiles.filter { it.key.startsWith(dir) }.values.sum() }
            .filter { it <= 100000 }.sum()
    }

    override fun partTwo(): Any {
        var currentDir = "/"
        allDirs.add(currentDir)
        inputList.forEach { s ->
            cdRegex.matchEntire(s)?.let {
                val (dir) = it.destructured
                if (dir == "..") {
                    currentDir = currentDir.split("/").dropLast(2).joinToString("/") + "/"
                } else {
                    currentDir += "$dir/"
                    allDirs.add(currentDir)
                }
            } ?: fileRegex.matchEntire(s)?.let {
                val (size, name) = it.destructured
                allFiles[currentDir + name] = size.toInt()
            }
        }

        val min = 30000000 - (70000000 - allFiles.values.sum())

        return allDirs.map { dir -> allFiles.filter { it.key.startsWith(dir) }.values.sum() }
            .filter { it >= min }.min()
    }
}