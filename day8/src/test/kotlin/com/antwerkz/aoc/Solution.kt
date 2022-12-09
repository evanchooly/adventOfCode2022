package com.antwerkz.aoc

class Day8Solution : TestBase() {
    override fun day(): Int = 8
    override fun sampleSolutionPart1() = 21

    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): Int {
        var visible = 0

        input.forEachIndexed { x, row ->
            row.forEachIndexed { y, cell ->
                if (isVisible(input, x, y)) visible++
            }
        }

        return visible
    }

    override fun solvePart2(input: List<String>) = TODO()

    private fun isVisible(forest: List<String>, x: Int, y: Int): Boolean {
        return x == 0 || x == forest.size - 1
            || y == 0 || y == forest.size - 1
            || visibleFromEast(forest, x, y)
            || visibleFromWest(forest, x, y)
            || visibleFromNorth(forest, x, y)
            || visibleFromSouth(forest, x, y)
    }
    private fun visibleFromEast(forest: List<String>, x: Int, y: Int) =
        (0 until x).maxOf { forest[y][it] } < forest[y][x]
    private fun visibleFromWest(forest: List<String>, x: Int, y: Int) =
        ((x+1) until forest.size).maxOf { forest[y][it] } < forest[y][x]

    private fun visibleFromNorth(forest: List<String>, x: Int, y: Int) =
        (0 until y).maxOf { forest[it][x] } < forest[y][x]

    private fun visibleFromSouth(forest: List<String>, x: Int, y: Int) =
        ((y+1) until forest.size).maxOf { forest[it][x] } < forest[y][x]
}