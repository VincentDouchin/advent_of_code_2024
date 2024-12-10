
import java.io.File
import java.math.BigDecimal

fun part1(input:String):BigDecimal{
    val regex = Regex("""mul\((\d+),(\d+)\)""")
    val matches = regex.findAll(input).map { matchResult ->
        matchResult.groupValues[1].toInt() to matchResult.groupValues[2].toInt()
    }.toList()
    val res = matches.fold(0){acc,nb->acc+(nb.first*nb.second)}
    return res.toBigDecimal()
}

fun part2(input:String):BigDecimal{
    val regex = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")
    val matches = regex.findAll(input).toList()
    var enabled = true
    var res = 0

    for (match in matches){
        when(match.value){
            "don't()" -> enabled = false
            "do()" -> enabled = true
            else -> if(enabled){res += match.groupValues[1].toInt() * match.groupValues[2].toInt() }
        }
    }
    return res.toBigDecimal()
}

fun main() {
    val line = File("./input.txt").readText()
    println(part1(line))
    println(part2(line))
}
