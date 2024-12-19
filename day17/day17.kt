import java.io.File
import kotlin.math.pow

fun run(a: Long, program: List<Long>): List<Long> {
    var A = a
    var B = 0L
    var C = 0L
    var pointer = 0
    val output = mutableListOf<Long>()
    while (pointer < program.size) {

        val opcode = program[pointer]
        val operand = program[pointer + 1]
        val combo =
                when (operand) {
                    4L -> A
                    5L -> B
                    6L -> C
                    else -> operand
                }
        when (opcode) {
            0L -> {
                val nb = (A / (2.toDouble().pow(combo.toDouble()))).toLong()
                A = nb
            }
            1L -> {
                val nb = B.toLong().xor(operand.toLong()).toLong()
                B = nb
            }
            2L -> {
                val nb = combo % 8
                B = nb
            }
            3L -> {
                if (A != 0L) {
                    pointer = operand.toInt() - 2
                }
            }
            4L -> {
                val nb = B.toLong().xor(C.toLong()).toLong()
                B = nb
            }
            5L -> {
                val nb = combo % 8
                output.add(nb)
            }
            6L -> {
                val nb = (A / (2.toDouble().pow(combo.toDouble()))).toLong()
                B = nb
            }
            7L -> {
                val nb = (A / (2.toDouble().pow(combo.toDouble()))).toLong()
                C = nb
            }
        }
        pointer += 2
    }
    // prLongln("A:${A} B:${B} C:${C}")
    return output
}

private fun part2(program: List<Long>, target: List<Long>): Long {
    var A =
            if (target.size == 1) {
                0
            } else {
                8 * part2(program, target.slice(1..target.size - 1))
            }

    while (run(A, program) != target) {
        A++
    }

    println(A)
    return A
}

fun main() {
    val input = File("./input.txt").readText().replace("\r\n", "\n").split("\n\n")
    val registers = input[0].split("\n").map { it.split(": ")[1].toLong() }
    val program = input[1].split(" ")[1].split(",").map { it.toLong() }

    // run(listOf(0, 0, 9), listOf(2,6))
    // run(listOf(10, 0, 9), listOf(5,0,5,1,5,4))
    // run(listOf(2024, 0, 0), listOf(0, 1, 5, 4, 3, 0))
    // run(listOf(0, 29, 0), listOf(1, 7))
    // run(listOf(0, 2024, 43690), listOf(4,0))
    // run(listOf(729, 0, 0), listOf(0,1,5,4,3,0))
    // println(run(107416732707226, program))
    // part2(listOf(2024, 0, 0), listOf(0, 3, 5, 4, 3, 0))
    part2(program, program)
}

// [47792830,0,47792830]
// [0]
// 2,4,*1,5,*7,5,*1,6,*4,3,5,5,0,3,3,0
