import java.io.File

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

    while (true) {
        val blankIndex = mem.indexOfFirst  { it.first == -1 }
        val lastFile = mem.indexOfLast { it.first != -1 }

        if (blankIndex == -1 || blankIndex > lastFile) {
            break
        }

        val bl = mem[blankIndex]
        val lf = mem[lastFile]

        if (bl.second > lf.second) {
            mem.removeAt(blankIndex)
            mem.add(blankIndex, Pair(-1, bl.second - lf.second))
            mem.add(blankIndex, Pair(lf.first, lf.second))
            mem.removeAt(lastFile+1)
        } else if (bl.second == lf.second) {
            mem.removeAt(blankIndex)
            mem.add(blankIndex, Pair(lf.first, lf.second))
            mem.removeAt(lastFile)
        } else { // free space is less than the last file size
            mem.removeAt(blankIndex)
            mem.add(blankIndex, Pair(lf.first, bl.second))
            mem.add(lastFile, Pair(lf.first, lf.second - bl.second))
            mem.removeAt(lastFile+1)
        }
    }

    mem.removeIf { it.first == -1 }

    var res = 0L
    var i = 0
    for (m in mem) {
        for (j in 0 until m.second) {
            res += m.first * ( i++)
        }
    }

    println(res)
}