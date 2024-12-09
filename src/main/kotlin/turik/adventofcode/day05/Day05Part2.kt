import java.io.File

fun main() {
    val file = File("inputs/day05.txt")

    var res = 0

    var part = 0
    val instructions = mutableMapOf<Int, MutableList<Int>>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.isEmpty()) {
                part++
            } else if (part == 0) {
                val (x: Int, y: Int) = line.split("|").map {it.toInt()}
                instructions.compute(x) { _, curi ->
                    if (curi == null) {
                        mutableListOf(y)
                    } else {
                        curi.add(y)
                        curi
                    }
                }
            } else { //if (part == 1)
                val update = line.split(",").map { it.toInt() }
                var correct = true

                for (i in 0 until update.size - 1) {
                    val curi = instructions[update[i]] ?: listOf()
                    for (j in i+1 until update.size) {
                        if (!curi.contains(update[j])) {
                            correct = false
                            break
                        }
                    }
                    if (!correct) {
                        break
                    }
                }

                if (!correct) {
                    res += update.sortedBy {
                        update.filter { u -> instructions[u]?.contains(it) ?: false }.count()
                    }[update.size/2]
                }
            }
        }
    }

    println(res)
}