import java.io.File

typealias Point = Pair<Int, Int>

fun main() {

    fun perimeter(region: Set<Point>): Int {
        if (region.size == 1) {
            return 4
        }
        val edges = mutableSetOf<String>()

        for (pt in region) {
            val pedges = listOf(
                "H" + pt.first.toString() + (pt.first + 1).toString() + pt.second.toString(),
                "H" + pt.first.toString() + (pt.first + 1).toString() + (pt.second + 1).toString(),
                "V" + pt.second.toString() + (pt.second + 1).toString() + pt.first.toString(),
                "V" + pt.second.toString() + (pt.second + 1).toString() + (pt.first + 1).toString(),
            )

            for (tmp in pedges) {
                if (edges.contains(tmp)) {
                    edges.remove(tmp)
                } else {
                    edges.add(tmp)
                }
            }
        }

        return edges.size
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

    CHAR_REGIONS.forEach { c, region ->
        println("region " + c)

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
            res += visited.size * perimeter(visited)
        }

    }

    println(res)
}