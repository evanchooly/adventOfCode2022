package com.antwerkz.aoc.day3

import com.antwerkz.aoc.TestBase

class Day3Solution : TestBase() {
    override fun day(): Int = 3
    override fun sampleSolutionPart1() = 157

    override fun sampleSolutionPart2() = 70

    override fun solvePart1(input: List<String>) = input
        .flatMap { it.chunked(it.length / 2) }
        .chunked(2)
        .map { Rucksack(it.map { chunk -> chunk.toList() }) }
        .sumOf { it.priority() }

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