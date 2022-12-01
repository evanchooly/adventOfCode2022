package com.antwerkz.aoc.day1

import java.io.File
import org.testng.annotations.Test

class Day1Solution {
    @Test
    fun solution() {
        solve("input1-sample.txt")
        solve("input1.txt")
    }

    private fun solve(input: String) {
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
        val biggest = elves.map { elf -> elf to elf.load.sum() }
            .maxBy { it.second }

        println("for ${input}: biggest is elf #${biggest.first.number} with ${biggest.second} calories")
    }
}

private fun MutableList<Elf>.addElf(): Elf {
    return Elf(size + 1).also {
        add(it)
    }
}
