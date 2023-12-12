import kotlin.math.pow

fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun String.matches(patternMapping:List<Int>) =
    fold(listOf(mutableListOf<Char>())){ result, char ->
        if (result.last().isEmpty() || result.last().last() != char) result + listOf(mutableListOf(char))
        else {
            result.last().add(char)
            result}
    }.filter{it.contains('#')}.map{it.size} == patternMapping

fun String.qPositions() = mapIndexedNotNull { index, c -> if (c == '?') index else null }

fun String.replaceQMarks(qPositions:List<Int>, binaryPattern:String) =
    mapIndexed { index, c ->
        val indexOfQ = qPositions.indexOfFirst{it == index}
        if (indexOfQ >= 0 ) binaryPattern[indexOfQ] else c
    }.joinToString("")

fun Int.toPattern(length:Int) = ("0".repeat(length) + toString(2))
    .takeLast(length)
    .replace('1','#')
    .replace('0','.')

fun binaryPatterns(qPositions:List<Int>, missingSprings:Int):List<String> =
    (0..(2.0.pow(qPositions.size).toInt())).map{ it.toPattern(qPositions.size)}.dropLast(1).filter{it.count{it == '#'} == missingSprings}

fun String.variants(missingSprings: Int):List<String> {
    val qPositions = qPositions()
    val binaryPatterns = binaryPatterns(qPositions, missingSprings)
    return binaryPatterns.map{ this.replaceQMarks(qPositions, it) }
}

data class Springs(val status:String, val groups:List<Int>) {
    val missingSprings = groups.sum() - status.count { it == '#' }
}

fun String.toSprings()= Springs(  split(" ").first(), split(" ").last().split(",").map(String::toInt))

fun partOne(sampleData:List<String>) :Int {
    return sampleData.map(String::toSprings).sumOf{springs -> springs.status.variants(springs.missingSprings).count{it.matches(springs.groups)}}
}
fun partTwo(sampleData:List<String>):Int {
    return 0
}