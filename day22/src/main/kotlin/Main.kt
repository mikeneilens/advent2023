
fun partOne(sampleData:List<String>) :Int {
    val bricks = sampleData.toBricks().sortedBy { it.zRange.first }
    val pile = bricks.dropBricks()
    return pile.count {brick -> brick.canBeRemovedFrom(pile) }
}

val floor = Brick(-Int.MAX_VALUE..Int.MAX_VALUE, -Int.MAX_VALUE..Int.MAX_VALUE,0..0 )

fun List<String>.toBricks() = map(String::toBrick)
fun String.toBrick() = Brick(toCoordinateRange(0), toCoordinateRange(1), toCoordinateRange(2))
fun String.toCoordinateRange(n:Int) = toCordinateValue(0,n)..toCordinateValue(1,n)
fun String.toCordinateValue(m:Int, n:Int) = (split("~")[m].split(",")[n].toInt())

data class Brick(val xRange:IntRange, val yRange:IntRange, val zRange:IntRange) {
    fun floorOverlaps(other:Brick):Boolean = (xRange overlaps other.xRange) && (yRange overlaps other.yRange)

    fun stackAbove(other: Brick) = copy(zRange = (other.zRange.last + 1)..(other.zRange.last + 1 + zRange.last - zRange.first) )

    fun bricksAbove(pile:List<Brick>) = pile.filter{zRange.last + 1 == it.zRange.first && it.floorOverlaps(this)}

    fun bricksBelow(pile:List<Brick>, removedBricks:Set<Brick> = setOf()) =
        pile.filter{zRange.first == it.zRange.last + 1 && it.floorOverlaps(this) && it !in removedBricks}

    fun canBeRemovedFrom(pile:List<Brick>) =
        bricksAbove(pile).none{brickAbove -> brickAbove.bricksBelow(pile.filter{it!= this}).isEmpty() }
}

fun List<Brick>.dropBricks(pile:List<Brick> = listOf(floor)):List<Brick> =
    if (isEmpty()) pile
    else {
        val brickToRestOn = pile.filter { it.floorOverlaps(first()) }.maxBy { it.zRange.last }
        val droppedBrick = first().stackAbove(brickToRestOn)
        drop(1).dropBricks(pile + listOf(droppedBrick))
    }

fun partTwo(sampleData:List<String>):Int {
    val pile = sampleData.toBricks().sortedBy { it.zRange.first }.dropBricks()
    return pile.removeBricks(bricksRestingOnBricks(pile))
}

fun List<Brick>.removeBricks(bricksRestingOnBricks:Map<Brick, List<Brick>>) =
    drop(1).sumOf{  brick ->
        val mutableMap = bricksRestingOnBricks.map{Pair(it.key,it.value.toMutableList())}.toMap().toMutableMap()
        mutableMap.removeBrick(brick)
        this.size - mutableMap.size - 1
    }

fun MutableMap<Brick, MutableList<Brick>>.removeBrick(brick:Brick) {
    remove(brick)
    val impactedBricks = filter{brick in it.value }
    impactedBricks.forEach {it.value.remove(brick)}
    impactedBricks.filterValues(List<Brick>::isEmpty).forEach{ (key, _) -> removeBrick(key) }
}

fun bricksRestingOnBricks(pile:List<Brick>) = pile.associateWith { brick -> brick.bricksBelow(pile).toList() }

infix fun IntRange.overlaps(other:IntRange) = last in other || other.last in this
