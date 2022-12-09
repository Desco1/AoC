package com.desco.aoc2022

import com.desco.Day

object Day02: Day(2, 2022) {

    override fun partOne(): Any {
        return inputList.sumOf {
            val (g1, g2) = it.split(" ")
            val opponent = when (g1) {
                "A" -> Selection.ROCK
                "B" -> Selection.PAPER
                else -> Selection.SCISSORS
            }
            val player = when (g2) {
                "X" -> Selection.ROCK
                "Y" -> Selection.PAPER
                else -> Selection.SCISSORS
            }
            player.against(opponent) + player.ordinal + 1
        }
    }

    override fun partTwo(): Any {
        return inputList.sumOf {
            val (g1, g2) = it.split(" ")
            val opponent = when (g1) {
                "A" -> Selection.ROCK
                "B" -> Selection.PAPER
                else -> Selection.SCISSORS
            }
            val outcome = when (g2) {
                "X" -> Outcome.LOSE
                "Y" -> Outcome.DRAW
                else -> Outcome.WIN
            }
            val player = outcome.getSelectionAgainst(opponent)
            player.against(opponent) + player.ordinal + 1
        }
    }

    enum class Selection {
        ROCK,
        PAPER,
        SCISSORS;

        fun against(sel: Selection): Int {
            if (this == sel) {
                return 3
            }
            return when (this) {
                ROCK -> if (sel == SCISSORS) {
                    6
                } else {
                    0
                }
                PAPER -> if (sel == ROCK) {
                    6
                } else {
                    0
                }
                SCISSORS -> if (sel == PAPER) {
                    6
                } else {
                    0
                }
            }
        }
    }

    enum class Outcome {
        LOSE,
        DRAW,
        WIN;

        fun getSelectionAgainst(selection: Selection): Selection {
            if (this == DRAW) return selection
            return if (this == WIN) {
                when (selection) {
                    Selection.ROCK -> Selection.PAPER
                    Selection.PAPER -> Selection.SCISSORS
                    Selection.SCISSORS -> Selection.ROCK
                }
            } else {
                when (selection) {
                    Selection.ROCK -> Selection.SCISSORS
                    Selection.PAPER -> Selection.ROCK
                    Selection.SCISSORS -> Selection.PAPER
                }
            }
        }
    }
}