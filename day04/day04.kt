
import java.io.File
import kotlin.collections.getOrNull

fun main() {
    val input = File("./input.txt").readLines().map { it.toList() }
    val positions = listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1), Pair(1, 1), Pair(-1, 1), Pair(-1, -1), Pair(1, -1))
    val positionsCross = listOf(listOf(Pair(1, 1), Pair(-1, -1)), listOf(Pair(-1, 1), Pair(1, -1)))
    val letters = listOf('X', 'M', 'A', 'S')
    var count1 = 0
    var count2 = 0

    fun checkXMAS(
        lastPosition: Pair<Int, Int>,
        letterIndex: Int,
        dir: Pair<Int, Int>,
    ) {
        val (x, y) = lastPosition
        val letter = letters[letterIndex]
        if (input.getOrNull(y)?.getOrNull(x) == letter) {
            if (letterIndex == letters.lastIndex) {
                count1 += 1
            } else {
                checkXMAS(Pair(x + dir.first, y + dir.second), letterIndex + 1, dir)
            }
        }
    }

    fun checkMAS(position: Pair<Int, Int>) {
        val (x, y) = position
        if (input.getOrNull(y)?.getOrNull(x) == 'A') {
            val isX =
                positionsCross.all { pos ->
                    val lettersAtCrosses = pos.map { (x1, y1) -> input.getOrNull(y + y1)?.getOrNull(x + x1) }

                    'M' in lettersAtCrosses && 'S' in lettersAtCrosses
                }
            if (isX) {
                count2 += 1
            }
        }
    }
    for (y in input.indices) {
        val line = input[y]
        for (x in line.indices) {
            checkMAS(Pair(x, y))
            for (dir in positions) {
                checkXMAS(Pair(x, y), 0, dir)
            }
        }
    }
    println(count2)
}
