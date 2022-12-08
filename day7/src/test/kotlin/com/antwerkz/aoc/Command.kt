package com.antwerkz.aoc

interface Command {
    fun execute()
}

class ChangeDir(val fileSystem: FileSystem, val newDir: String) : Command {
    override fun execute() {
        fileSystem.cd(newDir)
    }

    override fun toString(): String {
        return "cd $newDir"
    }
}

class ListDir(var fileSystem: FileSystem) : Command {
    override fun execute() {
        while(fileSystem.input.isNotEmpty() && !fileSystem.input[0].startsWith("$")) {
            val entry = fileSystem.input.removeFirst()
            fileSystem.current.add(
                when {
                    entry.startsWith("dir") -> Folder(fileSystem.current, entry.substringAfter(" "))
                    else -> {
                        val parts = entry.split(" ")
                        File(parts[1], parts[0].toLong())
                    }
                })
        }
    }
}