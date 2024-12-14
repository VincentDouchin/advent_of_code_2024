import java.io.File

fun part1(robots: List<List<Int>>, gridw: Int, gridh: Int, seconds: Int): Int {
    var tl = 0
    var tr = 0
    var bl = 0
    var br = 0
    var hw = gridw / 2 + 1
    var hh = gridh / 2 + 1
    val positions = mutableListOf<Pair<Int, Int>>()
    println("" + gridw / 2 + " " + gridh / 2)
    for ((px, py, vx, vy) in robots) {
        var x = (px + (vx * seconds)) % gridw
        var y = (py + (vy * seconds)) % gridh
        if (x < 0) {
            x = x + gridw
        }
        if (y < 0) {
            y = y + gridh
        }
        x += 1
        y += 1

        val l = x < hw
        val r = x > hw
        val b = y < hh
        val t = y > hh
        if (t && l) {
            tl += 1
        }
        if (t && r) {
            tr += 1
        }
        if (b && l) {
            bl += 1
        }
        if (b && r) {
            br += 1
        }
        positions.add(Pair(x, y))
    }
    println("" + tl + " " + tr + " " + bl + " " + br)
    for (y in 1..gridh) {
        for (x in 1..gridw) {
            if (x == hw || y == hh) {
                print(" ")
            } else if (Pair(x, y) in positions) {
                print("X")
            } else {
                print(".")
            }
        }
        print("\n")
    }
    return tl * tr * br * bl
}

fun part2(robots: List<List<Int>>, gridw: Int, gridh: Int, seconds: Int): Int? {
    for (i in 0..seconds) {
        val positions = mutableListOf<Pair<Int, Int>>()
        for ((px, py, vx, vy) in robots) {
            var x = (px + (vx * i)) % gridw
            var y = (py + (vy * i)) % gridh
            if (x < 0) {
                x = x + gridw
            }
            if (y < 0) {
                y = y + gridh
            }
            x += 1
            y += 1
            var coord = Pair(x, y)

            positions.add(coord)
        }
        if (positions.any { coord ->
                    (1..15).all { nb ->
                        positions.any { c2 ->
                            c2.first == coord.first + nb && c2.second == coord.second
                        }
                    }
                }
        ) {
            var t = ""

            for (y in 1..gridh) {
                for (x in 1..gridw) {
                    if (Pair(x, y) in positions) {
                        t += "X"
                    } else {
                        t += "."
                    }
                }
                t += "\n"
            }

            File("./res.txt").writeText(t)
            return i
        }
    }
    return null
}

fun main() {
    val robots =
            File("./input.txt").readLines().map {
                it.replace(" ", ",").replace("p=", "").replace("v=", "").split(",").map {
                    it.toInt()
                }
            }
    println(part1(robots, 101, 103, 10_000))
    println(part2(robots, 101, 103, 10_000))
}
