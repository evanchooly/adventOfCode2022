package com.antwerkz.aoc

import java.io.File
import java.net.URI
import org.apache.hc.client5.http.fluent.Request
import org.testng.SkipException
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

abstract class TestBase {
    companion object {
        val INPUT = "src/test/resources/input.txt"
        val SAMPLE = "src/test/resources/sample.txt"
    }

    lateinit var sample: List<String>
    lateinit var data: List<String>

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

    @Test
    fun part3() {
        try {
            samplePart3()
            println("Solution to day ${day()} part 3:  ${solvePart3(data)}")
        } catch (_: NotImplementedError) {
            throw SkipException("part 3 not implemented")
        }
    }

    abstract fun samplePart1()
    abstract fun samplePart2()
    open fun samplePart3(): Unit = TODO()

    abstract fun solvePart1(input: List<String>): Int
    abstract fun solvePart2(input: List<String>): Int
    open fun solvePart3(input: List<String>): Int = TODO()

    private fun String.read(): List<String> {
        return File(this).readLines()
    }
}
