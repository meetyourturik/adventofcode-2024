import java.io.File

class Game2(val ba: Pair<Long, Long>, val bb: Pair<Long, Long>, val prize: Pair<Long, Long>)

fun playGame2(game: Game2): Long {
    val z = game.ba.first * game.bb.second - game.bb.first * game.ba.second
    if (z == 0L) {
        return 0L
    }
    val c1 = game.prize.first * game.bb.second - game.bb.first * game.prize.second
    val c2 = game.ba.first * game.prize.second - game.prize.first * game.ba.second
    if (c1 % z != 0L || c2 % z != 0L) {
        return 0L
    }
    return 3 * (c1 / z) + (c2 / z)
}

fun main() {
    val file = File("inputs/day13.txt")

    val NUMBER = "\\d+".toRegex()
    val games = mutableListOf<Game2>()
    val tmp = mutableListOf<Pair<Long, Long>>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.isNotEmpty()) {
                var (x,y) = NUMBER.findAll(line).map { it.value.toLong() }.toList()
                if (line.startsWith("Prize")) {
                   x += 10_000_000_000_000L
                   y += 10_000_000_000_000L
                }
                tmp.add(Pair(x,y))
            } else {
                games.add(Game2(tmp[0], tmp[1], tmp[2]))
                tmp.clear()
            }
        }
    }

    val res = games.fold(0L) { acc, game ->
        acc + playGame2(game)
    }

    println(res)
}