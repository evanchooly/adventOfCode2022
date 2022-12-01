package com.antwerkz.aoc.day1

import java.io.File
import org.testng.annotations.Test

class Day1Solution {
    @Test
    fun part1() {
        solvePart1("input1-sample.txt")
        solvePart1("input1.txt")
    }

    @Test
    fun part2() {
        solvePart2("input1-sample.txt")
        solvePart2("input1.txt")
    }

    private fun solvePart1(input: String) {
        val elves = load(input)

        val biggest = elves.map { elf -> elf to elf.load.sum() }
            .maxBy { it.second }

        println("for ${input}: biggest is elf #${biggest.first.number} with ${biggest.second} calories")
    }
    private fun solvePart2(input: String) {
        val elves = load(input)

        val biggest = elves.map { elf -> elf to elf.load.sum() }
            .sortedByDescending { it.second }
            .take(3)

        println("for ${input}: total of the biggest 3 is: " + biggest.sumOf {it.second})

    }

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
