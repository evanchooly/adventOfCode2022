package com.antwerkz.aoc

import kotlin.math.max

class Day8Solution : TestBase() {
    override fun day(): Int = 8
    override fun sampleSolutionPart1() = 21

    override fun sampleSolutionPart2() = 8

    override fun solvePart1(input: List<String>): Int {
        var visible = 0

        for( row in input.indices) {
            for (column in input.indices) {
                if (isVisible(input, row, column)) visible++
            }
        }

        return visible
    }

    override fun solvePart2(input: List<String>): Int {
        var score = 0

        for( row in input.indices) {
            for (column in input.indices) {
                score = max(score, scenicScore(input, row, column))
            }
        }

        return score
    }

    private fun scenicScore(forest: List<String>, row: Int, column: Int): Int {
        val up = Stepper(forest, row, column, forest.size) { this.row-- }.run()
        val down  = Stepper(forest, row, column, forest.size) { this.row++ }.run()
        val left  = Stepper(forest, row, column, forest.size) { this.column-- }.run()
        val right = Stepper(forest, row, column, forest.size) { this.column++ }.run()

        return right * left * up * down
    }

    private fun isVisible(forest: List<String>, row: Int, column: Int): Boolean {
        val target = forest[row][column].digitToInt()

        val up = MaxStepper(forest, row, column, forest.size) { this.row-- }.run() < target
        val down  = MaxStepper(forest, row, column, forest.size) { this.row++ }.run() < target
        val left = MaxStepper(forest, row, column, forest.size) { this.column-- }.run() < target
        val right = MaxStepper(forest, row, column, forest.size) { this.column++ }.run() < target

        return up || down || left || right
    }
}

open class Stepper(val forest: List<String>, var row: Int, var column: Int, var max: Int, val nextStep: Stepper.() -> Unit) {
    var steps = 0
    val target = forest[row][column]

    open fun run(): Int {
        while(next()) {
            if(forest[row][column] >= target) return steps
        }
        return steps
    }

    fun done() = row < 0 || row >= max
        || column < 0 || column >= max

    open fun next(): Boolean {
        nextStep()
        if(!done()) steps++
        return !done()
    }
}

class MaxStepper(forest: List<String>, row: Int, column: Int, max: Int, step: Stepper.() -> Unit):
    Stepper(forest, row, column, max, step) {

    var largest = -1
    override fun run(): Int {
        while(next()) {
            largest = max(forest[row][column].digitToInt(), largest)
        }
        return largest
    }
}