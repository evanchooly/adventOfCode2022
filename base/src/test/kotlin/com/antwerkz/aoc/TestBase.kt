package com.antwerkz.aoc

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import org.testng.Assert
import org.testng.Assert.fail
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

open class TestBase {
    @Test
    fun alwaysFail() {
        fail("as expected")
    }

    @Test(dataProvider = "inputs")
    open fun part1(setName: String, data: List<String>) {
    }

    @Test(dataProvider = "inputs")
    open fun part2(setName: String, data: List<String>) {
    }

    @DataProvider(name = "inputs")
    fun readInputs(): List<Pair<String, List<String>>> {
        return listOf(
            "sample.txt" to "/sample.txt".gras(),
            "input.txt" to "/input.txt".gras()
        )
    }
    private fun String.gras(): List<String> {
        return File(this).readLines()
//        val lines = BufferedReader(InputStreamReader(javaClass.getResourceAsStream(this)))
//            .lines()
//        return lines.collect(toCollection(MutableList<String>()))
    }
}
