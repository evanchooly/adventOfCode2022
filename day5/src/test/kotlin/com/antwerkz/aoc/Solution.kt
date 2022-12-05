package com.antwerkz.aoc.day

import com.antwerkz.aoc.TestBase

class Day5Solution : TestBase() {
    val visualize = false
    override fun day(): Int = 5
    override fun sampleSolutionPart1() = "CMZ"

    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): String {
        val stacks = buildStacks(input)

        stacks.render()
        input.subList(input.indexOf("") + 1, input.size)
            .forEach {
                if (visualize) println("it = ${it}")
                var split = it.split(" ")
                stacks.move(split[1].toInt(), split[3].toInt() - 1, split[5].toInt() - 1)
            }
        return stacks.joinToString("") { it.last() }
    }

    private fun MutableList<MutableList<String>>.move(take: Int, source: Int, target: Int) {
        this[target] += this[source].takeLast(take).reversed()
        this[source] = this[source].dropLast(take).toMutableList()
        render()
    }

    private fun buildStacks(input: List<String>): MutableList<MutableList<String>> {
        val blank = input.indexOf("")
        val inputStacks = input.subList(0, blank - 1)
            .map { it.chunked(4).map { s -> s.trim() } }
            .map { it.map { s -> s.substringAfter('[').substringBefore(']') } }
        val stacks = MutableList(inputStacks[0].size) { mutableListOf<String>() }

        inputStacks
            .reversed()
            .forEach { list ->
                list.forEachIndexed() { index, s -> if (s != "") stacks[index] += s }
            }
        return stacks
    }

    override fun solvePart2(input: List<String>) = TODO()
    private fun List<List<String>>.render() {
        if (!visualize) return 
        forEachIndexed { index, it ->
            print("${index + 1}: ")
            it.forEach { s ->
                print("[${s.ifEmpty { " " }}] ")
            }
            println()
        }
        println()
    }
}
