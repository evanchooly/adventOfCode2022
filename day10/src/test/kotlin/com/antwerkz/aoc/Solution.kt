package com.antwerkz.aoc

import CRT
import CRT.Companion.console
import java.io.File

class Day10Solution : TestBase() {
    override fun day(): Int = 10

    override fun sampleSolutionPart1() = listOf(420, 1140, 1800, 2940, 2880, 3960, 13140)
    override fun sampleSolutionPart2() = -1

    override fun solvePart1(input: List<String>): List<Int> {
        val results = input.flatMap {
            processInstruction(it)
        }
        val targetCycles = intArrayOf(20, 60, 100, 140, 180, 220)
        val regValues = mutableListOf<Int>()
        regValues += 1
        for (cycle in results.indices) {
            regValues += regValues.last() + results[cycle]
        }
        val strengths = targetCycles
            .map { regValues[it - 1] * it }

        return strengths + strengths.sum()
    }

    private fun processInstruction(instruction: String): List<Int> {
        return when (instruction) {
            "noop" -> listOf(0)
            else -> listOf(0, instruction.substringAfter(" ").toInt())
        }
    }

    override fun solvePart2(input: List<String>): Int {
        val sprite = Sprite()
        val crt = CRT(sprite)

        input.forEach { update ->
            when {
                update.startsWith("addx") -> {
                    if(console) println("${crt.cycle+1} :  $update")
                    val adjustment = update.substringAfter(" ").toInt()
                    crt.cycle()
                    crt.cycle()
                    sprite.position += adjustment
                }
                else -> {
                    crt.cycle()
                }
            }
        }
        val message = crt.render()
        File("target/part2-table-rendered.txt").writeText(message)

        return -1
    }
}

