data class Position(val x:Int = 0, val y:Int = 0) {
    infix operator fun plus(other:Position) = Position(this.x + other.x, this.y + other.y)

    fun surroundingPositions(maxX:Int, maxY:Int) = listOf(Position(x -1, y), Position( y -1, x),Position(x +1, y), Position( y +1, x)).filter{ it.x in 0..maxX && it.y in 0..maxY  }
}

enum class Direction(val offset:Position){Left(Position(-1,0)), Right(Position(1,0)), Up(Position(0,-1)), Down(Position(0,1))}

data class Pipe(val type:Char, val direction:Direction)

val validRightPipes = listOf(Pipe('-', Direction.Right), Pipe('7', Direction.Right), Pipe('J',Direction.Right),Pipe('S',Direction.Right))
val validLeftPipes = listOf(Pipe('-', Direction.Left),Pipe('F', Direction.Left),Pipe('L', Direction.Left),Pipe('S',Direction.Left))
val validUpPipes = listOf(Pipe('|', Direction.Up),Pipe('7', Direction.Up),Pipe('F', Direction.Up),Pipe('S',Direction.Up))
val validDownPipes = listOf(Pipe('|',Direction.Down), Pipe('J', Direction.Down),Pipe('L', Direction.Down),Pipe('S',Direction.Down))

val validPipes = mapOf(
    '|' to validUpPipes + validDownPipes,
    '-' to validLeftPipes + validRightPipes,
    'L' to validUpPipes + validRightPipes,
    'J' to validUpPipes + validLeftPipes,
    '7' to validLeftPipes + validDownPipes,
    'F' to validRightPipes + validDownPipes,
    'S' to validLeftPipes + validRightPipes + validUpPipes + validDownPipes
)

typealias Maze = List<String>

fun Maze.pipeAt(position:Position) = if (position.x !in 0..(first().lastIndex) || position.y !in 0..lastIndex) '.' else get(position.y)[position.x]

fun Maze.pipesNextTo(position:Position) =
    validPipes.getValue(pipeAt(position))
        .filter{connection:Pipe ->  connection.type == pipeAt(position + connection.direction.offset)}
        .map{Pair(position + it.direction.offset, it)}

fun Maze.pipesNotVisitedNextTo(position: Position, visited:MutableList<Position>) =
    pipesNextTo(position).filter { it.first !in visited }

tailrec fun Maze.traverseLoop(position:Position, visited:MutableList<Position> = mutableListOf(),  connections:MutableList<Pipe> = mutableListOf()):MutableList<Position> {
    visited.add(position)
    val nextPipe = pipesNotVisitedNextTo(position,visited)
    return if (nextPipe.isEmpty()) visited
    else {
        connections.add(nextPipe.first().second)
        traverseLoop(nextPipe.first().first,visited, connections)
    }
}

fun Maze.startPosition():Position {
    val startY = indexOfFirst { it.contains('S') }
    return Position(this[startY].indexOfFirst { it == 'S'}, startY)
}

fun partOne(sampleData:List<String>) :Int {
    val startPosition = sampleData.startPosition()
    val visited = sampleData.traverseLoop(startPosition)
    return visited.size / 2
}

fun partTwo(sampleData:List<String>):Int {
    val (visits, connections) = visitsAndPipes(sampleData)
    val map = addVisitsToMap(visits)
    map.addSidesToMap(visits, connections, side1,'1')
    map.addSidesToMap(visits, connections, side2,'2')
    map.floodMap('2', sampleData.first().lastIndex, sampleData.lastIndex)
    map.print()
    return map.values.count { it == '2' }
}

fun visitsAndPipes(sampleData:List<String>):Pair<List<Position>,List<Pipe>> {
    val startPosition = sampleData.startPosition()
    val connections = mutableListOf(Pipe('S',Direction.Down))
    val visited = sampleData.traverseLoop(startPosition, connections = connections)
    return Pair(visited, connections)
}

fun addVisitsToMap(visited:List<Position>):MutableMap<Position,Char> = visited.associateWith { 'P' }.toMutableMap()

fun MutableMap<Position,Char>.addSidesToMap(visited: List<Position>, connections: List<Pipe>, sideMap:Map<Pipe,List<Position>>, symbol:Char) {
    visited.zip(connections).forEach{(position, connection) ->
        sideMap[connection]?.forEach {offset ->
            if (  !contains(position + offset)) set(position + offset,symbol)
        }
    }
}

fun MutableMap<Position,Char>.floodMap(symbol:Char, maxX:Int, maxY:Int) {
    val newPositions = filter{it.value == symbol}.keys.flatMap{it.surroundingPositions(maxX, maxY)}.filter{it !in this}
    if (newPositions.isEmpty()) return
    newPositions.forEach { set(it, symbol) }
    floodMap(symbol, maxX, maxY)
}

val side1 = mapOf(
    Pipe('|', Direction.Up) to listOf(Position(x= -1)),
    Pipe('|', Direction.Down) to listOf(Position(x= +1)),
    Pipe('-', Direction.Right) to listOf(Position(y= -1)),
    Pipe('-', Direction.Left) to listOf(Position(y= +1)),
    Pipe('L', Direction.Left) to listOf(Position(x= -1),Position(x= -1,y= +1),Position(y= +1 )),
    Pipe('L', Direction.Down) to listOf(),
    Pipe('J', Direction.Down) to listOf(Position(x= +1),Position(x= +1,y= +1),Position(y= +1 )),
    Pipe('J', Direction.Right) to listOf(),
    Pipe('7', Direction.Right) to listOf(Position(x= +1),Position(x= +1,y= -1),Position(y= -1 )),
    Pipe('7', Direction.Up) to listOf(),
    Pipe('F', Direction.Up) to listOf(Position(x= -1),Position(x= -1,y= -1),Position(y= -1 )),
    Pipe('F', Direction.Left) to listOf(),
)
val side2 = mapOf(
    Pipe('|', Direction.Up) to side1.getValue(Pipe('|', Direction.Down)),
    Pipe('|', Direction.Down) to side1.getValue(Pipe('|', Direction.Up)),
    Pipe('-', Direction.Right) to side1.getValue(Pipe('-', Direction.Left)),
    Pipe('-', Direction.Left) to side1.getValue(Pipe('-', Direction.Right)),
    Pipe('L', Direction.Left) to side1.getValue(Pipe('L', Direction.Down)),
    Pipe('L', Direction.Down) to  side1.getValue(Pipe('L', Direction.Left)),
    Pipe('J', Direction.Down) to side1.getValue(Pipe('J', Direction.Right)),
    Pipe('J', Direction.Right) to side1.getValue(Pipe('J', Direction.Down)),
    Pipe('7', Direction.Right) to side1.getValue(Pipe('7', Direction.Up)),
    Pipe('7', Direction.Up) to side1.getValue(Pipe('7', Direction.Right)),
    Pipe('F', Direction.Up) to side1.getValue(Pipe('F', Direction.Left)),
    Pipe('F', Direction.Left) to side1.getValue(Pipe('F', Direction.Up)),
)

fun Map<Position, Char>.print() {
    (0..(keys.maxOf{it.y})).forEach{y ->
        (0..(keys.maxOf{it.y})).forEach { x ->
            print( this[Position(x,y)] ?: ' ')
        }
        println()
    }
}


