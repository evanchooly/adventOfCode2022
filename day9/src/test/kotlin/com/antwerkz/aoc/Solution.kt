package com.antwerkz.aoc

import java.io.File
import org.testng.Assert
import org.testng.annotations.BeforeTest

data class Location(var row: Int = 0, var column: Int = 0) {
    var start = false
    fun shiftRight() {
        column++
    }
    fun shiftDown() {
        row++
    }

    override fun toString(): String {
        return "Location(row=$row, column=$column${if(start) " start" else ""})"
    }
}

class Day9Solution : TestBase() {
    override fun day(): Int = 9

    override fun samplePart2() {
        val sample2 = "src/test/resources/sample2.txt".read()

        Assert.assertEquals(solvePart2(sample), sampleSolutionPart2())
        Assert.assertEquals(solvePart2(sample2), 37)
    }

    override fun sampleSolutionPart1() = 13

    override fun sampleSolutionPart2() = 1

    override fun solvePart1(input: List<String>): Int {
        File("target/steps").deleteRecursively()

        val head = Position('H')
        val tail = head.follower('T')
        head.render("steps")
        input.map {
            val split = it.split(" ")
            split[0].toMove(split[1].toInt())
        }.forEachIndexed { index, it ->
            it.process(head)
            val step = "%04d".format(index)
            head.render("move_${step}")
        }

        tail.renderHistory("history")
        return tail.history.toSet().size
    }

    override fun solvePart2(input: List<String>): Int {
        File("target/steps").deleteRecursively()
        val head = Position('H')
        var tail = head
        repeat(9) { i -> tail = tail.follower('1' + i) }

        head.render("move_0000")
        input.map {
            val split = it.split(" ")
            split[0].toMove(split[1].toInt())
        }.forEachIndexed { index, it ->
            it.process(head)
            val step = "%04d".format(index + 1)
            head.render("move_${step}")
        }

        tail.renderHistory("history")
        return tail.history.toSet().size
    }

    companion object {
        val output = true
    }
}
