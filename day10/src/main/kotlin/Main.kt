data class Position(val x:Int = 0, val y:Int = 0) {
    infix operator fun plus(other:Position) = Position(this.x + other.x, this.y + other.y)

    fun surroundPositions(maxX:Int, maxY:Int) = listOf(Position(x -1, y), Position( y -1, x),Position(x +1, y), Position( y +1, x)).filter{ it.x in 0..maxX && it.y in 0..maxY  }
}

data class ValidConnection(val pipes:List<Connection>)

enum class Direction(val offset:Position){Left(Position(-1,0)), Right(Position(1,0)), Up(Position(0,-1)), Down(Position(0,1))}

data class Connection(val type:Char, val direction:Direction)

val validRightConnections = ValidConnection(pipes = listOf(Connection('-', Direction.Right), Connection('7', Direction.Right), Connection('J',Direction.Right),Connection('S',Direction.Right)))
val validLeftConnections = ValidConnection(pipes = listOf(Connection('-', Direction.Left),Connection('F', Direction.Left),Connection('L', Direction.Left),Connection('S',Direction.Left)))
val validUpConnections = ValidConnection(pipes = listOf(Connection('|', Direction.Up),Connection('7', Direction.Up),Connection('F', Direction.Up),Connection('S',Direction.Up)))
val validDownConnections = ValidConnection(pipes = listOf(Connection('|',Direction.Down), Connection('J', Direction.Down),Connection('L', Direction.Down),Connection('S',Direction.Down)))

val validConnections = mapOf(
    '|' to ValidConnection(validUpConnections.pipes + validDownConnections.pipes),
    '-' to ValidConnection(validLeftConnections.pipes + validRightConnections.pipes),
    'L' to ValidConnection(validUpConnections.pipes + validRightConnections.pipes),
    'J' to ValidConnection(validUpConnections.pipes + validLeftConnections.pipes),
    '7' to ValidConnection(validLeftConnections.pipes + validDownConnections.pipes),
    'F' to ValidConnection(validRightConnections.pipes + validDownConnections.pipes),
    'S' to ValidConnection(validLeftConnections.pipes + validRightConnections.pipes + validUpConnections.pipes + validDownConnections.pipes)
)

typealias Maze = List<String>

fun Maze.pipeAt(position:Position) = if (position.x !in 0..(first().lastIndex) || position.y !in 0..lastIndex) '.' else get(position.y)[position.x]

fun Maze.pipesNextTo(position:Position) =
    validConnections.getValue(pipeAt(position)).pipes
        .filter{connection:Connection ->  connection.type == pipeAt(position + connection.direction.offset)}
        .map{Pair(position + it.direction.offset, it)}

fun Maze.pipesNotVisitedNextTo(position: Position, visited:MutableList<Position>) =
    pipesNextTo(position).filter { it.first !in visited }

tailrec fun Maze.traverseLoop(position:Position, visited:MutableList<Position> = mutableListOf(),  connections:MutableList<Connection> = mutableListOf()):MutableList<Position> {
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
    val (visits, connections) = visitsAndConnections(sampleData)
    val map = addVisitsToMap(visits)
    map.addSidesToMap(visits, connections, side1,'1')
    map.addSidesToMap(visits, connections, side2,'2')
    map.floodMap('2', sampleData.first().lastIndex, sampleData.lastIndex)
    map.print()
    return map.values.count { it == '2' }
}

fun visitsAndConnections(sampleData:List<String>):Pair<List<Position>,List<Connection>> {
    val startPosition = sampleData.startPosition()
    val connections = mutableListOf(Connection('S',Direction.Down))
    val visited = sampleData.traverseLoop(startPosition, connections = connections)
    replaceS(sampleData, visited, connections)
    return Pair(visited, connections)
}

fun replaceS(sampleData: List<String>, visited: List<Position>, connections: MutableList<Connection>) {
    val directionOfS = sampleData.pipesNotVisitedNextTo(visited.last(), visited.drop(1).toMutableList()).first().second.direction
    val typeOfS = sMap.getValue(Pair(connections.last().direction, connections[1].direction))
    connections[0] = Connection(typeOfS, directionOfS)
}

fun addVisitsToMap(visited:List<Position>):MutableMap<Position,Char> = visited.associateWith { 'P' }.toMutableMap()

fun MutableMap<Position,Char>.addSidesToMap(visited: List<Position>, connections: List<Connection>, sideMap:Map<Connection,List<Position>>, symbol:Char) {
    visited.zip(connections).forEach{(position, connection) ->
        sideMap[connection]?.forEach {offset ->
            if (  !contains(position + offset)) set(position + offset,symbol)
        }
    }
}

fun MutableMap<Position,Char>.floodMap(symbol:Char, maxX:Int, maxY:Int) {
    val newPositions = filter{it.value == symbol}.keys.flatMap{it.surroundPositions(maxX, maxY)}.filter{this[it] == null}
    if (newPositions.isEmpty()) return
    newPositions.forEach { set(it, symbol) }
    floodMap(symbol, maxX, maxY)
}

val sMap = mapOf(
    Pair(Direction.Down,Direction.Right) to 'L',
    Pair(Direction.Down,Direction.Down) to '|',
    Pair(Direction.Down,Direction.Left) to 'J',
    Pair(Direction.Left,Direction.Down) to 'F',
    Pair(Direction.Left,Direction.Left) to '-',
    Pair(Direction.Left,Direction.Up) to 'L',
    Pair(Direction.Up,Direction.Left) to '7',
    Pair(Direction.Up,Direction.Up) to '|',
    Pair(Direction.Up,Direction.Right) to 'F',
    Pair(Direction.Right,Direction.Up) to 'J',
    Pair(Direction.Right,Direction.Right) to '-',
    Pair(Direction.Right,Direction.Down) to '7',
)

val side1 = mapOf(
    Connection('|', Direction.Up) to listOf(Position(x= -1)),
    Connection('|', Direction.Down) to listOf(Position(x= +1)),
    Connection('-', Direction.Right) to listOf(Position(y= -1)),
    Connection('-', Direction.Left) to listOf(Position(y= +1)),
    Connection('L', Direction.Left) to listOf(Position(x= -1),Position(x= -1,y= +1),Position(y= +1 )),
    Connection('L', Direction.Down) to listOf(),
    Connection('J', Direction.Down) to listOf(Position(x= +1),Position(x= +1,y= +1),Position(y= +1 )),
    Connection('J', Direction.Right) to listOf(),
    Connection('7', Direction.Right) to listOf(Position(x= +1),Position(x= +1,y= -1),Position(y= -1 )),
    Connection('7', Direction.Up) to listOf(),
    Connection('F', Direction.Up) to listOf(Position(x= -1),Position(x= -1,y= -1),Position(y= -1 )),
    Connection('F', Direction.Left) to listOf(),
)
val side2 = mapOf(
    Connection('|', Direction.Up) to side1.getValue(Connection('|', Direction.Down)),
    Connection('|', Direction.Down) to side1.getValue(Connection('|', Direction.Up)),
    Connection('-', Direction.Right) to side1.getValue(Connection('-', Direction.Left)),
    Connection('-', Direction.Left) to side1.getValue(Connection('-', Direction.Right)),
    Connection('L', Direction.Left) to side1.getValue(Connection('L', Direction.Down)),
    Connection('L', Direction.Down) to  side1.getValue(Connection('L', Direction.Left)),
    Connection('J', Direction.Down) to side1.getValue(Connection('J', Direction.Right)),
    Connection('J', Direction.Right) to side1.getValue(Connection('J', Direction.Down)),
    Connection('7', Direction.Right) to side1.getValue(Connection('7', Direction.Up)),
    Connection('7', Direction.Up) to side1.getValue(Connection('7', Direction.Right)),
    Connection('F', Direction.Up) to side1.getValue(Connection('F', Direction.Left)),
    Connection('F', Direction.Left) to side1.getValue(Connection('F', Direction.Up)),
)

fun Map<Position, Char>.print() {
    (0..(keys.maxOf{it.y})).forEach{y ->
        (0..(keys.maxOf{it.y})).forEach { x ->
            print( this[Position(x,y)] ?: ' ')
        }
        println()
    }
}


