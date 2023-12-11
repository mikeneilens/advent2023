data class Position(val x:Int = 0, val y:Int = 0)

fun partOne(sampleData:List<String>, largeWidth:Int = 2) :Long {
    val rowWidths = sampleData.rowWidths(largeWidth)
    val colWidths = sampleData.colWidths(largeWidth)
    return sampleData.toPositions().total(rowWidths, colWidths)
}

fun List<String>.toPositions() = (0..(first().lastIndex)).flatMap { x ->
    (0..lastIndex).mapNotNull{y -> if (this[y][x] == '#') Position(x,y) else null}}

fun List<Position>.total(rowWidths:Map<Int, Int>, colWidths:Map<Int, Int>) =
    galaxyPairs().sumOf{it.first().distanceTo(it.last(), rowWidths, colWidths).toLong()}

tailrec fun List<Position>.galaxyPairs(result:List<Set<Position>> = listOf()):List<Set<Position>> =
    if (size < 2) result else drop(1).galaxyPairs( result + drop(1).map{setOf(first(),it) })

fun List<String>.rowWidths(largeWidth:Int = 2) =
    mapIndexed { i, s -> if ('#' in s) Pair(i,1) else Pair(i,largeWidth) }.toMap()

fun List<String>.colWidths(largeWidth:Int = 2) =
    first().indices.map {i -> if ('#' in this.map{it[i]}) Pair(i,1) else Pair(i,largeWidth) }.toMap()

fun Position.distanceTo(other:Position, rowWidths:Map<Int, Int>, colWidths:Map<Int, Int>) =
    distanceTo(x, other.x, colWidths) + distanceTo(y, other.y, rowWidths) - noOfMatchingCoordinates(other)

fun distanceTo(start:Int, end:Int, widths:Map<Int, Int>) =
    ((minOf(start, end) +1)..(maxOf(start, end) -1)).sumOf{ widths.getValue(it)} + 1

fun Position.noOfMatchingCoordinates(other:Position) = listOf(Pair(x, other.x), Pair(y, other.y)).count { it.first == it.second }

fun partTwo(sampleData:List<String>, largeWidth:Int):Long {
    return partOne(sampleData, largeWidth)
}