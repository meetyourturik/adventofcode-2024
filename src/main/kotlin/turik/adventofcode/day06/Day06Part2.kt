import java.io.File

var LINES: List<String> = listOf()
var HEIGHT = -1 .. -1
var WIDTH = -1 .. -1

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

val BLOCK: Char = '#'

fun inField(pos: Pair<Int, Int>): Boolean {
    return pos.first in HEIGHT && pos.second in WIDTH
}


fun checkObstacleCycle(possibleBlock: Pair<Int, Int>, visited: Set<Pair<Pair<Int, Int>, Pair<Int, Int>>>, posInc: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean {
    var posWdir = posInc
    val visitedCurr = visited.toMutableSet()

    while (true) {
        if (visitedCurr.contains(posWdir)) {
            return true
        }
        visitedCurr.add(posWdir)
        var (pos, dir) = posWdir
        val nextPos = Pair(pos.first + dir.first, pos.second + dir.second)
        if (!inField(nextPos)) {
            return false
        }
        if (nextPos.equals(possibleBlock) || LINES[nextPos.first][nextPos.second] == BLOCK) {
            dir = nextDir(dir)
        } else {
            pos = nextPos
        }
        posWdir = Pair(pos, dir)
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

    HEIGHT = 0 until LINES.size
    WIDTH = 0 until LINES[0].length

    for (i in HEIGHT) {
        for (j in WIDTH) {
            if (LINES[i][j] in COURSORS.keys) {
                START = Pair(i, j)
                pos = Pair(i, j)
                dir = COURSORS.get(LINES[i][j])!!
                break
            }
        }
    }

    val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    val res = mutableSetOf<Pair<Int, Int>>()

    while (true) {
        visited.add(Pair(pos, dir))

        val nextDir = nextDir(dir)
        val nextPos = Pair(pos.first + dir.first, pos.second + dir.second)

        if (!inField(nextPos)) {
            break
        }

        if (!START.equals(nextPos)
            && !visited.map { it.first }.contains(nextPos)
            && LINES[nextPos.first][nextPos.second] != BLOCK
            && checkObstacleCycle(nextPos, visited, Pair(pos, nextDir))
        ) {
            res.add(nextPos)
        }

        if (LINES[nextPos.first][nextPos.second] == BLOCK) {
            dir = nextDir
        } else {
            pos = nextPos
        }
    }

    println(res.size)
}
