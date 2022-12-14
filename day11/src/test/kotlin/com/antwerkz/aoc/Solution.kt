package com.antwerkz.aoc

class Day11Solution : TestBase() {
    override fun day() = 11
    override fun sampleSolutionPart1() = 10605L

    override fun sampleSolutionPart2() = 2713310158L

    override fun solvePart1(input: List<String>): Long {
        val monkeys = parse(input.iterator(), 3)
        return watch(monkeys, 20)
    }

    override fun solvePart2(input: List<String>): Long {
        val monkeys = parse(input.iterator(), 1)
        return watch(monkeys, 10000)
    }

    private fun watch(monkeys: List<Monkey>, rounds: Int): Long {
        (1..rounds).forEach {
            monkeys.forEach { monkey ->
                monkey.inspect(monkeys)
            }
            if(it == 1 || it == 20 || it % 1000 == 0) {
                println(monkeys.map { monkey -> monkey.inspected })
            }
        }
        return monkeys.sortedBy { it.inspected }
            .takeLast(2)
            .chunked(2)
            .map { it: List<Monkey> -> it[0].inspected * it[1].inspected }
            .first()
    }

    private fun parse(input: Iterator<String>, worryAdjustment: Long): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()
        while (input.hasNext()) {
            val line = input.next()
            if (line.startsWith("Monkey ")) {
                val worries = input.next()
                    .substringAfter(":")
                    .trim()
                    .split(", ")
                    .map { it.toLong() }
                    .toMutableList()
                val operation = buildOperation(
                    input.next()
                        .substringAfter(" = ")
                        .split(" "), worryAdjustment == 1L
                )
                val test = buildReaction(input, worryAdjustment)
                
                monkeys += Monkey(worries, operation, test)
            }
        }
        
        return monkeys
    }

    private fun buildReaction(input: Iterator<String>, worryAdjustment: Long): Monkey.(List<Monkey>) -> Unit {
        val divisor = input.next().substringAfterLast(" ").toLong()
        val trueMonkey = input.next().substringAfterLast(" ").toInt()
        val falseMonkey = input.next().substringAfterLast(" ").toInt()

        return { monkeys: List<Monkey> ->
            while (items.isNotEmpty()) {
                val newWorry = items.removeFirst().update() / worryAdjustment
                if (newWorry % divisor == 0L) {
                    monkeys[trueMonkey].items += newWorry
                } else {
                    monkeys[falseMonkey].items += newWorry
                }
                inspected++
            }
        }
    }

    private fun buildOperation(equation: List<String>, truncate: Boolean = false): (Long) -> Long {
        val MODULO = 23*19*13*17
        return when (equation[1]) {
            "*" -> {
                if (equation[2] != "old") { old -> var new = old * equation[2].toLong()
                    if(truncate) new %= MODULO
                    new
                }
                else { old -> var new = old * old
                    if(truncate) new %= MODULO
                    new
                }
            }

            "+" -> {
                if (equation[2] != "old") { old -> var new = old + equation[2].toLong()
                    if(truncate) new %= MODULO
                    new
                }
                else { old -> var new = old + old
                    if(truncate) new %= MODULO
                    new
                }
            }

            else -> throw IllegalStateException("unknown operation: ${equation[1]}")
        }
    }
}