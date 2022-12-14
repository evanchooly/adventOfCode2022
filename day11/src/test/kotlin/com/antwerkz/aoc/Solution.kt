package com.antwerkz.aoc

class Day11Solution : TestBase() {
    private var divisors = mutableListOf<Long>()
    var modulo = 1L
    
    override fun day() = 11
    override fun sampleSolutionPart1() = 10605L

    override fun sampleSolutionPart2() = 2713310158L

    override fun solvePart1(input: List<String>): Long {
        val monkeys = parse(input.iterator(), 3)
        return watch(monkeys, 20)
    }

    override fun solvePart2(input: List<String>): Long {
        val monkeys = parse(input.iterator(), 1)
        modulo = lcm(divisors)
        return watch(monkeys, 10000)
    }

    private fun watch(monkeys: List<Monkey>, rounds: Int): Long {
        repeat((1..rounds).count()) {
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

    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a  * b / gcd(a, b)
    }
    
    fun lcm(values: List<Long>): Long {
        var result = lcm(values[0], values[1])
        for(value in values.drop(2)) {
            result = lcm(result, value)
        }
        return result
    }
    private fun buildReaction(input: Iterator<String>, worryAdjustment: Long): Monkey.(List<Monkey>) -> Unit {
        val divisor = input.next().substringAfterLast(" ").toLong()
        divisors += divisor
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
        return when (equation[1]) {
            "*" -> {
                if (equation[2] != "old") { old -> var new = old * equation[2].toLong()
                    if(truncate) new %= modulo
                    new
                }
                else { old -> var new = old * old
                    if(truncate) new %= modulo
                    new
                }
            }

            "+" -> {
                if (equation[2] != "old") { old -> var new = old + equation[2].toLong()
                    if(truncate) new %= modulo
                    new
                }
                else { old -> var new = old + old
                    if(truncate) new %= modulo
                    new
                }
            }

            else -> throw IllegalStateException("unknown operation: ${equation[1]}")
        }
    }
}