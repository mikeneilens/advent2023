fun partOne(sampleData:List<String>) :Int {
    return sampleData.parse().sumOf { it.sumLast() }
}

fun List<String>.parse() = map{it.split(" ").map(String::toInt)}

fun List<Int>.toDifference():List<Int> = drop(1).fold(Pair(first(), listOf<Int>())){(last, result), v -> Pair(v, result + (v - last))}.second

tailrec fun List<Int>.allDifferences(result:List<List<Int>> = mutableListOf() ):List<List<Int>> {
    if (all { it==0 }) return result
    else {
        val differenceList:List<Int> = toDifference()
        return differenceList.allDifferences(result + listOf(differenceList))
    }
}

fun List<Int>.sumLast():Int = last() + allDifferences().sumOf{it.last()}

fun partTwo(sampleData:List<String>):Int {
    return sampleData.parse().sumOf { it.sumFirst() }
}

fun List<Int>.sumFirst():Int = first() - allDifferences().reversed().map{it.first()}.fold(0){a, v ->  v - a }
