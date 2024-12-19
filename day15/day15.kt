import java.io.File

class Pos(var x: Int, var y: Int) {
    fun add(other: Pos) {
        x += other.x
        y += other.y
    }

    operator fun plus(other: Pos): Pos {
        return Pos(this.x + other.x, this.y + other.y)
    }
    fun isIn(list: MutableList<Pos>): Boolean {
        return list.any { it.x == x && it.y == y }
    }
    override fun toString(): String {
        return "(" + x + "," + y + ")"
    }
    fun gps(): Int {
        return (y - 1) * 100 + (x - 1)
    }
}

fun part1(rawGrid: String, movements: String) {
    val boxes = mutableListOf<Pos>()
    val walls = mutableListOf<Pos>()
    var robot = Pos(0, 0)

    for ((y, line) in rawGrid.split("\n").withIndex()) {
        for ((x, tile) in line.toList().withIndex()) {
            val pos = Pos(x + 1, y + 1)

            if (tile == 'O') {
                boxes.add(pos)
            }
            if (tile == '@') {
                robot = pos
            }
            if (tile == '#') {
                walls.add(pos)
            }
        }
    }

    val dirMap = hashMapOf<Char, Pos>()

    dirMap.put('^', Pos(0, -1))

    dirMap.put('v', Pos(0, 1))

    dirMap.put('>', Pos(1, 0))

    dirMap.put('<', Pos(-1, 0))

    for (dir_char in movements.toList()) {
        val dir = dirMap.get(dir_char)
        if (dir != null) {
            var canPush = false
            val pushed = mutableListOf<Pos>()
            var newPos = robot + dir

            while (!canPush) {
                if (newPos.isIn(boxes)) {
                    val box = boxes.find { it.x == newPos.x && it.y == newPos.y }
                    if (box != null) {
                        pushed.add(box)
                    }
                } else if (newPos.isIn(walls)) {
                    break
                } else {
                    canPush = true
                }
                newPos.add(dir)
            }
            if (canPush) {
                robot.add(dir)
                for (box in pushed) {
                    box.add(dir)
                }
            }
        }
    }

    var text = ""

    for ((y, line) in rawGrid.split("\n").withIndex()) {
        for (x in line.toList().indices) {
            val p = Pos(x + 1, y + 1)

            if (walls.any { it.x == p.x && it.y == p.y }) {
                text += "#"
            } else if (boxes.any { it.x == p.x && it.y == p.y }) {
                text += "O"
            } else if (p.x == robot.x && p.y == robot.y) {
                text += "@"
            } else {
                text += "."
            }
        }
        text += "\n"
    }

    println(text)

    print(boxes.sumOf { it.gps() })
}

fun expend(line: String): String {
    return line.replace("#", "##").replace("O", "[]").replace(".", "..").replace("@", "@.")
}

class Box(var left: Int, var y: Int) {
    fun right(): Int {
        return left + 1
    }
    fun pushedBy(pos: Pos): Boolean {
        return (right() == pos.x || left == pos.x) && y == pos.y
    }
    fun add(pos: Pos) {
        left += pos.x
        y += pos.y
    }
    fun positions(dir: Pos): List<Pos> {
        return listOf(Pos(left + dir.x, y + dir.y), Pos(right() + dir.x, y + dir.y))
    }
    fun gps(): Int {
        return (y - 1) * 100 + (left - 1)
    }
    fun isIn(walls: MutableList<Pos>): Boolean {
        return positions(Pos(0, 0)).any { p -> p.isIn(walls) }
    }
}

fun part2(rawGrid: String, movements: String) {
    val boxes = mutableListOf<Box>()
    val walls = mutableListOf<Pos>()
    var robot = Pos(0, 0)

    val dirMap = hashMapOf<Char, Pos>()
    dirMap.put('^', Pos(0, -1))
    dirMap.put('v', Pos(0, 1))
    dirMap.put('>', Pos(1, 0))
    dirMap.put('<', Pos(-1, 0))

    for ((y, line) in rawGrid.split("\n").withIndex()) {
        for ((x, tile) in expend(line).toList().withIndex()) {
            val pos = Pos(x + 1, y + 1)

            if (tile == '[') {
                boxes.add(Box(x + 1, y + 1))
            }
            if (tile == '@') {
                robot = pos
            }
            if (tile == '#') {
                walls.add(pos)
            }
        }
    }
    for ((i, dir_char) in movements.toList().withIndex()) {
        val dir = dirMap.get(dir_char)

        if (dir != null) {
            var canPush = false
            val pushed = mutableSetOf<Box>()
            var newPos = mutableSetOf(robot + dir)
            val boxes2 = boxes.toMutableList()
            while (!canPush) {
                val box = boxes2.filter { b -> newPos.any { np -> b.pushedBy(np) } }
                if (pushed.any { it.isIn(walls) } || newPos.any { it.isIn(walls) }) {
                    break
                } else if (!box.isEmpty()) {
                    pushed.addAll(box)
                    boxes2.removeAll(box)
                    newPos = box.flatMap { it.positions(dir) }.toMutableSet()
                } else {
                    canPush = true
                }
            }
            if (canPush) {
                robot.add(dir)
                pushed.forEach { it.add(dir) }
            }
        }
    }
    var text = ""
    for ((y, line) in rawGrid.split("\n").withIndex()) {
        for (x in expend(line).toList().indices) {
            val p = Pos(x + 1, y + 1)

            if (walls.any { it.x == p.x && it.y == p.y }) {
                text += "#"
            } else if (boxes.any { (it.left == p.x || it.right() == p.x) && it.y == p.y }) {
                text += "O"
            } else if (p.x == robot.x && p.y == robot.y) {
                text += "@"
            } else {
                text += "."
            }
        }
        text += "\n"
    }
    print(text)
    for (box in boxes) {
        if (box.positions(Pos(0, 0)).any { p -> p.isIn(walls) }) {
            println("error" + box)
        }
    }
    println(boxes.sumOf { it.gps() })
}

fun main() {
    val (rawGrid, movements) = File("./input.txt").readText().replace("\r\n", "\n").split("\n\n")
    part2(rawGrid, movements)
}
