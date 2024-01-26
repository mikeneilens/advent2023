import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testdata = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent().split("\n")

    "part one" should ({
        "mapping testdata to grid gives a first row of 2,4,1,3,4,3,2,3,1,1,3,2,3" {
            testdata.toChart().filter { it.key.row == 0 }.values shouldBe listOf(2,4,1,3,4,3,2,3,1,1,3,2,3)
            testdata.toChart().filter { it.key.row == 12 }.values shouldBe listOf(4,3,2,2,6,7,4,6,5,5,5,3,3)
        }
        "if crucible direction is Right and steps < 3 valid new directions are Up ,Down, Right" {
            val chart = testdata.toChart()
            val crucible = Crucible(Position(1,1), Direction.Right, 2 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right, Direction.Up, Direction.Down)
        }
        "if crucible direction is Right and steps = 3 valid new directions are Up ,Down" {
            val chart = testdata.toChart()
            val crucible = Crucible(Position(1,1), Direction.Right, 3 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Up, Direction.Down)
        }
        "if crucible direction is Right and steps < 3 and current position in top left corner valid new directions are Down, Right" {
            val chart = testdata.toChart()
            val crucible = Crucible(Position(0,0), Direction.Right, 2 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right, Direction.Down)
        }
        "starting at top left, newCrucibleQueue should have crucibles with position (row=1 , col=0) and position(row=0, col=1) " {
            val chart = testdata.toChart()
            val crucible = Crucible(Position(0,0), Direction.Right, 0 )
            val heatLoss = mutableMapOf<Crucible, Int>()
            expandCrucibleQueue(listOf(crucible), heatLoss, chart) shouldBe listOf(
                Crucible(Position(0,1), Direction.Down, 1),
                Crucible(Position(1,0), Direction.Right, 1),
            )
            heatLoss.minHeatLoss(Position(0,1)) shouldBe 3
            heatLoss.minHeatLoss(Position(1,0)) shouldBe 4
        }
        "starting at top left, newCrucibleQueue after two cycles should have crucibles with position (row=1 , col=0) and position(row=0, col=1) " {
            val chart = testdata.toChart()
            val crucible = Crucible(Position(0,0), Direction.Right, 0 )
            val heatLoss = mutableMapOf<Crucible, Int>()
            val cruciblequeue1 = expandCrucibleQueue(listOf(crucible), heatLoss, chart)
            val cruciblequeue2 = expandCrucibleQueue(cruciblequeue1, heatLoss, chart)
            cruciblequeue2 shouldBe listOf(
                Crucible(position=Position(col=1, row=0), direction=Direction.Right, steps=1),
                Crucible(position=Position(col=1, row=1), direction=Direction.Right, steps=1),
                Crucible(position=Position(col=0, row=2), direction=Direction.Down, steps=2)
            )
            heatLoss.minHeatLoss(Position(col=1, row=0)) shouldBe 4
            heatLoss.minHeatLoss(Position(col=1, row=1)) shouldBe 5
            heatLoss.minHeatLoss(Position(col=0, row=2)) shouldBe 6
        }
        "find end of test data" {
            val chart = testdata.toChart()
            val crucible = Crucible(Position(0,0), Direction.Right, 0 )
            val heatLoss = mutableMapOf<Crucible, Int>()
            val end = Position(testdata.first().lastIndex, testdata.lastIndex)
            expandCrucibleQueueUntilEnd(listOf(crucible), heatLoss, chart, end) shouldBe 102
        }

        "part one with test data" {
            partOne(testdata) shouldBe 102
        }
        "part one should be 0" {
//            partOne(sampleData) shouldBe 845
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})