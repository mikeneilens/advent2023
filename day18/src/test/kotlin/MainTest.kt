import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent().split("\n")
    "part one" should ({
        "converting test data to instructions" {
            val instructions = testData.toInstructions()
            instructions.size shouldBe 14
            instructions.first() shouldBe Instruction(Direction.Right, 6, "#70c710")
            instructions.last() shouldBe Instruction(Direction.Up, 2, "#7a21e3")
        }
        "processing the first instruction adds 6 data to row 0 of the map" {
            val map = mutableMapOf<Position, String>()
            testData.first().toInstruction().process(Position(0,0),map) shouldBe Position(0,6)
            map[Position(0,1)] shouldBe  "#70c710"
            map[Position(0,2)] shouldBe  "#70c710"
            map[Position(0,3)] shouldBe  "#70c710"
            map[Position(0,4)] shouldBe  "#70c710"
            map[Position(0,5)] shouldBe  "#70c710"
            map[Position(0,6)] shouldBe  "#70c710"
            map[Position(0,0)] shouldBe  null
            map[Position(0,7)] shouldBe  null
        }
        "print testdata map after processing it" {
            val map = mutableMapOf<Position, String>()
            testData.toInstructions().fold(Position(0,0)){ position, instruction ->
                instruction.process(position, map)
            }
            map.addBoundary()
            map.flood("2")
        }
        "part one with test data should be 62" {
            partOne(testData) shouldBe 62
        }
        "part one with sample data should be 46334" {
            partOne(sampleData) shouldBe 46334
        }
    })
    "part two" should ({
        "calcualte area for the test data using part one co-ordinates" {
            val positions = testData.toInstructions().fold(listOf(Position(0,0))){positions, instruction ->
                positions + listOf(positions.last() + (instruction.direction.offset * instruction.steps) )
            }
            val totalSteps = testData.toInstructions().sumOf { it.steps }
            areaUsingShoeLaceAndPicksTheorm(positions, totalSteps) shouldBe 62
        }
        "convert string to instruction" {
            "R 6 (#70c710)".toInstruction2() shouldBe Instruction(Direction.Right,461937,"")
        }
        "part two using test data should be 952408144115" {
            partTwo(testData) shouldBe 952408144115L
        }
        "part two using sample data should be 102000662718092" {
            partTwo(sampleData) shouldBe 102000662718092
        }
    })

})