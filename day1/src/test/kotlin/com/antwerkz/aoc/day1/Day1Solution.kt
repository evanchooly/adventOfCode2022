package com.antwerkz.aoc.day1

import com.antwerkz.aoc.TestBase
import org.testng.Assert.assertEquals

class Day1Solution : TestBase() {
    override fun day() = 1

    override fun sampleSolutionPart1()= 24000

    override fun sampleSolutionPart2() = 45000

    override fun solvePart1(input: List<String>) = load(input)
        .map { elf -> elf to elf.load.sum() }
        .maxBy { it.second }
        .second

    override fun solvePart2(input: List<String>) = load(input)
        .map { elf -> elf to elf.load.sum() }
        .sortedByDescending { it.second }
        .take(3).sumOf { it.second }

    private fun load(input: List<String>): MutableList<Elf> {
        val elves = mutableListOf<Elf>()
        var elf = elves.addElf()

        input.forEach { line ->
            if (line == "") {
                elf = elves.addElf();
            } else {
                elf.load += line.toInt()
            }
        }
        return elves
    }

    private fun MutableList<Elf>.addElf(): Elf {
        return Elf(size + 1).also {
            add(it)
        }
    }
}
