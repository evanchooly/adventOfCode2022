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
        val up = visibleUp(forest, x, y)
        val left = visibleToLeft(forest, x, y)
        val right = visibleToRight(forest, x, y)
        val down = visibleDown(forest, x, y)
        var visible = right * left * up * down

        return visible
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

    private fun visibleToLeft(forest: List<String>, x: Int, y: Int): Int {
        var current = y
        var visible = 0

        while(--current >= 0) {
            visible++
            if (forest[x][current] >= forest[x][y]) return visible
        }
        return visible
    }

    private fun visibleToRight(forest: List<String>, x: Int, y: Int): Int {
        var current = y
        var visible = 0

        while(++current < forest.size) {
            visible++
            if (forest[x][current] >= forest[x][y]) return visible
        }
        return visible
    }

    private fun visibleUp(forest: List<String>, x: Int, y: Int): Int {
        var current = x
        var visible = 0

        while(--current >= 0) {
            visible++
            if(forest[current][y] >= forest[x][y]) return visible
        }
        return visible
    }

    private fun visibleDown(forest: List<String>, x: Int, y: Int): Int {
        var current = x
        var visible = 0

        while(++current < forest.size) {
            visible++
            if(forest[current][y] >= forest[x][y]) return visible
        }
        return visible
    }
}