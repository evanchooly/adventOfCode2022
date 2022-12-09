package com.antwerkz.aoc

import kotlin.math.abs

typealias Location = Pair<Int, Int>

class Day9Solution : TestBase() {
    override fun day(): Int = 9
    override fun sampleSolutionPart1() = 13

    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): Int {
        val head = Position("head")
        val tail = Position("tail")
        input.map { val split = it.split(" ")
            split[0].toMove(split[1].toInt())
        }.forEach {
            it.process(head, tail)
        }

        return tail.history.size
    }

    override fun solvePart2(input: List<String>) = TODO()
}

class Position(val name: String) {
    var location: Location = 0 to 0
    val history = mutableSetOf(location)

    fun moveTo(location: Location) {
        this.location = location
        history.add(location)
    }

    fun react(other: Position) {
        val rowOffset = offset(other.location.first, location.first)
        val colOffset = offset(other.location.second, location.second)
        if(abs(colOffset) > 1) {
            if(rowOffset == 0) {
                moveTo(location.first to location.second + abs(colOffset)/colOffset)
            } else {
                moveTo(other.location.first to location.second + abs(colOffset)/colOffset)
            }
        } else if(abs(rowOffset) > 1) {
            if(colOffset == 0) {
                moveTo(location.first + abs(rowOffset)/rowOffset to location.second)
            } else {
                moveTo(location.first + abs(rowOffset)/rowOffset to other.location.second)
            }
        }
    }


    private fun offset(other: Int, mine: Int) = other - mine
    override fun toString(): String {
        return "Position($name [$location])"
    }
}
class Move(val name: String, val count: Int, val update: Location.() -> Location) {
    fun process(head: Position, tail: Position) {
        (0 until count).forEach {
            head.moveTo(head.location.update())
            tail.react(head)
        }
    }

    override fun toString(): String {
        return "Move($name, $count)"
    }
}
private fun String.toMove(count: Int):Move {
    return when (this) {
        "U" -> Move("U", count) { first - 1 to second }
        "D" -> Move("D", count) { first + 1 to second }
        "L" -> Move("L", count) { first to second - 1 }
        "R" -> Move("R", count) { first to second + 1 }
        else -> throw IllegalStateException("unknown move: $this")
    }
}
