fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun String.toValues() = fold(""){result, char ->
    if (result == "" || (char == ' ' && result.last() != ' ' ) )
        result + ' '
    else if (char != ' ') result + char else result
}.split(" ").drop(2).map{it.trim().toInt()}

data class TimeDistance(val time:Int, val distance:Int) {
    fun winners() = (0..time).filter{timeButtonPressed ->  timeButtonPressed * (time - timeButtonPressed) > distance}
}

fun List<String>.toTimeDistance() = first().toValues().zip(last().toValues()).map{ TimeDistance(it.first, it.second)}

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toTimeDistance().map(TimeDistance::winners).fold(1){a, v -> a * v.size}
}

fun partTwo(sampleData:List<String>):Int {
    return 0
}