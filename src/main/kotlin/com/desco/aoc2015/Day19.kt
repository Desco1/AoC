package com.desco.aoc2015

import com.desco.Day

object Day19: Day(19, 2015) {

    private val startingMolecule = inputList.last()
    private val translationRegex = "([A-Za-z]+) => ([A-Za-z]+)".toRegex()
    private val allTranslations = mutableListOf<Translation>()

    init {
        inputList.forEach {
            translationRegex.matchEntire(it)?.let {
                val (start, end) = it.destructured
                allTranslations.add(Translation(end.toRegex(), start))
            }
        }

    }

    override fun partOne(): Any {
        val allMolecules = mutableSetOf<String>()
        for ((start, end) in allTranslations) {
            for (matchResult in start.findAll(startingMolecule)) {
                allMolecules.add(startingMolecule.replaceRange(matchResult.range.first, matchResult.range.last + 1, end))
            }
        }
        return allMolecules.size
    }

    override fun partTwo(): Any {

        var i = 0
        var molecule = startingMolecule
        allTranslations.sortBy {
            -it.start.pattern.length
        }
        while (molecule != "e") {
            run outer@ {
                for (translation in allTranslations) {
                    translation.start.find(molecule)?.let<MatchResult, Unit> {
                        molecule = molecule.replaceRange(it.range.start, it.range.endInclusive + 1, translation.end)
                        return@outer
                    }
                }
            }

            i++
        }

        return i
    }

    private fun combinationWithRepetition(size: Int): List<List<Translation>> {
        val allCombinations = mutableListOf(mutableListOf<Translation>())
        val combination = IntArray(size)

        fun generate(k: Int) {
            if (k >= size) {
                allCombinations.add(MutableList(size) { allTranslations[combination[it]] })
            } else {
                for (j in allTranslations.indices) {
                    if (k == 0 || j > combination[k - 1]) {
                        combination[k] = j
                        generate(k + 1)
                    }
                }
            }
        }

        generate(0)
        return allCombinations
    }

    data class Translation(val start: Regex, val end: String)
}