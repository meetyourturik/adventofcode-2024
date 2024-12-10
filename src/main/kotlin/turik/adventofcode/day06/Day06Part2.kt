import java.io.File

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


fun checkObstacleCycle(possibleBlock: Pair<Int, Int>, visited: MutableSet<Triple<Int, Int, Pair<Int, Int>>>, posInc: Triple<Int, Int, Pair<Int, Int>>): Boolean {
    var posTriple = Triple(posInc.first, posInc.second, posInc.third)
    val visitedCurr = mutableSetOf<Triple<Int, Int, Pair<Int, Int>>>()
    visitedCurr.addAll(visited)

    while (true) {
        if (visitedCurr.contains(posTriple)) {
            return true
        }
        visitedCurr.add(posTriple)
        val nextPos = Pair(posTriple.first + posTriple.third.first, posTriple.second + posTriple.third.second)
        if (!inField(nextPos)) {
            return false
        }
        if (LINES[nextPos.first][nextPos.second] == BLOCK || possibleBlock.equals(nextPos)) {
            posTriple = Triple(posTriple.first, posTriple.second, nextDir(posTriple.third))
        } else {
            posTriple = Triple(nextPos.first, nextPos.second, posTriple.third)
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

    val visited = mutableSetOf<Triple<Int, Int, Pair<Int, Int>>>()

    val res = mutableSetOf<Pair<Int, Int>>()

    while (inField(pos)) {
        val nextDir = nextDir(dir)
        while (
            inField(pos) // within the field
            &&
            (LINES.getOrElse(pos.first + dir.first, { _ -> "-"}).getOrElse(pos.second + dir.second, { _ -> '-'}) != BLOCK) // next step isn't block
        ) {
            var posTriple = Triple(pos.first, pos.second, dir)
            visited.add(posTriple)

            val possibleBlock = Pair(pos.first + dir.first, pos.second + dir.second)

            posTriple = Triple(pos.first, pos.second, nextDir)
            if (inField(possibleBlock) && !START.equals(possibleBlock) && LINES[possibleBlock.first][possibleBlock.second] != BLOCK && checkObstacleCycle(possibleBlock, visited, posTriple)) {
                res.add(possibleBlock)
            }
            pos = possibleBlock
        }
        dir = nextDir
    }

    println(res.size)
}

// aiming for 2162