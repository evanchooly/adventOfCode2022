import com.antwerkz.aoc.Sprite

class CRT(val sprite: Sprite) {
    companion object {
        val console = false
    }
    val screen = MutableList(6) { "" }
    var cycle = 0

    fun cycle() {
        screen[cycle/40] += if(sprite.covered(cycle % 40)) "#" else "."
        render()
        cycle++
    }
    
    fun render(): String {
        val list = screen.joinToString("\n")
        
        if(console) {
            println("*** cycle ${cycle + 1} ***")
            println(screen[0])
            println()
        }
        return list
    }

    override fun toString() = "(cycle: ${cycle+1}): ${screen[0]}"
}