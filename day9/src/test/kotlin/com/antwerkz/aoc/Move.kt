package com.antwerkz.aoc

class Move(val name: String, val count: Int, val update: Location.() -> Location) {
    fun process(position: Position) {
        (0 until count).forEach { _ ->
            position.moveTo(position.location.update())
        }
    }

    override fun toString(): String {
        return "Move($name, $count)"
    }
}

fun String.toMove(count: Int): Move {
    return when (this) {
        "U" -> Move("U", count) { Location(row - 1, column) }
        "D" -> Move("D", count) { Location(row + 1, column) }
        "L" -> Move("L", count) { Location(row, column - 1) }
        "R" -> Move("R", count) { Location(row, column + 1) }
        else -> throw IllegalStateException("unknown move: $this")
    }
}