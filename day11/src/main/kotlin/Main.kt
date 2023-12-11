data class Position(val x:Int = 0, val y:Int = 0) {
    infix operator fun plus(other:Position) = Position(this.x + other.x, this.y + other.y)
}

fun partOne(sampleData:List<String>, largeWidth:Int = 2) :Long {
    val rowWidths = sampleData.rowWidths(largeWidth)
    val colWidths = sampleData.colWidths(largeWidth)
    return sampleData.toPositions().total(rowWidths, colWidths)
}

fun List<String>.toPositions() = (0..(first().lastIndex)).flatMap { x ->
    (0..lastIndex).mapNotNull{y -> if (this[y][x] == '#') Position(x,y) else null}}

fun List<Position>.total(rowWidths:Map<Int, Int>, colWidths:Map<Int, Int>) =
    flatMap{first -> map{second -> setOf(first, second) }}.toSet().sumOf{it.first().distanceTo(it.last(), rowWidths, colWidths).toLong()}

fun List<String>.rowWidths(largeWidth:Int = 2) =
    mapIndexed { i, s ->
        if (s.any { it == '#' }) Pair(i,1) else Pair(i,largeWidth)
    }.toMap()

fun List<String>.colWidths(largeWidth:Int = 2) =
    first().mapIndexed { i, _ ->
        if (this.map{it[i]}.any{ it == '#'}) Pair(i,1) else Pair(i,largeWidth)
    }.toMap()

fun distanceTo(s:Int, e:Int, colWidths:Map<Int, Int>) =
    ((minOf(s, e) +1)..(maxOf(s, e) -1)).sumOf{ colWidths.getValue(it)} + 1

fun Position.distanceTo(other:Position, rowWidths:Map<Int, Int>, colWidths:Map<Int, Int>) =
    distanceTo(x, other.x, colWidths) + distanceTo(y, other.y, rowWidths) - listOf(Pair(x, other.x), Pair(y, other.y)).map{ if (it.first == it.second) 1 else 0 }.sum()

fun partTwo(sampleData:List<String>, largeWidth:Int):Long {
    return partOne(sampleData, largeWidth)
}