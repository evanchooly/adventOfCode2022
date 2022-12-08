package com.antwerkz.aoc

class Day7Solution : TestBase() {
    override fun day(): Int = 7
    override fun sampleSolutionPart1() = 95437L

    override fun sampleSolutionPart2() = TODO()

    override fun solvePart1(input: List<String>): Any {
        val system = FileSystem(input.toMutableList())
        
        system.process()
        return system.fileSizes()
    }

    override fun solvePart2(input: List<String>) = TODO()
}

class FileSystem(val input: MutableList<String>) {
    var rootFolder = Folder(null,"/")
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

open class File(val name: String, val size: Long = -1) {
    open fun size() = size
    override fun toString(): String {
        return "${javaClass.simpleName}(name='$name')"
    }
}

class Folder(val parent: Folder?, name: String): File(name) {
    var contents = sortedMapOf<String, File>()
    override fun size() = contents.values.sumOf { it.size() }
    fun cd(newDir: String): Folder {
        return (if(newDir == "..") parent else contents[newDir] as Folder) ?: throw IllegalStateException("no parent directory for $name")
    }

    fun add(file: File) {
        contents[file.name] = file
    }

    fun findBySize(limit: Long) {
        
        val (folders, files) = contents.values.partition { it is Folder }
        
//        val largeFolders = folders.map { it.limit(limit) }
        val folderSize = folders.sumOf { it.size() }
        val fileSize = files.sumOf { it.size() }
        
        val total = folderSize + fileSize
        TODO("Not yet implemented")
    }

    fun collectFolders(): List<Folder> {
        val folders = contents.values
            .filter { it is Folder }
            .map { it as Folder }
        val list = folders + folders
            .flatMap { (it as Folder).collectFolders() }
        return list
    }
}

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