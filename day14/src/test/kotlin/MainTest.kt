import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testdata1 = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent().split("\n")
    "part one" should ({
        "calculate rolls aboove a line" {
            val lineAbove = "O.OO#....#"
            val string    = ".....##..."
            val rollsAbove = listOf(0,1,1,1,0,0,1,1,1,0)
            string.rollsNorth(lineAbove, rollsAbove) shouldBe listOf(0,2,1,1,0,0,0,2,2,0)
        }
        "calculate rolls north for the test data" {
            testdata1.rollsNorth() shouldBe listOf(
                listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                listOf(0, 1, 1, 1, 0, 0, 1, 1, 1, 0),
                listOf(0, 2, 1, 1, 0, 0, 0, 2, 2, 0),
                listOf(1, 3, 2, 0, 1, 0, 0, 3, 3, 1),
                listOf(1, 3, 3, 0, 1, 1, 1, 4, 0, 1),
                listOf(2, 3, 0, 1, 2, 2, 2, 0, 0, 0),
                listOf(2, 4, 0, 2, 3, 0, 3, 0, 1, 0),
                listOf(3, 5, 0, 3, 4, 0, 3, 1, 2, 0),
                listOf(0, 6, 1, 4, 5, 0, 0, 0, 3, 1),
                listOf(0, 7, 2, 5, 6, 0, 0, 0, 4, 2)
            )
        }
        "weights on each row in test data" {
            val rollsAbove = testdata1.rollsNorth()
            testdata1.weightOnEachRow(rollsAbove) shouldBe listOf(10, 29, 0, 34, 19, 14, 15, 4, 0, 11)
        }
        "part one with test data should be 136" {
            partOne(testdata1) shouldBe 136
        }
        "part one with sample data should be 105208" {
            partOne(sampleData) shouldBe 105208
        }
    })
    "part two" should ({
        "OO.#O....O rolled west should be" {
            "OO.#O....O".rollsWest() shouldBe listOf(0, 0, 0, 0, 0, 0, 1, 2, 3, 4)
        }
        "test data rolled west" {
            testdata1.mappedWest() shouldBe listOf(
                "O....#....",
                "OOO.#....#",
                ".....##...",
                "OO.#OO....",
                "OO......#.",
                "O.#O...#.#",
                "O....#OO..",
                "O.........",
                "#....###..",
                "#OO..#...."
            )
        }
        "sample data cycled once" {
            testdata1.cycled() shouldBe """
                .....#....
                ....#...O#
                ...OO##...
                .OO#......
                .....OOO#.
                .O#...O#.#
                ....O#....
                ......OOOO
                #...O###..
                #..OO#....
            """.trimIndent().split("\n")
        }
        "sample data cycled twice" {
            testdata1.cycled().cycled() shouldBe """
                .....#....
                ....#...O#
                .....##...
                ..O#......
                .....OOO#.
                .O#...O#.#
                ....O#...O
                .......OOO
                #..OO###..
                #.OOO#...O
            """.trimIndent().split("\n")
        }
        "sample data cycled three" {
            testdata1.cycled().cycled().cycled() shouldBe """
                .....#....
                ....#...O#
                .....##...
                ..O#......
                .....OOO#.
                .O#...O#.#
                ....O#...O
                .......OOO
                #...O###.O
                #.OOO#...O
            """.trimIndent().split("\n")
        }
        "part two with testdata should be 0" {
           // partTwo(testdata1) shouldBe 0
        }
        "part two with sampledata should be 0" {
            partTwo(sampleData) shouldBe 102943
        }

    })
})