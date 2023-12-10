import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData1 = """
        .....
        .S-7.
        .|.|.
        .L-J.
        .....
    """.trimIndent().split("\n")
    val testData2 = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
    """.trimIndent().split("\n")
    "part one" should ({
        "pipe at 0,0 in testdata1 is '.'" {
            testData1.pipeAt(Position(0,0)) shouldBe '.'
        }
        "pipe at 1,1 in testdata1 is 'S'" {
            testData1.pipeAt(Position(1,1)) shouldBe 'S'
        }
        "pipe at -1,1 and 5,1 in testdata1 is '.'" {
            testData1.pipeAt(Position(-1,1)) shouldBe '.'
            testData1.pipeAt(Position(5,1)) shouldBe '.'
        }
        "pipe at 1,-1 and 1,5 in testdata1 is '.'" {
            testData1.pipeAt(Position(1,-1)) shouldBe '.'
            testData1.pipeAt(Position(1,5)) shouldBe '.'
        }
        "pipe locations next to 1,1 in test data are (2,1) and (1,2)  " {
            testData1.pipesNextTo(Position(1,1)) shouldBe listOf(
                Pair(Position(2,1),Connection('-',Direction.Right)),
                Pair(Position(1,2),Connection('|',Direction.Down)))
        }
        "pipe locations not visited next to 1,1 when (1,2) has been visited in test data are (2,1) " {
            testData1.pipesNotVisitedNextTo(Position(1,1), mutableListOf(Position(1,2))) shouldBe listOf(Pair(Position(x=2, y=1), Connection('-', Direction.Right)))
        }
        "pipe locations not visited next to 1,1 when (1,2) and (2,1) has been visited in test data is empty " {
            testData1.pipesNotVisitedNextTo(Position(1,1), mutableListOf(Position(1,2),Position(2,1))) shouldBe listOf<Pair<Int,Int>>()
        }
        "traversing loop of testdata1 starting at 1,1" {
            val connections = mutableListOf<Connection>()
            testData1.traverseLoop(Position(1,1), connections= connections) shouldBe listOf(
                Position(x=1, y=1),
                Position(x=2, y=1),
                Position(x=3, y=1),
                Position(x=3, y=2),
                Position(x=3, y=3),
                Position(x=2, y=3),
                Position(x=1, y=3),
                Position(x=1, y=2))
            connections shouldBe listOf(
                Connection(type='-', direction=Direction.Right),
                Connection(type='7', direction=Direction.Right),
                Connection(type='|', direction=Direction.Down),
                Connection(type='J', direction=Direction.Down),
                Connection(type='-', direction=Direction.Left),
                Connection(type='L', direction=Direction.Left),
                Connection(type='|', direction=Direction.Up)
            )
        }
        "traversing loop of testdata2 starting at 0,2" {
            testData2.traverseLoop(Position(0,2)) shouldBe listOf(
                Position(x=0, y=2), Position(x=1, y=2), Position(x=1, y=1), Position(x=2, y=1),
                Position(x=2, y=0), Position(x=3, y=0), Position(x=3, y=1), Position(x=3, y=2),
                Position(x=4, y=2), Position(x=4, y=3), Position(x=3, y=3), Position(x=2, y=3),
                Position(x=1, y=3), Position(x=1, y=4), Position(x=0, y=4), Position(x=0, y=3))
        }
        "part one using testData1 should be 4" {
            partOne(testData1,'F') shouldBe 4
        }
        "part one using testData2 should be 8" {
            partOne(testData2, 'F') shouldBe 8
        }
        "part one using sample data should be 7005" {
            partOne(sampleData, '7') shouldBe 7005
        }
    })
    "part two" should ({
        "part two with sampleData is 417" {
            partTwo(sampleData) shouldBe 417
        }
    })
})