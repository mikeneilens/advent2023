import kotlin.math.pow

fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

enum class HandType(val value:Double) {
    FiveOfAKind(6 * 13.0.pow(5)),
    FourOfAKind(5 * 13.0.pow(5)),
    FullHouse(4 * 13.0.pow(5)),
    ThreeOfAKind(3 * 13.0.pow(5)),
    TwoPair(2 * 13.0.pow(5)),
    OnePair(13.0.pow(5)),
    HighCard(0.0)
}

data class Hand(val cards:String, val bid:Int = 0, val cardValues:Map<Char, Int> =
    mapOf('A' to 12, 'K' to 11, 'Q' to 10, 'J' to 9, 'T' to 8, '9' to 7, '8' to 6, '7' to 5, '6' to 4, '5' to 3, '4' to 2, '3' to 1, '2' to 0)
    ) {

    fun totalScore() = handType().value + cardsScore()

    fun totalScoreP2() = handTypeP2().value + cardsScore()

    fun cardsScore() = cards.reversed().mapIndexed{n, c -> 13.0.pow(n) * cardValues.getValue(c)}.sum()

    fun toMappedCards(map:MutableMap<Char, Int> = mutableMapOf()):Map<Char, Int> {
        cards.forEach{c -> map[c] = (map[c] ?: 0) + 1}
        return map
    }

    fun handType() = when(toMappedCards().values.sorted()) {
        listOf(5) -> HandType.FiveOfAKind
        listOf(1,4) -> HandType.FourOfAKind
        listOf(2,3) -> HandType.FullHouse
        listOf(1,1,3) -> HandType.ThreeOfAKind
        listOf(1,2,2) -> HandType.TwoPair
        listOf(1,1,1,2) -> HandType.OnePair
        else -> HandType.HighCard
    }

    fun handTypeP2() = when(toMappedCards().values.sorted()) {
        listOf(5) -> HandType.FiveOfAKind
        listOf(1,4) -> if(cards.contains('J')) HandType.FiveOfAKind else HandType.FourOfAKind
        listOf(2,3) -> if(cards.contains('J')) HandType.FiveOfAKind else HandType.FullHouse
        listOf(1,1,3) -> if(cards.quantityOf('J') == 1  || cards.filter{it == 'J'}.length == 3) HandType.FourOfAKind else HandType.ThreeOfAKind
        listOf(1,2,2) -> if(cards.quantityOf('J') == 2) HandType.FourOfAKind else if(cards.quantityOf('J') == 1) HandType.FullHouse else HandType.TwoPair
        listOf(1,1,1,2) -> if(cards.quantityOf('J') in 1..2) HandType.ThreeOfAKind else HandType.OnePair
        else -> if(cards.quantityOf('J') == 1) HandType.OnePair else HandType.HighCard
    }
}

fun List<String>.toHands() = map{Hand(it.split(" ")[0], it.split(" ")[1].toInt())}

fun List<Hand>.winnings() = sortedBy { it.totalScore() }.mapIndexed {i,c -> Pair((i + 1), c) }.sumOf { it.first * it.second.bid }

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toHands().winnings()
}

fun partTwo(sampleData:List<String>):Int {
    return sampleData.toHandsP2().winningsP2()
}

fun List<Hand>.winningsP2() = sortedBy { it.totalScoreP2() }.mapIndexed { i, c -> Pair((i + 1), c) }.sumOf { it.first * it.second.bid }

fun List<String>.toHandsP2(cardValues:Map<Char, Int> = mapOf(
    'A' to 12, 'K' to 11, 'Q' to 10, 'J' to 0, 'T' to 9, '9' to 8, '8' to 7, '7' to 6, '6' to 5, '5' to 4, '4' to 3, '3' to 2, '2' to 1)) =
    map{Hand(it.split(" ")[0], it.split(" ")[1].toInt(), cardValues)}

fun String.quantityOf(c:Char) = filter{it == 'J'}.length
