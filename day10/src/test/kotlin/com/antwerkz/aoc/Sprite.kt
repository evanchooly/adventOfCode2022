package com.antwerkz.aoc

import CRT.Companion.console

data class Sprite(var position: Int = 1) {
    fun covered(location: Int): Boolean {
        val range = (position - 1)..(position + 1)
        val contained = location in range
        if(console) println("checking $location against $range ==>  $contained")
        return contained
    }
}