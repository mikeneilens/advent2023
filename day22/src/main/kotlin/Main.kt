
fun partOne(sampleData:List<String>) :Int {
    val bricks = sampleData.toBricks().sortedBy { it.zRange.first }
    val pile = bricks.dropBricks()
    return pile.count {it.canBeRemoved(pile) }
}

val floor = Brick(-Int.MAX_VALUE..Int.MAX_VALUE, -Int.MAX_VALUE..Int.MAX_VALUE,0..0 )

fun List<String>.toBricks() = map(String::toBrick)

fun String.toBrick() = Brick(
    split("~").toCordinateRange(0),
    split("~").toCordinateRange(1),
    split("~").toCordinateRange(2)
)

fun List<String>.toCordinateRange(n:Int) = (first().split(",")[n].toInt())..(last().split(",")[n].toInt())

data class Brick(val xRange:IntRange, val yRange:IntRange, val zRange:IntRange) {
    fun floorOverlaps(other:Brick):Boolean = (xRange overlaps other.xRange) && (yRange overlaps other.yRange)

    fun stackAbove(other: Brick) = copy(zRange = (other.zRange.last + 1)..(other.zRange.last + 1 + zRange.last - zRange.first) )

    fun bricksAbove(pile:List<Brick>) =
        pile.filter{zRange.last + 1 == it.zRange.first && it.floorOverlaps(this)}

    fun bricksBelow(pile:List<Brick>, removedBricks:Set<Brick> = setOf()) =
        pile.filter{zRange.first == it.zRange.last + 1 && it.floorOverlaps(this) && it !in removedBricks}

    fun canBeRemoved(pile:List<Brick>):Boolean {
        val bricksAbove = bricksAbove(pile)
        if (bricksAbove.size == 0 ) return true
        return bricksAbove.all{brickAbove -> brickAbove.bricksBelow(pile.filter{it!= this}).isNotEmpty() }
    }
}

fun List<Brick>.dropBricks(pile:List<Brick> = listOf(floor)):List<Brick> {
    return if (isEmpty()) pile
    else {
        val brickToRestOn = pile.filter { it.floorOverlaps(first()) }.maxBy { it.zRange.last }
        val droppedBrick = first().stackAbove(brickToRestOn)
        drop(1).dropBricks(pile + listOf(droppedBrick))
    }
}

fun List<Brick>.printX() {
    println("X")
    (0..maxOf {it.zRange.last}).reversed().forEach { z ->
        (0..maxOf {it.xRange.last}).forEach { x ->
            if (any{brick -> z in brick.zRange && x in brick.xRange }) print("#") else print(" ")
        }
        println()
    }
}
fun List<Brick>.printY() {
    println("Y")
    (0..maxOf {it.zRange.last}).reversed().forEach { z ->
        (0..maxOf {it.yRange.last}).forEach { y ->
            if (any{brick -> z in brick.zRange && y in brick.yRange }) print("#") else print(" ")
        }
        println()
    }
}

fun partTwo(sampleData:List<String>):Int {
    val pile = sampleData.toBricks().dropBricks()
    return removeBricks(pile)
}

fun MutableMap<Brick, MutableList<Brick>>.remove1(brick:Brick) {
    this.remove(brick)
    val impactedBricks = filter{brick in it.value }
    impactedBricks.forEach {it.value.remove(brick)}
    impactedBricks.filterValues{ it.isEmpty() }.forEach{ (key, value) -> remove1(key) }
}

fun removeBricks(pile:List<Brick>):Int {
    val bricksRestingOnBricks = bricksRestingOnBricks(pile)
    return pile.drop(1).sumOf{
        brick ->
        val mutableMap = bricksRestingOnBricks.map{Pair(it.key,it.value.toMutableList())}.toMap().toMutableMap()
        mutableMap.remove1(brick)
        pile.size - mutableMap.size - 1
    }
}

fun bricksRestingOnBricks(pile:List<Brick>) =
    pile.associate{brick-> Pair(brick,brick.bricksBelow(pile).toMutableList())}


infix fun IntRange.overlaps(other:IntRange) = last in other || other.last in this
