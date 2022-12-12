package com.desco.aoc2022

import com.desco.Day
import java.util.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.sqrt

object Day12: Day(12, 2022) {

    private val chars = ('a' .. 'z').toList()
    private val moves = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0
    )

    private val startX = floor(inputList.joinToString("").indexOf('S') / inputList.first().count().toDouble()).toInt()
    private val startY = inputList.joinToString("").indexOf('S') % inputList.first().count()
    private val destX = floor(inputList.joinToString("").indexOf('E') / inputList.first().count().toDouble()).toInt()
    private val destY = inputList.joinToString("").indexOf('E') % inputList.first().count()
    private val map = inputList.map { it.map {
            when (it) {
                'S' -> 0
                'E' -> chars.size - 1
                else -> chars.indexOf(it)
            }
        }
    }

    override fun partOne(): Any {
        return findPath(startX, startY, destX, destY)!!.size
    }

    override fun partTwo(): Any {
        // not very "efficient", but at least it doesn't hang the process like another day I know
        return map.mapIndexed { x, it ->
            it.mapIndexed { y, c ->
                x to y
            }
        }.flatten().filter { map[it.first][it.second] == 0 }.minOf {
            findPath(it.first, it.second, destX, destY)?.size ?: Integer.MAX_VALUE
        }
    }

    private fun findPath(startX: Int, startY: Int, goalX: Int, goalY: Int): List<Node>? {
        val nodes = mutableMapOf<Int, Node>()

        val openSet = PriorityQueue<Node> { n1, n2 ->
            if (n1.fScore < n2.fScore) {
                -1
            } else if (n2.fScore < n1.fScore) {
                1
            } else {
                0
            }
        }

        val startNode = Node(startX, startY)
        startNode.gScore = 0F
        startNode.fScore = sqrt(((goalX - startX) * (goalX - startX) + (goalY - startY) * (goalY - startY)).toDouble()).toFloat()
        nodes[startNode.hashCode()] = startNode
        openSet.offer(startNode)
        startNode.inOpenSet = true

        var endedAt: Node? = null

        loop@
        while (openSet.size > 0) {
            val current = openSet.remove()
            current.inOpenSet = false

            if (current.x == goalX && current.y == goalY) {
                endedAt = current
                break
            }

            for ((x, y) in moves) {
                try {
                    val height = map[current.x + x][current.y + y]
                    val currentHeight = map[current.x][current.y]
                    if (height - currentHeight > 1) {
                        continue
                    }
                } catch (e: IndexOutOfBoundsException) {
                    continue
                }

                val hash = Node(current.x + x, current.y + y).hashCode()
                var node = nodes.getOrDefault(hash, null)

                if (node == null) {
                    node = Node(current.x + x, current.y + y)
                    nodes[hash] = node
                }

                val tentativeGScore = current.gScore + 1

                if (tentativeGScore < node.gScore) {
                    node.parentNode = current
                    node.gScore = tentativeGScore
                    node.fScore = node.gScore + sqrt(((goalX - node.x) * (goalX - node.x) + (goalY - node.y) * (goalY - node.y)).toDouble()).toFloat()

                    if (!node.inOpenSet) {
                        openSet.offer(node)
                        node.inOpenSet = true
                    }
                }
            }
        }

        if (endedAt != null) {
            val path = mutableListOf<Node>()

            while (endedAt?.parentNode != null) {
                path.add(endedAt)
                endedAt = endedAt.parentNode
            }
            return path
        }
        return null
    }

    private data class Node(val x: Int, val y: Int) {

        var gScore = Float.POSITIVE_INFINITY
        var fScore = Float.POSITIVE_INFINITY
        var inOpenSet = false

        var parentNode: Node? = null

        override fun equals(other: Any?): Boolean {
            if (other !is Node) return false

            return this.x == other.x && this.y == other.y
        }

        override fun hashCode(): Int {
            return x * max(map.first().count(), map.count()) + y
        }
    }
}