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
            positionOfS.surroundingPositions(map,11) shouldBe listOf(Position(row=5, col=4), Position(row=4, col=5))
        }
        "walking 1 steps with test data 1 gives 2 locations" {
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            walk(listOf(positionOfS),0,1,map,11) shouldBe listOf(Position(row=5, col=4), Position(row=4, col=5))
        }
        "walking 2 steps with test data 1 gives 4 locations" {
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            walk(listOf(positionOfS),0,2,map,11) shouldBe listOf(Position(row=5, col=5), Position(row=5, col=3), Position(row=6, col=4), Position(row=3, col=5))
        }
        "walking 6 steps with test data 1 gives 16 locations" {
            val map = testdata1.createMap()
            val positionOfS = Position(5,5)
            walk(listOf(positionOfS),0,6,map, 11).size shouldBe 16
        }
        "part one should be 3642" {
            partOne(sampleData) shouldBe 3642
        }
    })
    "part two" should ({

        "column is on map" {
            val map = testdata1.createMap()
            Position(5,10).isOn(map, 11) shouldBe true
            Position(5,11).isOn(map, 11) shouldBe true
            Position(5,12).isOn(map, 11) shouldBe false
            Position(5,13).isOn(map, 11) shouldBe false
            Position(5,14).isOn(map, 11) shouldBe true
            Position(5,15).isOn(map, 11) shouldBe true
            Position(5,16).isOn(map, 11) shouldBe true
            Position(5,17).isOn(map, 11) shouldBe false
        }
        "row is on map" {
            val map = testdata1.createMap()
            Position(10,5).isOn(map, 11) shouldBe true
            Position(11,5).isOn(map, 11) shouldBe true
            Position(12,5).isOn(map, 11) shouldBe false
            Position(13,5).isOn(map, 11) shouldBe false
            Position(0,5).isOn(map, 11) shouldBe true
        }
        "walking 10 steps with test data 1 gives 50 locations" {
            val map = testdata1.createMap()
            val positionOfS = testdata1.positionOfS(2)
            val x = walk(listOf(positionOfS),0,10,map, 11)
            x.size shouldBe 50
        }
        "walking 50 steps with test data 1 gives 1594 locations" {
            val map = testdata1.createMap()
            val positionOfS = testdata1.positionOfS(4)
            val x = walk(listOf(positionOfS),0,50,map, 11)
            x.size shouldBe 1594
        }
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})