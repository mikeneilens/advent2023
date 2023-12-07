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
        "number of each kind of card in 13331 is listOf(2,3)" {
            Hand("43334").noOfDistinctCards shouldBe listOf(2,3)
        }
        "AAAAA is five of a kind" {
            HandType.fromHand(Hand("AAAAA")) shouldBe HandType.FiveOfAKind
        }
        "AA8AA is four of a kind" {
            HandType.fromHand(Hand("AA8AA")) shouldBe HandType.FourOfAKind
        }
        "23332 is full house" {
            HandType.fromHand(Hand("23332")) shouldBe HandType.FullHouse
        }
        "TTT98 is three of a kind" {
            HandType.fromHand(Hand("TTT98")) shouldBe HandType.ThreeOfAKind
        }
        "23432 is two pair" {
            HandType.fromHand(Hand("23432")) shouldBe HandType.TwoPair
        }
        "A23A4 is one pair" {
            HandType.fromHand(Hand("A23A4")) shouldBe HandType.OnePair
        }
        "23456 is High card" {
            HandType.fromHand(Hand("23456")) shouldBe HandType.HighCard
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
            HandType.fromHand(Hand("QQQQ*")) shouldBe HandType.FiveOfAKind
        }
        "QQQQ2 handtype is four of a kind" {
            HandType.fromHand(Hand("QQQQ2")) shouldBe HandType.FourOfAKind
        }
        "best hand type for 1234* is one pair" {
            HandType.fromHand(Hand("1234*")) shouldBe HandType.OnePair
        }
        "best hand type for *234* is three of a kind" {
            HandType.fromHand(Hand("*234*")) shouldBe HandType.ThreeOfAKind
        }
        "best hand type for 2234* is three of a kind" {
            HandType.fromHand(Hand("2234*")) shouldBe HandType.ThreeOfAKind
        }
        "best hand type for 2233* is full house" {
            HandType.fromHand(Hand("2233*")) shouldBe HandType.FullHouse
        }
        "best hand type for 22**4 is four of a kind" {
            HandType.fromHand(Hand("22**4")) shouldBe HandType.FourOfAKind
        }
        "best hand type for ***23 is four of a kind" {
            HandType.fromHand(Hand("***23")) shouldBe HandType.FourOfAKind
        }
        "best hand type for T55*5 is four of a kind" {
            HandType.fromHand(Hand("T55*5")) shouldBe HandType.FourOfAKind
        }
        "best hand type for 22**2 is five of a kind" {
            HandType.fromHand(Hand("22**2")) shouldBe HandType.FiveOfAKind
        }
        "best hand type for 2222* is five of a kind" {
            HandType.fromHand(Hand("2222*")) shouldBe HandType.FiveOfAKind
        }
        "best hand type for ****2 is five of a kind" {
            HandType.fromHand(Hand("****2")) shouldBe HandType.FiveOfAKind
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

        "part two should be248747492" {
            partTwo(sampleData) shouldBe 248747492
        }
    })
})