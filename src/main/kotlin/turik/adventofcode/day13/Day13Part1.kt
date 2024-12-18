import java.io.File

class Game(val ba: Pair<Int, Int>, val bb: Pair<Int, Int>, val prize: Pair<Int, Int>)

fun playGame(game: Game): Int {
    var tokens = Int.MAX_VALUE
    for (i in 1..100) {
        for (j in 1..100) {
            val x = game.ba.first * i + game.bb.first*j
            val y = game.ba.second * i + game.bb.second*j
            val tokenstmp = 3*i+j
            if (x == game.prize.first && y == game.prize.second && tokenstmp < tokens) {
                tokens = tokenstmp
            }
        }
    }

    return if (tokens == Int.MAX_VALUE) {
        0
    } else {
        tokens
    }
}

fun main() {
    val file = File("inputs/day13.txt")

    val NUMBER = "\\d+".toRegex()
    val games = mutableListOf<Game>()
    val tmp = mutableListOf<Pair<Int, Int>>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.isNotEmpty()) {
                val (x,y) = NUMBER.findAll(line).map { it.value.toInt() }.toList()
                tmp.add(Pair(x,y))
            } else {
                games.add(Game(tmp[0], tmp[1], tmp[2]))
                tmp.clear()
            }
        }
    }

    val res = games.fold(0) { acc, game ->
        acc + playGame(game)
    }

    println(res)
}