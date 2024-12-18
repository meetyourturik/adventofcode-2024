import java.io.File

fun blinkStone(stone: String): List<String> {
    return if (stone.equals("0")) {
        listOf("1")
    } else if (stone.length % 2 == 0) {
        stone.length.let { l ->
            listOf(
                stone.slice(0 until l / 2),
                stone.slice(l / 2 until l).toLong().toString()
            )
        }
    } else {
        listOf((stone.toLong() * 2024).toString())
    }
}

fun main() {
    val file = File("inputs/day11.txt")

    val stones = mutableListOf<String>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            stones.addAll(line.split(" "))
        }
    }

    for (i in 1..25) {
        val stonestmp = stones.toMutableList()
        stones.clear()
        for (s in stonestmp) {
            stones.addAll(blinkStone(s))
        }
    }

    println(stones.size)
}