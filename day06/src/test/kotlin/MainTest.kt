import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent().split("\n")
    "part one" should ({
        "Time:      7  15   30 to times is 7,15,30" {
            "Time:      7  15   30".toValues() shouldBe listOf(7,15,30)
        }
        "Distance:  9  40  200 to distances is 9,40,200" {
            "Distance:  9  40  200".toValues() shouldBe listOf(9,40,200)
        }
        "Time Distances for test data are TimeDistance(7,9), TimeDistance(15,40), TimeDistance(30,200)" {
            testData.toTimeDistance() shouldBe listOf(TimeDistance(7,9), TimeDistance(15,40), TimeDistance(30,200))
        }
        "Winners in TimeDistance(7,9) are 2,3,4,5 " {
            TimeDistance(7,9).winners() shouldBe listOf(2,3,4,5)
        }
        "part one using test data should be 288" {
            partOne(testData) shouldBe 288
        }
        "part one using sample data should be 303600" {
            partOne(sampleData) shouldBe 303600
        }
    })
    "part two" should ({
        "Time:      7  15   30 as a single time is 71530" {
            "Time:      7  15   30".toValue() shouldBe 71530L
        }
        "Distance:  9  40  200 as a single time is 940200" {
            "Distance:  9  40  200".toValue() shouldBe 940200L
        }
        "count while 2..7 is < 5 is 3 " {
            (2..7).countWhile{ it < 5 } shouldBe 3
        }
        "part two with testdata should be 71503" {
            partTwo(testData) shouldBe 71503
        }
        "part two with sample data should be 23654842" {
            partTwo(sampleData) shouldBe 23654842
        }
    })
})