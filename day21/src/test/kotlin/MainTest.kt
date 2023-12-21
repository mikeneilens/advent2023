import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testdata1 = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent().split("\n")

    "part one" should ({

        "surrounding postiions in testdata1 of S are "{
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            positionOfS.surroundingPositions(map) shouldBe listOf(Position(row=5, col=4), Position(row=4, col=5))
        }
        "walking 1 steps with test data 1 gives 2 locations" {
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            walk(listOf(positionOfS),0,1,map) shouldBe listOf(Position(row=5, col=4), Position(row=4, col=5))
        }
        "walking 2 steps with test data 1 gives 4 locations" {
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            walk(listOf(positionOfS),0,2,map) shouldBe listOf(Position(row=5, col=5), Position(row=5, col=3), Position(row=6, col=4), Position(row=3, col=5))
        }
        "walking 6 steps with test data 1 gives 16 locations" {
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            walk(listOf(positionOfS),0,6,map).size shouldBe 16
        }
        "part one should be 3642" {
            partOne(sampleData) shouldBe 3642
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 608603023105276L
        }
    })
})