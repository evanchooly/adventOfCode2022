package com.antwerkz.aoc

import kotlin.math.max

class Day8Solution : TestBase() {
    override fun day(): Int = 8
    override fun sampleSolutionPart1() = 21

    override fun sampleSolutionPart2() = 8

    override fun solvePart1(input: List<String>): Int {
        var visible = 0

        input.forEachIndexed { x, row ->
            row.forEachIndexed { y, cell ->
                if (isVisible(input, x, y)) visible++
            }
        }

        return visible
    }

    override fun solvePart2(input: List<String>): Int {
        var score = 0

        for( x in input.indices) {
            for (y in input.indices) {
                score = max(score, scenicScore(input, x, y))
            }
        }

        return score
    }

    private fun scenicScore(forest: List<String>, x: Int, y: Int): Int {
        val up = visible(forest, forest[x][y], Stepper(x, y, forest.size) { row-- })
        val down = visible(forest, forest[x][y], Stepper(x, y, forest.size) { row++ })
        val left = visible(forest, forest[x][y], Stepper(x, y, forest.size) { column-- })
        val right = visible(forest, forest[x][y], Stepper(x, y, forest.size) { column++ })

        return right * left * up * down
    }

    private fun isVisible(forest: List<String>, x: Int, y: Int): Boolean {
        return x == 0 || x == forest.size - 1
            || y == 0 || y == forest.size - 1
            || visibleFromRight(forest, x, y)
            || visibleFromLeft(forest, x, y)
            || visibleFromUp(forest, x, y)
            || visibleFromDown(forest, x, y)
    }

    private fun visibleFromLeft(forest: List<String>, x: Int, y: Int) =
        (0 until x).maxOf { forest[y][it] } < forest[y][x]
    private fun visibleFromRight(forest: List<String>, x: Int, y: Int) =
        ((x+1) until forest.size).maxOf { forest[y][it] } < forest[y][x]
    private fun visibleFromUp(forest: List<String>, x: Int, y: Int) =
        (0 until y).maxOf { forest[it][x] } < forest[y][x]
    private fun visibleFromDown(forest: List<String>, x: Int, y: Int) =
        ((y+1) until forest.size).maxOf { forest[it][x] } < forest[y][x]

    private fun visible(forest: List<String>, target: Char, stepper: Stepper): Int {
        while(stepper.next()) {
            if(forest[stepper.row][stepper.column] >= target) return stepper.steps
        }
        return stepper.steps
    }
}

class Stepper(var row: Int, var column: Int, var max: Int, val step: Stepper.() -> Unit) {
    var steps = 0

    fun done() = row < 0 || row >= max
        || column < 0 || column >= max

    fun next(): Boolean {
        this.step()
        if(!done()) steps++
        return !done()
    }
}