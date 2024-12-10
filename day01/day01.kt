
import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val lines = File("./input.txt").readLines().map { it.split("   ").map { nb -> nb.toLong() } }
    val left = lines.map { it[0] }.sorted()
    val right = lines.map { it[1] }.sorted()
    val res1 =
        left.zip(right).fold(0L) { acc, nb ->
            val diff = nb.second - nb.first
            acc + diff.absoluteValue
        }
    val res2 = left.map { l -> l * right.count { r -> r == l } }.sum()
    println(res1.toBigDecimal().toPlainString())
    println(res2.toBigDecimal().toPlainString())
}
