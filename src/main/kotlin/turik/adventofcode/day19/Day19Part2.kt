import java.io.File

fun main() {
    val PATTERNS = mutableSetOf<String>()
    val IMPOSSIBLES = mutableSetOf<String>()
    val MEMORY = mutableMapOf<String, Long>()

    fun countOptions(design: String): Long {
        if (IMPOSSIBLES.contains(design)) {
            return 0
        }
        if (MEMORY.containsKey(design)) {
            return MEMORY.get(design)!!
        }
        var res = 0L
        for (pat in PATTERNS) {
            if (design.equals(pat)) {
                res++
            }
            if (design.startsWith(pat)) {
                res += countOptions(design.slice(pat.length until  design.length))
            }
        }
        if (res == 0L) {
            IMPOSSIBLES.add(design)
        } else {
            MEMORY.put(design, res)
        }
        return res
    }

    val file = File("inputs/day19.txt")

    var res = 0L
    var readingDesigns = false

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.isEmpty()) {
                readingDesigns = true
            } else if (!readingDesigns) {
                PATTERNS.addAll(line.split(", "))
            } else {
                res += countOptions(line)
            }
        }
    }

    println(res)
}