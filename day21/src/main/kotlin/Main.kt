
data class Position(val row:Int, val col:Int) {
    operator fun plus(other:Position) = Position(row + other.row, col + other.col)
    operator fun times(t:Int) = Position(row * t, col * t)

    fun surroundingPositions(map:Set<Position>) = Direction.values().map{this + it.offset}.filter { it.isOn(map) }

    fun isOn(map:Set<Position>) = this in map
}

enum class Direction(val offset:Position) {
    East(Position(row = 0, col = +1)),
    West(Position(row = 0, col = -1)),
    North(Position(row = -1, col = 0),),
    South(Position(row = +1, col = 0),),
}

fun partOne(sampleData:List<String>) :Int {
    val s = sampleData.positionOfS()
    val map = sampleData.createMap()
    return walk(listOf(s),0, 64, map).size
}

fun List<String>.positionOfS() = Position(indexOfFirst{it.contains('S')},  this[indexOfFirst{it.contains('S')}].indexOfFirst { it == 'S' })

fun List<String>.createMap():Set<Position> =
    flatMapIndexed { row, string -> string.mapIndexed {col, char ->
            if (char in listOf('.','S')) Position(row, col) else null
        } }.filterNotNull().toSet()

tailrec fun walk(positions:List<Position>, steps:Int, maxSteps:Int, map:Set<Position>):List<Position> {
    if (steps == maxSteps) return positions
    val newPositions = positions.flatMap { position -> position.surroundingPositions(map) }.distinct()
    return walk(newPositions, steps + 1, maxSteps, map)
}

fun partTwo(sampleData:List<String>):Int {
    return 0
}