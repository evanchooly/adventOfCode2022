package com.antwerkz.aoc

import org.testng.annotations.Test

class Day10Solution : TestBase() {
    override fun day(): Int = 10

    @Test
    fun testPart1() {
        val list = listOf("noop", "addx 3", "addx -5")
        list.map { processInstruction(it) }

    }
    override fun sampleSolutionPart1() = listOf(420, 1140, 1800, 2940, 2880, 3960, 13140)
    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): List<Int> {
        val results = input.flatMap {
            processInstruction(it)
        }
        val targetCycles = intArrayOf(20, 60, 100, 140, 180, 220)
        val regValues = mutableListOf<Int>()
        regValues += 1
        for ( cycle in results.indices) {
            regValues += regValues.last() + results[cycle]
        }
        val strengths = targetCycles
            .map { regValues[it-1] * it }

        return strengths + strengths.sum()
    }

    private fun processInstruction(instruction: String): List<Int> {
        return when (instruction) {
            "noop" -> listOf(0)
            else -> listOf(0, instruction.substringAfter(" ").toInt())
        }
    }

    override fun solvePart2(input: List<String>) = TODO()
}