import kotlin.math.pow

fun main() {
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

fun List<Int>.valueOfWinningNumbers() = if (isNotEmpty()) 2.0.pow(size - 1).toInt() else 0

fun String.scoreForGame() = yourNumbersThatWin(toWinningNumbers(), toYourNumbers()).valueOfWinningNumbers()

fun partTwo(sampleData:List<String>):Int {
    return sampleData.map { yourNumbersThatWin(it.toWinningNumbers(), it.toYourNumbers())}
        .createGameNodes()
        .sumOf{it.noOfCards()}
}

data class Game(val gameNode:GameNode, val indicesOfSubsequentGameNumbers:List<Int>) {

    fun updateSubsequentGameNodes(games:List<Game>) {
        gameNode.subsequentGameNodes = indicesOfSubsequentGameNumbers.map { index -> games[index].gameNode}
    }
}

class GameNode(val gameNo:Int, var subsequentGameNodes:List<GameNode> = emptyList() ) {
    fun noOfCards():Int = 1 + subsequentGameNodes.sumOf { it.noOfCards() }
}

fun List<List<Int>>.createGameNodes():List<GameNode> {
    val games = createListOfGames()
    return games.map {game -> game.apply{updateSubsequentGameNodes(games)}.gameNode }
}

fun List<List<Int>>.createListOfGames() =
    mapIndexed { i, winningNumbers -> Game(GameNode(i + 1), winningNumbers.indicesOfSubsequentGameNumbers(i + 1) ) }

fun List<Int>.indicesOfSubsequentGameNumbers(gameNo:Int) = if ( isEmpty()) listOf() else ((gameNo) until gameNo + size).toList()

