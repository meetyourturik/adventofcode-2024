import java.io.File
import kotlin.math.pow

fun main() {
    var REG_A = -1
    var REG_B = -1
    var REG_C = -1

    val PROGRAM = mutableListOf<Int>()
    var INSTRUCTION_POINTER = 0

    var OUTPUT = ""

    fun getCombo(op: Int): Int {
        return if (op in 0..3) {
            op
        } else if (op == 4) {
            REG_A
        } else if (op == 5) {
            REG_B
        } else if (op == 6) {
            REG_C
        } else {
            throw RuntimeException("unknown operator")
        }
    }

    val OPCODES = mapOf(
        0 to { op: Int -> // adv
            val denom = 2.0.pow(getCombo(op)).toInt()
            REG_A = REG_A / denom
            INSTRUCTION_POINTER += 2
        },
        6 to { op: Int -> // bdv
            val denom = 2.0.pow(getCombo(op)).toInt()
            REG_B = REG_A / denom
            INSTRUCTION_POINTER += 2
        },
        7 to { op: Int -> // cdv
            val denom = 2.0.pow(getCombo(op)).toInt()
            REG_C = REG_A / denom
            INSTRUCTION_POINTER += 2
        },
        1 to { op: Int -> // bxl
            REG_B = REG_B xor op
            INSTRUCTION_POINTER += 2
        },
        2 to { op: Int -> // bst
            REG_B = getCombo(op) % 8
            INSTRUCTION_POINTER += 2
        },
        3 to { op: Int -> // jnz
            if (REG_A != 0) {
                INSTRUCTION_POINTER = op
            } else {
                INSTRUCTION_POINTER += 2
            }
        },
        4 to { op: Int -> // bxc
            REG_B = REG_B xor REG_C
            INSTRUCTION_POINTER += 2
        },
        5 to { op: Int -> // out
            val v = getCombo(op) % 8
            if (!OUTPUT.isEmpty()) {
                OUTPUT += ','
            }
            OUTPUT += v
            INSTRUCTION_POINTER += 2
        }
    )

    val file = File("inputs/day17.txt")

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.startsWith("Register A:")) {
                REG_A = line.split(" ")[2].toInt()
            } else if (line.startsWith("Register B:")) {
                REG_B = line.split(" ")[2].toInt()
            } else if (line.startsWith("Register C:")) {
                REG_C = line.split(" ")[2].toInt()
            } else if (line.startsWith("Program: ")) {
                PROGRAM.addAll(line.substring("Program: ".length).split(",").map { it.toInt() })
            }
        }
    }

    while (INSTRUCTION_POINTER < PROGRAM.size) {
        val opcode = PROGRAM[INSTRUCTION_POINTER]
        val operand = PROGRAM[INSTRUCTION_POINTER + 1]
        OPCODES[opcode]!!.invoke(operand)
    }

    println(OUTPUT)
}