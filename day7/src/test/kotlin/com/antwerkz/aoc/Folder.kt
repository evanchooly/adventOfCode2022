package com.antwerkz.aoc

class Folder(val parent: Folder?, name: String): File(name) {
    var contents = linkedMapOf<String, File>()
    override fun size() = contents.values.sumOf { it.size() }
    fun cd(newDir: String): Folder {
        return (if(newDir == "..") parent else contents[newDir] as Folder) ?: throw IllegalStateException("no parent directory for $name")
    }

    fun add(file: File) {
        contents[file.name] = file
    }

    fun collectFolders(): List<Folder> {
        val folders = contents.values
            .filterIsInstance<Folder>()
        return folders + folders
            .flatMap { it.collectFolders() }
    }

    override fun tree(indent: String): MutableList<String> {
        var curIndent = indent
        val tree = mutableListOf("${indent}- $name (dir, size=${size()})")
        curIndent += "  "
        contents.values.forEach { tree += it.tree(curIndent) }
        return tree
    }
}