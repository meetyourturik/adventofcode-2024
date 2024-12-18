import java.io.File

fun main() {
    val WALLS = mutableSetOf<Pair<Int, Int>>()
    val BOXES = mutableSetOf<Pair<Int, Int>>()

    fun moveUp(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first - 1, pos.second)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(nextPos) && !BOXES.contains(Pair(pos.first - 1, pos.second - 1))) { // it's free
            return nextPos
        } else { // there's a box
            // first finding all the boxes that would be affected by the move
            val potentialBoxes = mutableSetOf<Pair<Int, Int>>()
            if (BOXES.contains(nextPos)) {
                potentialBoxes.add(nextPos)
            }
            if (BOXES.contains(Pair(pos.first - 1, pos.second - 1))) {
                potentialBoxes.add(Pair(pos.first - 1, pos.second - 1))
            }
            for (i in pos.first - 1 downTo  1) {
                for (box in potentialBoxes.filter { it.first == i }) {
                    listOf(-1, 0, 1).forEach {
                        if (BOXES.contains(Pair(box.first - 1, box.second + it))) {
                            potentialBoxes.add(Pair(box.first - 1, box.second + it))
                        }
                    }
                }
            }
            // then, checking if any of them is touching the wall
            for (box in potentialBoxes) {
                if (WALLS.contains(Pair(box.first - 1, box.second)) || WALLS.contains(Pair(box.first - 1, box.second + 1))) {
                    return pos
                }
            }
            // if non is touching the wall, moving all potential boxes
            for (box in potentialBoxes) {
                BOXES.remove(box)
            }
            for (box in potentialBoxes) {
                BOXES.add(Pair(box.first - 1, box.second))
            }

            return nextPos
        }
    }

    fun moveDown(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first + 1, pos.second)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(nextPos) && !BOXES.contains(Pair(pos.first + 1, pos.second - 1))) { // it's free
            return nextPos
        } else { // there's a box
            // first finding all the boxes that would be affected by the move
            val potentialBoxes = mutableSetOf<Pair<Int, Int>>()
            if (BOXES.contains(nextPos)) {
                potentialBoxes.add(nextPos)
            }
            if (BOXES.contains(Pair(pos.first + 1, pos.second - 1))) {
                potentialBoxes.add(Pair(pos.first + 1, pos.second - 1))
            }
            for (i in pos.first + 1 .. 100) {
                for (box in potentialBoxes.filter { it.first == i }) {
                    listOf(-1, 0, 1).forEach {
                        if (BOXES.contains(Pair(box.first + 1, box.second + it))) {
                            potentialBoxes.add(Pair(box.first + 1, box.second + it))
                        }
                    }
                }
            }
            // then, checking if any of them is touching the wall
            for (box in potentialBoxes) {
                if (WALLS.contains(Pair(box.first + 1, box.second)) || WALLS.contains(Pair(box.first + 1, box.second + 1))) {
                    return pos
                }
            }
            // if non is touching the wall, moving all potential boxes
            for (box in potentialBoxes) {
                BOXES.remove(box)
            }
            for (box in potentialBoxes) {
                BOXES.add(Pair(box.first + 1, box.second))
            }

            return nextPos
        }
    }

    fun moveLeft(pos: Pair<Int, Int>): Pair<Int, Int> {
        val nextPos = Pair(pos.first, pos.second - 1)
        if (WALLS.contains(nextPos)) { // can't move
            return pos
        } else if (!BOXES.contains(Pair(pos.first, pos.second - 2))) { // it's free
            return nextPos
        } else { // there's a box
            val potentialBoxes = mutableSetOf<Pair<Int, Int>>()
            for (i in pos.second - 2 downTo  0 step 2) {
                if (BOXES.contains(Pair(pos.first, i))) {
                    potentialBoxes.add(Pair(pos.first, i))
                    continue
                } else if (WALLS.contains(Pair(pos.first, i+1))) {
                    return pos
                } else { // found free space
                    for (box in potentialBoxes) {
                        BOXES.remove(box)
                    }
                    for (box in potentialBoxes) {
                        BOXES.add(Pair(box.first, box.second - 1))
                    }
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
            val potentialBoxes = mutableSetOf<Pair<Int, Int>>()
            for (i in pos.second + 1 .. 100 step 2) {
                if (BOXES.contains(Pair(pos.first, i))) {
                    potentialBoxes.add(Pair(pos.first, i))
                    continue
                } else if (WALLS.contains(Pair(pos.first, i))) {
                    return pos
                } else { // found free space
                    for (box in potentialBoxes) {
                        BOXES.remove(box)
                    }
                    for (box in potentialBoxes) {
                        BOXES.add(Pair(box.first, box.second + 1))
                    }
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
                var doubleLine = ""
                for (c in line) {
                    when (c) {
                        '#' -> doubleLine+= "##"
                        '.' -> doubleLine+= ".."
                        'O' -> doubleLine+= "[]"
                        '@' -> doubleLine+= "@."
                    }
                }
                if (doubleLine.contains("@")) {
                    pos = Pair(i, doubleLine.indexOf('@'))
                }
                doubleLine.forEachIndexed { index, c ->
                    when (c) {
                        '#' -> WALLS.add(Pair(i, index))
                        '[' -> BOXES.add(Pair(i, index))
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
