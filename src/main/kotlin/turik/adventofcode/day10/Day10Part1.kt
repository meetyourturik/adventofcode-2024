import java.io.File

fun main() {
    val file = File("inputs/day01.txt")

    var res = 0

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            println(line)
        }
    }

    println(res)
}