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
            val crucible = Crucible(Bearing(Position(1,1), Direction.Right, 2), 0 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right, Direction.Up, Direction.Down)
        }
        "if crucible direction is Right and steps = 3 valid new directions are Up ,Down" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(1,1), Direction.Right, 3), 0 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Up, Direction.Down)
        }
        "if crucible direction is Right and steps < 3 and current position in top left corner valid new directions are Down, Right" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(0,0), Direction.Right, 2), 0 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right, Direction.Down)
        }
        "starting at top left, newCrucibleQueue should have crucibles with position (row=1 , col=0) and position(row=0, col=1) " {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(0,0), Direction.Right, 0), 0 )
            val visited = mutableSetOf<Bearing>()
            val end = Position(testdata.first().lastIndex, testdata.lastIndex)
            expandCrucibleQueue(listOf(crucible), visited, chart) shouldBe listOf(
                Crucible(Bearing(Position(0,1), Direction.Down, 1),3),
                Crucible(Bearing(Position(1,0), Direction.Right, 1), 4),
            )
        }
        "starting at top left, newCrucibleQueue after two cycles should have crucibles with position (row=1 , col=0) and position(row=0, col=1) " {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(0,0), Direction.Right, 0), 0 )
            val visited = mutableSetOf<Bearing>()
            val cruciblequeue1 = expandCrucibleQueue(listOf(crucible), visited, chart)
            val cruciblequeue2 = expandCrucibleQueue(cruciblequeue1, visited, chart)
            cruciblequeue2 shouldBe listOf(
                Crucible(Bearing(position=Position(col=1, row=0), direction=Direction.Right, steps=1), 4),
                Crucible(Bearing(position=Position(col=1, row=1), direction=Direction.Right, steps=1), 5),
                Crucible(Bearing(position=Position(col=0, row=2), direction=Direction.Down, steps=2), 6)
            )
        }
        "find end of test data" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(0,0), Direction.Right, 0), 0 )
            val visited = mutableSetOf<Bearing>()
            val end = Position(testdata.first().lastIndex, testdata.lastIndex)
            expandCrucibleQueueUntilEnd(listOf(crucible), visited, chart, end) shouldBe 102
        }

        "part one with test data" {
            partOne(testdata) shouldBe 102
        }
        "part one should be 845" {
            partOne(sampleData) shouldBe 845
        }
    })
    "part two" should ({
        val testdata2 = """
            111111111111
            999999999991
            999999999991
            999999999991
            999999999991
        """.trimIndent().split("\n")

        "for an ultra crucible that has travelled 0 steps it can go in any direction" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(5,5), Direction.None, 0), 0,4 , 10)
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Left, Direction.Right, Direction.Up, Direction.Down)
        }
        "for an ultra crucible that has travelled 3 steps it must keep going in the same direction" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(5,5), Direction.Right, 3), 0,4, 10 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right)
        }
        "for an ultra crucible that has travelled 4 steps it can continue in the same direction or turn" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(5,5), Direction.Right, 4), 0, 4, 10 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right, Direction.Up, Direction.Down)
        }
        "for an ultra crucible that has travelled 9 steps it can continue in the same direction or turn" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(5,5), Direction.Right, 9), 0, 4, 10 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Right, Direction.Up, Direction.Down)
        }
        "for an ultra crucible that has travelled 10 steps it must turn" {
            val chart = testdata.toChart()
            val crucible = Crucible(Bearing(Position(5,5), Direction.Right, 10), 0, 4, 10 )
            crucible.validDirections(chart).toSet() shouldBe setOf(Direction.Up, Direction.Down)
        }

        "part two with test data" {
            partTwo(testdata) shouldBe 94
        }
        "part two with test data2" {
            partTwo(testdata2) shouldBe 71
        }

        "part two should be 993" {
            partTwo(sampleData) shouldBe 993
        }
    })
})