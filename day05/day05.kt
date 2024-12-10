import java.io.File

fun isValidOrdering(rules: List<List<Int>>, order: List<Int>): Boolean {
    var valid = true
    for (rule in rules) {
        if (order.containsAll(rule)) {
            val (i1, i2) = rule.map { order.indexOf(it) }
            valid = valid && i1 < i2
        }
    }
    return valid
}

fun getMiddle(order: List<Int>): Int {
    return order[order.size / 2]
}

fun orderPages(rules: List<List<Int>>, order: List<Int>): List<Int> {
    var pages = order.toMutableList()

    for ((i, nb) in pages.withIndex()) {
        for (rule in rules) {
            if (rule[0] == nb && rule[1] in pages.slice(0..i)) {
                pages = pages.filter { rule[1] != it }.toMutableList()
                pages.add(i, rule[1])
            }
        }
    }
    if (isValidOrdering(rules, pages)) {
        return pages
    }
    return orderPages(rules, pages)
}

fun main() {
    val input = File("./input.txt").readText()
    val (rules, pages) =
            input.split("\n\n").map {
                it.split("\n").map { it.split("[|,]".toRegex()).map { it.toInt() } }
            }

    val res1 = pages.filter { isValidOrdering(rules, it) }.sumOf { getMiddle(it) }
    val res2 =
            pages.filter { !isValidOrdering(rules, it) }.map { orderPages(rules, it) }.sumOf {
                getMiddle(it)
            }

    println(res1)
    println(res2)
}
