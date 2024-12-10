import java.io.File

fun main() {
    val grid = File("./input.txt").readLines().map { it.toList() }

    fun can_walk_path(last: Char, x: Int, y: Int): Int? {
        if (x < 0 || x >= grid[0].size || y < 0 || y >= grid.size) {
            return null
        }

        val new = grid[y][x]
        if (new != '.' && (last.toString().toInt() + 1) == new.toString().toInt()) {
            return new.toString().toInt()
        } else {
            return null
        }
    }

    fun walk_path(x: Int, y: Int): MutableSet<Pair<Int, Int>> {
        val last = grid[y][x]
        var count = mutableSetOf<Pair<Int, Int>>()
        for ((xx, yy) in listOf(Pair(0, 1), Pair(1, 0), Pair(-1, 0), Pair(0, -1))) {
            val path = can_walk_path(last, x + xx, y + yy)
            if (path == 9) {
                count += Pair(x + xx, y + yy)
            } else if (path != null) {
                count += walk_path(x + xx, y + yy)
            }
        }

        return count
    }
    fun walk_path2(x: Int, y: Int): Int {
        val last = grid[y][x]
        var count = 0
        for ((xx, yy) in listOf(Pair(0, 1), Pair(1, 0), Pair(-1, 0), Pair(0, -1))) {
            val path = can_walk_path(last, x + xx, y + yy)
            if (path == 9) {
                count += 1
            } else if (path != null) {
                count += walk_path2(x + xx, y + yy)
            }
        }

        return count
    }
    var trail_heads = 0
    var paths = 0
    for ((y, line) in grid.withIndex()) {
        for ((x, tile) in line.withIndex()) {
            if (tile == '0') {
                trail_heads += walk_path(x, y).size
                paths += walk_path2(x, y)
            }
        }
    }

    println(trail_heads)
    println(paths)
}
