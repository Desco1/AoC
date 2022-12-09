package com.desco.aoc2015

import com.desco.Day

object Day21: Day(21, 2015) {

    private val boss = inputList.map { it.split(" ").last() }.run {
        val (hp, damage, armor) = this
        Fighter(hp.toInt(), damage.toInt(), armor.toInt())
    }
    private val player = Fighter(100, 0, 0)
    private val allItems = mutableListOf(
        Item("Dagger", 8, 4, 0, "Weapon"),
        Item("Shortsword", 10, 5, 0, "Weapon"),
        Item("Warhammer", 25, 6, 0, "Weapon"),
        Item("Longsword", 40, 7, 0, "Weapon"),
        Item("Greataxe", 75, 8, 0, "Weapon"),
        Item("None", 0, 0, 0, "Armor"),
        Item("Leather", 13, 0, 1, "Armor"),
        Item("Chainmail", 31, 0, 2, "Armor"),
        Item("Splintmail", 53, 0, 3, "Armor"),
        Item("Bandedmail", 75, 0, 4, "Armor"),
        Item("Platemail", 102, 0, 5, "Armor"),
        Item("None1", 0, 0, 0, "Ring"),
        Item("None2", 0, 0, 0, "Ring"),
        Item("Damage +1", 25, 1, 0, "Ring"),
        Item("Damage +2", 50, 2, 0, "Ring"),
        Item("Damage +3", 100, 3, 0, "Ring"),
        Item("Defense +1", 20, 0, 1, "Ring"),
        Item("Defense +2", 40, 0, 2, "Ring"),
        Item("Defense +3", 80, 0, 3, "Ring"),
    )

    override fun partOne(): Any {
        val weapons = allItems.filter { it.type == "Weapon" }
        val armors = allItems.filter { it.type == "Armor" }
        val rings = combinationWithRepetition(allItems.filter { it.type == "Ring" })

        val winningItems = mutableListOf<List<Item>>()
        for (weapon in weapons) {
            for (armor in armors) {
                for ((r1, r2) in rings) {
                    val list = listOf(weapon, armor, r1, r2)
                    if (player.withItems(list).fight(boss.copy()) != -1) {
                        winningItems.add(list)
                    }
                }
            }
        }
        return winningItems.minOf {
            it.sumOf { it.cost }
        }
    }

    override fun partTwo(): Any {
        val weapons = allItems.filter { it.type == "Weapon" }
        val armors = allItems.filter { it.type == "Armor" }
        val rings = combinationWithRepetition(allItems.filter { it.type == "Ring" })

        val losing = mutableListOf<List<Item>>()
        for (weapon in weapons) {
            for (armor in armors) {
                for ((r1, r2) in rings) {
                    val list = listOf(weapon, armor, r1, r2)
                    if (player.withItems(list).fight(boss.copy()) == -1) {
                        losing.add(list)
                    }
                }
            }
        }
        return losing.maxOf {
            it.sumOf { it.cost }
        }
    }

    private data class Fighter(var hp: Int, var damage: Int, var armor: Int) {

        fun withItems(items: List<Item>): Fighter {
            return this.copy(damage = this.damage + items.sumOf { it.damage }, armor = this.armor + items.sumOf { it.armor })
        }

        fun fight(other: Fighter): Int {
            var turns = 0
            while (other.hp > 0 && this.hp > 0) {
                turns++
                other.hp -= this.damage - other.armor
                if (other.hp <= 0) {
                    return turns
                } else {
                    this.hp -= other.damage - this.armor
                }
            }
            return -1
        }
    }
    private data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int, val type: String)

    private fun combinationWithRepetition(items: List<Item>): List<List<Item>> {
        val allCombinations = mutableListOf<MutableList<Item>>()
        val combination = IntArray(2)

        fun generate(k: Int) {
            if (k >= 2) {
                allCombinations.add(MutableList(2) { items[combination[it]] })
            } else {
                for (j in items.indices) {
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
}