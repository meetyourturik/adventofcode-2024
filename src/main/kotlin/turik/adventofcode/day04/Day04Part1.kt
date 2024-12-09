import java.io.File

fun main() {
    val file = File("inputs/day04.txt")

    val lines = mutableListOf<String>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            lines.add(line)
        }
    }

    val verticals = MutableList(lines[0].length) {i -> ""}
    val diagonals1 = MutableList(lines.size + lines[0].length - 1) {i -> ""}
    val diagonals2 = MutableList(lines.size + lines[0].length - 1) {i -> ""}

    for (i in 0 until lines.size) {
        for (j in 0 until lines[i].length) {
            var tmp = diagonals1[lines.size-1-i+j]
            tmp += lines[i][j]
            diagonals1[lines.size-1-i+j] = tmp

            tmp = verticals[j]
            tmp += lines[i][j]
            verticals[j] = tmp
        }
    }

    for (i in lines.size - 1 downTo  0) {
        for (j in lines[i].length-1 downTo  0) {
            var tmp = diagonals2[i+j]
            tmp += lines[i][j]
            diagonals2[i+j] = tmp
        }
    }

    val XMAS = "XMAS".toRegex()
    val SAMX = "SAMX".toRegex()

    var res = 0

    lines.addAll(diagonals1)
    lines.addAll(diagonals2)
    lines.addAll(verticals)

    for (line in lines) {
        res += XMAS.findAll(line).count()
        res += SAMX.findAll(line).count()
    }

    println(res)
}