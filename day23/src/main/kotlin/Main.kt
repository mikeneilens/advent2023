
fun partOne(sampleData:List<String>) :Int {
    val grid = sampleData.Grid()
    val paths = grid.startPostition().findPath(grid, grid.endPostition(), setOf(grid.startPostition()) )
    return paths.maxOf { it.size } - 1
}

fun Position.findPath(grid:Grid, endPosition:Position, visited:Set<Position> ):List<Set<Position>> {
    if (this == endPosition) return listOf(visited)
    return surroundingPositions(grid).filter { it !in visited  }.flatMap{position ->
        position.findPath(grid,endPosition, visited + position )
    }
}

typealias Grid = Map<Position,Char>

fun List<String>.Grid() = flatMapIndexed{ row, s -> s.mapIndexed { col, c -> Pair(Position(row, col),c)}}.toMap()
fun Grid.startPostition() = filter{it.key.row == 0 && it.value == '.' }.keys.first()
fun Grid.endPostition() = filter{it.key.row == keys.maxOf { it.row } && it.value == '.' }.keys.first()

data class Position(val row:Int, val col:Int) {
    operator fun plus(other:Position) = Position(row + other.row, col + other.col)

    fun surroundingPositions(grid:Grid) = offsets.getValue(grid.getValue(this)).map{ offset -> this + offset}.filter { grid[it] != '#' && it.isOn(grid)}

    fun isOn(grid:Grid) = Position( row , col  ) in grid

    companion object {
        val offsets = mapOf(
            '.' to listOf(Position(row = 0, col = +1), Position(row = 0, col = -1), Position(row = -1, col = 0), Position(row = +1, col = 0)),
            '>' to listOf(Position(row =  0, col = +1)),
            '^' to listOf(Position(row = -1, col =  0)),
            'v' to listOf(Position(row = +1, col =  0)),
            '<' to listOf(Position(row =  0, col = -1)),
        )
    }
}

fun partTwo(sampleData:List<String>):Int {
    val grid = sampleData.Grid2()
    val junctions = grid.findJunctions().toSet()
    val connections:Map<Position,List<Pair<Position, Int>>> = junctions.findConnections(grid)
    val paths = grid.startPostition().findPath2(connections, grid.endPostition(), setOf(), setOf(grid.startPostition()))
    return paths.maxOf{ it.sumOf { it.second } }
}

var max = 0

fun Position.findPath2(connections:Map<Position, List<Pair<Position,Int>>>, endPosition:Position, visited:Set<Pair<Position,Int>>, visitedPositions:Set<Position> ):List<Set<Pair<Position,Int>>> {
    if (this == endPosition) {
        val distance = visited.sumOf { it.second }
        if (distance > max) { max = distance; println("end reaced $distance") }
        return listOf(visited)
    }
    return connections.getValue(this).filter { it.first !in visitedPositions  }.flatMap{ pair ->
        pair.first.findPath2(connections, endPosition, visited + pair, visitedPositions + pair.first )
    }
}

fun Grid.findJunctions() =
    filter{it.value != '#' && it.key.surroundingPositions(this).size > 2}.keys + listOf(this.startPostition(), endPostition())

fun Set<Position>.findConnections(grid:Grid) =
    associate{position -> Pair(position, position.findConnections(grid, this - position, setOf(position) ))}

fun Position.findConnections(grid:Grid, otherJunctions:Set<Position>, visited:Set<Position> ):List<Pair<Position, Int>> {
    if (this in otherJunctions) return listOf(Pair(this, visited.size - 1))
    return surroundingPositions(grid).filter { it !in visited  }.flatMap{position ->
        position.findConnections(grid, otherJunctions, visited + position )
    }
}

fun List<String>.Grid2() = flatMapIndexed{ row, s -> s.replaceSlopes().mapIndexed { col, c -> Pair(Position(row, col),c)}}.toMap()

fun String.replaceSlopes() = replace("<",".").replace(">",".").replace("^",".").replace("v",".")