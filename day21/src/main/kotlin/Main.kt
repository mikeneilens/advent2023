import kotlin.math.pow

data class Position(val row:Long, val col:Long) {
    operator fun plus(other:Position) = Position(row + other.row, col + other.col)

    fun surroundingPositions(map:Set<Position>) = offsets.map{offset -> this + offset}.filter { it.isOn(map) }

    fun isOn(map:Set<Position>) = Position( row , col  ) in map

    companion object {
        val offsets = listOf(Position(row = 0, col = +1), Position(row = 0, col = -1), Position(row = -1, col = 0), Position(row = +1, col = 0))
    }
}

fun partOne(sampleData:List<String>) :Long {
    val positionOfS = sampleData.positionOfS()
    val map = sampleData.createMap()
    return map.walk(positionOfS, 64).toLong()
}

fun List<String>.positionOfS(n:Long = 0) = Position( n * size + size/2, n * size + size/2)

fun List<String>.createMap():Set<Position> =
    flatMapIndexed { row, string -> string.mapIndexed {col, char ->
            if (char in listOf('.','S')) Position(row.toLong(), col.toLong()) else null
        } }.filterNotNull().toSet()

fun  Set<Position>.walk(position:Position, maxSteps:Long) = walk(listOf(position),0, maxSteps, this).size

tailrec fun walk(positions:List<Position>, steps:Long, maxSteps:Long, map:Set<Position>):List<Position> {
    if (steps == maxSteps) return positions
    val newPositions = positions.flatMap { position -> position.surroundingPositions(map) }.distinct()
    return walk(newPositions, steps + 1, maxSteps, map)
}

fun partTwo(sampleData:List<String>):Long {
    val map = sampleData.createMap()
    val steps = 26501365L
    val size = sampleData.size.toLong()
    val gridWidth = steps / size - 1
    val positionOfS = sampleData.positionOfS()

    val odd = ((gridWidth / 2) * 2 + 1).toDouble().pow(2).toLong()
    val even = (((gridWidth + 1) / 2) * 2).toDouble().pow(2).toLong()

    return odd * map.oddSteps(size, positionOfS) +
            even * map.evenSteps(size, positionOfS) +
            map.stepsInCorners(size, positionOfS) +
            (gridWidth + 1) * map.stepsInSmallEdges(size) +
            gridWidth * map.stepsInLargeEdges(size)
}

fun Set<Position>.oddSteps(size: Long, s: Position) = walk(s, size * 2 + 1 )

fun Set<Position>.evenSteps(size: Long, s: Position) = walk(s, size * 2  )

fun  Set<Position>.stepsInCorners(size: Long, s: Position) =
        listOf(Position(size - 1, s.col), Position(s.row, 0), Position(0, s.col), Position(s.row, size - 1)).map { walk(it, size - 1) }.sum()

fun  Set<Position>.stepsInSmallEdges(size: Long) =
    listOf(Position(size - 1 , 0),Position(size - 1 , size - 1),Position(0 , 0),Position(0 , size -1 )).map{walk(it,size/2 - 1)}.sum()

fun  Set<Position>.stepsInLargeEdges(size: Long) =
    listOf(Position(size -1  , 0 ),Position(size -1  , size -1 ),Position(0  , 0 ),Position(0  , size -1  )).map{walk(it,size * 3 /2 - 1)}.sum()
