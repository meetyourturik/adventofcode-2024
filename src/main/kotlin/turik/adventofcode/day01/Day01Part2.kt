import java.io.File

fun main() {
    val file = File("inputs/day01.txt")

    val l1 = mutableListOf<Int>()
    val m2 = mutableMapOf<Int, Int>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val (i1, i2) = line.split("   ")
            l1.add(i1.toInt())
            m2.compute(i2.toInt()) { _, count -> (count ?: 0) + 1 }
        }
    }

    var res = 0

    for (i in l1) {
        res += i * m2.getOrDefault(i, 0)
    }

    println(res)
}