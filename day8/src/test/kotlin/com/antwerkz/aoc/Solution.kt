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
        val largest = object {
            var up: Int = 0
            var down: Int = 0
            var left: Int = 0
            var right: Int = 0

            override fun toString() = "up:$up down:$down left:$left right:$right"
        }
        val visible = object {
            var up = false
            var down = false
            var left = false
            var right = false

            override fun toString() = "up:$up down:$down left:$left right:$right"
        }
        val target = forest[row][column].digitToInt()

        largest.up = MaxStepper(forest, row, column, forest.size) { this.row-- }.run()
        visible.up = largest.up < target

        largest.down = MaxStepper(forest, row, column, forest.size) { this.row++ }.run()
        visible.down  = largest.down < target

        largest.left = MaxStepper(forest, row, column, forest.size) { this.column-- }.run()
        visible.left = largest.left < target

        largest.right = MaxStepper(forest, row, column, forest.size) { this.column++ }.run()
        visible.right = largest.right < target

        return visible.up || visible.down || visible.left || visible.right
    }

    private fun visibleFromLeft(forest: List<String>, x: Int, y: Int) =
        (0 until x).maxOf { forest[y][it] } < forest[y][x]
    private fun visibleFromRight(forest: List<String>, x: Int, y: Int) =
        ((x+1) until forest.size).maxOf { forest[y][it] } < forest[y][x]
    private fun visibleFromUp(forest: List<String>, x: Int, y: Int) =
        (0 until y).maxOf { forest[it][x] } < forest[y][x]
    private fun visibleFromDown(forest: List<String>, x: Int, y: Int) =
        ((y+1) until forest.size).maxOf { forest[it][x] } < forest[y][x]
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