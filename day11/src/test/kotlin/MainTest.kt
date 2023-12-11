import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testData1 = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent().split("\n")

    "part one" should ({
        "col widths" {
            val colWidths = testData1.colWidths()
            colWidths[0] shouldBe 1
            colWidths[1] shouldBe 1
            colWidths[2] shouldBe 2
            colWidths[3] shouldBe 1
            colWidths[4] shouldBe 1
            colWidths[5] shouldBe 2
        }
        "row widths" {
            val rowWidths = testData1.rowWidths()
            rowWidths[0] shouldBe 1
            rowWidths[1] shouldBe 1
            rowWidths[2] shouldBe 1
            rowWidths[3] shouldBe 2
        }
        "distance to row = 0 and row = 8 is 10" {
            val rowWidths = testData1.rowWidths()
            distanceTo(0,8, rowWidths) shouldBe 10
        }
        "distance to col = 3 and col = 7 is 5" {
            val colWidths = testData1.colWidths()
            distanceTo(3,7, colWidths) shouldBe 5
        }
        "distance between row = 0, col= 3 and row = 8, col =7 is 15" {
            val rowWidths = testData1.rowWidths()
            val colWidths = testData1.colWidths()
            Position(3,0).distanceTo(Position(7,8), rowWidths, colWidths) shouldBe 15
        }
        "distance between row = 2, col= 0 and row = 6, col =9 is 17" {
            val rowWidths = testData1.rowWidths()
            val colWidths = testData1.colWidths()
            Position(0,2).distanceTo(Position(9,6), rowWidths, colWidths) shouldBe 17
        }
        "distance between row = 9, col= 0 and row = 9, col =4 is 5" {
            val rowWidths = testData1.rowWidths()
            val colWidths = testData1.colWidths()
            Position(0,9).distanceTo(Position(4,9), rowWidths, colWidths) shouldBe 5
        }
        "distance between row = 5, col= 5 and row = 5, col =5 is 0" {
            val rowWidths = testData1.rowWidths()
            val colWidths = testData1.colWidths()
            Position(5,5).distanceTo(Position(5,5), rowWidths, colWidths) shouldBe 0
        }
        "testdata1 to positions " {
            testData1.toPositions() shouldBe listOf(Position(x=0, y=2), Position(x=0, y=9), Position(x=1, y=5), Position(x=3, y=0), Position(x=4, y=9), Position(x=6, y=4), Position(x=7, y=1), Position(x=7, y=8), Position(x=9, y=6))
        }

        "galaxy pairs in the testdata" {
            val galaxyPairs = testData1.toPositions().galaxyPairs()
             galaxyPairs.contains(setOf(Position(x=0, y=2), Position(x=0, y=9))) shouldBe true
             galaxyPairs.contains(setOf(Position(x=0, y=2), Position(x=1, y=5))) shouldBe true
             galaxyPairs.contains(setOf(Position(x=0, y=2), Position(x=3, y=0))) shouldBe true
             galaxyPairs.contains(setOf(Position(x=0, y=2), Position(x=4, y=9))) shouldBe true
        }
        "part one for testData should be 374" {
            partOne(testData1) shouldBe 374L
        }
        "part one for sample data should be 9918828" {
            partOne(sampleData) shouldBe 9918828L
        }
    })
    "part two" should ({
        "part two for testData and width of 10 should be 1030" {
            partTwo(testData1,10) shouldBe 1030L
        }
        "part two for testData and width of 100 should be 8410" {
            partTwo(testData1,100) shouldBe 8410L
        }
        "part two for sample and width of 1000000 should be 692506533832" {
            partTwo(sampleData,1000000) shouldBe 692506533832L
        }
    })
})