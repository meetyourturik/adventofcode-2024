import java.io.File

fun Pair<Int, Int>.path() = '[' + this.first.toString() + ":" + this.second + ']'

fun main() {
    val file = File("inputs/day10.txt")

    val lines = mutableListOf<String>()
    val trailheads = mutableSetOf<Pair<Int, Int>>()
    var i = 0

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            lines.add(line)
            line.forEachIndexed { j, c ->
                if (c == '0') {
                    trailheads.add(Pair(i, j))
                }
            }
            i++
        }
    }

    fun countTrails(pos: Pair<Int, Int>, h: Int, trails: MutableSet<String>, path: String) {
        val np = path + pos.path()
        if (h == 9) {
            trails.add(np)
        }
        val nh = h+1
        if (lines.getOrElse(pos.first - 1, { "" }).getOrElse(pos.second, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first - 1, pos.second), nh, trails, np)
        }
        if (lines.getOrElse(pos.first, { "" }).getOrElse(pos.second + 1, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first, pos.second + 1), nh, trails, np)
        }
        if (lines.getOrElse(pos.first + 1, { "" }).getOrElse(pos.second, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first + 1, pos.second), nh, trails, np)
        }
        if (lines.getOrElse(pos.first, { "" }).getOrElse(pos.second - 1, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first, pos.second - 1), nh, trails, np)
        }
    }

    var res = 0

    for (th in trailheads) {
        val tr = mutableSetOf<String>()
        countTrails(th, 0, tr, "")
        res += tr.size
    }

    println(res)
}

