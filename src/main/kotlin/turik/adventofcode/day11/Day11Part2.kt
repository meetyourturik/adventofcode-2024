import java.io.File
import kotlin.math.pow

val LIMIT = 75
val TWOPOWERS = listOf(100_000, 2, 4, 8, 16, 32, 64, 128)

fun main() {

    val file = File("inputs/day11.txt")

    val stones = mutableListOf<String>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            stones.addAll(line.split(" "))
        }
    }

    // precalculating 0 to 9
    val m = mutableMapOf(
        0 to listOf(listOf(1), listOf(2024), listOf(20, 24), listOf(2,0,2,4)),
        1 to listOf(listOf(2024), listOf(20, 24), listOf(2,0,2,4)),
        2 to listOf(listOf(4048), listOf(40, 48), listOf(4,0,4,8)),
        3 to listOf(listOf(6072), listOf(60, 72), listOf(6,0,7,2)),
        4 to listOf(listOf(8096), listOf(80, 96), listOf(8,0,9,6)),
        5 to listOf(listOf(10820), listOf(20482880), listOf(2048, 2880), listOf(20,48,28,80), listOf(2,0,4,8,2,8,8,0)),
        6 to listOf(listOf(12144), listOf(24579456), listOf(2457, 9456), listOf(24,57,94,56), listOf(2,4,5,7,9,4,5,6)),
        7 to listOf(listOf(14168), listOf(28676032), listOf(2867, 6032), listOf(28,67,60,32), listOf(2,8,6,7,6,0,3,2)),
        8 to listOf(listOf(16192), listOf(32772608), listOf(3277, 2608), listOf(32,77,26,8)),
        9 to listOf(listOf(18216), listOf(36869184), listOf(3686, 9184), listOf(36,86,91,84), listOf(3,6,8,6,9,1,8,4))
    )

    val COUNTS = mutableMapOf(
        0 to MutableList(LIMIT + 1) {0L},
        1 to MutableList(LIMIT + 1) {0L},
        2 to MutableList(LIMIT + 1) {0L},
        3 to MutableList(LIMIT + 1) {0L},
        4 to MutableList(LIMIT + 1) {0L},
        5 to MutableList(LIMIT + 1) {0L},
        6 to MutableList(LIMIT + 1) {0L},
        7 to MutableList(LIMIT + 1) {0L},
        8 to MutableList(LIMIT + 1) {0L},
        9 to MutableList(LIMIT + 1) {0L},
    )

    for (e in m) {
        COUNTS[e.key]!![0] = 1
        e.value.forEachIndexed { index, ints ->
            COUNTS[e.key]!![index+1] = ints.size.toLong()
        }
    }

    for (i in 3..LIMIT) {
        for (c in COUNTS.keys) {
            if (COUNTS[c]!![i] == 0L) {
                COUNTS[c]!![i] = m[c]!!.last().fold(0L) { acc, e ->
                    if (e < 10) {
                        acc + COUNTS[e]!![i - m[c]!!.size] //
                    } else if (i == m[c]!!.size + 1) {
                        acc + 2
                    } else {
                        val e1 = e % 10
                        val e2 = e / 10
                        acc + COUNTS[e1]!![i - 1 - m[c]!!.size] + COUNTS[e2]!![i - 1 - m[c]!!.size]
                    }
                }
            }
        }
    }

    // precalculations done

    var res = 0L

    for (i in 1..LIMIT) {
        val stonestmp = stones.toList()
        stones.clear()
        for (stone in stonestmp) {
            if (stone.length == 1) {
                res += COUNTS[stone.toInt()]!![LIMIT + 1 - i]
            } else if (stone.length in TWOPOWERS && !stone.contains('0')) {
                val power = TWOPOWERS.indexOf(stone.length)
                if (LIMIT + 1 - i <= power) {
                     res += 2.0.pow(LIMIT + 1 - i).toLong()
                } else {
                    for (ch in stone) {
                        res += COUNTS[ch.code - '0'.code]!![LIMIT + 1 - i - power]
                    }
                }
            } else {
                stones.addAll(blinkStone(stone))
            }
        }
    }

    println(res + stones.size)
}
