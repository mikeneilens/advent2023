
import java.util.*


fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

enum class Direction {
    Left, Right
}

data class Instructions(var nextStep:Int = 0, val directions:List<Direction>, var count:Int = 0 ) {
    fun nextStep():Direction {
        val next = directions[nextStep]
        if (nextStep < directions.lastIndex) nextStep ++ else nextStep = 0
        count ++
        return next
    }
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
    var key = "AAA"
    while (key != "ZZZ") {
        key = nodes.get(key, instructions.nextStep())
    }
    return instructions.count
}

fun partTwo(sampleData:List<String>):Long {
    val nodes = sampleData.toNodes()
    val nodesEndingInA= nodes.keys.filter{it.endsWith('A')}
    val instructions = nodesEndingInA.map{sampleData.toInstructions()}
    var keys = nodesEndingInA.toMutableList()
    val factors = keys.mapIndexed { i, startKey ->
        var key = startKey
        while (!key.endsWith('Z')) {
            key = nodes.get(key, instructions[i].nextStep())
        }
        instructions[i].count.toLong()
    }
    return lcm(factors)
}

private fun gcd(x: Long, y: Long): Long {
    return if (y == 0L) x else gcd(y, x % y)
}

fun lcm(numbers: List<Long>): Long {
    return numbers.fold(
        1L
    ) { x, y -> x * (y / gcd(x, y)) }
}