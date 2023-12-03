import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testData = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...${'$'}.*....
                .664.598..
    """.trimIndent().split("\n")

    "part one" should ({
        "calculate positions adjacent to chunk(startcol=2, endCol=3, row=2) when not at end of row or column" {
            val numberChunk = NumberChunk(startCol = 2, endCol = 3, row = 2, data = testData)
            val expectedPositions = setOf(
                Position(1,1),
                Position(1,2),
                Position(1,3),
                Position(1,4),
                Position(2,1),
                Position(2,4),
                Position(3,1),
                Position(3,2),
                Position(3,3),
                Position(3,4),
            )
            numberChunk.adjacentPositions().toSet() shouldBe expectedPositions
        }
        "calculate positions adjacent to chunk(startcol=8, endCol=9, row=2) when is at end of row" {
            val numberChunk = NumberChunk(startCol = 8, endCol = 9, row = 2, testData)
            val expectedPositions = setOf(
                Position(1,7),
                Position(1,8),
                Position(1,9),
                Position(2,7),
                Position(3,7),
                Position(3,8),
                Position(3,9),
            )
            numberChunk.adjacentPositions().toSet() shouldBe expectedPositions
        }
        "calculate positions adjacent to chunk(startcol=2, endCol=3, row=9) when at end of column" {
            val numberChunk = NumberChunk(startCol = 2, endCol = 3, row = 9, testData)
            val expectedPositions = setOf(
                Position(8,1),
                Position(8,2),
                Position(8,3),
                Position(8,4),
                Position(9,1),
                Position(9,4),
            )
            numberChunk.adjacentPositions().toSet() shouldBe expectedPositions
        }
        "convert '..592.....' on row 5 to listOf(numberchunk(startcol=2, endCol=4,row=5))" {
            "..592.....".toNumberChunks(row=5, testData) shouldBe listOf(NumberChunk(2,4,5, testData))
        }

        "convert '467..114..' on row 5 to list containing two NumberChunk's" {
            val expectedFirstChunk = NumberChunk(0,2,5, testData)
            val expectedSecondChunk = NumberChunk(5,7,5, testData)
            "467..114..".toNumberChunks(row=5, testData) shouldBe listOf(expectedFirstChunk, expectedSecondChunk)
        }

        "value of numberChunk(startCol = 6, endCol = 8, row = 2) in testData is 633" {
            NumberChunk(startCol = 6, endCol = 8, row = 2, testData).value() shouldBe 633
        }
        "numberChunks adjacent to symbols in the test datat" {
            val expectedResult = listOf(
                NumberChunk(startCol=0, endCol=2, row=0, testData),
                NumberChunk(startCol=2, endCol=3, row=2, testData),
                NumberChunk(startCol=6, endCol=8, row=2, testData),
                NumberChunk(startCol=0, endCol=2, row=4, testData),
                NumberChunk(startCol=2, endCol=4, row=6, testData),
                NumberChunk(startCol=6, endCol=8, row=7, testData),
                NumberChunk(startCol=1, endCol=3, row=9, testData),
                NumberChunk(startCol=5, endCol=7, row=9, testData))
            testData.toNumberChunks().adjacentToSymbols() shouldBe expectedResult
        }
        "part one with test data should be 4361" {
            partOne(testData) shouldBe 4361
        }
        "part one should be 538046" {
            partOne(sampleData) shouldBe 538046
        }
    })
    "part two" should ({
        "update an astrisk map showing number chunks for each asterisk position in the test data" {
            val asteriskMap = mutableMapOf<Position,List<NumberChunk>>()
            testData.toNumberChunks().updateAsteriskMap(asteriskMap)

            val asteriskRow1Col3 = asteriskMap[Position(1,3)]
            asteriskRow1Col3?.size shouldBe 2
            asteriskRow1Col3?.get(0)?.value() shouldBe 467
            asteriskRow1Col3?.get(1)?.value() shouldBe 35

            val asteriskRow4Col3 = asteriskMap[Position(4,3)]
            asteriskRow4Col3?.size shouldBe 1
            asteriskRow4Col3?.get(0)?.value() shouldBe 617

            val asteriskRow8Col5 = asteriskMap[Position(8,5)]
            asteriskRow8Col5?.size shouldBe 2
            asteriskRow8Col5?.get(0)?.value() shouldBe 755
            asteriskRow8Col5?.get(1)?.value() shouldBe 598
        }
        "gear ratios in test data is [16345, 451490]" {
            val asteriskMap = mutableMapOf<Position,List<NumberChunk>>()
            testData.toNumberChunks().updateAsteriskMap(asteriskMap)
            asteriskMap.gearRatios() shouldBe listOf(16345, 451490)
        }
        "part two using test data should be 467835" {
            partTwo(testData) shouldBe 467835
        }
        "part two using sample data should be 467835" {
            partTwo(sampleData) shouldBe 81709807
        }
    })
})