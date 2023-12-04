import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testData = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent().split("\n")

    "part one" should ({
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53 winning numbers are [41,48,83,86,17]" {
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53".toWinningNumbers() shouldBe listOf(41,48,83,86,17)
        }
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53 your numbers are [83,86,6,31,17,9,48,53]" {
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53".toYourNumbers() shouldBe listOf(83,86,6,31,17,9,48,53)
        }
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53 your numbers that win are [48, 83, 17, 86]" {
            val line = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
            val yourNumbers = line.toYourNumbers()
            val winninigNumbers = line.toWinningNumbers()
            yourNumbersThatWin(winninigNumbers, yourNumbers) shouldBe listOf(83, 86, 17, 48)
        }
        "winning numbers [48, 83, 17, 86] has value of 8 " {
            listOf(83, 86, 17, 48).valueOfWinningNumbers() shouldBe 8
        }
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53 has score of 8" {
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53".scoreForGame() shouldBe 8
        }
        "part one using test data should be 13" {
            partOne(testData) shouldBe 13
        }
        "part one using sample data should be 21138" {
            partOne(sampleData) shouldBe 21138
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})