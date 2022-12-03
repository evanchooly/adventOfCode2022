package com.antwerkz.aoc.day3

import com.antwerkz.aoc.TestBase
import org.testng.Assert.assertEquals

class Day3Solution : TestBase() {
    override fun day(): Int = 3
    override fun samplePart1() = assertEquals(solvePart1(sample), 157)

    override fun samplePart2() = assertEquals(solvePart2(sample), 70)

    override fun solvePart1(input: List<String>) = input
        .map {
            Rucksack(it.chunked(it.length / 2)
                .map { chunk -> chunk.toList() })
        }.sumOf { it.priority() }

    override fun solvePart2(input: List<String>) = input
        .chunked(3)
        .map { Rucksack(it.map { chars -> chars.toList() }) }
        .sumOf { it.priority() }
}

class Rucksack(val buckets: List<List<Char>>) {
    fun priority(): Int {
        val reduce = buckets.reduce { acc, chars -> if (acc.isNotEmpty()) acc.intersect(chars).toList() else chars }
        return prioritize(reduce.first())
    }

    private fun prioritize(intersect: Char): Int {
        return when {
            intersect > 'a' -> intersect - 'a' + 1
            else -> intersect - 'A' + 27
        }
    }
}