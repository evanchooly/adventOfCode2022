package com.antwerkz.aoc.day somevalue

import com.antwerkz.aoc.TestBase
import org.testng.Assert.assertEquals

class Solution : TestBase() {
    override fun day(): Int = somevalue
    override fun samplePart1() {
        assertEquals(solvePart1(sample), somevalue)
    }

    override fun samplePart2() {
        assertEquals(solvePart2(sample), somevalue)
    }

    override fun solvePart1(input: List<String>) {}

    override fun solvePart2(input: List<String>) {}
}