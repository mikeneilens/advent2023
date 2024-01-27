import java.util.PriorityQueue

fun partOne(sampleData:List<String>, stoppingDistance: Int = 0, maxSteps: Int = 3) :Int {
    val chart = sampleData.toChart()
    val crucible = Crucible(Bearing(Position(0,0), Direction.None, 0), 0, stoppingDistance, maxSteps)
    val visited = mutableSetOf<Bearing>()
    val end = Position(sampleData.first().lastIndex, sampleData.lastIndex)
    return expandCrucibleQueueUntilEnd(PriorityQueue<Crucible>().apply{add(crucible)}, visited, chart, end)
}

typealias Chart = Map<Position, Int>

fun List<String>.toChart() = flatMapIndexed{ row, s -> s.mapIndexed { col, c -> Pair(Position(col,row), c.toString().toInt())}}.toMap()

data class Crucible(val bearing: Bearing, val heatLoss:Int, val stoppingDistance:Int = 0, val maxSteps:Int = 3):Comparable<Crucible> {

    fun validDirections(chart:Chart) = Direction.values()
        .filter{newDirection ->  (newDirection != Direction.None && newDirection.offset != bearing.direction.offset.reversed)
                && isValid(newDirection)
                && (newPositionFor(newDirection) in chart) }

    fun isValid(newDirection: Direction) =
            ((bearing.steps == 0) || (bearing.steps < stoppingDistance && newDirection == bearing.direction)
                || (bearing.steps in stoppingDistance until maxSteps)
                || (bearing.steps >= maxSteps && newDirection != bearing.direction))

    fun newCrucible(newDirection:Direction, visited:MutableSet<Bearing>, chart:Chart):Crucible? {
        val newBearing = Bearing(newPositionFor(newDirection), newDirection, newStepsFor(newDirection))
        return if (newBearing !in visited) {
            visited.add(newBearing)
            Crucible(newBearing, newHeatLossFor(newDirection, chart), stoppingDistance, maxSteps)
        } else null
    }

    fun newPositionFor(newDirection: Direction) = bearing.position + newDirection.offset

    fun newStepsFor(newDirection: Direction) = if (newDirection == bearing.direction) bearing.steps + 1 else 1

    fun newHeatLossFor(newDirection:Direction, chart:Chart) = heatLoss + chart.getValue(newPositionFor(newDirection))

    fun isAt(end:Position) = bearing.position == end && bearing.steps >= stoppingDistance

    override fun compareTo(other: Crucible): Int = heatLoss - other.heatLoss
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

fun expandCrucibleQueue(crucibleQueue:PriorityQueue<Crucible>, visited:MutableSet<Bearing>, chart:Chart):PriorityQueue<Crucible>  {
    val crucible = crucibleQueue.remove()
    crucible.validDirections(chart).mapNotNull{ newDirection -> crucible.newCrucible(newDirection, visited, chart) }
        .forEach { crucibleQueue.add(it) }
    return crucibleQueue
}

tailrec fun expandCrucibleQueueUntilEnd(crucibleQueue: PriorityQueue<Crucible>, visited:MutableSet<Bearing>, chart:Chart, end:Position, stoppingDistance:Int = 0):Int {
    if ( atEndOfSearch(crucibleQueue, end)) return crucibleQueue.firstOrNull()?.heatLoss ?:0
    val newCrucibleQueue = expandCrucibleQueue(crucibleQueue, visited, chart)
    return expandCrucibleQueueUntilEnd(newCrucibleQueue, visited, chart, end, stoppingDistance)
}

fun atEndOfSearch(crucibleQueue:PriorityQueue<Crucible>, end:Position) =
    crucibleQueue.isEmpty() || crucibleQueue.first().isAt(end)

fun partTwo(sampleData:List<String>):Int {
    return partOne(sampleData, stoppingDistance = 4, maxSteps = 10)
}




