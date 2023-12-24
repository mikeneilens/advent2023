import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testdata = """
        19, 13, 30 @ -2,  1, -2
        18, 19, 22 @ -1, -1, -2
        20, 25, 34 @ -2, -2, -4
        12, 31, 28 @ -1, -2, -1
        20, 19, 15 @  1, -5, -3
    """.trimIndent().split("\n")

    "part one" should ({
        "convert 19, 13, 30 @ -2,  1, -2 to a hailstone" {
            "19, 13, 30 @ -2,  1, -2".toHailstone() shouldBe Hailstone(Position(19.0,13.0), Position(-2.0,1.0))
        }
        "Hailstone A: 19, 13, 30 @ -2, 1, -2, Hailstone B: 18, 19, 22 @ -1, -1, -2 cross at x=14.333, y=15.333" {
            val hailstoneA = "19, 13, 30 @ -2, 1, -2".toHailstone()
            val hailstoneB = "18, 19, 22 @ -1, -1, -2".toHailstone()
            val intersection = hailstoneA.positionOfIntersection(hailstoneB)
            intersection.asText() shouldBe Position(14.333, 15.333).asText()
            hailstoneA.intersectionIsFuture(intersection) shouldBe true
        }
        "Hailstone A: 19, 13, 30 @ -2, 1, -2, Hailstone B: 20, 19, 15 @ 1, -5, -3 did not cross in the future"{
            val hailstoneA = "19, 13, 30 @ -2, 1, -2".toHailstone()
            val hailstoneB = "20, 19, 15 @ 1, -5, -3".toHailstone()
            val intersection = hailstoneA.positionOfIntersection(hailstoneB)
            hailstoneA.intersectionIsFuture(intersection) shouldBe false
        }
        "Hailstone A: 18, 19, 22 @ -1, -1, -2, Hailstone B: 20, 25, 34 @ -2, -2, -4 are parallel so never cross" {
            val hailstoneA = "18, 19, 22 @ -1, -1, -2".toHailstone()
            val hailstoneB = "20, 25, 34 @ -2, -2, -4".toHailstone()
            hailstoneA.isParallelWith(hailstoneB) shouldBe true
        }
        "Hailstone A: 19, 13, 30 @ -2, 1, -2, Hailstone B: 20, 25, 34 @ -2, -2, -4 intersect inside the test ara" {
            val hailstoneA = "19, 13, 30 @ -2, 1, -2".toHailstone()
            val hailstoneB = "20, 25, 34 @ -2, -2, -4".toHailstone()
            val intersection = hailstoneA.positionOfIntersection(hailstoneB)
            intersection.inRange(ValidRange(7.0, 27.0)) shouldBe true
        }
        "Hailstone A: 19, 13, 30 @ -2, 1, -2, Hailstone B: 12, 31, 28 @ -1, -2, -1 intersect outside the test ara" {
            val hailstoneA = "19, 13, 30 @ -2, 1, -2".toHailstone()
            val hailstoneB = "12, 31, 28 @ -1, -2, -1".toHailstone()
            val intersection = hailstoneA.positionOfIntersection(hailstoneB)
            intersection.inRange(ValidRange(7.0, 27.0)) shouldBe false
        }
        "part one with testdata should be 2" {
            partOne(testdata, ValidRange(7.0, 27.0)) shouldBe 2
        }
        "part one with sampleData should be 16812" {
            partOne(sampleData, ValidRange(200000000000000.0, 400000000000000.0)) shouldBe 16812
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})