import java.io.File

fun main() {
    val PATTERNS = mutableSetOf<String>()
    val IMPOSSIBLES = mutableSetOf<String>()

    fun isPossible(design: String): Boolean {
        if (IMPOSSIBLES.contains(design)) {
            return false
        }

        for (pat in PATTERNS) {
            if (design.equals(pat)) {
                return true
            }
            if (design.startsWith(pat)) {
                if (isPossible(design.slice(pat.length until  design.length))) {
                    return true
                }
            }
        }
        IMPOSSIBLES.add(design)
        return false
    }

    val file = File("inputs/day19.txt")

    var res = 0
    var readingDesigns = false

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.isEmpty()) {
                readingDesigns = true
            } else if (!readingDesigns) {
                PATTERNS.addAll(line.split(", "))
            } else {
                res += if (isPossible(line)) 1 else 0
            }
        }
    }

    println(res)
}