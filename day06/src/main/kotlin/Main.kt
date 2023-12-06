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
    fun winners() = (0..time).filter{timeButtonPressed ->  timeButtonPressed * (time - timeButtonPressed) > distance}

    fun indexOfFirstWinner() = (0..time).indexOfFirst{timeButtonPressed ->  timeButtonPressed * (time - timeButtonPressed) > distance}.toLong()

    fun numberOfWinners():Long {
        val start = indexOfFirstWinner()
        var timeButtonPressed = start
        while (timeButtonPressed * (time-timeButtonPressed) > distance) { timeButtonPressed++ }
        return timeButtonPressed - start
    }
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
