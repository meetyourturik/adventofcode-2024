import java.io.File

fun main() {
    val WALLS = mutableSetOf<Pair<Int, Int>>()
    val BOXES = mutableSetOf<Pair<Int, Int>>()

    fun moveLeft(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first, pos.second - 1)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(nextPos)) { // it's free
            return nextPos
        } else { // there's a box
            for (i in pos.second - 1 downTo  0) {
                if (BOXES.contains(Pair(pos.first, i))) {
                    continue
                } else if (WALLS.contains(Pair(pos.first, i))) {
                    return pos
                } else { // found free space
                    BOXES.remove(nextPos)
                    BOXES.add(Pair(pos.first, i))
                    return nextPos
                }
            }
        }

        return pos
    }

    fun moveRight(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first, pos.second + 1)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(nextPos)) { // it's free
            return nextPos
        } else { // there's a box
            for (i in pos.second + 1 .. 55) {
                if (BOXES.contains(Pair(pos.first, i))) {
                    continue
                } else if (WALLS.contains(Pair(pos.first, i))) {
                    return pos
                } else { // found free space
                    BOXES.remove(nextPos)
                    BOXES.add(Pair(pos.first, i))
                    return nextPos
                }
            }
        }

        return pos
    }

    fun moveUp(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first - 1, pos.second)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(nextPos)) { // it's free
            return nextPos
        } else { // there's a box
            for (i in pos.first - 1 downTo  0) {
                if (BOXES.contains(Pair(i, pos.second))) {
                    continue
                } else if (WALLS.contains(Pair(i, pos.second))) {
                    return pos
                } else { // found free space
                    BOXES.remove(nextPos)
                    BOXES.add(Pair(i, pos.second))
                    return nextPos
                }
            }
        }

        return pos
    }

    fun moveDown(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first + 1, pos.second)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(nextPos)) { // it's free
            return nextPos
        } else { // there's a box
            for (i in pos.first + 1 ..  55) {
                if (BOXES.contains(Pair(i, pos.second))) {
                    continue
                } else if (WALLS.contains(Pair(i, pos.second))) {
                    return pos
                } else { // found free space
                    BOXES.remove(nextPos)
                    BOXES.add(Pair(i, pos.second))
                    return nextPos
                }
            }
        }

        return pos
    }
    val file = File("inputs/day15.txt")

    var readingMap = true
    var moves = ""

    var pos = Pair(-1, -1)
    var i = 0

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.isEmpty()) {
                readingMap = false
            } else if (readingMap) {
                if (line.contains("@")) {
                    pos = Pair(i, line.indexOf('@'))
                }
                line.forEachIndexed { index, c ->
                    when (c) {
                        '#' -> WALLS.add(Pair(i, index))
                        'O' -> BOXES.add(Pair(i, index))
                    }
                }
                i++
            } else { // reading moves
                moves += line
            }
        }
    }

    moves.forEach {
        when (it) {
            '^' -> pos = moveUp(pos)
            '>' -> pos = moveRight(pos)
            'v' -> pos = moveDown(pos)
            '<' -> pos = moveLeft(pos)
        }
    }

    println(BOXES.fold(0) { ac, box ->
        ac + 100*box.first + box.second
    })
}
