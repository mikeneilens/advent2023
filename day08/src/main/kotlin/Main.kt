fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

enum class Direction { Left, Right }

data class Instructions(val directions:List<Direction>, var count:Int = 0 ) {
    fun nextStep():Direction = directions[count++ % directions.size]
}

fun List<String>.toInstructions() = Instructions(directions  = first().toDirections())
fun String.toDirections() = map {if (it == 'L') Direction.Left else Direction.Right}

typealias Nodes = Map<String, Map<Direction, String>>

fun Nodes.get(key:String, direction:Direction) = getValue(key).getValue(direction)

fun List<String>.toNodes(nodes:MutableMap<String, Map<Direction, String>> = mutableMapOf()):Nodes {
    drop(2).forEach { string ->
        nodes[string.substring(0..2)] = mapOf(Direction.Left to string.substring(7..9), Direction.Right to string.substring(12..14)  )
    }
    return nodes
}

fun partOne(sampleData:List<String>) :Int {
    val instructions = sampleData.toInstructions()
    val nodes = sampleData.toNodes()
    return countToTarget(nodes, instructions, "AAA") { it == "ZZZ" }
}

fun countToTarget(nodes: Nodes, instructions: Instructions, startKey:String, target:(String)->Boolean): Int {
    var key = startKey
    while (!target(key)) {
        key = nodes.get(key, instructions.nextStep())
    }
    return instructions.count
}

fun partTwo(sampleData:List<String>):Long {
    val nodes = sampleData.toNodes()
    return nodes.keys.filter{it.endsWith('A')}.map { startKey ->
        val instructions = sampleData.toInstructions()
        countToTarget(nodes, instructions, startKey) { it.endsWith('Z') }.toLong()
    }.lowestCommonMultiple()
}


fun List<Long>.lowestCommonMultiple(): Long =
    fold(1L) { x, y -> x * (y / greatestCommonDenominator(x, y)) }

tailrec fun greatestCommonDenominator(x: Long, y: Long):Long =
    if (y == 0L) x else greatestCommonDenominator(y, x % y)
