package com.antwerkz.aoc

import java.lang.IllegalStateException
import kotlin.math.abs

class Position(val name: Char) {
    val history: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var location: Location

    init {
        val map = Grid.get()
        val row = map.rows - 1
        val column = 0
        this.location = row to column
        history += this.location
    }
    constructor(name: Char, startLocation: Location = 0 to 0) : this(name) {
        if(startLocation.first < 0 || startLocation.second < 0) throw IllegalStateException("negative coordinates: $startLocation")
        location = startLocation
        history += this.location
    }

    private var follower: Position? = null

    fun follower(name: Char): Position {
        return Position(name).also {
            it.location = location
            follower = it
        }
    }

    fun moveTo(location: Location) {
        if(history.isEmpty()) history += this.location

        this.location = location
        history += location

        follower?.react(this)
    }


    fun react(other: Position) {
//        println("${name} reacting to ${other.name}")

        val rowOffset = offset(other.location.first, location.first)
        val colOffset = offset(other.location.second, location.second)
        if(abs(colOffset) > 1) {
            if(rowOffset == 0) {
                moveTo(location.first to location.second + abs(colOffset) /colOffset)
            } else {
                moveTo(other.location.first to location.second + abs(colOffset) /colOffset)
            }
        } else if(abs(rowOffset) > 1) {
            if(colOffset == 0) {
                moveTo(location.first + abs(rowOffset) /rowOffset to location.second)
            } else {
                moveTo(location.first + abs(rowOffset) /rowOffset to other.location.second)
            }
        }
    }
    private fun offset(other: Int, mine: Int) = other - mine

    override fun toString(): String {
        return "Position($name $location)"
    }

    fun render(fileName: String? = null) {
        val map = Grid.get()
        render(map)
        map.print(fileName)
    }
    fun render(map: Grid) {
        map.plot(name, location.first, location.second)
        follower?.let { map.render(it) }
    }
}