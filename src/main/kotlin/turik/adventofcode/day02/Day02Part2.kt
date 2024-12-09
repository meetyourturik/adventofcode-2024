import java.io.File

fun main() {
    val file = File("inputs/day02.txt")

    var res = 0;

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val ints = line.split(" ").map { it.toInt() }

            var safe = isSafe(ints) // reusing isSafe from Part1, magic!
            safe = safe || isSafe(ints.drop(1))
            safe = safe || isSafe(ints.dropLast(1))

            for (i in 1 until ints.size - 1) {
                safe = safe || isSafe(ints.slice(0 until i) + ints.slice(i+1 until ints.size))
            }

            if (safe) {
                res++
            }
        }
    }

    println(res)
}