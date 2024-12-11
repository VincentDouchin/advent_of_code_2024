import java.io.File
import kotlin.collections.hashMapOf

fun blink(stone: Long): List<Long> {
    if (stone == 0L) {
        return listOf(1)
    } else if (stone.toString().length % 2 == 0) {
        val mid = stone.toString().length / 2
        val firstHalf = stone.toString().substring(0, mid)
        val secondHalf = stone.toString().substring(mid)
        return listOf(firstHalf.toLong(), secondHalf.toLong())
    } else {
        return listOf(stone * 2024)
    }
}

fun part1(input: List<Long>, amount: Long): List<Long> {
    var stones = input
    for (_i in 1..amount) {
        stones =
                stones.flatMap { stone ->
                    if (stone == 0L) {
                        listOf(1)
                    } else if (stone.toString().length % 2 == 0) {
                        val mid = stone.toString().length / 2
                        val firstHalf = stone.toString().substring(0, mid)
                        val secondHalf = stone.toString().substring(mid)
                        listOf(firstHalf.toLong(), secondHalf.toLong())
                    } else {
                        listOf(stone * 2024)
                    }
                }
    }

    return stones
}

fun assign_stones(stones: List<Long>): HashMap<Long, Long> {
    var stone_map = hashMapOf<Long, Long>()
    for (stone in stones) {
        val s = stone_map.getOrDefault(stone, 0L)
        stone_map.set(stone, s + 1L)
    }
    return stone_map
}

fun part2(input: List<Long>, amount: Long): Long {
    var stone_map = assign_stones(input)

    for (_i in 1..amount) {
        val new_map = hashMapOf<Long, Long>()

        for ((stone, quantity) in stone_map.entries) {

            blink(stone).forEach { new_map.set(it, quantity + new_map.getOrDefault(it, 0)) }
        }

        stone_map = new_map
    }
    var res = 0L
    for (q in stone_map.values) {
        res += q
    }
    return res
}

fun main() {
    val input = File("./input.txt").readText().split(' ').map { it.toLong() }
    val res1 = part1(input, 25).size
    println(res1)
    // 55312

    val res2 = part2(input, 75)
    println(res2)
}
