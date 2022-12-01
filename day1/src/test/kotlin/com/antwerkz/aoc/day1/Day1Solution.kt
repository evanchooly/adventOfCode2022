package com.antwerkz.aoc.day1

import com.antwerkz.aoc.TestBase
import java.io.File
import org.testng.Assert.assertEquals
import org.testng.annotations.Test

class Day1Solution: TestBase() {
    @Test
    override fun part1(setName: String, data: List<String>) {
        assertEquals(solvePart1("sample.txt"), 24000);
        println("[${setName}] biggest load is ${solvePart1("input.txt")} calories")
    }

    @Test
    override fun part2(setName: String, data: List<String>) {
        assertEquals(solvePart2("sample.txt"), 45000);
        println("total of the biggest 3 is ${solvePart2("input.txt")} calories")
    }

    private fun solvePart1(input: List<String>) = load(input)
        .map { elf -> elf to elf.load.sum() }
        .maxBy { it.second }
        .second

    private fun solvePart2(input: List<String>) = load(input)
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
