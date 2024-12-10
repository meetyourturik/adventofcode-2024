import java.io.File

fun main() {
    val file = File("inputs/day06.txt")
    val COURSORS = listOf('^', '>', 'v', '<')
    val BLOCK = '#'

    var lines: List<String>

    file.bufferedReader().use { reader ->
        lines = reader.lines().toList()
    }

    var pos = Pair(-1, -1)
    /*  0
        ^
     3< . >1
        v
        2
    */
    var dir = -1

    for (i in 0 until lines.size) {
        for (j in 0 until lines[i].length) {
            if (lines[i][j] in COURSORS) {
                pos = Pair(i, j)
                dir = COURSORS.indexOf(lines[i][j])
                break
            }
        }
    }

    val visited = mutableSetOf<Pair<Int, Int>>()

    val HEIGHT = lines.size
    val WIDTH = lines[0].length

    while (pos.first >= 0 && pos.first < HEIGHT && pos.first >= 0 && pos.second < WIDTH) {
        if (dir == 0) { // going UP
            while (pos.first >= 0 && (pos.first < 1 || lines[pos.first - 1][pos.second] != BLOCK)) {
                visited.add(pos)
                pos = Pair(pos.first - 1, pos.second)
            }
        } else if (dir == 1) { // going RIGHT
            while (pos.second < WIDTH && (pos.second > WIDTH-2 || lines[pos.first][pos.second + 1] != BLOCK)) {
                visited.add(pos)
                pos = Pair(pos.first, pos.second + 1)
            }
        } else if (dir == 2) { // going DOWN
            while (pos.first < HEIGHT && (pos.first > HEIGHT-2 || lines[pos.first + 1][pos.second] != BLOCK)) {
                visited.add(pos)
                pos = Pair(pos.first + 1, pos.second)
            }
        } else { // going LEFT
            while (pos.second >= 0 && (pos.second < 1 || lines[pos.first][pos.second - 1] != BLOCK)) {
                visited.add(pos)
                pos = Pair(pos.first, pos.second - 1)
            }
        }
        dir = (dir+1) % 4
    }

    println(visited.size)
}