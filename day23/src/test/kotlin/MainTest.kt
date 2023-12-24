import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testdata = """
        #.#####################
        #.......#########...###
        #######.#########.#.###
        ###.....#.>.>.###.#.###
        ###v#####.#v#.###.#.###
        ###.>...#.#.#.....#...#
        ###v###.#.#.#########.#
        ###...#.#.#.......#...#
        #####.#.#.#######.#.###
        #.....#.#.#.......#...#
        #.#####.#.#.#########v#
        #.#...#...#...###...>.#
        #.#.#v#######v###.###v#
        #...#.>.#...>.>.#.###.#
        #####v#.#.###v#.#.###.#
        #.....#...#...#.#.#...#
        #.#########.###.#.#.###
        #...###...#...#...#.###
        ###.###.#.###v#####v###
        #...#...#.#.>.>.#.>.###
        #.###.###.#.###.#.#v###
        #.....###...###...#...#
        #####################.#
    """.trimIndent().split("\n")

    "part one" should ({
        "start position in test data " {
            testdata.Grid().startPostition() shouldBe Position(0,1)
        }
        "end position in test data " {
            testdata.Grid().endPostition() shouldBe Position(22,21)
        }
        "part one using test data should be 94" {
            partOne(testdata) shouldBe 94
        }
        "part one using sample data should be 2438" {
//            partOne(sampleData) shouldBe 2438
        }
    })
    "part two" should ({
        "find junctions" {
            val grid = testdata.Grid2()
            val junctions = grid.findJunctions()
            junctions.size shouldBe 9
        }
        "find connection of first junction to other junction" {
            val grid = testdata.Grid2()
            val junctions = grid.findJunctions().toSet()
            val first = junctions.first {it.row == 0}
            first.findConnections(grid, junctions - first, setOf(first)) shouldBe listOf(Pair(Position(5,3), 15))
        }
        "find connection of junction(5,3) to other junction" {
            val grid = testdata.Grid2()
            val junctions = grid.findJunctions().toSet()
            val row5col3 = Position(5,3)
            row5col3.findConnections(grid, junctions - row5col3, setOf(row5col3)) shouldBe listOf(
                Pair(Position(3, 11),22),
                Pair(Position(0, 1),15),
                Pair(Position(13, 5),22))
        }
        "find all connections" {
            val grid = testdata.Grid2()
            val junctions = grid.findJunctions().toSet()
            val connections = junctions.findConnections(grid)
            connections.size shouldBe 9
        }
        "find paths using test data" {
            val grid = testdata.Grid2()
            val junctions = grid.findJunctions().toSet()
            val connections:Map<Position,List<Pair<Position, Int>>> = junctions.findConnections(grid)
            val paths = grid.startPostition().findPath2(connections, grid.endPostition(), setOf(), setOf(grid.startPostition()))
            paths.maxOf{ it.sumOf { it.second } } shouldBe 154
        }
        "part two with test data should be 154" {
            partTwo(testdata) shouldBe 154
        }
        "part two should be 0" {
            partTwo(sampleData) shouldBe 6658
        }
    })
})