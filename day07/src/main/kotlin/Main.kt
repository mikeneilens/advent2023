import kotlin.math.pow

fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

val cardValues:Map<Char, Int> =
    mapOf('A' to 13, 'K' to 12, 'Q' to 11, 'J' to 10, 'T' to 9, '9' to 8, '8' to 7, '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1, '*' to 0)

enum class HandType(val value:Double, val typeWithWildCard:HandType?) {
    FiveOfAKind(6 * 14.0.pow(5), FiveOfAKind),
    FourOfAKind(5 * 14.0.pow(5), FiveOfAKind),
    FullHouse(4 * 14.0.pow(5), FiveOfAKind),
    ThreeOfAKind(3 * 14.0.pow(5), FourOfAKind),
    TwoPair(2 * 14.0.pow(5), FullHouse),
    OnePair(14.0.pow(5), ThreeOfAKind),
    HighCard(0.0, OnePair);

    fun promoteIf(hasWildCard:Boolean) = if (hasWildCard) this.typeWithWildCard else this

    companion object {
        fun fromHand(hand:HandForGame) = when(hand.noOfDistinctCards) {
            listOf(5) -> FiveOfAKind
            listOf(1,4) -> FourOfAKind.promoteIf(hand.noOfWildCards > 0)
            listOf(2,3) -> FullHouse.promoteIf(hand.noOfWildCards > 0)
            listOf(1,1,3) -> ThreeOfAKind.promoteIf(hand.noOfWildCards > 0)
            listOf(1,2,2) -> if(hand.noOfWildCards == 2 ) FourOfAKind else TwoPair.promoteIf(hand.noOfWildCards > 0)
            listOf(1,1,1,2) -> OnePair.promoteIf(hand.noOfWildCards > 0)
            else ->  HighCard.promoteIf(hand.noOfWildCards > 0)
        }
    }
}

interface HandForGame {
    val noOfDistinctCards:List<Int>
    val noOfWildCards:Int
}

data class Hand(val cards:String, val bid:Int = 0):HandForGame {

    fun totalScore() = (HandType.fromHand(this)?.value ?: 0.0) + cardsScore()

    fun cardsScore() = cards.reversed().mapIndexed{n, c -> 14.0.pow(n) * cardValues.getValue(c)}.sum()

    override val noOfDistinctCards = cardValues.keys.map{ cardSymbol -> cards.count { it == cardSymbol }}.filter { it != 0 }.sorted()

    override val noOfWildCards = cards.filter { it == '*' }.length
}

fun List<String>.toHands() = map{Hand(it.split(" ")[0], it.split(" ")[1].toInt())}

fun List<Hand>.winnings() = sortedBy { it.totalScore() }.foldIndexed(0){i, total, hand -> total + (i + 1) * hand.bid }

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toHands().winnings()
}

fun partTwo(sampleData:List<String>):Int {
    return sampleData.toHandsP2().winnings()
}

fun List<String>.toHandsP2() =
    map{Hand(it.split(" ")[0].replace('J','*'), it.split(" ")[1].toInt())}
