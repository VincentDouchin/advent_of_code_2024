import java.io.File
import java.util.Collections

class Fragment(var id: Int?, var size: Int) {}

fun checksum(file: MutableList<String>): Long {
    return file.withIndex().filter { (i, c) -> c != "." }.sumOf { (i, nb) ->
        i * nb.toString().toLong()
    }
}

fun reorder(file: MutableList<String>): MutableList<String> {

    var first: Int?
    var last: Int?
    var finished = false
    while (!finished) {
        first = file.withIndex().find { (i, c) -> c == "." }!!.index
        last = file.withIndex().findLast { (i, c) -> c != "." }!!.index

        finished = first > last
        if (!finished) {
            Collections.swap(file, first, last)
        }
    }
    return file
}

fun transform(input: String): MutableList<String> {
    var id = 0
    var res = mutableListOf<String>()
    var isFile = true
    for (char in input.toList()) {
        val nb = char.toString().toInt()
        for (i in IntRange(1, nb)) {
            if (isFile) {
                res.add(id.toString())
            } else {
                res.add(".")
            }
        }
        if (isFile) {
            id += 1
        }
        isFile = !isFile
    }
    return res
}

fun transform2(input: String): MutableList<Fragment> {
    var id = 0
    var res = mutableListOf<Fragment>()
    var isFile = true
    for (char in input.toList()) {
        val size = char.toString().toInt()

        val fr =
                if (isFile) {
                    Fragment(id, size)
                } else {
                    Fragment(null, size)
                }
        res.add(fr)
        if (isFile) {
            id += 1
        }
        isFile = !isFile
    }
    return res
}

fun reorder2(input: MutableList<Fragment>): MutableList<Fragment> {
    var file = input
    for (fragment in file.toList().asReversed()) {
        if (fragment.id != null) {
            val i = file.indexOf(fragment)
            val freeSpace =
                    file.withIndex().find { (j, f) ->
                        f.id == null && f.size >= fragment.size && i > j
                    }

            if (freeSpace != null) {
                val (j, free) = freeSpace

                free.size -= fragment.size
                file.add(j, Fragment(fragment.id, fragment.size))
                fragment.id = null
            }
        }
    }
    return file
}

fun joinFragments(file: MutableList<Fragment>): MutableList<String> {
    val res = mutableListOf<String>()
    for (fragment in file) {
        for (i in IntRange(1, fragment.size)) {
            if (fragment.id != null) {
                res.add(fragment.id.toString())
            } else {
                res.add(".")
            }
        }
    }
    return res
}

fun main() {
    val file = File("./input.txt").readText()
    val res1 = checksum(reorder(transform(file)))
    println(res1)
    val res2 = checksum(joinFragments(reorder2(transform2(file))))

    println(res2)
}
