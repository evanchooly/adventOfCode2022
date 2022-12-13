package com.antwerkz.aoc

data class Monkey(
    val items: MutableList<Int>,
    val update: Int.() -> Int,
    val operation: Monkey.(List<Monkey>) -> Unit
) {
    var inspected = 0
    
    fun inspect(monkies: List<Monkey>) {
        this.operation(monkies)
    }

    override fun toString(): String {
        return "Monkey(inspected=$inspected, items=$items)"
    }
} 