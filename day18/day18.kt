import java.io.File
import java.util.PriorityQueue

val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

fun printGrid(bytes: List<Pair<Int, Int>>, path: List<Pair<Int, Int>>, size: Int) {
    var text = ""
    for (y in 0..size) {
        for (x in 0..size) {
            if (path.any { it.first == x && it.second == y }) {
                text += "O"
            } else if (bytes.any { it.first == x && it.second == y }) {
                text += "#"
            } else {
                text += "."
            }
        }
        text += "\n"
    }
    File("./grid.txt").writeText(text)
    println(text)
}

fun part1(input: List<Pair<Int, Int>>, seconds: Int): List<Pair<Int, Int>> {
    val bytes = input.subList(0, seconds)
    return bytes
}

fun findPath(bytes: List<Pair<Int, Int>>, size: Int): List<Pair<Int, Int>>? {
    data class State(val coord: Pair<Int, Int>, val path: List<Pair<Int, Int>>, val dist: Int)
    val queue = PriorityQueue<State>(compareBy { it.dist })
    queue.add(State(Pair(0, 0), listOf<Pair<Int, Int>>(Pair(0, 0)), 0))
    var i = 0
    val visited = mutableSetOf<Pair<Int, Int>>()
    while (queue.isNotEmpty()) {
        val cur = queue.poll()
        // i++
        // if (i % 2000 == 0) {
        //     println("${queue.size} ${cur.dist}")
        // }
        if (cur.coord in visited) continue

        visited.add(cur.coord)

        if (cur.coord == Pair(size, size)) {
            return cur.path
        }
        for (dir in directions) {
            val newCoord = Pair(cur.coord.first + dir.first, cur.coord.second + dir.second)
            if (newCoord !in bytes &&
                            newCoord.first <= size &&
                            newCoord.second <= size &&
                            newCoord.first >= 0 &&
                            newCoord.second >= 0
            ) {
                val newPath = cur.path + newCoord
                val newDist = cur.dist + 1
                queue.add(State(newCoord, newPath, newDist))
            }
        }
    }
    return null
}

fun part2(input: List<Pair<Int, Int>>, size: Int): Pair<Int, Int>? {
    for (i in input.indices) {
        val bytes = input.subList(0, i + 1)
        val path = findPath(bytes, size)
		println(i)
        if (path == null) {
            return input[i]
        } 
    }
    return null
}

fun main() {
    val input =
            File("./input.txt").readLines().map {
                val nbs = it.split(",").map { nb -> nb.toInt() }
                Pair(nbs[0], nbs[1])
            }
    // val bytes = part1(input, 1024)
    // val path = findPath(bytes, 70)!!
    // printGrid(bytes, path, 70)

    // println(path.size - 1)
    val coord = part2(input, 70)
    println(coord)
}
