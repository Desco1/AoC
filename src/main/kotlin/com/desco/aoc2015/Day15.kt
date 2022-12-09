package com.desco.aoc2015

import com.desco.Day
import kotlin.math.max

object Day15: Day(15, 2015) {

    private val ingredientRegex = "([A-Za-z]+): capacity (-?\\d), durability (-?\\d), flavor (-?\\d), texture (-?\\d), calories (-?\\d)".toRegex()
    private val ingredients = mutableListOf<Ingredient>()

    override fun partOne(): Any {
        inputList.forEach {
            ingredientRegex.matchEntire(it)?.let {
                val (name, capacity, durability, flavor, texture, calories) = it.destructured
                ingredients.add(Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt()))
            }
        }

        return combinationWithRepetition().maxOf {
            val capacity = max(it.sumOf { it.capacity }, 0)
            val durability = max(it.sumOf { it.durability }, 0)
            val flavor = max(it.sumOf { it.flavor }, 0)
            val texture = max(it.sumOf { it.texture }, 0)
            capacity * durability * flavor * texture
        }
    }

    override fun partTwo(): Any {
        inputList.forEach {
            ingredientRegex.matchEntire(it)?.let {
                val (name, capacity, durability, flavor, texture, calories) = it.destructured
                ingredients.add(Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt()))
            }
        }

        return combinationWithRepetition().maxOf {
            val capacity = max(it.sumOf { it.capacity }, 0)
            val durability = max(it.sumOf { it.durability }, 0)
            val flavor = max(it.sumOf { it.flavor }, 0)
            val texture = max(it.sumOf { it.texture }, 0)
            val calories = it.sumOf { it.calories }
            if (calories != 500) {
                0
            } else {
                capacity * durability * flavor * texture
            }
        }
    }

    private fun combinationWithRepetition(): List<List<Ingredient>> {
        val allCombinations = mutableListOf<MutableList<Ingredient>>()
        val combination = IntArray(100)

        fun generate(k: Int) {
            if (k >= 100) {
                allCombinations.add(MutableList(100) { ingredients[combination[it]] })
            } else {
                for (j in ingredients.indices) {
                    if (k == 0 || j >= combination[k - 1]) {
                        combination[k] = j
                        generate(k + 1)
                    }
                }
            }
        }

        generate(0)
        return allCombinations
    }

    data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)
}