import java.io.File

fun main() {
    val file = File("inputs/day04.txt")

    val lines = mutableListOf<String>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            lines.add(line)
        }
    }

    val MAS = listOf("MAS", "SAM")

    var res = 0

    for (i in 0 until lines.size - 2) {
        for (j in 0 until lines[0].length - 2) {
            if (lines[i+1][j+1] != 'A') {
                continue
            }

            val d1 = lines[i][j]+"A"+lines[i+2][j+2]
            val d2 = lines[i][j+2]+"A"+lines[i+2][j]

            if (MAS.contains(d1) && MAS.contains(d2)) {
                res++
            }
        }
    }

    println(res)
}