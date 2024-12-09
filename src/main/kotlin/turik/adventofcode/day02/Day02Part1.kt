import java.io.File

fun isSafe(list: List<Int>): Boolean {
    var dir = 0;
    for (i in 1 until list.size) {
        val i1 = list.get(i-1)
        val i2 = list.get(i)
        val curDir = i2 - i1
        if (curDir == 0 || Math.abs(curDir) > 3 || curDir * dir < 0) {
            return false
        }
        if (dir == 0) {
            dir = curDir
            continue
        }
    }
    return true
}

fun main() {
    val file = File("inputs/day02.txt")

    var res = 0;

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val ints = line.split(" ").map { it.toInt() }
            if (isSafe(ints)) {
                res++
            }
        }
    }

    println(res)
}