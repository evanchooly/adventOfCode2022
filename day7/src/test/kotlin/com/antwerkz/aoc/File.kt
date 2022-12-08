package com.antwerkz.aoc

open class File(val name: String, val fileSize: Long = -1) {
    open fun size() = fileSize
    override fun toString(): String {
        return "${javaClass.simpleName}(name='$name', size=${size()})"
    }

    open fun tree(indent: String = ""): List<String> {
        return listOf("${indent}- $name (file, size=$fileSize)")
    }
}