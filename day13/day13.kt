import java.io.File

data class Vec2(val x: Long, val y: Long) {
    fun equals(other: Vec2): Boolean {
        return x == other.x && y == other.y
    }
    fun add(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }
    fun addScalar(nb: Long): Vec2 {
        return Vec2(x + nb, y + nb)
    }
    fun superior(other: Vec2): Boolean {
        return x > other.x || y > other.y
    }
    fun mul(nb: Long): Vec2 {
        return Vec2(x * nb, y * nb)
    }
}

fun part1(input: List<ClawMachine>): Long {
    var token = 0L

    for (machine in input) {
        var price: Long? = null
        for (a in 1..100) {
            val posA = machine.a.mul(a.toLong())
            for (b in 1..100) {
                val pos = machine.b.mul(b.toLong()).add(posA)

                if (pos.superior(machine.prize)) {
                    break
                }
                if (pos.equals(machine.prize)) {
                    val cost = a * 3L + b
                    if (price == null) {
                        price = cost
                    } else {
                        price = Math.min(price, cost)
                    }
                }
            }
        }
        println(price)
        if (price != null) {
            token += price
        }
    }
    return token
}

fun part2(input: List<ClawMachine>): Long {
    val machines = input.map { m -> ClawMachine(m.a, m.b, m.prize.addScalar(10000000000000)) }
    var token = 0L
    for (m in machines) {
        val a = (m.prize.x * m.b.y - m.prize.y * m.b.x) / (m.b.y * m.a.x - m.b.x * m.a.y)
        val b = (m.prize.x * m.a.y - m.prize.y * m.a.x) / (m.a.y * m.b.x - m.b.y * m.a.x)

        if (m.a.x * a + m.b.x * b == m.prize.x && m.a.y * a + m.b.y * b == m.prize.y) {
            token += 3 * a + b
        }
    }
    return token
}

data class ClawMachine(val a: Vec2, val b: Vec2, val prize: Vec2) {}

fun main() {
    val input =
            File("./input.txt")
                    .readLines()
                    .filter { it != "" }
                    .windowed(3, 3)
                    .map { it.joinToString("") }
                    .map {
                        val regex = Regex("\\d+")

                        val numbers = regex.findAll(it).map { it.value.toLong() }.toList()
                        ClawMachine(
                                Vec2(numbers[0], numbers[1]),
                                Vec2(numbers[2], numbers[3]),
                                Vec2(numbers[4], numbers[5])
                        )
                    }

    println(part2(input))
}
