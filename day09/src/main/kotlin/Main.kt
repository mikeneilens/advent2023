fun partOne(sampleData:List<String>) :Int {
    return sampleData.parse().sumOf(History::sumLast)
}

typealias History = List<Int>

fun List<String>.parse() = map{it.split(" ").map(String::toInt)}

fun History.listOfDifferences() = windowed(2,1).map{it.last() - it.first()}

tailrec fun List<Int>.allDifferences(result:List<History> = listOf() ):List<History> =
    if (all { it==0 }) result
    else listOfDifferences().allDifferences(result + listOf(listOfDifferences()))

fun History.sumLast() = last() + allDifferences().sumOf(History::last)

fun partTwo(sampleData:List<String>):Int {
    return sampleData.parse().map(List<Int>::reversed). sumOf(History::sumLast)
}
