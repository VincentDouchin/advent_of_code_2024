

import java.io.File
fun main(){
    val input = File("./input.txt").readLines().map{it.toList()}
    val antinodes = mutableSetOf<Pair<Int,Int>>()
    val tFrequencyAntinodes = mutableSetOf<Pair<Int,Int>>()
    val positions = mutableMapOf<Char, MutableList<Pair<Int,Int>>>()

    for (y in input.indices){
        for (x in input.indices){
            val char = input[y][x]
            if(char != '.'){
                val coords = Pair(x,y)
                tFrequencyAntinodes.add(Pair(x,y))
                if(positions.get(char) == null){
                   positions.set(char, mutableListOf(coords)) 
                }else {
                   positions.get(char)?.add(coords)
                }                
            }
        }
    }
    for (coords in positions.values){
        for (coord in coords){
            for(othercoord in coords){
                if(othercoord != coord){
                    val xO = othercoord.first - coord.first   
                    val yO = othercoord.second - coord.second  
                    val x = coord.first - xO
                    val y = coord.second - yO
                    val isInBounds = x >= 0 && x <input.first().size && y >= 0 && y < input.size
                    // val isOtherPosition = Pair(x,y) in positions.values.flatten()
                    if(isInBounds ){
                        antinodes.add(Pair(x,y))
                    }
                }
            }

        }
    }
    for (coords in positions.values){
        for (coord in coords){
            for(othercoord in coords){
                if(othercoord != coord){
                    val xO = othercoord.first - coord.first   
                    val yO = othercoord.second - coord.second  
                    var x = coord.first - xO
                    var y = coord.second - yO
                    
                    while(x >= 0 && x <input.first().size && y >= 0 && y < input.size){

                        tFrequencyAntinodes.add(Pair(x,y))
                        x -= xO
                        y -= yO
                    }
                    var x1 = x
                    var y1 = y
                    while(x1 >= 0 && x1 <input.first().size && y1 >= 0 && y1 < input.size){

                        tFrequencyAntinodes.add(Pair(x1,y1))
                        x1 += xO
                        y1 += yO
                    }
                    
                }
            }

        }
    }

    var l = mutableListOf<String>()
    for(y in input.indices){
        var str = ""
        for (x in input[y].indices){
            if(Pair(x,y) in tFrequencyAntinodes){
                str += '#'
            }else{
                str += input[y][x]
            }
        }
        l.add(str)
        println(str)
    }
    println(tFrequencyAntinodes.size )
}