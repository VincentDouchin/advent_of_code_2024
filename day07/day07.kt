

import java.io.File

fun tryCombinations1(op:Pair<Long,List<Long>>):Long{
    if(isValidOperation1(op.first, op.second.toMutableList(), 0, '+')||isValidOperation1(op.first, op.second.toMutableList(), 0, '*')){
        return op.first
    }
    return 0
}
fun tryCombinations2(op:Pair<Long,List<Long>>):Long{
    if(isValidOperation2(op.first, op.second.toMutableList(), 0, '+')||isValidOperation2(op.first, op.second.toMutableList(), 0, '*')||isValidOperation2(op.first, op.second.toMutableList(), 0, '|')){
        return op.first
    }
    return 0
}


fun isValidOperation1(target:Long,nbs:MutableList<Long>, last:Long, operation:Char):Boolean{
    
    if(nbs.size == 0){
        return last == target
    }
    val first = nbs.removeFirst()
    val new = if(operation == '+') {
        last + first
    }else {
        last * first
    }
    return isValidOperation1(target, nbs.toMutableList(),new,'+')||isValidOperation1(target, nbs.toMutableList(),new,'*')
}
fun isValidOperation2(target:Long,nbs:MutableList<Long>, last:Long, operation:Char):Boolean{
    
    if(nbs.size == 0){
        return last == target
    }
    val first = nbs.removeFirst()
    val new = if(operation == '+') {
        last + first
    }else if(operation == '*'){
        last * first
    }else {
        (last.toString()+first.toString()).toLong()
    }
    return isValidOperation2(target, nbs.toMutableList(),new,'+')||isValidOperation2(target, nbs.toMutableList(),new,'*')||isValidOperation2(target, nbs.toMutableList(),new,'|')
}

fun main(){
    val input = File("./input.txt").readLines().map{ l ->
        val parts = l.split(':')
        Pair(parts[0].toLong(), parts[1].trim().split(' ').map{c->c.toLong()})
    }
    val res = input.sumOf {tryCombinations2(it)}
    println(res)

}