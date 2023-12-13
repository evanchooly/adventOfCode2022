package com.antwerkz.aoc

import kotlin.math.abs

class Day12Solution : TestBase() {
    override fun day(): Int = 12
    override fun sampleSolutionPart1() = 31

    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): Int {
        val sPos = find(input, 'S')
        val ePos = find(input, 'E')
        val path = mutableListOf(sPos)
        while (path.last() != ePos) {
            val next = next(path, ePos)
        }
        return -1
    }

    private fun next(path: List<Pair<Int, Int>>, target: Pair<Int, Int>): Pair<Int, Int> {
        val current = path.last()
        val preferred = cardinalize(normalize(target.first - current.first) to normalize(target.second - current.second))
        for (direction in directions(preferred)) {
            
        }
        TODO("Not yet implemented")
    }

    fun directions(preferred: Pair<Int, Int>): List<Pair<Int, Int>> {
        var directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
        
    }
    private fun normalize(i: Int): Int {
        return if(i == 0) i else i/abs(i)
    }

    private fun cardinalize(pair: Pair<Int, Int>): Pair<Int, Int> {
        return if (pair.first != 0 && pair.second != 0) pair.first to 0 else pair
    }
    private fun find(input: List<String>, target: Char): Pair<Int, Int> {
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, letter ->
                if(letter == target) return row to col
            }
        }
        
        throw IllegalStateException("couldn't find '$target'")
    }

    override fun solvePart2(input: List<String>) = TODO()
}