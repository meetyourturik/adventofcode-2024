import java.io.File
import kotlin.math.min

fun main() {
    val WALLS = mutableSetOf<Pair<Int, Int>>()
    var START = Pair(-1, -1)
    var END = Pair(-1, -1)
    val SCORE_CONT = 1
    val SCORE_TURN = 1001

    val file = File("inputs/day16.txt")

    var i = 0
    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            line.forEachIndexed { index, c ->
                if (c == '#') {
                    WALLS.add(Pair(i, index))
                } else if (c == 'S') {
                    START = Pair(i, index)
                } else if (c == 'E') {
                    END = Pair(i, index)
                }
            }
            i++
        }
    }

    // line change, column change, going horizontally
    val DIRS = listOf(Triple(0,1, true), Triple(0,-1, true), Triple(1,0, false), Triple(-1, 0, false))

    var score = Int.MAX_VALUE
    val visited = mutableMapOf<Pair<Int, Int>, Int>()

    val q = ArrayDeque<Triple<Pair<Int, Int>, Boolean, Int>>() //pos, going horizontally, score
    q.add(Triple(START, true, 0))

    while (q.isNotEmpty()) {
        val posTriple = q.removeFirst()
        val pos = posTriple.first
        visited.put(pos, posTriple.third)

        DIRS.forEach { dir ->
            val npos = Pair(pos.first + dir.first, pos.second + dir.second)
            val nscore = if (posTriple.second xor dir.third) {
                posTriple.third + SCORE_TURN
            } else {
                posTriple.third + SCORE_CONT
            }
            if (END.equals(npos)) {
                score = min(score, nscore)
            } else if (!WALLS.contains(npos) && (visited.compute(npos) { _, sc -> sc ?: Int.MAX_VALUE })!! > nscore && q.none { it.first == npos && it.third < nscore }) {
                q.add(Triple(npos, dir.third, nscore))
            }
        }
    }

    println(score)
}