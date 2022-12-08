package com.antwerkz.aoc

class Day7Solution : TestBase() {
    override fun day(): Int = 7
    override fun sampleSolutionPart1() = 95437L

    override fun sampleSolutionPart2() = 24933642L

    override fun solvePart1(input: List<String>): Any {
        val system = FileSystem(input.toMutableList())

        system.process()
        return system.fileSizes()
    }

    override fun solvePart2(input: List<String>): Any {
        val system = FileSystem(input.toMutableList())
        system.process()
        val target = 30000000L - (70_000_000L - system.rootFolder.size())

        return system.rootFolder.collectFolders()
            .sortedBy { it.size() }
            .filter { it.size() >= target }
            .minBy { it.size() }
            .size()
    }
}
