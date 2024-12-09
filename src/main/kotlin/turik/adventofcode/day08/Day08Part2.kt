import java.io.File

fun main() {
    val file = File("inputs/day08.txt")

    val positions: MutableMap<Char, MutableList<Pair<Int, Int>>> = mutableMapOf()
    var HEIGHT = 0
    var WIDTH = -1

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            WIDTH = line.length
            for (j in 0 until WIDTH) {
                val c = line[j]
                if (c != '.') {
                    positions.getOrPut(c, { mutableListOf() }).add(Pair(HEIGHT, j))
                }
            }
        HEIGHT++
        }
    }

    val res = mutableSetOf<Pair<Int, Int>>()

    positions.values.forEach { pl ->
        for (p1 in pl) {
            for (p2 in pl) {
                val fd = p1.first - p2.first
                val sd = p1.second - p2.second
                for (i in 0 .. 100) {
                    res.add(Pair(p1.first + fd*i, p1.second + sd*i))
                    res.add(Pair(p2.first - fd*i, p2.second - sd*i))
                }
            }
        }
    }

    println(res.filter { p ->
        (p.first in 0 until  HEIGHT) && (p.second in 0 until WIDTH)
    }.count())
}