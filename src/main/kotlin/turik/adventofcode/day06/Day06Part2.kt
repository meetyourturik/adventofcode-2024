import java.io.File
import kotlin.system.exitProcess

var LINES: List<String> = listOf()
var HEIGHT = -1
var WIDTH = -1

val COURSORS = mapOf(
    '^' to Pair(-1, 0),
    '>' to Pair(0, 1),
    'v' to Pair(1, 0),
    '<' to Pair(0, -1)
)

fun nextDir(dir: Pair<Int, Int>): Pair<Int, Int> {
    val curpos = COURSORS.values.indexOf(dir)
    return COURSORS.values.toList()[(curpos+1) % 4]
}

val BLOCK = '#'

fun inField(pos: Pair<Int, Int>): Boolean {
    return pos.first in 0 until HEIGHT && pos.second in 0 until WIDTH
}


fun checkObstacleCycle(possibleBlock: Pair<Int, Int>, visited: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>, posInc: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean {
    var posWdir = posInc
    val visitedCurr = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    visitedCurr.addAll(visited)

    while (true) {
        if (visitedCurr.contains(posWdir)) {
            return true
        }
        visitedCurr.add(posWdir)
        val (pos, dir) = posWdir
        val nextPos = Pair(pos.first + dir.first, pos.second + dir.second)
        if (!inField(nextPos)) {
            return false
        }
        if (LINES[nextPos.first][nextPos.second] == BLOCK || possibleBlock.equals(nextPos)) {
            posWdir = Pair(pos, nextDir(dir))
        } else {
            posWdir = Pair(nextPos, dir)
        }
    }
}

fun main() {
    val file = File("inputs/day06.txt")

    file.bufferedReader().use { reader ->
        LINES = reader.lines().toList()
    }

    var pos = Pair(-1, -1)
    var START = Pair(-1, -1)
    var dir = Pair(-100, -100)

    HEIGHT = LINES.size
    WIDTH = LINES[0].length

    for (i in 0 until HEIGHT) {
        for (j in 0 until WIDTH) {
            if (LINES[i][j] in COURSORS) {
                START = Pair(i, j)
                pos = Pair(i, j)
                dir = COURSORS.get(LINES[i][j])!!
                break
            }
        }
    }

    val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    val res = mutableSetOf<Pair<Int, Int>>()

    while (inField(pos)) {
        val nextDir = nextDir(dir)
        while (
            inField(pos) // within the field
            &&
            (LINES.getOrElse(pos.first + dir.first, { _ -> "-"}).getOrElse(pos.second + dir.second, { _ -> '-'}) != BLOCK) // next step isn't block
        ) {
            visited.add(Pair(pos, dir))
            val possibleBlock = Pair(pos.first + dir.first, pos.second + dir.second)

            if (inField(possibleBlock) && !START.equals(possibleBlock) && LINES[possibleBlock.first][possibleBlock.second] != BLOCK && checkObstacleCycle(possibleBlock, visited, Pair(pos, nextDir))) {
                res.add(possibleBlock)
            }
            pos = possibleBlock
        }

        visited.add(Pair(pos, dir))
        dir = nextDir
    }

    println(res.size)
}

// aiming for 2162