fun partOne(sampleData:List<String>) :Int {
    val chart = sampleData.toChart()
    val crucible = Crucible(Position(0,0), Direction.Right, 0 )
    val heatLoss = mutableMapOf<Crucible, Int>()
    val end = Position(sampleData.first().lastIndex, sampleData.lastIndex)
    return expandCrucibleQueueUntilEnd(listOf(crucible), heatLoss, chart, end)
}

fun partTwo(sampleData:List<String>):Int {
    return 0
}

typealias Chart = Map<Position, Int>

fun List<String>.toChart() = flatMapIndexed{ row, s -> s.mapIndexed { col, c -> Pair(Position(col,row), c.toString().toInt())  }}.toMap()

data class Position(val col:Int, val row:Int) {

    val  reversed:Position get() = Position(-col, -row)

    operator fun plus(other:Position) = Position(col + other.col, row + other.row)
}

data class Crucible(val position: Position, val direction: Direction, val steps:Int) {
    fun validDirections(chart:Chart) = Direction.values()
        .filter{(it != Direction.None && it.offset != direction.offset.reversed)
                && (steps < 3 || it != direction)
                && (position + it.offset in chart) }
}

enum class Direction(val offset:Position) {
    Right(Position(row = 0, col = +1)),
    Left(Position(row = 0, col = -1)),
    Up(Position(row = -1, col = 0)),
    Down(Position(row = +1, col = 0)),
    None(Position(row = 0, col = 0)),
}

fun expandCrucibleQueue(crucibleQueue:List<Crucible>, heatLoss:MutableMap<Crucible, Int>, chart:Chart):List<Crucible>  {
    val crucible = crucibleQueue.first()
    val newDirections = crucible.validDirections(chart)
    val newCrucibles = newDirections.mapNotNull{ newDirection ->
        val newPosition = crucible.position + newDirection.offset
        val newSteps = if (newDirection == crucible.direction) crucible.steps + 1 else 1
        val newCrucible = Crucible(newPosition, newDirection, newSteps)
        if (newCrucible !in heatLoss) {
            heatLoss[newCrucible] = heatLoss.getOrDefault(crucible,0) + chart.getValue(newPosition)
            newCrucible }
        else null
    }
    return (crucibleQueue.drop(1) + newCrucibles).sortedBy { heatLoss[it] }
}

tailrec fun expandCrucibleQueueUntilEnd(crucibleQueue: List<Crucible>, heatLoss:MutableMap<Crucible, Int>, chart:Chart, end:Position):Int {
    if (end == crucibleQueue.first().position || crucibleQueue.isEmpty()) return heatLoss.minHeatLoss(end)
    val newCrucibleQueue = expandCrucibleQueue(crucibleQueue, heatLoss, chart)
    return expandCrucibleQueueUntilEnd(newCrucibleQueue, heatLoss, chart, end)
}

fun Map<Crucible, Int>.minHeatLoss(position:Position) = toList().filter{it.first.position == position}.minOf { it.second }


