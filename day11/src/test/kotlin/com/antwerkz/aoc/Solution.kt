package com.antwerkz.aoc

class Day11Solution : TestBase() {
    override fun day(): Int = 11
    override fun sampleSolutionPart1() = 10605

    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): Int {
        val monkeys = parse(input.iterator())

        repeat(20) {
            monkeys.forEach { monkey ->
                monkey.inspect(monkeys)
            }
        }
        return monkeys.sortedBy { it.inspected }
            .takeLast(2)
            .chunked(2)
            .map { it: List<Monkey> -> it[0].inspected * it[1].inspected }
            .first()
    }

    private fun parse(input: Iterator<String>): List<Monkey> {
        val monkies = mutableListOf<Monkey>()
        while (input.hasNext()) {
            val line = input.next()
            if (line.startsWith("Monkey ")) {
                val worries = input.next()
                    .substringAfter(":")
                    .trim()
                    .split(",")
                    .map { it.trim().toInt() }
                    .toMutableList()
                val operation = buildOperation(
                    input.next()
                        .substringAfter(" = ")
                        .split(" ")
                )
                val test = buildReaction(input)
                
                monkies += Monkey(worries, operation, test)
            }
        }
        
        return monkies
    }

    private fun buildReaction(input: Iterator<String>): Monkey.(List<Monkey>) -> Unit {
        var test = input.next().substringAfter(": ").trim()
        if (!test.startsWith("divisible")) throw IllegalStateException("unexpected test: ${test}")
        val divisor = test.substringAfterLast(" ").toInt()
        val trueMonkey = input.next().substringAfterLast(" ").toInt()
        val falseMonkey = input.next().substringAfterLast(" ").toInt()

        return { monkies: List<Monkey> ->
            while (items.isNotEmpty()) {
                val newWorry = items.removeFirst().update() / 3
                if (newWorry % divisor == 0) {
                    monkies[trueMonkey].items += newWorry
                } else {
                    monkies[falseMonkey].items += newWorry
                }
                inspected++
            }
        }
    }

    private fun buildOperation(equation: List<String>): (Int) -> Int {
        return when (equation[1]) {
            "*" -> {
                if (equation[2] != "old") { old -> old * equation[2].toInt() }
                else { old -> old * old }
            }

            "+" -> {
                if (equation[2] != "old") { old -> old + equation[2].toInt() }
                else { old -> old + old }
            }

            else -> throw IllegalStateException("unknown operation: ${equation[1]}")
        }
    }

    override fun solvePart2(input: List<String>) = TODO()
}