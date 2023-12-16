fun partOne(sampleData:List<String>) :Int {
    val beams = mutableSetOf<Beam>()
    val grid = sampleData.toGrid()
    Beam(Position(0,-1),Direction.Right).move(grid, beams)
    return grid.visitedBy(beams).size
}

fun List<String>.toGrid() =
    flatMapIndexed{ row, s -> s.mapIndexed{ col, c -> Pair(Position(row, col),c)} }.toMap()

fun Beam.move(grid:Map<Position, Char>, beams:MutableSet<Beam> = mutableSetOf()) {
    if (this in beams) return else beams.add(this)
    val beam = copy(position = position + direction.offset )
    if (beam.position !in grid.keys) return
    val collision = Collision(grid.getValue(beam.position),beam.direction)
    resultsOfCollision[collision]?.forEach { beam.copy(direction = it).move(grid,beams) }
}

fun Map<Position, Char>.visitedBy(beams:Set<Beam>):List<Position> {
    val positionOfBeams = beams.map{it.position}.distinct()
    return keys.filter{position -> position in positionOfBeams}
}

data class Position(val row:Int, val col:Int) {
    operator fun plus(other:Position) = Position(row + other.row, col + other.col)
}

enum class Direction(val offset:Position) {
    Right(Position(row = 0, col = +1)),
    Left(Position(row = 0, col = -1)),
    Up(Position(row = -1, col = 0)),
    Down(Position(row = +1, col = 0)),
}

data class Beam(val position:Position, val direction:Direction)

data class Collision(val mirror:Char, val direction:Direction)

val resultsOfCollision = mapOf(
    Collision('-', Direction.Right) to listOf(Direction.Right),
    Collision('-', Direction.Left) to listOf(Direction.Left),
    Collision('-', Direction.Up) to listOf(Direction.Left, Direction.Right),
    Collision('-', Direction.Down) to listOf(Direction.Left, Direction.Right),
    Collision('|', Direction.Right) to listOf(Direction.Up, Direction.Down),
    Collision('|', Direction.Left) to listOf(Direction.Up, Direction.Down),
    Collision('|', Direction.Up) to listOf(Direction.Up),
    Collision('|', Direction.Down) to listOf(Direction.Down),
    Collision('\\', Direction.Right) to listOf(Direction.Down),
    Collision('\\', Direction.Left) to listOf(Direction.Up),
    Collision('\\', Direction.Up) to listOf(Direction.Left),
    Collision('\\', Direction.Down) to listOf(Direction.Right),
    Collision('/', Direction.Right) to listOf(Direction.Up),
    Collision('/', Direction.Left) to listOf(Direction.Down),
    Collision('/', Direction.Up) to listOf(Direction.Right),
    Collision('/', Direction.Down) to listOf(Direction.Left),
    Collision('.', Direction.Right) to listOf(Direction.Right),
    Collision('.', Direction.Left) to listOf(Direction.Left),
    Collision('.', Direction.Up) to listOf(Direction.Up),
    Collision('.', Direction.Down) to listOf(Direction.Down)
)

fun partTwo(sampleData:List<String>):Int {
    return 0
}