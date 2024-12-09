import java.io.File

fun main() {
    val file = File("inputs/day03.txt")

    val patternMul = """mul\(\d{1,3},\d{1,3}\)"""
    val regexMul = Regex(patternMul)

    val patternD = "\\d{1,3}"
    val regexD = Regex(patternD)

    var res = 0;

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            regexMul.findAll(line).forEach { mul ->
                val v1 = regexD.findAll(mul.value)
                var tmp = 1
                v1.forEach {
                    tmp *= it.value.toInt()
                }
                res += tmp
            }
        }
    }

    println(res)
}