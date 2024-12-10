import java.io.File

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

    val mem = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()

    fun countTrails(pos: Pair<Int, Int>, h: Int, summits: MutableSet<Pair<Int, Int>>) {
        if (h == 9) {
            summits.add(pos)
        }
        val nh = h+1
        if (lines.getOrElse(pos.first - 1, { "" }).getOrElse(pos.second, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first - 1, pos.second), nh, summits)
        }
        if (lines.getOrElse(pos.first, { "" }).getOrElse(pos.second + 1, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first, pos.second + 1), nh, summits)
        }
        if (lines.getOrElse(pos.first + 1, { "" }).getOrElse(pos.second, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first + 1, pos.second), nh, summits)
        }
        if (lines.getOrElse(pos.first, { "" }).getOrElse(pos.second - 1, { 'j' } )- '0' == nh) {
            countTrails(Pair(pos.first, pos.second - 1), nh, summits)
        }
    }

    var res = 0

    for (th in trailheads) {
        val ss = mutableSetOf<Pair<Int, Int>>()
        countTrails(th, 0, ss)
        val count = ss.size
        res += count
    }

    println(res)
}