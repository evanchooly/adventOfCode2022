package com.antwerkz.aoc.day somevalue

import com.antwerkz.aoc.TestBase
import org.testng.Assert.assertEquals

class Solution : TestBase() {
    override fun day(): Int = somevalue
    override fun samplePart1() {
        assertEquals(solvePart1(sample), 0)
    }

    override fun samplePart2() {
        assertEquals(solvePart2(sample), 0)
    }

    override fun solvePart1(input: List<String>) = -1

    override fun solvePart2(input: List<String>) = -1
}