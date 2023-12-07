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
        "cardsScore of cards 23 is 16" {
            Hand("23").cardsScore() shouldBe 16.0
        }
        "cardsScore of cards 33 is 30" {
            Hand("33").cardsScore() shouldBe 30.0
        }
        "cardsScore of cards 43 is 44" {
            Hand("43").cardsScore() shouldBe 44.0
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
        "QQQQ* handtype is five of a kind" {
           Hand("QQQQ*").handType() shouldBe HandType.FiveOfAKind
        }
        "QQQQ2 handtype is four of a kind" {
           Hand("QQQQ2").handType() shouldBe HandType.FourOfAKind
        }
        "best hand type for 1234* is one pair" {
            Hand("1234*").handType() shouldBe HandType.OnePair
        }
        "best hand type for *234* is three of a kind" {
            Hand("*234*").handType() shouldBe HandType.ThreeOfAKind
        }
        "best hand type for 2234* is three of a kind" {
            Hand("2234*").handType() shouldBe HandType.ThreeOfAKind
        }
        "best hand type for 2233* is full house" {
            Hand("2233*").handType() shouldBe HandType.FullHouse
        }
        "best hand type for 22**4 is four of a kind" {
            Hand("22**4").handType() shouldBe HandType.FourOfAKind
        }
        "best hand type for ***23 is four of a kind" {
            Hand("***23").handType() shouldBe HandType.FourOfAKind
        }
        "best hand type for T55*5 is four of a kind" {
            Hand("T55*5").handType() shouldBe HandType.FourOfAKind
        }
        "best hand type for 22**2 is five of a kind" {
            Hand("22**2").handType() shouldBe HandType.FiveOfAKind
        }
        "best hand type for 2222* is five of a kind" {
            Hand("2222*").handType() shouldBe HandType.FiveOfAKind
        }
        "best hand type for ****2 is five of a kind" {
            Hand("****2").handType() shouldBe HandType.FiveOfAKind
        }
        "*KKK2 is weaker than QQQQ2" {
            val hands = listOf("*KKK2 123", "QQQQ2 123").toHandsP2()
            (hands[0].totalScore() < hands[1].totalScore()) shouldBe true
        }
        "***** is stronger than QQQQ2" {
            val hands = listOf("***** 123", "QQQQ2 123").toHandsP2()
            (hands[0].totalScore() > hands[1].totalScore()) shouldBe true
        }
        "testData winnings is 5905" {
            testData.toHandsP2().winnings() shouldBe 5905
        }

        "part two should be 0" {
            partTwo(sampleData) shouldBe 248747492
        }
    })
})