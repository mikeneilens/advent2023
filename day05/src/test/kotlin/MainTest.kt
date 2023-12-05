import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testData = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent().split("\n")

    "part one" should ({
        "with source range 98..99 and an offset of -48 the destination index is 50 when source is 98" {
            val rangeOffset = RangeOffset(98L..99L, -48L  )
            val seedResourceMap = ResourceMap(listOf(rangeOffset))
            seedResourceMap.destinationIndex(98) shouldBe 50
        }
        "with source range 98..99 and an offset of -48 the destination index is 51 when source is 99" {
            val rangeOffset = RangeOffset(98L..99L, -48L  )
            val seedResourceMap = ResourceMap(listOf(rangeOffset))
            seedResourceMap.destinationIndex(98) shouldBe 50
        }
        "with source range 98..99 and an offset of -48 the destination index is 100 when source is 100" {
            val rangeOffset = RangeOffset(98L..99L, -48L  )
            val seedResourceMap = ResourceMap(listOf(rangeOffset))
            seedResourceMap.destinationIndex(100) shouldBe 100
        }
        "with source range 98..99, offset of -48 and sourceRange 50..97, offset 2 the destination index is 53 when source is 51" {
            val rangeOffset1 = RangeOffset(98L..99L, -48L  )
            val rangeOffset2 = RangeOffset(50L..97L, +2L  )
            val seedResourceMap = ResourceMap(listOf(rangeOffset1, rangeOffset2))
            seedResourceMap.destinationIndex(51) shouldBe 53
        }

        "parse 50 98 2 to RangeOffset(98..99, -48)" {
            "50 98 2".toRangeOffset() shouldBe RangeOffset(98L..99L, -48L)
        }

        "parse 52 50 48 to RangeOffset(50..97, +2)" {
            "52 50 48".toRangeOffset() shouldBe RangeOffset(50L..97L, +2L)
        }

        "create seeds 79 14 55 13 from the test data" {
            testData.toSeeds() shouldBe listOf(79, 14, 55, 13)
        }

        "create ResourceMap for seeds-to-soil from the test data" {
            val rangeOffset1 = RangeOffset(98L..99L, -48L  )
            val rangeOffset2 = RangeOffset(50L..97L, +2L  )
            val seedResourceMap = ResourceMap(listOf(rangeOffset1, rangeOffset2))
            testData.toResourceMap(0) shouldBe seedResourceMap
        }
        "using test data location for seed 79 is 82" {
            seedToLocation(79L, testData.toResourceMaps()) shouldBe 82L
        }
        "using test data location for seed 13 is 35" {
            seedToLocation(13L, testData.toResourceMaps()) shouldBe 35L
        }

        "part one using test data should be 35" {
            partOne(testData) shouldBe 35
        }
        "part one using sample data should be 35" {
            partOne(sampleData) shouldBe 227653707L
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})