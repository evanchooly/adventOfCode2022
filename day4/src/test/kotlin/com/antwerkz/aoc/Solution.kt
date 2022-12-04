package com.antwerkz.aoc

class Day4Solution : TestBase() {
    override fun day(): Int = 4
    override fun sampleSolutionPart1() = 2

    override fun solvePart1(input: List<String>): Int {
        return input
            .map { it.split(",") }
            .map { it[0].toRange() to it[1].toRange() }
            .mapNotNull { contains(it.first, it.second) }
            .count()
    }

    override fun sampleSolutionPart2() = 4

    override fun solvePart2(input: List<String>) = input
        .map { it.split(",") }
        .map { it[0].toRange() to it[1].toRange() }
        .count { it.first.overlaps(it.second) }
}

private fun IntRange.overlaps(other: IntRange): Boolean {
    return this.fullyContains(other) || other.contains(first) || other.contains(last)
}

fun IntRange.fullyContains(other: IntRange): Boolean {
    return start <= other.first && endInclusive >= other.last
}

fun String.toRange(): IntRange {
    val split = split("-")
    return IntRange(split[0].toInt(), split[1].toInt())
}

data class Elf(val range: IntRange)

private fun contains(first: IntRange, second: IntRange): IntRange? {
    return when {
        first.fullyContains(second) -> first
        second.fullyContains(first) -> second
        else -> null
    }
}
