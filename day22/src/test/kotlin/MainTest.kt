import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testdata1 = """
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent().split("\n")
    "part one" should ({
        "create brick from 1,0,1~1,2,1" {
            "1,0,1~1,2,1".toBrick() shouldBe Brick(1..1, 0..2, 1..1)
        }
        "no data with invalid ranges" {
            sampleData.toBricks().none{ it.xRange.isEmpty() || it.yRange.isEmpty() || it.zRange.isEmpty()} shouldBe true
        }
        "identical ranges overlap" {
            (0..2).overlaps(0..2)  shouldBe true
        }
        "different ranges overlap" {
            (1..3).overlaps(0..2)  shouldBe true
        }
        "brick floors overlap" {
            val brick1 = Brick(0..2, 0..2, 5..6)
            val brick2 = Brick(1..3, 0..2, 1..2)
             brick1.floorOverlaps(brick2) shouldBe true
        }
        "drop brick onto floor" {
            val brick1 = Brick(2..2, 0..2, 5..6)
            listOf(brick1).dropBricks() shouldBe listOf(floor, Brick(2..2, 0..2, 1..2))
        }
        "drop brick onto pile where top of pile overlaps the next brick" {
            val brick1 = Brick(0..2, 0..2, 5..6)
            val brick2 = Brick(1..3, 0..2, 1..2)
            listOf(brick1).dropBricks(listOf(floor, brick2)) shouldBe listOf(floor, brick2, Brick(0..2, 0..2, 3..4))
        }
        "drop brick onto pile where top of pile does not overlap the next brick" {
            val brick1 = Brick(0..2, 0..2, 5..6)
            val brick2 = Brick(3..4, 0..2, 1..2)
            listOf(brick1).dropBricks(listOf(floor, brick2)) shouldBe listOf(floor, brick2, Brick(0..2, 0..2, 1..2))
        }
        "drop brick between two stacks and then drop a brick which should rest on one of the stacks" {
            val brick1InStack1 = Brick(0..2, 0..2, 5..6)
            val brick2InStack1 = Brick(0..2, 0..2, 1..2)
            val brick1InStack2 = Brick(5..6, 0..2, 5..6)
            val brick2InStack2 = Brick(5..6, 0..2, 1..2)
            val middleBrick = Brick(3..4, 0..2, 7..8)
            val finalBrick = Brick(3..5, 0..2, 7..8)
            val pile = listOf(brick1InStack1,brick1InStack2,brick2InStack1,brick2InStack2,middleBrick,finalBrick).dropBricks()
            pile shouldBe listOf(
                Brick(xRange=-2147483647..2147483647, yRange=-2147483647..2147483647, zRange=0..0),
                Brick(xRange=0..2, yRange=0..2, zRange=1..2), //brick1Stack1
                Brick(xRange=5..6, yRange=0..2, zRange=1..2), //brick1Stack2
                Brick(xRange=0..2, yRange=0..2, zRange=3..4), //brick2Stack1
                Brick(xRange=5..6, yRange=0..2, zRange=3..4), //brick2Stack2
                Brick(xRange=3..4, yRange=0..2, zRange=1..2), //middleBrick
                Brick(xRange=3..5, yRange=0..2, zRange=5..6) //finalBrick
            )
        }
        "drop test data" {
            val droppedBricks = testdata1.toBricks().dropBricks()
            droppedBricks.sortedByDescending { it.zRange.first } shouldBe listOf(
                Brick(xRange=1..1, yRange=1..1, zRange=5..6),
                Brick(xRange=0..2, yRange=1..1, zRange=4..4),
                Brick(xRange=0..0, yRange=0..2, zRange=3..3),
                Brick(xRange=2..2, yRange=0..2, zRange=3..3),
                Brick(xRange=0..2, yRange=0..0, zRange=2..2),
                Brick(xRange=0..2, yRange=2..2, zRange=2..2),
                Brick(xRange=1..1, yRange=0..2, zRange=1..1),
                Brick(xRange=-2147483647..2147483647, yRange=-2147483647..2147483647, zRange=0..0)
            )
        }
        "find bricks supported by a brick" {
            val pile = listOf(
                Brick(xRange=0..2, yRange=0..0, zRange=2..2),
                Brick(xRange=0..2, yRange=2..2, zRange=2..2),
                Brick(xRange=1..1, yRange=0..2, zRange=1..1),
                Brick(xRange=-2147483647..2147483647, yRange=-2147483647..2147483647, zRange=0..0)
            )
            Brick(xRange=1..1, yRange=0..2, zRange=1..1).bricksAbove(pile) shouldBe listOf(
                Brick(xRange=0..2, yRange=0..0, zRange=2..2),
                Brick(xRange=0..2, yRange=2..2, zRange=2..2)
            )
        }
        "part one should be 5 with testdata" {
            partOne(testdata1) shouldBe 5
        }
        "part one should be 0" {
            partOne(sampleData) shouldBe 524
        }
    })
    "part two" should ({
        "test " {
            val pile = testdata1.toBricks().dropBricks()
            val bricksRestingOnBricks = bricksRestingOnBricks(pile).map{Pair(it.key, it.value.toMutableList())}.toMap().toMutableMap()
            bricksRestingOnBricks.removeBrick(pile[1])
            pile.size - bricksRestingOnBricks.size shouldBe 7
        }
        "part two with testdata should be 7" {
            partTwo(testdata1) shouldBe 7
        }
    "part two with sample data should be 77070" {
        //57700 is too low
        //31603 is too low
            partTwo(sampleData) shouldBe 77070
        }
    })
})