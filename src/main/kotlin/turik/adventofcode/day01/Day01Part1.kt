import java.io.File

fun main() {
    val file = File("inputs/day01.txt")

    val l1 = mutableListOf<Int>()
    val l2 = mutableListOf<Int>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val (i1, i2) = line.split("   ")
            l1.add(i1.toInt())
            l2.add(i2.toInt())
        }
    }

    l1.sort()
    l2.sort()

    var res = 0

    for (i in 0 until l1.size) {
        res += Math.abs(l1.get(i) - l2.get(i))
    }

    println(res)
}