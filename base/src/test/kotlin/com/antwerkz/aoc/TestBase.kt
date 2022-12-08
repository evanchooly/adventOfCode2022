package com.antwerkz.aoc

import java.io.File
import java.net.URI
import org.apache.hc.client5.http.fluent.Request
import org.testng.Assert
import org.testng.SkipException
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

abstract class TestBase {
    companion object {
        const val INPUT = "src/test/resources/input.txt"
        const val SAMPLE = "src/test/resources/sample.txt"
    }

    private lateinit var sample: List<String>
    private lateinit var data: List<String>

    @BeforeClass
    fun downloadInput() {
        val file = File(INPUT)
        if (!file.exists() || file.length() == 0L) {
            val url = URI("https://adventofcode.com/2022/day/${day()}/input")

            Request.get(url)
                .userAgent("Mozilla/5.0 (compatible; aoc; +https://github.com/evanchooly/adventofcode2022)")
                .addHeader("Cookie", "session=" + System.getenv("SESSION_ID"))
                .execute()
                .saveContent(file)
        }

        data = INPUT.read()
        sample = SAMPLE.read()
    }

    @Test
    fun part1() {
        samplePart1()
        println("Solution to day ${day()} part 1:  ${solvePart1(data)}")
    }

    abstract fun day(): Int

    @Test
    fun part2() {
        try {
            samplePart2()
            println("Solution to day ${day()} part 2:  ${solvePart2(data)}")
        } catch (_: NotImplementedError) {
            throw SkipException("part 2 not implemented")
        }
    }

    private fun samplePart1() = Assert.assertEquals(solvePart1(sample), sampleSolutionPart1())
    private fun samplePart2() = Assert.assertEquals(solvePart2(sample), sampleSolutionPart2())

    abstract fun sampleSolutionPart1(): Any
    abstract fun sampleSolutionPart2(): Any

    abstract fun solvePart1(input: List<String>): Any
    abstract fun solvePart2(input: List<String>): Any

    fun String.read(): List<String> {
        return File(this).readLines()
    }
}
