
fun partOne(sampleData:List<String>, validRange: ValidRange = ValidRange(7.0,27.0)) :Int {
    val intersections = sampleData.map { it.toHailstone() }.process(listOf())
    return intersections.count{it.inRange(validRange)}
}

fun List<Hailstone>.process(intersections: List<Position>):List<Position> {
    if (isEmpty()) return intersections
    val hailstone = first()
    val newIntersections = drop(1)
        .filter{hailstone.notParallelWith(it)}
        .map { otherHailstone -> Triple(hailstone.positionOfIntersection(otherHailstone), hailstone, otherHailstone)}
        .filter{(intersection, hailstone1, hailstone2) -> hailstone1.intersectionIsFuture(intersection) && hailstone2.intersectionIsFuture(intersection)}
        .map{it.first}
    return drop(1).process(intersections + newIntersections)
}

data class Position(val x:Double, val y:Double) {
    fun inRange(validRange:ValidRange) = (x >= validRange.min && x <= validRange.max) && (y >= validRange.min && y <= validRange.max)

    fun asText() = "Position(x=${x.format(2)}, y=${y.format(2)}"
}

fun Double.format(scale: Int) = "%.${scale}f".format(this)

data class Hailstone(val position:Position, val direction:Position) {
    val a = direction.y
    val b = - direction.x
    val c = position.x * direction.y - position.y * direction.x

    fun notParallelWith(other:Hailstone) = a * other.b != b * other.a

    fun positionOfIntersection(other:Hailstone) = Position(xCrossesWith(other), yCrossesWith(other))

    fun xCrossesWith(other: Hailstone) = (c * other.b - other.c * b) / (a * other.b - other.a * b)

    fun yCrossesWith(other: Hailstone) = (other.c * a - c * other.a) / (a * other.b - other.a * b)

    fun intersectionIsFuture(intersection:Position) = (intersection.x - position.x) * direction.x >= 0 && (intersection.y - position.y) * direction.y >= 0
}

data class ValidRange(val min:Double, val max:Double)

fun String.toHailstone() = Hailstone( split(" @ ")[0].toPosition(), split(" @ ")[1].toPosition())
fun String.toPosition() = Position( split(", ")[0].trim().toDouble(), split(", ")[1].trim().toDouble())

fun partTwo(sampleData:List<String>):Int {
    return 0
}