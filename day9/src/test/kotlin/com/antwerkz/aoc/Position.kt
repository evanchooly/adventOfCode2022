package com.antwerkz.aoc

import java.io.File
import java.lang.Thread.sleep
import kotlin.math.abs
import kotlin.math.max

class Position(val name: Char, var location: Location = Location()) {
    var history = mutableListOf(location)
    private var follower: Position? = null

    init {
        location.start = true
    }

    fun follower(name: Char): Position {
        follower = Position(name, location.copy())
        return follower!!
    }

    fun shiftRight() {
        history.forEach { it.shiftRight() }
        follower?.let { it.shiftRight() }
    }

    fun shiftDown() {
        history.forEach { it.shiftDown() }
        follower?.let { it.shiftDown() }
    }

    fun moveTo(newLoc: Location) {
        if (newLoc.column < 0) {
            newLoc.shiftRight()
            shiftRight()
        }
        if (newLoc.row < 0) {
            newLoc.shiftDown()
            shiftDown()
        }

        location = newLoc
        history += location

        follower?.react(this)
    }

    fun react(other: Position) {
//        println("${name} reacting to ${other.name}")
        val rowOffset = offset(other.location.row, location.row)
        val colOffset = offset(other.location.column, location.column)
        if (abs(colOffset) > 1) {
            if (rowOffset == 0) {
                moveTo(Location(location.row, location.column + abs(colOffset) / colOffset))
            } else {
                moveTo(Location(other.location.row, location.column + abs(colOffset) / colOffset))
            }
        } else if (abs(rowOffset) > 1) {
            if (colOffset == 0) {
                moveTo(Location(location.row + abs(rowOffset) / rowOffset, location.column))
            } else {
                moveTo(Location(location.row + abs(rowOffset) / rowOffset, other.location.column))
            }
        }
    }

    private fun offset(other: Int, mine: Int) = other - mine

    override fun toString(): String {
        return "Position($name $location)"
    }

    fun render(fileName: String) {
        if(!Day9Solution.output) return
        var file = File("target/steps", fileName)
        if(file.exists()) {
            var i = 1
            while(file.exists()) {
                val suffix = "%05d".format(i++)
                file = File("target/steps", "$fileName-$suffix")
            }
        }
        file.parentFile.mkdirs()
        val positions = collect()
        val grid = buildGrid(positions)

        positions.forEach {
            val pos = it.location
            if (grid[pos.row][pos.column] == '.') grid[pos.row][pos.column] = it.name
        }
        val start = history[0]
        if (grid[start.row][start.column] == '.') grid[start.row][start.column] = 's'

        val map = grid.map { it.joinToString("") }
            .joinToString("\n")

        file.writeText(map)
    }

    fun renderHistory(fileName: String) {
        val file = File("target/steps", fileName)
        val historyGrid = buildGrid(collect())
        tail().history.forEach {
            if (historyGrid[it.row][it.column] != 's') historyGrid[it.row][it.column] = '#'
        }

        val start = history[0]
        historyGrid[start.row][start.column] = 's'

        historyGrid.forEach {
            file.appendText(it.joinToString("") + '\n')
        }

    }
    fun tail(): Position = follower?.tail() ?: this

    private fun buildGrid(positions: List<Position>): MutableList<MutableList<Char>> {
        var maxRow = 0
        var maxCol = 0
        positions.forEach {
            maxRow = max(maxRow, it.maxRow())
            maxCol = max(maxCol, it.maxColumn())
        }
        val grid = MutableList(maxRow + 1) { MutableList(maxCol + 1) { '.' } }
        return grid
    }

    private fun maxRow(): Int {
        return history.maxOf { it.row }
    }

    private fun maxColumn(): Int {
        return history.maxOf { it.column }
    }

    private fun normalize(rowNorm: Int, colNorm: Int) =
        Location(location.row + rowNorm, location.column + colNorm)

    private fun collect(): List<Position> {
        val list = mutableListOf(this)
        follower?.let {
            list += it.collect()
        }

        return list
    }
}