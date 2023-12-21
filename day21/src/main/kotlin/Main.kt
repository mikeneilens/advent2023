
data class Position(val row:Int, val col:Int) {
    operator fun plus(other:Position) = Position(row + other.row, col + other.col)
    operator fun times(t:Int) = Position(row * t, col * t)

    fun surroundingPositions(map:Set<Position>, size:Int) = Direction.values().map{this + it.offset}.filter { it.isOn(map, size) }

    fun isOn(map:Set<Position>, size:Int) = Position( row % size, col % size ) in map
}

fun Int.remainder(other:Int) = this % other

enum class Direction(val offset:Position) {
    East(Position(row = 0, col = +1)),
    West(Position(row = 0, col = -1)),
    North(Position(row = -1, col = 0),),
    South(Position(row = +1, col = 0),),
}

fun partOne(sampleData:List<String>) :Int {
    val s = sampleData.positionOfS()
    val map = sampleData.createMap()
    return walk(listOf(s),0, 64, map, sampleData.size).size
}

fun List<String>.positionOfS(n:Int = 1) = Position( n * size + size/2, n * size + size/2)

fun List<String>.createMap():Set<Position> =
    flatMapIndexed { row, string -> string.mapIndexed {col, char ->
            if (char in listOf('.','S')) Position(row, col) else null
        } }.filterNotNull().toSet()

tailrec fun walk(positions:List<Position>, steps:Int, maxSteps:Int, map:Set<Position>, size:Int):List<Position> {
    if (steps == maxSteps) return positions
    val newPositions = positions.flatMap { position -> position.surroundingPositions(map, size) }.distinct()
    return walk(newPositions, steps + 1, maxSteps, map, size)
}

fun partTwo(sampleData:List<String>):Int {
    return 0
}