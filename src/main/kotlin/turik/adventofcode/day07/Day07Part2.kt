import java.io.File

fun main() {
    val file = File("inputs/day07.txt")

    var res = 0L

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            val (rs, ns) = line.split(": ")
            val value = rs.toLong()
            val nums = ns.split(" ").map{ it.toInt() }
            var take = false
            for (i in 0 until Math.pow(3.0, (nums.size - 1).toDouble()).toInt()) {
                val ops = Integer.toString(i,3).padStart(nums.size - 1, '0')

                val test = nums.foldIndexed(0L) { index, acc, n ->
                    if (index == 0) {
                        n.toLong()
                    } else if (ops[index - 1] == '0') {
                        acc + n
                    } else if (ops[index - 1] == '1') {
                        acc * n
                    } else if (ops[index - 1] == '2') {
                        (acc.toString() + "" + n.toString()).toLong()
                    } else {
                        acc
                    }
                }

                if (test == value) {
                    take = true
                    break
                }
            }
            if (take) {
                res += value
            }
        }
    }

    println(res)
}