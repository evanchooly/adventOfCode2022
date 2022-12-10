package com.antwerkz.aoc

import java.io.File

class Grid(val rows: Int = 21, val columns: Int = 26) {
    companion object {
        var output = false
        private var generator = { Grid() }
        fun set(rows: Int, columns: Int) {
            generator = { Grid(rows, columns) }
        }
        fun get() = generator()
    }
    val field = MutableList(rows) { MutableList(columns) { '.' } }

    fun render(position: Position) {
        position.render(this)
    }
    fun plot(name: Char, row: Int, col: Int) {
        if (output && field[row][col] == '.') field[row][col] = name
    }

    fun plotHistory(position: Position, fileName: String? = null) {
        if(output) {
            position.history.forEach {
                field[it.first][it.second] = '#'
            }
            print(fileName)
        }
    }

    fun print(fileName: String? = null) {
        if (output) {
            val file = fileName?.let { name ->
                File("target/steps/$name.txt")
            }
            file?.parentFile?.mkdirs()
            field.forEach {
                println(it.joinToString(""))
                file?.appendText(it.joinToString("") + "\n")
            }
            println()
            file?.appendText("\n")
        }
    }
}