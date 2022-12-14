package com.antwerkz.aoc

data class Monkey(
    val items: MutableList<Long>,
    val update: Long.() -> Long,
    val operation: Monkey.(List<Monkey>) -> Unit
) {
    var inspected = 0L
    
    fun inspect(monkeys: List<Monkey>) {
        this.operation(monkeys)
    }

    override fun toString(): String {
        return "Monkey(inspected=$inspected, items=$items)"
    }
} 