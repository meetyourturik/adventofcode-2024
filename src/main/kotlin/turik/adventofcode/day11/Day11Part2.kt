import java.io.File

val LIMIT = 75
val UNOS = 0..9
val TWOPOWERS = listOf(2,4,8,16,32,64,128)

fun main() {
    /*
    val file = File("inputs/day11.txt")

    val stones = mutableListOf<String>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            stones.addAll(line.split(" "))
        }
    }
     */

    val stones = mutableListOf("8")

    var res = 0

    val COUNTS = mutableMapOf(1 to listOf(1,2,3))

    for (i in 1..LIMIT) {
        val stonestmp = stones.toList()
        stones.clear()
        for (s in stonestmp) {
            if (s.toInt() in COUNTS.keys) {
               res += COUNTS[s.toInt()]!![LIMIT - i]
            } else if (s.length in TWOPOWERS) {
                val power = s.split("")
            } else {
                stones.addAll(blinkStone(s))
            }
        }
    }
    println(res)
}
