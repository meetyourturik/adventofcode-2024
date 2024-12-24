import java.io.File

fun main() {

    fun inverse(s: String): String {
        return if (s == "I") {
            "O"
        } else {
            "I"
        }
    }

    fun sides(region: Set<Point>): Int {
        if (region.size == 1) {
            return 4
        }

        // horizontal/vertical + inner/outer, segment, upper/lefter coord
        val edges = mutableListOf<Triple<Pair<String, String>, Point, Int>>()

        for (pt in region) {
            listOf(
                Triple(Pair("H", "I"), Pair(pt.first, pt.first + 1), pt.second),
                Triple(Pair("H", "O"), Pair(pt.first, pt.first + 1), pt.second + 1),
                Triple(Pair("V", "I"), Pair(pt.second, pt.second + 1), pt.first),
                Triple(Pair("V", "O"), Pair(pt.second, pt.second + 1), pt.first + 1)
            ).forEach { potedge ->
                val rev = inverse(potedge.first.second)
                if (edges.contains(Triple(Pair(potedge.first.first, rev), potedge.second, potedge.third))) {
                    edges.remove(Triple(Pair(potedge.first.first, rev), potedge.second, potedge.third))
                } else {
                    edges.add(potedge)
                }
            }
        }

        var fences = 0

        while (edges.isNotEmpty()) {
            val edge = edges.removeFirst()

            val q = ArrayDeque<Triple<Pair<String, String>, Point, Int>>()
            val visited = mutableSetOf<Triple<Pair<String, String>, Point, Int>>()
            q.add(edge)

            while (q.isNotEmpty()) {
                val pt = q.removeLast()
                edges.remove(pt)
                visited.add(pt)

                listOf(1, -1).forEach {
                    val npt = Triple(pt.first, Point(pt.second.first + it, pt.second.second + it), pt.third)
                    if (edges.contains(npt) && !visited.contains(npt) && !q.contains(npt)) {
                        q.add(npt)
                    }
                }
            }
            fences += 1
        }

        return fences
    }

    val file = File("inputs/day12.txt")
    val DIRS = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))
    val CHAR_REGIONS = mutableMapOf<Char, MutableList<Point>>()

    var i = 0
    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            line.forEachIndexed { index, c ->
                CHAR_REGIONS.getOrPut(c) { mutableListOf() }.add(Point(i, index))
            }
            i++
        }
    }

    var res = 0

    CHAR_REGIONS.forEach { (_, region) ->
        while (region.isNotEmpty()) {
            val point = region.removeFirst()
            val q = ArrayDeque<Point>()
            val visited = mutableSetOf<Point>()
            q.add(point)
            while (q.isNotEmpty()) {
                val pt = q.removeLast()
                region.remove(pt)
                visited.add(pt)

                DIRS.forEach { dir ->
                    val npt = Point(pt.first + dir.first, pt.second + dir.second)
                    if (region.contains(npt) && !visited.contains(npt) && !q.contains(npt)) {
                        q.add(npt)
                    }
                }
            }
            res += visited.size * sides(visited)
        }
    }

    println(res)
}
