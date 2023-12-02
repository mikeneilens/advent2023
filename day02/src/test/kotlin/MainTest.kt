import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent().split("\n")

    "part one" should ({
        "parse string starting with 'Game 1: ' to 1"{
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green".toGameNo() shouldBe 1
        }
        "parse string starting with 'Game 12: ' to 12"{
            "Game 12: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green".toGameNo() shouldBe 12
        }
        "parse a string containing Game 1: 3 blue, 4 red into a list of Blue(3),Red(4)" {
            "Game 1: 3 blue, 4 red".toSets() shouldBe listOf(listOf(BlueCube(3),RedCube(4)))
        }

        "parse a string into a game" {
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green".parse() shouldBe
                    Game(gameNo = 1,
                         sets = listOf(listOf(BlueCube(3), RedCube(4)),
                                listOf( RedCube(1), GreenCube(2), BlueCube(6)),
                                listOf(GreenCube(2))))
        }

        "red cube with quantity of 12 is valid" {
            RedCube(12).isValid() shouldBe true
        }
        "red cube with quantity of 13 is not valid" {
            println(RedCube(13))
            RedCube(13).isValid() shouldBe false
        }
        "a list of cubes containing a red cube with quantity 12 is valid" {
            val cubes = listOf(RedCube(12),GreenCube(0),BlueCube(0))
            cubes.isValid() shouldBe true
        }
        "a list of cubes containing a blue cube with quantity 15 is not valid" {
            val cubes = listOf(RedCube(12),GreenCube(0),BlueCube(15))
            cubes.isValid() shouldBe false
        }

        "parse the test data correctly" {
            val result = testData.parse()
            result.size shouldBe 5
            result[0].gameNo shouldBe 1
            result[0].sets[0] shouldBe listOf(BlueCube(3),RedCube(4))
            result[0].sets[1] shouldBe listOf(RedCube(1),GreenCube(2),BlueCube(6))
            result[0].sets[2] shouldBe listOf(GreenCube(2))
            result[4].gameNo shouldBe 5
            result[4].sets[0] shouldBe listOf(RedCube(6),BlueCube(1),GreenCube(3))
            result[4].sets[1] shouldBe listOf(BlueCube(2),RedCube(1),GreenCube(2))
        }
        "valid game numbers in the test data are 1, 2, 5" {
            testData.parse().validGameNumbers() shouldBe listOf(1, 2, 5)
        }
        "part one using test data should be 8" {
            partOne(testData) shouldBe 8
        }
        "part one using sample data should be 2449" {
            partOne(sampleData) shouldBe 2449
        }
    })
    "part two" should ({
        "In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes." {
            testData.parse().first().minimumNoOfReds() shouldBe 4
            testData.parse().first().minimumNoOfGreens() shouldBe 2
            testData.parse().first().minimumNoOfBlues() shouldBe 6
        }
        "power of each set of cubes in the test data should be 48, 12, 1560, 630, and 36" {
            testData.parse().power() shouldBe listOf(48, 12, 1560, 630, 36)
        }
        "part two using test data should be 2286" {
            partTwo(testData) shouldBe 2286
        }
        "part two using sample data should be 63981" {
            partTwo(sampleData) shouldBe 63981
        }
    })
})