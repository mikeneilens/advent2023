import kotlin.math.pow

fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun partOne(sampleData:List<String>) :Int {
    return sampleData.sumOf { it.scoreForGame() }
}

fun String.toWinningNumbers() = winningNumbersString().split(" ").map(String::toInt)
fun String.toYourNumbers() = yourNumbersString().split(" ").map(String::toInt)

fun String.winningNumbersString() = split(": ").last().split(" | ").first().replace("  "," ").trim()
fun String.yourNumbersString()= split(": ").last().split(" | ").last().replace("  "," ").trim()

fun yourNumbersThatWin(winningNumbers:List<Int>, yourNumbers:List<Int>) = yourNumbers.filter{it in winningNumbers}

fun List<Int>.valueOfWinningNumbers() = if (size > 0) 2.0.pow(size - 1).toInt() else 0

fun String.scoreForGame() = yourNumbersThatWin(toWinningNumbers(), toYourNumbers()).valueOfWinningNumbers()

fun partTwo(sampleData:List<String>):Int {
    return 0
}