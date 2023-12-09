fun partOne(sampleData:List<String>) :Int {
    return sampleData.parse().sumOf { it.sumLast() }
}

typealias History = List<Int>

fun List<String>.parse() = map{it.split(" ").map(String::toInt)}

fun History.listOfDifferences() = drop(1).fold(Pair(first(), listOf<Int>())){ (last, result), v -> Pair(v, result + (v - last))}.second

tailrec fun List<Int>.allDifferences(result:List<History> = listOf() ):List<History> =
    if (all { it==0 }) result
    else listOfDifferences().allDifferences(result + listOf(listOfDifferences()))

fun History.sumLast() = last() + allDifferences().sumOf{it.last()}

fun partTwo(sampleData:List<String>):Int {
    return sampleData.parse().sumOf { it.sumFirst() }
}

fun History.sumFirst():Int = first() - allDifferences().reversed().map{it.first()}.fold(0){a, v ->  v - a }
