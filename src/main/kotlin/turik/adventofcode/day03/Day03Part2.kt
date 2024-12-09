import java.io.File

fun main() {
    val file = File("inputs/day03.txt")

    val DO_COND = "do()"
    val DONT_COND = "don't()"

    val patternMul = """mul\(\d{1,3},\d{1,3}\)"""
    val regexMul = Regex(patternMul)

    val patternNum = "\\d{1,3}"
    val regexNum = Regex(patternNum)

    val patternCon = """do(n't)?\(\)"""
    val regexCon = Regex(patternCon)

    var res = 0;

    var active = true

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val ranges = mutableListOf<IntRange>()
            var start = 0

            regexCon.findAll(line).forEach { cond ->
                if (active && cond.value.equals(DONT_COND)) {
                    ranges.add(IntRange(start, cond.range.endInclusive))
                    active = false
                } else if (!active && cond.value.equals(DO_COND)) {
                    start = cond.range.start
                    active = true
                }
            }

            if (active) {
                ranges.add(IntRange(start, line.length-1))
            }

            ranges.forEach { range ->
                regexMul.findAll(line.substring(range)).forEach { mul ->
                    val v1 = regexNum.findAll(mul.value)
                    var tmp = 1
                    v1.forEach {
                        tmp *= it.value.toInt()
                    }
                    res += tmp
                }
            }
        }
    }

    println(res)
}