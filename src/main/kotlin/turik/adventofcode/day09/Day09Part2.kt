import java.io.File

fun fragmentBlanks(mem: MutableList<Pair<Int, Int>>) {
    while (true) {
        var p = -1
        for (j in 0 until mem.size - 1) {
            if (mem[j].first == -1 && mem[j].first == mem[j+1].first) {
                p = j
            }
        }
        if (p == -1) {
            break
        }
        val b1 = mem[p]
        val b2 = mem[p+1]
        mem.removeAt(p)
        mem.removeAt(p)
        mem.add(p, Pair(-1, b1.second + b2.second))
    }
}

fun main() {
    val file = File("inputs/day09.txt")

    var input = ""

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            input = line
        }
    }

    var isfile = true
    var id = 0
    // first - file id (-1 for free space), second = size
    val mem = mutableListOf<Pair<Int, Int>>()

    for (c in input) {
        if (isfile) {
            mem.add(Pair(id++, c - '0'))
        } else if (c != '0') {
            mem.add(Pair(-1, c - '0'))
        }
        isfile = !isfile
    }

    id--
    while (id >= 0) {
        val fileId = mem.indexOfFirst { it.first == id }
        id--
        val lf = mem[fileId]
        val blankId = mem.indexOfFirst  { it.first == -1 && it.second >= lf.second}

        if (blankId == -1 || blankId > fileId) {
            continue
        }

        val bl = mem[blankId]

        if (bl.second > lf.second) {
            mem.removeAt(blankId)
            mem.add(blankId, Pair(-1, bl.second - lf.second))
            mem.add(blankId, Pair(lf.first, lf.second))
            mem.removeAt(fileId+1)
            mem.add(fileId+1, Pair(-1, lf.second))
        } else if (bl.second == lf.second) {
            mem.removeAt(blankId)
            mem.add(blankId, Pair(lf.first, lf.second))
            mem.removeAt(fileId)
            mem.add(fileId, Pair(-1, lf.second))
        }

        fragmentBlanks(mem)
    }

    var res = 0L
    var i = 0
    for (m in mem) {
        for (j in 0 until m.second) {
            if (m.first != -1) {
                res += m.first * i
            }
            i++
        }
    }

    println(res)
}