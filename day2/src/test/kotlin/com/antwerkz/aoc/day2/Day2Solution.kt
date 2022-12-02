package com.antwerkz.aoc.day2

import com.antwerkz.aoc.TestBase
import org.testng.Assert.assertEquals

class Day2Solution : TestBase() {
    override fun day(): Int = 2
    override fun samplePart1() {
        assertEquals(solvePart1(sample), 15)
    }

    override fun samplePart2() {
        assertEquals(solvePart2(sample), 12)
    }

    override fun solvePart1(data: List<String>) = data
        .sumOf {
            val (oppMove, myMove) = it.split(" ")
            Round(Move.map(oppMove), Move.map(myMove))
                .compete()
        }

    override fun solvePart2(data: List<String>) = data
        .sumOf {
            val (letter, hint) = it.split(" ")
            val move = Move.map(letter)
            Round(move, move.useHint(hint))
                .compete()
        }
}

data class Round(val oppMove: Move, val myMove: Move) {
    fun compete() = myMove.against(oppMove) + myMove.ordinal + 1
}

enum class Move {
    ROCK {
        override fun against(other: Move): Int {
            return when (other) {
                ROCK -> 3;
                PAPER -> 0;
                SCISSORS -> 6;
            }
        }

        override fun useHint(hint: String): Move {
            return when (hint) {
                "X" -> SCISSORS
                "Y" -> ROCK
                "Z" -> PAPER
                else -> TODO()
            }
        }
    },
    PAPER {
        override fun against(other: Move): Int {
            return when (other) {
                ROCK -> 6;
                PAPER -> 3;
                SCISSORS -> 0;
            }
        }

        override fun useHint(hint: String): Move {
            return when (hint) {
                "X" -> ROCK
                "Y" -> PAPER
                "Z" -> SCISSORS
                else -> TODO()
            }
        }
    },
    SCISSORS {
        override fun against(other: Move): Int {
            return when (other) {
                ROCK -> 0;
                PAPER -> 6;
                SCISSORS -> 3;
            }
        }

        override fun useHint(hint: String): Move {
            return when (hint) {
                "X" -> PAPER
                "Y" -> SCISSORS
                "Z" -> ROCK
                else -> TODO()
            }
        }
    };

    abstract fun against(other: Move): Int

    abstract fun useHint(hint: String): Move

    companion object {
        fun map(letter: String): Move {
            return when (letter) {
                "A", "X" -> ROCK;
                "B", "Y" -> PAPER;
                "C", "Z" -> SCISSORS;
                else -> TODO()
            }
        }
    }
}
