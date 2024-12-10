
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.text.toFloat

fun isSafe(line: List<Float>): Boolean {
    var valid = true
    var sign: Float? = null
    for (i in (0..(line.size - 2))) {
        var diff = (line[i] - line[i + 1])
        if (sign == null) {
            sign = diff.sign
        } else {
            valid = valid && sign == diff.sign
        }
        valid = valid && diff.absoluteValue >= 1 && diff.absoluteValue <= 3
    }
    return valid
}

fun checkAllCombinations(line: List<Float>): Boolean =
    line.mapIndexed { i, _ -> line.filterIndexed { j, _ -> i != j } }.any { isSafe(it) }

fun main() {
    val lines = File("./input.txt").readLines().map { it.split(' ').map { c -> c.toFloat() } }
    val resPart1 = lines.count { isSafe(it) }
    val resPart2 = lines.count { checkAllCombinations(it) }
    println(resPart1)
    println(resPart2)
}
