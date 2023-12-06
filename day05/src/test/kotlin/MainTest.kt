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
            val seedResourceMap = ResourceMap(listOf(rangeOffset2, rangeOffset1))
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
        " 1..100 segmented using 101..200 gives [1..100]" {
            (1L..100L).segmentUsing(101L..200L) shouldBe listOf(1L..100L)
        }
        " 201..300 segmented using 101..200 gives [1..100]" {
            (201L..300L).segmentUsing(101L..200L) shouldBe listOf(201L..300L)
        }
        " 101..200 segmented using 101..200 gives [1..100]" {
            (101L..200L).segmentUsing(101L..200L) shouldBe listOf(101L..200L)
        }
        " 102..199 segmented using 101..200 gives [1..100]" {
            (102L..199L).segmentUsing(101L..200L) shouldBe listOf(102L..199L)
        }
        " 1..300 segmented using 101..200 gives [1..100, 101..200, 201..300]" {
            (1L..300L).segmentUsing(101L..200L) shouldBe listOf(1..100L, 101..200L, 201..300L)
        }
        " 1..150 segmented using 101..200 gives [1..100, 101..150]" {
            (1L..150L).segmentUsing(101L..200L) shouldBe listOf(1..100L, 101..150L)
        }
        " 150..300 segmented using 101..200 gives [150..200, 201..300]" {
            (150L..300L).segmentUsing(101L..200L) shouldBe listOf(150..200L, 201..300L)
        }
        " 1..100 segmented using 20..30 and 40..50 gives 1..19, 20..30, 31..39, 40..50, 51..100" {
            val rangeOffset1 = RangeOffset(20..30L, 1)
            val rangeOffset2 = RangeOffset(40..50L, 2)
            listOf(1..100L).segmentUsing(listOf(rangeOffset1, rangeOffset2)) shouldBe listOf(
                1..19L, 20..30L, 31..39L, 40..50L, 51..100L
            )
        }
        "testdata toSeedRanges is " {
            testData.toSeedRanges() shouldBe listOf(79..92L, 55..67L)
        }
        "Locations for seed range 55..67L " {
            seedToLocation(listOf(55..67L), testData.toResourceMaps()).minOf { it.first } shouldBe 56L
        }
        "Locations for seed range 79..92 " {
            seedToLocation(listOf(79..92L), testData.toResourceMaps()).minOf { it.first } shouldBe 46L
        }
        "part two using test data should be 46" {
            partTwo(testData) shouldBe 46
        }
        "part two using sample data should be 78775051" {
            partTwo(sampleData) shouldBe 78775051
        }
    })

})