

import java.io.File
import kotlin.collections.getOrNull

fun walk(_tiles:List<List<Char>>,_gPos:Pair<Int,Int>):MutableSet<Pair<Int,Int>>{
    var tiles = _tiles
    var gPos = _gPos
    var dirIndex = 0
    var finished = false
    val dirs = listOf(Pair(0,-1),Pair(1,0),Pair(0,1),Pair(-1,0))
    val visited = mutableSetOf<Pair<Int,Int>>(gPos)
    
    while(!finished){
        val dir = dirs[dirIndex]
        
        val newPos = Pair(gPos.first + dir.first,gPos.second+dir.second)
        val newTile = tiles.getOrNull(newPos.second)?.getOrNull(newPos.first)
        if(newTile == '.' ){
            visited.add(newPos)
            gPos = newPos
        }
        if(newTile == '#'){
            dirIndex = (dirIndex+1)%dirs.size
        }
        if(newTile == null){
            finished = true
        }
    }
    return visited
}

fun walk2(_tiles:List<List<Char>>,_gPos:Pair<Int,Int>):Boolean{
    var tiles = _tiles
    var gPos = _gPos
    var dirIndex = 0
    val dirs = listOf(Pair(0,-1),Pair(1,0),Pair(0,1),Pair(-1,0))
    val visited = mutableSetOf<Pair<Pair<Int,Int>,Pair<Int,Int>>>(Pair(gPos,dirs[dirIndex]))


    while(true){
        val dir = dirs[dirIndex]
        
        val newPos = Pair(gPos.first + dir.first,gPos.second+dir.second)
        val newTile = tiles.getOrNull(newPos.second)?.getOrNull(newPos.first)
        if(newTile == '.' ){
            val pair = Pair(newPos,dir)
            if(pair in visited){
                return true
            }
            visited.add(Pair(newPos,dir))
            gPos = newPos
            
        }
        if(newTile == '#'){
            dirIndex = (dirIndex+1)%dirs.size
        }
        if(newTile == null){
            return false
        }
    }
}

fun main(){
    val input = File("./input.txt").readLines().map{it.toList()}
    val gy = input.indexOfFirst { '^' in it }
    val gx = input[gy].indexOf('^')
    var gPos = Pair(gx,gy)
    val tiles = input.map{it.map{ if(it == '^') '.' else it}}

    var count = 0
    val visited = walk(tiles,gPos) 

    for(pos in visited){
        val newTiles = tiles.toMutableList()
        val newLine = newTiles[pos.second].toMutableList()
        newLine[pos.first] = '#'
        newTiles[pos.second] = newLine
        if(walk2(newTiles.toList(),gPos)){
            count+= 1
        }
    }
    println(count)
}