package com.antwerkz.aoc

import java.io.File
import java.net.URI
import org.apache.hc.client5.http.fluent.Request
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

abstract class TestBase {
    companion object {
        val INPUT = "src/test/resources/input.txt"
        val SAMPLE = "src/test/resources/sample.txt"
    }

    val sample = SAMPLE.gras()
    lateinit var data: List<String>

    @BeforeClass
    fun downloadInput() {
        val file = File(INPUT)
        if (!file.exists() || file.length() == 0L) {
            val url = URI("https://adventofcode.com/2022/day/${day()}/input")

            Request.get(url)
                .userAgent("Mozilla/5.0 (compatible; aoc; +https://github.com/evanchooly/adventofcode2022)")
                .addHeader("Cookie", "session="+System.getenv("SESSION_ID"))
                .execute()
                .saveContent(file)
        }

        data = INPUT.gras()
    }

    @Test
    fun part1() {
        samplePart1()
        println("Solution to part 1:  ${solvePart1(data)}")
    }

    abstract fun day(): Int

    @Test
    fun part2() {
        samplePart2()
        println("Solution to part 2:  ${solvePart2(data)}")
    }

    @Test
    fun part3() {
        try {
            samplePart3()
            println("Solution to part 3:  ${solvePart3(data)}")
        } catch (_: NotImplementedError) {
        }
    }

    abstract fun samplePart1()
    abstract fun samplePart2()
    open fun samplePart3() {
        TODO()
    }

    abstract fun solvePart1(data: List<String>): Int
    abstract fun solvePart2(data: List<String>): Int
    open fun solvePart3(data: List<String>): Int {
        TODO()
    }

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
