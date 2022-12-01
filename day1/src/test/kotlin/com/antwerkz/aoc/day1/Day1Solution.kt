package com.antwerkz.aoc.day1

import java.io.File
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class Day1Solution {
    @Test
    fun part1() {
        assertEquals(solvePart1("input1-sample.txt"), 24000);
        println("biggest load is ${solvePart1("input1.txt")} calories")
    }

    @Test
    fun part2() {
        assertEquals(solvePart2("input1-sample.txt"), 45000);
        println("total of the biggest 3 is ${solvePart2("input1.txt")} calories")
    }

    private fun solvePart1(input: String) = load(input)
        .map { elf -> elf to elf.load.sum() }
        .maxBy { it.second }
        .second

    private fun solvePart2(input: String) = load(input)
        .map { elf -> elf to elf.load.sum() }
        .sortedByDescending { it.second }
        .take(3).sumOf { it.second }

    private fun load(input: String): MutableList<Elf> {
        val lines = File("src/test/resources/", input).readLines()
        val elves = mutableListOf<Elf>()
        var elf = elves.addElf()

        lines.forEach { line ->
            if (line == "") {
                elf = elves.addElf();
            } else {
                elf.load += line.toInt()
            }
        }
        return elves
    }
}

private fun MutableList<Elf>.addElf(): Elf {
    return Elf(size + 1).also {
        add(it)
    }
}
