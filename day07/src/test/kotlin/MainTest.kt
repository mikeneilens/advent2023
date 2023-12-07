import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent().split("\n")
    "part one" should ({
        "cardsScore of cards 23 is 1" {
            Hand("23").cardsScore() shouldBe 1.0
        }
        "cardsScore of cards 33 is 14" {
            Hand("33").cardsScore() shouldBe 14.0
        }
        "cardsScore of cards 43 is 27" {
            Hand("43").cardsScore() shouldBe 27.0
        }
        "AAAAA is five of a kind" {
            Hand("AAAAA").handType() shouldBe HandType.FiveOfAKind
        }
        "AA8AA is four of a kind" {
            Hand("AA8AA").handType() shouldBe HandType.FourOfAKind
        }
        "23332 is full house" {
            Hand("23332").handType() shouldBe HandType.FullHouse
        }
        "TTT98 is three of a kind" {
            Hand("TTT98").handType() shouldBe HandType.ThreeOfAKind
        }
        "23432 is two pair" {
            Hand("23432").handType() shouldBe HandType.TwoPair
        }
        "A23A4 is one pair" {
            Hand("A23A4").handType() shouldBe HandType.OnePair
        }
        "23456 is High card" {
            Hand("23456").handType() shouldBe HandType.HighCard
        }
        "testData winnings is 6440" {
            testData.toHands().winnings() shouldBe 6440
        }
        "part one should be 247815719" {
            partOne(sampleData) shouldBe 247815719
        }
    })
    "part two" should ({
        "QQQQJ handtype is five of a kind" {
           Hand("QQQQJ").handTypeP2() shouldBe HandType.FiveOfAKind
        }
        "QQQQ2 handtype is four of a kind" {
           Hand("QQQQ2").handTypeP2() shouldBe HandType.FourOfAKind
        }
        "best hand type for 1234J is one pair" {
            Hand("1234J").handTypeP2() shouldBe HandType.OnePair
        }
        "best hand type for J234J is three of a kind" {
            Hand("J234J").handTypeP2() shouldBe HandType.ThreeOfAKind
        }
        "best hand type for 2234J is three of a kind" {
            Hand("2234J").handTypeP2() shouldBe HandType.ThreeOfAKind
        }
        "best hand type for 2233J is full house" {
            Hand("2233J").handTypeP2() shouldBe HandType.FullHouse
        }
        "best hand type for 22JJ4 is four of a kind" {
            Hand("22JJ4").handTypeP2() shouldBe HandType.FourOfAKind
        }
        "best hand type for JJJ23 is four of a kind" {
            Hand("JJJ23").handTypeP2() shouldBe HandType.FourOfAKind
        }
        "best hand type for T55J5 is four of a kind" {
            Hand("T55J5").handTypeP2() shouldBe HandType.FourOfAKind
        }
        "best hand type for 22JJ2 is five of a kind" {
            Hand("22JJ2").handTypeP2() shouldBe HandType.FiveOfAKind
        }
        "best hand type for 2222J is five of a kind" {
            Hand("2222J").handTypeP2() shouldBe HandType.FiveOfAKind
        }
        "best hand type for JJJJ2 is five of a kind" {
            Hand("JJJJ2").handTypeP2() shouldBe HandType.FiveOfAKind
        }
        "JKKK2 is weaker than QQQQ2" {
            val hands = listOf("JKKK2 123", "QQQQ2 123").toHandsP2()
            (hands[0].totalScoreP2() < hands[1].totalScoreP2()) shouldBe true
        }
        "JJJJJ is stronger than QQQQ2" {
            val hands = listOf("JJJJJ 123", "QQQQ2 123").toHandsP2()
            (hands[0].totalScoreP2() > hands[1].totalScoreP2()) shouldBe true
        }
        "testData winnings is 5905" {
            testData.toHandsP2().winningsP2() shouldBe 5905
        }

        "part two should be 0" {
            partTwo(sampleData) shouldBe 248747492
        }
    })
})