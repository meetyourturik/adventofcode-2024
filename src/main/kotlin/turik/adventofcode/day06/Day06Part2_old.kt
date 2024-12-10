import java.io.File

var HEIGHT = -1
var WIDTH = -1
var START = Pair(-1, -1)
var LINES: List<String> = listOf()
val COURSORS = listOf('^', '>', 'v', '<')
val BLOCK = '#'
val VISITED = mutableSetOf<Triple<Int, Int, Int>>()

fun nextDir(d: Int): Int = (d+1) % 4

fun checkObstacle(curr: Triple<Int, Int, Int>, vis: MutableSet<Triple<Int, Int, Int>>): Boolean {
    if ((curr.third == 0 && curr.first == 0) ||
        (curr.third == 1 && curr.second == WIDTH - 1) ||
        (curr.third == 2 && curr.first == HEIGHT - 1) ||
        (curr.third == 3 && curr.second == 0)
    ) { // trying to place an obstacle over the edge
        return false
    }
    /*
    if (vis.contains(curr)) {
        return true
    }
     */

    val dir = nextDir(curr.third)

    //vis.add(curr)
    //vis.add(Triple(curr.first, curr.second, dir))

    when (curr.third) {
        0 -> {
            for (i in curr.second until WIDTH-1) {
                if (i > curr.second && vis.contains(Triple(curr.first, i, dir))) {
                    return true
                }
                if (LINES[curr.first][i+1] == BLOCK) {
                    return checkObstacle(Triple(curr.first, i, dir), vis)
                }
                vis.add(Triple(curr.first, i, dir))
            }
        }
        1 -> {
            for (i in curr.first until HEIGHT-1) {
                if (i > curr.first && vis.contains(Triple(i, curr.second, dir))) {
                    return true
                }
                if (LINES[i+1][curr.second] == BLOCK) {
                    return checkObstacle(Triple(i, curr.second, dir), vis)
                }
                vis.add(Triple(i, curr.second, dir))
            }
        }
        2 -> {
            for (i in curr.second downTo  1) {
                if (i < curr.second && vis.contains(Triple(curr.first, i, dir))) {
                    return true
                }
                if (LINES[curr.first][i-1] == BLOCK) {
                    return checkObstacle(Triple(curr.first, i, dir), vis)
                }
                vis.add(Triple(curr.first, i, dir))
            }
        }
        3 -> {
            for (i in curr.first downTo 1) {
                if (i < curr.first && vis.contains(Triple(i, curr.second, dir))) {
                    return true
                }
                if (LINES[i-1][curr.second] == BLOCK) {
                    return checkObstacle(Triple(i, curr.second, dir), vis)
                }
                vis.add(Triple(i, curr.second, dir))
            }
        }
    }

    return false
}

fun main() {
    val file = File("inputs/day06.txt")

    file.bufferedReader().use { reader ->
        LINES = reader.lines().toList()
    }

    var pos = Triple(-1, -1, -1) // (line, column, dir)
    /*  0
        ^
     3< . >1
        v
        2
    */
    var dir = -1

    for (i in 0 until LINES.size) {
        for (j in 0 until LINES[i].length) {
            if (LINES[i][j] in COURSORS) {
                dir = COURSORS.indexOf(LINES[i][j])
                pos = Triple(i, j, dir)
                START = Pair(i, j)
                break
            }
        }
    }

    HEIGHT = LINES.size
    WIDTH = LINES[0].length

    var res = 0

    while (pos.first >= 0 && pos.first < HEIGHT && pos.first >= 0 && pos.second < WIDTH) {
        if (dir == 0) { // going UP
            while (pos.first >= 0 && (pos.first < 1 || LINES[pos.first - 1][pos.second] != BLOCK)) {
                val visitedTmp = mutableSetOf<Triple<Int, Int, Int>>()
                visitedTmp.addAll(VISITED)
                if (!START.equals(Pair(pos.first - 1, pos.second)) && checkObstacle(pos, visitedTmp)) {
                    println(pos)
                    res++
                }
                VISITED.add(pos)
                pos = Triple(pos.first - 1, pos.second, dir)
            }
        } else if (dir == 1) { // going RIGHT
            while (pos.second < WIDTH && (pos.second > WIDTH-2 || LINES[pos.first][pos.second + 1] != BLOCK)) {
                val visitedTmp = mutableSetOf<Triple<Int, Int, Int>>()
                visitedTmp.addAll(VISITED)
                if (!START.equals(Pair(pos.first, pos.second + 1)) && checkObstacle(pos, visitedTmp)) {
                    println(pos)
                    res++
                }
                VISITED.add(pos)
                pos = Triple(pos.first, pos.second + 1, dir)
            }
        } else if (dir == 2) { // going DOWN
            while (pos.first < HEIGHT && (pos.first > HEIGHT-2 || LINES[pos.first + 1][pos.second] != BLOCK)) {
                val visitedTmp = mutableSetOf<Triple<Int, Int, Int>>()
                visitedTmp.addAll(VISITED)
                if (!START.equals(Pair(pos.first + 1, pos.second)) && checkObstacle(pos, visitedTmp)) {
                    println(pos)
                    res++
                }
                VISITED.add(pos)
                pos = Triple(pos.first + 1, pos.second, dir)
            }
        } else { // going LEFT
            while (pos.second >= 0 && (pos.second < 1 || LINES[pos.first][pos.second - 1] != BLOCK)) {
                val visitedTmp = mutableSetOf<Triple<Int, Int, Int>>()
                visitedTmp.addAll(VISITED)
                if (!START.equals(Pair(pos.first, pos.second - 1)) && checkObstacle(pos, visitedTmp)) {
                    println(pos)
                    res++
                }
                VISITED.add(pos)
                pos = Triple(pos.first, pos.second - 1, dir)
            }
        }
        dir = nextDir(dir)
        //VISITED.add(Triple(pos.first, pos.second, dir))
    }

    println(res)
}