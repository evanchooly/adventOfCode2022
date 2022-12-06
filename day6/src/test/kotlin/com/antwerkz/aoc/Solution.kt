package com.antwerkz.aoc

class Day6Solution : TestBase() {
    override fun day(): Int = 6

    override fun sampleSolutionPart1() = listOf(7, 5, 6, 10, 11)
    
    override fun sampleSolutionPart2() = listOf(19, 23, 23, 29, 26)
    override fun solvePart1(input: List<String>) = input
        .map { findSequence(it, 4) }

    override fun solvePart2(input: List<String>) = input
        .map { findSequence(it, 14) }

    fun findSequence(line: String, size: Int): Int {
        line.windowed(size, 1, transform = { s -> s.toSet().size == s.length })
            .forEachIndexed { index, unique ->
                if(unique) return index + size
            }

        throw IllegalStateException("Did not find a sequence")
    }

}