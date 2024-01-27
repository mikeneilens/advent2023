fun partOne(sampleData:List<String>) :Int {
    val chart = sampleData.toChart()
    val crucible = Crucible(Bearing(Position(0,0), Direction.None, 0), 0 )
    val visited = mutableSetOf<Bearing>()
    val end = Position(sampleData.first().lastIndex, sampleData.lastIndex)
    return expandCrucibleQueueUntilEnd(listOf(crucible), visited, chart, end)
}

typealias Chart = Map<Position, Int>

fun List<String>.toChart() = flatMapIndexed{ row, s -> s.mapIndexed { col, c -> Pair(Position(col,row), c.toString().toInt())  }}.toMap()

interface CrucibleType {
    val bearing: Bearing
    val heatloss:Int
    fun validDirections(chart:Chart):List<Direction>
    fun newCrucible(newDirection:Direction, visited:MutableSet<Bearing>, chart:Chart):CrucibleType?
}

data class Bearing(val position: Position, val direction: Direction, val steps:Int)

data class Position(val col:Int, val row:Int) {

    val  reversed:Position get() = Position(-col, -row)

    operator fun plus(other:Position) = Position(col + other.col, row + other.row)
}

enum class Direction(val offset:Position) {
    Right(Position(row = 0, col = +1)),
    Left(Position(row = 0, col = -1)),
    Up(Position(row = -1, col = 0)),
    Down(Position(row = +1, col = 0)),
    None(Position(row = 0, col = 0)),
}

data class Crucible(override val bearing: Bearing, override val heatloss:Int):CrucibleType {

    override fun validDirections(chart:Chart) = Direction.values()
        .filter{(it != Direction.None && it.offset != bearing.direction.offset.reversed)
                && (bearing.steps < 3 || it != bearing.direction)
                && (bearing.position + it.offset in chart) }

    override fun newCrucible(newDirection:Direction, visited:MutableSet<Bearing>, chart:Chart):Crucible? {
        val newPosition = bearing.position + newDirection.offset
        val newSteps = if (newDirection == bearing.direction) bearing.steps + 1 else 1
        val newHeatloss = heatloss + chart.getValue(newPosition)
        val newCrucible = Crucible(Bearing(newPosition, newDirection, newSteps), newHeatloss)
        return if (newCrucible.bearing !in visited) {
            visited.add(newCrucible.bearing)
            newCrucible }
        else null
    }
}

fun expandCrucibleQueue(crucibleQueue:List<CrucibleType>, visited:MutableSet<Bearing>, chart:Chart):List<CrucibleType>  {
    val crucible = crucibleQueue.first()
    val newDirections = crucible.validDirections(chart)
    val newCrucibles = newDirections.mapNotNull{ newDirection ->
        crucible.newCrucible(newDirection, visited, chart)
    }
    return (crucibleQueue.drop(1) + newCrucibles).sortedBy { it.heatloss }
}

tailrec fun expandCrucibleQueueUntilEnd(crucibleQueue: List<CrucibleType>, visited:MutableSet<Bearing>, chart:Chart, end:Position, stoppingDistance:Int = 0):Int {
    if ( crucibleQueue.isEmpty() || end == crucibleQueue.first().bearing.position && crucibleQueue.first().bearing.steps >= stoppingDistance) return crucibleQueue.firstOrNull()?.heatloss ?:0
    val newCrucibleQueue = expandCrucibleQueue(crucibleQueue, visited, chart)
    return expandCrucibleQueueUntilEnd(newCrucibleQueue, visited, chart, end, stoppingDistance)
}

fun partTwo(sampleData:List<String>):Int {
    val chart = sampleData.toChart()
    val crucible = UltraCrucible(Bearing(Position(0,0), Direction.None, 0), 0 )
    val visited = mutableSetOf<Bearing>()
    val end = Position(sampleData.first().lastIndex, sampleData.lastIndex)
    return expandCrucibleQueueUntilEnd(listOf(crucible), visited, chart, end, 4)
}

data class UltraCrucible(override val bearing: Bearing, override val heatloss:Int):CrucibleType {

    override fun validDirections(chart:Chart) = Direction.values()
        .filter{(it != Direction.None && it.offset != bearing.direction.offset.reversed)
                && ((bearing.steps == 0) || (bearing.steps < 4 && it == bearing.direction) || (bearing.steps in 4..9) || (bearing.steps >= 10 && it != bearing.direction))
                && (bearing.position + it.offset in chart) }

    override fun newCrucible(newDirection:Direction, visited:MutableSet<Bearing>, chart:Chart):UltraCrucible? {
        val newPosition = bearing.position + newDirection.offset
        val newSteps = if (newDirection == bearing.direction) bearing.steps + 1 else 1
        val newHeatloss = heatloss + chart.getValue(newPosition)
        val newCrucible = UltraCrucible(Bearing(newPosition, newDirection, newSteps), newHeatloss)
        return if (newCrucible.bearing !in visited) {
            visited.add(newCrucible.bearing)
            newCrucible }
        else null
    }
}




