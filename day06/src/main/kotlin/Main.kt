fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun String.toValues() = fold(""){result, char ->
    if (result == "" || (char == ' ' && result.last() != ' ' ) )
        result + ' '
    else if (char != ' ') result + char else result
}.split(" ").drop(2).map{it.trim().toLong()}

data class TimeDistance(val time:Long, val distance:Long) {
    fun distanceTravelled(timeButtonPressed:Long) = timeButtonPressed * (time - timeButtonPressed)

    fun winners() = (0..time).filter{timeButtonPressed ->  distanceTravelled(timeButtonPressed) > distance}

    fun indexOfFirstWinner() = (0..time).indexOfFirst{distanceTravelled(it) > distance}.toLong()

    fun numberOfWinners() = (indexOfFirstWinner()..time).countWhile{ distanceTravelled(it) > distance }
}

fun List<String>.toTimeDistance() = first().toValues().zip(last().toValues()).map{ TimeDistance(it.first, it.second)}

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toTimeDistance().map(TimeDistance::winners).fold(1){a, v -> a * v.size}
}

fun partTwo(sampleData:List<String>):Long {
    return TimeDistance(sampleData.first().toValue(), sampleData.last().toValue()).numberOfWinners()
}

fun String.toValue() = fold(""){result, char ->
    if (result == "" || (char == ' ' && result.last() != ' ' ) )
        result + ' '
    else if (char != ' ') result + char else result
}.split(" ").drop(2).joinToString("").trim().toLong()

//helper function to count until the predicate is false
inline fun <T> Iterable<T>.countWhile(predicate: (T) -> Boolean): Long {
    var s = 0L
    for (item in this) {
        if (!predicate(item))
            break
        s ++
    }
    return s
}
