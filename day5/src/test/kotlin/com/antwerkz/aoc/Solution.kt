package com.antwerkz.aoc.day

import com.antwerkz.aoc.TestBase

class Day5Solution : TestBase() {
    val visualize = false
    override fun day(): Int = 5
    override fun sampleSolutionPart1() = "CMZ"

    override fun sampleSolutionPart2() = "MCD"

    override fun solvePart1(input: List<String>) = stackCrates(input, false)
    override fun solvePart2(input: List<String>) = stackCrates(input, true)

    private fun stackCrates(input: List<String>, multiMove: Boolean): String {
        val stacks = buildStacks(input)

        stacks.render()
        input.subList(input.indexOf("") + 1, input.size)
            .forEach {
                if (visualize) println("it = ${it}")
                var split = it.split(" ")
                stacks.move(split[1].toInt(), split[3].toInt() - 1, split[5].toInt() - 1, multiMove)
            }
        return stacks.joinToString("") { it.last() }
    }

    private fun MutableList<MutableList<String>>.move(take: Int, source: Int, target: Int, multiMove: Boolean) {
        var strings = this[source].takeLast(take)
        if(!multiMove) strings = strings.reversed()
        
        this[target] += strings
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
