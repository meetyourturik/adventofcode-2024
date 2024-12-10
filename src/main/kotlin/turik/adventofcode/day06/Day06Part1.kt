import java.io.File

fun main() {
    val file = File("inputs/day06.txt")
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

    var lines: List<String>

    file.bufferedReader().use { reader ->
        lines = reader.lines().toList()
    }

    var pos = Pair(-1, -1)

    var dir = Pair(-100, -100)

    for (i in 0 until lines.size) {
        for (j in 0 until lines[i].length) {
            if (lines[i][j] in COURSORS) {
                pos = Pair(i, j)
                dir = COURSORS.get(lines[i][j])!!
                break
            }
        }
    }

    val visited = mutableSetOf<Pair<Int, Int>>()

    val HEIGHT = lines.size
    val WIDTH = lines[0].length

    while (pos.first >= 0 && pos.first < HEIGHT && pos.first >= 0 && pos.second < WIDTH) {
        while (
            (pos.first in 0 until HEIGHT && pos.second in 0 until WIDTH) // within the field
            &&
            (lines.getOrElse(pos.first + dir.first, {_ -> "-"}).getOrElse(pos.second + dir.second, {_ -> '-'}) != BLOCK) // next step isn't block
        ) {
            visited.add(pos)
            pos = Pair(pos.first + dir.first, pos.second + dir.second)
        }
        dir = nextDir(dir)
    }

    println(visited.size)
}