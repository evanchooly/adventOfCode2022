package com.antwerkz.aoc

import java.io.File
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

abstract class TestBase {
    val sample = "src/test/resources/sample.txt".gras()
    val data = "src/test/resources/input.txt".gras()

    @Test
    fun part1() {
        samplePart1();
        println("Solution to part 1:  ${solvePart1(data)}")
    }

    @Test
    fun part2() {
        samplePart2()
        println("Solution to part 2:  ${solvePart2(data)}")
    }

    abstract fun samplePart1()

    abstract fun samplePart2()
    abstract fun solvePart1(data: List<String>): Int
    abstract fun solvePart2(data: List<String>): Int

    @DataProvider(name = "inputs")
    fun readInputs(): Array<Array<List<String>>> {
        return arrayOf(arrayOf())
    }

    private fun String.gras(): List<String> {
        return File(this).readLines()
    }

    @DataProvider(name = "dummy")
    fun bob() {
    }
}
