package com.desco.aoc2015

import com.desco.Day
import kotlin.math.max

object Day22: Day(22, 2015) {

    private val boss = inputList.map {it.split(" ").last() }.run {
        val (hp, dmg) = this
        Fighter(hp.toInt(), dmg.toInt())
    }
    private val player = Fighter(50, 0, 0, 500)

    override fun partOne(): Any {
        var maxSpells = 1
        while (true) {
            val spells = permutationsOfSize(maxSpells)
            val results = spells.map { player.copy().withSpells(it, boss.copy()) }.filter { it > -1 }
            if (results.isNotEmpty()) {
                return results.minOf { it }
            } else {
                maxSpells++
            }
        }
    }

    override fun partTwo(): Any {
        var maxSpells = 1
        while (true) {
            val spells = permutationsOfSize(maxSpells)
            val results = spells.map { player.copy().withSpells(it, boss.copy(), "Hard") }.filter { it > -1 }
            if (results.isNotEmpty()) {
                return results.minOf { it }
            } else {
                maxSpells++
            }
        }
    }

    private fun permutationsOfSize(size: Int): List<List<Spell>> {
        val allPermutations = mutableListOf<MutableList<Spell>>()
        val permutation = IntArray(size)

        outer@
        while (true) {
            allPermutations.add(MutableList(size) { Spell.values()[permutation[it]] })

            var i = 0
            while (true) {
                permutation[i]++
                if (permutation[i] < Spell.values().size) break
                permutation[i++] = 0
                if (i == size) break@outer
            }
        }

        return allPermutations
    }

    private data class Fighter(var hp: Int, var damage: Int, var armor: Int = 0, var mana: Int = 0) {

        fun withSpells(spells: List<Spell>, other: Fighter, difficulty: String = "Easy"): Int {
            var activeEffects = mutableMapOf<Effect, Int>()

            fun updateEffects() {
                for ((e, _) in activeEffects) {
                    when (e) {
                        Effect.SHIELD ->  {
                            this.armor = 7
                            activeEffects[e] = activeEffects[e]!! - 1
                        }
                        Effect.POISON -> {
                            other.hp -= 3
                            activeEffects[e] = activeEffects[e]!! - 1
                        }
                        Effect.RECHARGE -> {
                            this.mana += 101
                            activeEffects[e] = activeEffects[e]!! - 1
                        }
                    }
                }
                activeEffects = activeEffects.filter { (_, i) -> i > 0 }.toMutableMap()
                if (Effect.SHIELD !in activeEffects) {
                    this.armor = 0
                }
            }

            var mana = 0
            for (spell in spells) {
                if (difficulty == "Hard") {
                    this.hp -= 1
                    if (this.hp <= 0) {
                        return -1
                    }
                }
                updateEffects()
                if (other.hp <= 0) {
                    return mana
                }
                // Start of players turn
                if (this.hp > 0 && this.mana >= spell.cost) {
                    when (spell) {
                        Spell.MAGIC_MISSILE -> {
                            other.hp -= 4
                            mana += spell.cost
                            this.mana -= spell.cost
                        }
                        Spell.DRAIN -> {
                            other.hp -= 2
                            this.hp += 2
                            mana += spell.cost
                            this.mana -= spell.cost
                        }
                        Spell.SHIELD -> if (Effect.SHIELD !in activeEffects) {
                            activeEffects[Effect.SHIELD] = 6
                            mana += spell.cost
                            this.mana -= spell.cost
                        } else {
                            return -1
                        }
                        Spell.POISON -> if (Effect.POISON !in activeEffects) {
                            activeEffects[Effect.POISON] = 6
                            mana += spell.cost
                            this.mana -= spell.cost
                        } else {
                            return -1
                        }
                        Spell.RECHARGE -> if (Effect.RECHARGE !in activeEffects) {
                            activeEffects[Effect.RECHARGE] = 5
                            mana += spell.cost
                            this.mana -= spell.cost
                        } else {
                            return -1
                        }
                    }
                } else {
                    return -1
                }

                updateEffects()
                // Start of other's turn
                if (other.hp > 0) {
                    this.hp -= max(other.damage - this.armor, 1)
                    if (this.hp <= 0) {
                        return -1
                    }
                } else {
                    return mana
                }
            }
            return -1
        }
    }

    private enum class Spell(val cost: Int) {
        MAGIC_MISSILE(53),
        DRAIN(73),
        SHIELD(113),
        POISON(173),
        RECHARGE(229)
    }

    private enum class Effect {
        SHIELD,
        POISON,
        RECHARGE
    }
}