import kotlin.math.absoluteValue

fun partOne(sampleData:List<String>) :Long {
    val map = mutableMapOf<Position, String>()
    sampleData.toInstructions().fold(Position(0,0)){ position, instruction ->
        instruction.process(position, map)
    }
    map.addBoundary()
    map.flood("2")
    return map.count { it.value.startsWith("#") || it.value == "2" }.toLong()
}

fun List<String>.toInstructions() = map(String::toInstruction)

fun String.toInstruction() = Instruction (
    direction = charToDirection.getValue(split(" " )[0].first()),
    steps = split(" ")[1].toLong(),
    colour = split(" ")[2].removePrefix("(").removeSuffix(")"),
)

data class Instruction(val direction: Direction, val steps:Long, val colour:String) {
    fun process(start:Position, map:MutableMap<Position, String>):Position {
        (0 until steps).fold(start){ position, _ ->
            map.addColour(position, direction, colour)
            position + direction.offset
        }
        return start + direction.offset * steps
    }
}

fun MutableMap<Position, String>.addBoundary(){
    val rowStart = (keys.minOf { it.row } -1)
    val rowEnd = (keys.maxOf { it.row } +1)
    val colStart = (keys.minOf { it.col } -1)
    val colEnd = (keys.maxOf { it.col } +1)

    (rowStart..rowEnd).forEach { row -> this[Position(row, colStart)] = "X" }
    (rowStart..rowEnd).forEach { row -> this[Position(row, colEnd)] = "X" }
    (colStart..colEnd).forEach { col -> this[Position(rowStart, col)] = "X" }
    (colStart..colEnd).forEach { col -> this[Position(rowEnd, col)] = "X" }
}

fun MutableMap<Position, String>.addColour(position: Position, direction: Direction, colour:String) {
    this[position + direction.offset] = colour
    if ((position + direction.side1) !in this) this[position + direction.side1] = "1"
    if ((position + direction.side2) !in this) this[position + direction.side2] = "2"
}

fun MutableMap<Position, String>.flood(s:String) {
    val startingSize = size
    filter{it.value == s}.keys.forEach { position ->
        listOf(Direction.Left, Direction.Right, Direction.Down, Direction.Up).forEach {
                if ((position + it.offset) !in this) this[position + it.offset] = s
        }
    }
    if (size > startingSize) flood(s)
}


data class Position(val row:Long, val col:Long) {
    operator fun plus(other:Position) = Position(row + other.row, col + other.col)
    operator fun times(t:Long) = Position(row * t, col * t)
}

enum class Direction(val offset:Position, val side1:Position, val side2:Position) {
    Right(Position(row = 0, col = +1), Position(row = -1, col = +1), Position(row = +1, col = +1)),
    Left(Position(row = 0, col = -1), Position(row = +1, col = -1), Position(row = -1, col = -1)),
    Up(Position(row = -1, col = 0), Position(row = -1, col = -1), Position(row = -1, col = +1)),
    Down(Position(row = +1, col = 0), Position(row = +1, col = +1), Position(row = +1, col = -1)),
}

val charToDirection = mapOf( 'R' to Direction.Right, 'L' to Direction.Left, 'U' to Direction.Up, 'D' to Direction.Down)

fun partTwo(sampleData:List<String>):Long {
    val instructions = sampleData.toInstruction2()
    return areaUsingShoeLaceAndPicksTheorm(instructions.positions(), instructions.totalSteps())
}

fun List<String>.toInstruction2() = map(String::toInstruction2)

fun String.toInstruction2() = Instruction(
    charToDirection2.getValue(split(" ")[2].removeSuffix(")").last()),
    split(" ")[2].removePrefix("(#").removeSuffix(")").dropLast(1).toLong(16),
    ""
)

fun List<Instruction>.positions() = fold(listOf(Position(0,0))){positions, instruction ->
    positions + listOf(positions.last() + (instruction.direction.offset * instruction.steps) )
}
fun List<Instruction>.totalSteps() = sumOf { it.steps }

val charToDirection2 = mapOf( '0' to Direction.Right, '2' to Direction.Left, '3' to Direction.Up, '1' to Direction.Down)

fun areaUsingShoeLaceAndPicksTheorm(positions:List<Position>, totalSteps: Long) = totalSteps + calcInternalAreaFromPicksTheorm(positions, totalSteps)

fun calcAForShoeLaceTheorm(positions: List<Position>)
    = (positions.mapIndexed {i, p -> p.row * ( positions[(i+1).specialMod(positions.size)].col - positions[(i-1).specialMod(positions.size)].col )}
        .sum()/2 ).absoluteValue

fun Int.specialMod(size:Int) = if (this < 0 ) size - 1 else this % size

fun calcInternalAreaFromPicksTheorm(positions: List<Position>, totalSteps:Long) = calcAForShoeLaceTheorm(positions) - totalSteps/2 +1