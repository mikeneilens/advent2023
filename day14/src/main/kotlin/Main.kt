
fun partOne(sampleData:List<String>) :Int {
    return sampleData.mapped(List<String>::rollsNorth, ::updateMapForNorth).weight()
}

fun List<String>.rollsNorth() =
    fold(Pair( "#".repeat(size), listOf("0".repeat(size).map{"$it".toInt()}))){
        (lineAbove, rollsAbove),string ->
        Pair(string, rollsAbove + listOf( string.rollsNorth(lineAbove, rollsAbove.last())))
    }.second.drop(1)

fun String.rollsNorth(lineAbove:String, rollsAbove:List<Int>) =
    mapIndexed {i, c -> if (c == '#') 0 else if (lineAbove[i] == '.' ) rollsAbove[i] + 1 else rollsAbove[i]}

fun partTwo(sampleData:List<String>):Int {
    val scores = (0..300).fold(listOf(Pair(sampleData,0))){ a, _ ->
        val cycled = a.last().first.cycled()
        a + listOf(Pair(cycled, cycled.weight()))
    }.map{it.second}
    val mapOfScoreIndices:MutableMap<Int, List<Int>> = mutableMapOf()
    scores.indices.forEach { i -> mapOfScoreIndices[scores[i]] = (mapOfScoreIndices[scores[i]] ?: listOf()) + listOf(i)}
    val firstRepeatingValue = mapOfScoreIndices.toList().filter{it.second.size > 1}
    return scores[ firstRepeatingValue.first().second[0] + (1000000000 - firstRepeatingValue.first().second[0]) % (firstRepeatingValue.first().second[1] - firstRepeatingValue.first().second[0])]
}

fun List<String>.cycled():List<String> =
    mapped(List<String>::rollsNorth, ::updateMapForNorth)
        .mapped(List<String>::rollsWest, ::updateMapForWest)
        .reversed().mapped(List<String>::rollsNorth, ::updateMapForNorth).reversed()
        .map{it.reversed()}.mapped(List<String>::rollsWest, ::updateMapForWest).map { it.reversed() }

fun List<String>.rollsWest() = map(String::rollsWest)

fun String.rollsWest() =
    foldIndexed(listOf<Int>()){i, result, c ->
        if (i == 0 || c == '#')
            result + listOf(0)
        else if (this[i -1] == '.' ) result + listOf(result.last() + 1)
        else result + listOf(result.last())
    }

fun List<String>.mapped(rollsRocks:List<String>.()->List<List<Int>>, mapUpdater:MapUpdater):List<String> = mapWithRolls(rollsRocks(), mapUpdater)

private fun List<String>.mapWithRolls(rollsNorth: List<List<Int>>, updater:(MutableMap<Pair<Int, Int>, Char>,Int, Int, Char, List<List<Int>>)->Unit): List<String> {
    val map: MutableMap<Pair<Int, Int>, Char> = mutableMapOf()
    forEachIndexed { row, string ->
        string.forEachIndexed { col, c ->
            if (c != '.') updater(map, row, col, c, rollsNorth)
        }
    }
    return mapIndexed { row, string -> string.indices.map { col -> map[Pair(row, col)] ?: '.' }.joinToString("") }
}

typealias MapUpdater = (MutableMap<Pair<Int, Int>, Char>,Int, Int, Char, List<List<Int>>)->Unit

fun updateMapForNorth(map: MutableMap<Pair<Int, Int>, Char>, row: Int, col: Int, c: Char, rollsNorth: List<List<Int>>) {
    map[Pair(row - rollsNorth[row][col], col)] = c
}

fun updateMapForWest(map: MutableMap<Pair<Int, Int>, Char>, row: Int, col: Int, c: Char, rollsWest: List<List<Int>>) {
    map[Pair(row, col - rollsWest[row][col])] = c
}

fun List<String>.weight() = mapIndexed {i, s -> (size - i) * s.count { it == 'O' } }.sum()

