fun partOne(sampleData:List<String>, beam:Beam = Beam(Position(0,-1),Direction.Right)) :Int {
    val grid = sampleData.toGrid()
    val beams = grid.fireBeam(beam)
    return grid.visitedBy(beams).size
}

fun List<String>.toGrid() =
    flatMapIndexed{ row, s -> s.mapIndexed{ col, c -> Pair(Position(row, col),c)} }.toMap()

fun Map<Position, Char>.fireBeam(beam:Beam):Set<Beam> {
    val beams = mutableSetOf<Beam>()
    beam.move(this, beams)
    return beams
}

fun Beam.move(grid:Map<Position, Char>, beams:MutableSet<Beam> = mutableSetOf()) {
    if (this in beams) return else beams.add(this)
    val newPosition = position + direction.offset
    if (newPosition !in grid.keys) return
    val collision = Collision(grid.getValue(newPosition), direction)
    resultsOfCollision[collision]?.forEach {newDiretion -> Beam(newPosition, newDiretion).move(grid,beams) }
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
    val grid = sampleData.toGrid()
    val maxRow = sampleData.lastIndex
    val maxCol = sampleData.first().lastIndex
    val beams = grid.beamFromLeft() + grid.beamFromTop() + grid.beamFromRight(maxCol) + grid.beamFromBottom(maxRow)
    return beams.map { beam -> grid.visitedBy( grid.fireBeam(beam)).size }.max()
}

fun Map<Position, Char>.beamFromLeft() =
    keys.filter{key -> key.col == 0 }.map{Beam(Position(it.row, - 1),Direction.Right)}

fun Map<Position, Char>.beamFromRight(lastCol:Int) =
    keys.filter{key -> key.col == lastCol }.map{Beam(Position(it.row, lastCol + 1),Direction.Left)}

fun Map<Position, Char>.beamFromTop() =
    keys.filter{key -> key.row == 0 }.map{Beam(Position( -1, it.col),Direction.Down)}

fun Map<Position, Char>.beamFromBottom(lastRow:Int) =
    keys.filter{key -> key.row == lastRow }.map{Beam(Position(lastRow + 1 , it.col),Direction.Up)}