package com.antwerkz.aoc

import java.io.File
import org.testng.Assert
import org.testng.annotations.BeforeTest

typealias Location = Pair<Int, Int>

class Day9Solution : TestBase() {
    override fun day(): Int = 9

    @BeforeTest
    fun delete() {
        File("target/steps").deleteRecursively()
    }
    override fun samplePart2() {
        val sample2 = "src/test/resources/sample2.txt".read()

        Grid.set(5, 6)
        Assert.assertEquals(solvePart2(sample), sampleSolutionPart2())
        Grid.set(21, 26)
        Assert.assertEquals(solvePart2(sample2, 15 to 11), 36)
    }

    override fun sampleSolutionPart1() = 13

    override fun sampleSolutionPart2() = 1

    override fun solvePart1(input: List<String>): Int {
        Grid.set(100, 100)
        val rows = Grid.get().rows
        val head = Position('H', rows / 2 to rows / 2 )
        val tail = head.follower('T')
        head.render("initial")
        input.map { val split = it.split(" ")
            split[0].toMove(split[1].toInt())
        }.forEach {
            it.process(head)
        }

        Grid.get().plotHistory(tail, "part1")
        return tail.history.size
    }

    override fun solvePart2(input: List<String>): Int {
        return solvePart2(input, 0 to 0)
    }

    fun solvePart2(input: List<String>, location: Location): Int {
        val head = Position('H', location)
        var tail = head
        repeat(9) { i -> tail = tail.follower('1' + i) }

        head.render("initial")
        input.map { val split = it.split(" ")
            split[0].toMove(split[1].toInt())
        }.forEach {
            it.process(head)
        }

        Grid.get().plotHistory(tail)
        return tail.history.size
    } }

class Move(val name: String, val count: Int, val update: Location.() -> Location) {
    fun process(position: Position) {
        (0 until count).forEach { _ ->
            position.moveTo(position.location.update())
            position.render("${name}${count}")
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
