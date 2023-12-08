import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    "part one" should ({
        val testData1 = """
            RL

            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent().split("\n")

        val testData2 = """
            LLR

            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent().split("\n")

        "for testData1 instructions has directions Right, Left" {
            testData1.first().toDirections() shouldBe listOf(Direction.Right, Direction.Left)
        }

        "for testData1 first three instructions are Right, Left, Right" {
            val instructions = testData1.toInstructions()
            instructions.nextStep() shouldBe Direction.Right
            instructions.nextStep() shouldBe Direction.Left
            instructions.nextStep() shouldBe Direction.Right
        }
        "for testData1 first three nodes are AAA = (BBB, CCC), BBB = (DDD, EEE), CCC = (ZZZ, GGG)" {
            val nodes = testData1.toNodes()
            nodes.get("AAA",Direction.Left) shouldBe "BBB"
            nodes.get("AAA",Direction.Right) shouldBe "CCC"
            nodes.get("BBB",Direction.Left) shouldBe "DDD"
            nodes.get("BBB",Direction.Right) shouldBe "EEE"
            nodes.get("CCC",Direction.Left) shouldBe "ZZZ"
            nodes.get("CCC",Direction.Right) shouldBe "GGG"
        }

        "part one with testData1 should be 2" {
            partOne(testData1) shouldBe 2
        }
        "part one with testData2 should be 6" {
            partOne(testData2) shouldBe 6
        }
        "part one with sampleData should be 13301" {
            partOne(sampleData) shouldBe 13301
        }
    })
    "part two" should ({
        val testData2 = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent().split("\n")
        "part two using testData2 should be 6" {
            partTwo(testData2) shouldBe 6L
        }
        "part two using sample data should be 7309459565207" {
            partTwo(sampleData) shouldBe 7309459565207L
        }
    })
})