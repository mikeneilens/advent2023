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
    val winnersForEachCard = sampleData.map { yourNumbersThatWin(it.toWinningNumbers(), it.toYourNumbers())}
    val gameNodes = winnersForEachCard.createGameNodes()
    return gameNodes.sumOf{it.noOfCards()}
}

data class Game(val gameNode:GameNode, val subsequentGameNumbers:List<Int>)

class GameNode(val gameNo:Int, var subsequentGames:List<GameNode> ) {
    fun noOfCards():Int = 1 + subsequentGames.sumOf { it.noOfCards() }
}

fun List<List<Int>>.createGameNodes():List<GameNode> {
    val games = mapIndexed { i, winningNumbers ->
        Game(GameNode(i + 1, listOf()), winningNumbers.subsequentGameNumbers(i + 1) ) }
    games.forEach {game -> game.gameNode.subsequentGames = game.subsequentGameNumbers.map {gameNo -> games[gameNo - 1].gameNode}}
    return games.map{it.gameNode}
}

fun List<Int>.subsequentGameNumbers(gameNo:Int) = if ( isEmpty()) listOf() else ((gameNo + 1)..(gameNo + size)).toList()