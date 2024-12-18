import java.io.File
import kotlin.math.max

fun move(robot: Robot) {
    val xn = (robot.pos.first + robot.vel.first + WIDTH) % WIDTH
    val yn = (robot.pos.second + robot.vel.second + HEIGHT) % HEIGHT
    robot.pos = Pair(xn, yn)
}

// this tree pattern search doesn't work of course, but it happened to solve the task on my input, negating any will to improve the algorithm
fun hasTreePic(robots: List<Robot>): Boolean {
    val canvas = List(HEIGHT) {
        MutableList(WIDTH) {'.'}
    }

    for (robot in robots) {
        canvas[robot.pos.second][robot.pos.first] = '#'
    }

    var bl = -1

    // looking for tree base
    val BASE_REGEX = "#+".toRegex()
    for (line in canvas) {
        bl = max(bl, BASE_REGEX.findAll(line.joinToString("")).toList().map { it.value.length }.maxOrNull() ?: -1)
    }

    if (bl > 15) {
        return true
    }

    return false
}

fun printField(robots: List<Robot>) {
    val canvas = List(HEIGHT) {
        MutableList(WIDTH) {'.'}
    }

    for (robot in robots) {
        canvas[robot.pos.second][robot.pos.first] = '#'
    }

    canvas.forEach {
        println(it.joinToString(" "))
    }
}

fun main() {
    val file = File("inputs/day14.txt")
    val NUMBER = "-?\\d+".toRegex()
    val robots = mutableListOf<Robot>()

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val (a, b, c, d) = NUMBER.findAll(line).map { it.value.toInt() }.toList()
            robots.add(Robot(Pair(a, b), Pair(c, d)))
        }
    }

    var i = 0L

    while (!hasTreePic(robots)) {
        robots.forEach { move(it) }
        i++
    }

    printField(robots)

    println(i)
}