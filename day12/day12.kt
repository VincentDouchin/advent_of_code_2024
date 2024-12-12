import java.io.File
import kotlin.collections.withIndex

data class Garden(val area: Int, val perimeter: Int, var points: List<Point>) {
    operator fun plus(otherGarden: Garden): Garden {
        val newPoints = points.toMutableList()
        newPoints.addAll(otherGarden.points)
        return Garden(
                area + otherGarden.area,
                perimeter + otherGarden.perimeter,
                newPoints.toList()
        )
    }

    fun lines(): List<List<Point>> {
        return points.map { it.y }.toSet().map { y -> points.filter { it.y == y } }
    }
    fun columns(): List<List<Point>> {
        return points.map { it.x }.toSet().map { x -> points.filter { it.x == x } }
    }

    fun findPoint(p: Point): (Int, Int) -> Point? {
        return fun(x: Int, y: Int): Point? {
            return points.find { it.x == (p.x + x) && it.y == (p.y + y) }
        }
    }

    fun sides2(): Int {
        var ls = 0
        var rs = 0
        var ts = 0
        var bs = 0
        for (p in points) {
            val f = findPoint(p)
            val tr = f(1, -1)
            val bl = f(-1, 1)
            val l = f(-1, 0)
            val r = f(1, 0)
            val t = f(0, -1)
            val b = f(0, 1)
            if (l == null && t == null) {
                ls += 1
                ts += 1
            }
            if (r == null && b == null) {
                rs += 1
                bs += 1
            }
            if (t != null && r != null && tr == null) {
                bs += 1
                rs += 1
            }
            if (b != null && l != null && bl == null) {
                ts += 1
                ls += 1
            }
        }

        return rs + ls + ts + bs
    }
}

data class Point(val x: Int, val y: Int, val tile: Char) {}

fun main() {
    val grid =
            File("./input.txt").readLines().withIndex().map { (y, l) ->
                l.withIndex().map { (x, t) -> Point(x, y, t) }
            }
    val visited = mutableSetOf<Point>()

    val dirs = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0))

    fun walk1(point: Point): Garden {
        if (point in visited) {
            return Garden(0, 0, listOf())
        }
        visited.add(point)
        val next =
                dirs.map { (xx, yy) ->
                    val x1 = point.x + xx
                    val y1 = point.y + yy
                    if (x1 < 0 || y1 < 0 || x1 >= grid.first().size || y1 >= grid.size) {
                        null
                    } else {
                        grid[y1][x1]
                    }
                }
        val perimeter = next.count() { point.tile != it?.tile }
        var g = Garden(1, perimeter, listOf(point))
        for (n in next.filterNotNull().filter { it.tile == point.tile }) {
            g += walk1(n)
        }
        return g
    }
    var total = 0

    for (line in grid) {
        for (p in line) {
            if (p !in visited) {
                val garden = walk1(p)
                println("" + garden.sides2() + " " + garden.area)
                total += garden.sides2() * garden.area
            }
        }
    }
    println(total)
}
