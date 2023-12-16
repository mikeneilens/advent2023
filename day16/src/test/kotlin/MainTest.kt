import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent().split("\n")

    "part one" should ({
        "move beam starting at top left of grid" {
            val beams = mutableSetOf<Beam>()
            val grid = testData.toGrid()
            Beam(Position(0,0),Direction.Right).move(grid, beams)
            grid.visitedBy(beams).size shouldBe 46
        }
        "part one with test data should be 0" {
             partOne(testData) shouldBe 46
        }
        "part one with sample data should be 6740" {
            partOne(sampleData) shouldBe 6740
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})

fun printData(grid:Map<Position, Char>, beams:Set<Position>) {
    (0..49).forEach { row ->
        (0..49).forEach { col ->
            if (grid[Position(row, col)] == '.' && Position(row, col) in beams) print('B') else print(grid[Position(row, col)])
        }
        println()
    }
}