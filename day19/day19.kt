import java.io.File

fun walk(pattern: String, towels: List<String>): Boolean {
    if (pattern == "") {
        return true
    } else {
        for (color in towels) {
            if (pattern.startsWith(color)) {
                if (walk(pattern.substring(color.length, pattern.length), towels)) {
                    return true
                }
            }
        }
    }

    return false
}

fun walk2(pattern: String, towels: List<String>, memo: MutableMap<String, Long>): Long {
    if (memo.containsKey(pattern)) {
        return memo[pattern]!!
    }

    if (pattern.isEmpty()) {
        return 1
    }

    val result =
            towels
                    .filter { pattern.startsWith(it) }
                    .map { walk2(pattern.substring(it.length, pattern.length), towels, memo) }
                    .sum()

    memo[pattern] = result

    return result
}

fun part1(towels: List<String>, patterns: List<String>): Long {
    var res = 0L
    for (pattern in patterns) {
        if (walk(pattern, towels)) {
            res++
        }
    }
    return res
}

fun part2(towels: List<String>, patterns: List<String>): Long {
    val memo = mutableMapOf<String, Long>()

    return patterns.sumOf { walk2(it, towels, memo) }
}

fun main() {
    val input = File("./input.txt").readText().replace("\r\n", "\n").split("\n\n")
    val towels = input[0].replace(" ", "").split(",")
    val patterns = input[1].split("\n")

    println(part2(towels, patterns))
}
