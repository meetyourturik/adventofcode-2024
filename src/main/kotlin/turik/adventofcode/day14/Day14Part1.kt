import java.io.File

class Robot(var pos: Pair<Int, Int>, val vel: Pair<Int, Int>)

val WIDTH = 101
val HEIGHT = 103

fun move100(robot: Robot) {
    val xn = (robot.pos.first + 100*(robot.vel.first + WIDTH)) % WIDTH
    val yn = (robot.pos.second + 100*(robot.vel.second + HEIGHT)) % HEIGHT
    robot.pos = Pair(xn, yn)
}

fun main() {
    val file = File("inputs/day14.txt")
    val NUMBER = "-?\\d+".toRegex()
    val robots = mutableListOf<Robot>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val (a,b,c,d) = NUMBER.findAll(line).map { it.value.toInt() }.toList()
            robots.add(Robot(Pair(a,b), Pair(c,d)))
        }
    }


    robots.forEach { move100(it) }

    val QS = listOf(
        Pair(0 until WIDTH / 2 , 0 until HEIGHT / 2),
        Pair(WIDTH / 2 + 1 until WIDTH , 0 until HEIGHT / 2),
        Pair(0 until WIDTH / 2, HEIGHT / 2 + 1 until HEIGHT),
        Pair(WIDTH / 2 + 1 until WIDTH , HEIGHT / 2 + 1 until HEIGHT )
    )
    val qds = MutableList(4) { 0 }

    robots.forEach { robot ->
        val q = QS.indexOfFirst {robot.pos.first in it.first && robot.pos.second in it.second}
        if (q != -1) {
            qds[q]++
        }
    }

    println(qds.fold(1) { a, b -> a * b })
}