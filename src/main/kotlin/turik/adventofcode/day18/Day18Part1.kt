import java.io.File
import kotlin.math.min

fun main() {
    val HEIGHT = 70
    val WIDTH = 70
    val KILOBYTE = 1024
    val FIN = Pair(WIDTH, HEIGHT)

    val CORRUPTED = mutableListOf<Pair<Int, Int>>()

    val DIRS = listOf(Pair(0,1), Pair(0,-1), Pair(1,0), Pair(-1, 0))

    fun posInField(pos: Pair<Int, Int>): Boolean {
        return pos.first in 0 .. WIDTH && pos.second in 0 .. HEIGHT
    }

    fun search(): Int {
        var res = Int.MAX_VALUE
        val visited = mutableSetOf<Pair<Int, Int>>()

        val q = ArrayDeque<Triple<Int, Int, Int>>() //x, y, step

        visited.add(Pair(0, 0))
        q.add(Triple(0, 0, 0))

        while (!q.isEmpty()) {
            val pos = q.removeFirst()
            visited.add(Pair(pos.first, pos.second))

            DIRS.forEach {
                val npos = Pair(pos.first + it.first, pos.second + it.second)
                val nstep = pos.third + 1
                if (FIN.equals(npos)) {
                    res = min(res, nstep)
                } else if (!CORRUPTED.contains(npos) && posInField(npos) && !visited.contains(npos) && !q.any { it.first == npos.first && it.second == npos.second }) {
                    q.add(Triple(npos.first, npos.second, nstep))
                }
            }
        }

        return res
    }

    val file = File("inputs/day18.txt")
    var i = 1
    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val (x, y) = line.split(",").map { it.toInt() }
            if (i <= KILOBYTE) {
                CORRUPTED.add(Pair(x, y))
            }
            i++
        }
    }

    println(search())
}