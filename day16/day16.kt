import java.io.File
import java.util.PriorityQueue

val dirs = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))

data class Tile(val tile: Char, val x: Int, val y: Int) {
    override fun toString(): String {
        return "(" + tile + " x=" + x + " y=" + y + ")"
    }
    fun neighbors(grid: List<Tile>): MutableList<Pair<Tile, Pair<Int, Int>>> {
        val neighbors = mutableListOf<Pair<Tile, Pair<Int, Int>>>()
        for (dir in dirs) {
            val neighbor = grid.find { it.x == dir.first + x && it.y == dir.second + y }
            if (neighbor != null) {
                neighbors.add(Pair(neighbor, dir))
            }
        }
        return neighbors
    }
}

fun printPath(path: List<Tile>, grid: List<Tile>) {
    val maxy = grid.map { it.y }.max()
    val maxx = grid.map { it.x }.max()
    var text = ""
    for (y in 0..maxy + 1) {
        for (x in 0..maxx + 1) {
            if (path.any { it.x == x && it.y == y }) {
                text += "O"
            } else if (grid.any { it.x == x && it.y == y }) {
                text += "."
            } else {
                text += "#"
            }
        }
        text += "\n"
    }
    print(text)
}

data class State(
        val step: Int,
        val tile: Tile,
        val score: Int,
        val lastDir: Pair<Int, Int>?,
        val path: MutableList<Tile>
)

fun part1(grid: List<Tile>): Int? {
    val start = grid.find { it.tile == 'S' }!!
    val end = grid.find { it.tile == 'E' }!!

    data class State(val tile: Tile, val score: Int, val lastDir: Pair<Int, Int>?)

    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    val visited = mutableSetOf<Tile>() // To track visited tiles
    val queue = PriorityQueue<State>(compareBy { it.score }) // Priority queue sorted by score
    queue.add(State(start, 0, null))

    while (queue.isNotEmpty()) {
        val current = queue.poll()

        // If we reach the end tile, return the score
        if (current.tile == end) {
            println("Minimum score: ${current.score}")
            return current.score
        }

        // Skip if this tile is already visited
        if (current.tile in visited) continue
        visited.add(current.tile)

        // Add neighbors to the queue
        for (dir in directions) {
            val neighborX = current.tile.x + dir.first
            val neighborY = current.tile.y + dir.second
            val neighbor = grid.find { it.x == neighborX && it.y == neighborY }

            if (neighbor != null && neighbor !in visited) {
                val newScore =
                        if (current.lastDir == dir) current.score + 1 else current.score + 1001
                queue.add(State(neighbor, newScore, dir))
            }
        }
    }
    return null
    println("No valid path found!")
}

fun part2(grid: List<Tile>) {
    val start = grid.find { it.tile == 'S' }
    val end = grid.find { it.tile == 'E' }

    if (start == null || end == null) {
        println("Start or End tile is missing!")
        return
    }

    data class State(
            val tile: Tile,
            val score: Int,
            val lastDir: Pair<Int, Int>?,
            val path: List<Tile>
    )
    val best = part1(grid)!!
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    val visited = hashMapOf<Pair<Tile, Pair<Int, Int>>, Int>() // To track visited tiles
    val queue = PriorityQueue<State>(compareBy { it.score }) // Priority queue sorted by score
    queue.add(State(start, 0, Pair(1, 0), listOf<Tile>()))
    val path = mutableSetOf<Tile>(start, end)
    while (queue.isNotEmpty()) {
        val current = queue.poll()

        // If we reach the end tile, return the score
        if (current.tile == end && current.score == best) {
            path.addAll(current.path)
        }

        // Add neighbors to the queue
        for (dir in directions) {
            val neighborX = current.tile.x + dir.first
            val neighborY = current.tile.y + dir.second
            val neighbor = grid.find { it.x == neighborX && it.y == neighborY }
            if (neighbor != null) {
                val key = Pair(neighbor, dir)
                val newScore =
                        if (current.lastDir == dir) current.score + 1 else current.score + 1001
                val sc = visited.getOrDefault(key, Int.MAX_VALUE)
                if (sc >= newScore) {
                    visited[key] = newScore
                    val newPath = current.path + neighbor
                    queue.add(State(neighbor, newScore, dir, newPath))
                }
            }
        }
    }

    println("${path.size}")
}

fun main() {
    val input =
            File("./input.txt").readLines().map { it.toList() }.withIndex().flatMap { (y, l) ->
                l.withIndex().map { (x, c) -> Tile(c, x, y) }
            }
    val grid = input.filter { t -> t.tile != '#' }

    // part1(grid)
    part2(grid)
}
