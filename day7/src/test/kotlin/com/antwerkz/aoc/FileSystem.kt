package com.antwerkz.aoc

class FileSystem(val input: MutableList<String>) {
    var rootFolder = Folder(null, "/")
    var current = rootFolder
    fun process() {
        while(input.isNotEmpty()) {
            execute(input.removeFirst())
        }

    }

    private fun execute(line: String) {
        if(!line.startsWith("$")) throw IllegalStateException("command doesn't start with $$: $line")
        val entry = line.substring(2)
        val command = when {
            entry.startsWith("cd") -> ChangeDir(this, entry.substringAfter(" "))
            entry.startsWith("ls") -> ListDir(this)
            else -> throw IllegalStateException("unknown command: $entry")
        }

        command.execute()

    }

    fun fileSizes(): Long {
        val folders = rootFolder.collectFolders() + rootFolder
        val large = folders.filter { it.size() < 100_000 }
        return large.sumOf { it.size() }
    }

    fun cd(newDir: String) {
        current = if (newDir == "/") {
            rootFolder
        } else {
            current.cd(newDir)
        }
    }

    override fun toString(): String {
        return "FileSystem(${current})"
    }
}