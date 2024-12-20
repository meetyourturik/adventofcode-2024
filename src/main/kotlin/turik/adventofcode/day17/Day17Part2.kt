import java.io.File
import kotlin.math.pow

fun main() {
    var REG_A: Long = -1L
    var REG_B: Long = -1L
    var REG_C: Long = -1L

    val PROGRAM = mutableListOf<Int>()
    var INSTRUCTION_POINTER = 0

    var OUTPUT = ""

    fun getCombo(op: Int): Long {
        return if (op in 0..3) {
            op.toLong()
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
            val denom = 2.0.pow(getCombo(op).toDouble()).toLong()
            REG_A = REG_A / denom
            INSTRUCTION_POINTER += 2
        },
        6 to { op: Int -> // bdv
            val denom = 2.0.pow(getCombo(op).toDouble()).toLong()
            REG_B = REG_A / denom
            INSTRUCTION_POINTER += 2
        },
        7 to { op: Int -> // cdv
            val denom = 2.0.pow(getCombo(op).toDouble()).toLong()
            REG_C = REG_A / denom
            INSTRUCTION_POINTER += 2
        },
        1 to { op: Int -> // bxl
            REG_B = REG_B xor op.toLong()
            INSTRUCTION_POINTER += 2
        },
        2 to { op: Int -> // bst
            REG_B = getCombo(op) % 8
            INSTRUCTION_POINTER += 2
        },
        3 to { op: Int -> // jnz
            if (REG_A != 0L) {
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

    fun runIter() {
        while (INSTRUCTION_POINTER < PROGRAM.size - 2) { // smaller size to skip cycle
            val opcode = PROGRAM[INSTRUCTION_POINTER]
            val operand = PROGRAM[INSTRUCTION_POINTER + 1]
            OPCODES[opcode]!!.invoke(operand)
        }
    }

    val file = File("inputs/day17.txt")

    file.bufferedReader().use { reader ->
        reader.lines().forEach { line ->
            if (line.startsWith("Program: ")) {
                val program = line.substring("Program: ".length).split(",").map { it.toInt() }
                PROGRAM.addAll(program)
            }
        }
    }

    val OPTIONS = mutableListOf<Long>()

    fun testOut(out_pos: Int, a_init: Long) {
        for (i in 0..7) {
            REG_A = a_init + i
            val regatmp = REG_A
            REG_B = 0
            REG_C = 0
            INSTRUCTION_POINTER = 0
            OUTPUT = ""
            runIter()
            if (OUTPUT.toInt() == PROGRAM[out_pos]) {
                if (out_pos == 0) {
                    OPTIONS.add(regatmp)
                } else {
                    testOut(out_pos - 1, regatmp*8)
                }
            }
        }
    }

    testOut(PROGRAM.size - 1, 0)
    println(OPTIONS.minOrNull())
}