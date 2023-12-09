import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testData = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent().split("\n")

    "part one" should ({
        "parse test data" {
            testData.parse() shouldBe listOf(listOf(0,3, 6, 9, 12, 15),listOf(1, 3, 6, 10, 15, 21),listOf(10, 13, 16, 21, 30, 45))
        }
        "0 3 6 9 12 15 differences are listOf(3, 3, 3, 3, 3)" {
            listOf(0, 3, 6, 9, 12, 15).listOfDifferences() shouldBe  listOf(3, 3, 3, 3, 3)
        }
        "10 13 16 21 30 45 differences are listOf(3, 3, 3, 3, 3)" {
            listOf(10, 13, 16, 21, 30, 45).listOfDifferences() shouldBe  listOf(3, 3, 5, 9, 15)
        }
        "0 3 6 9 12 15 all differences are listOf(listOf(3, 3, 3, 3, 3),listOf(0, 0, 0, 0))" {
            listOf(0, 3, 6, 9, 12, 15).allDifferences() shouldBe  listOf(listOf(3, 3, 3, 3, 3),listOf(0, 0, 0, 0))
        }
        "0 3 6 9 12 15 sum of differences is 18" {
            listOf(0, 3, 6, 9, 12, 15).sumLast() shouldBe 18
        }
        "part one using testData should be 114" {
            partOne(testData) shouldBe 114
        }
        "part one using sample data should be 2105961943" {
            partOne(sampleData) shouldBe 2105961943
        }

    })
    "part two" should ({
        "part two using test data should be 2" {
             partTwo(testData) shouldBe 2
        }
        "part two using sample data should be 1019" {
             partTwo(sampleData) shouldBe 1019
        }
    })
})