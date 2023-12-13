import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testdata1 = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.

            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent().split("\n")

    val testdata2 = """
    ##..####..##..#
    #.##.#..##..##.
    ##..###.##..##.
    .#..#.#........
    ######.########
    ##..##...####..
    ##..###.#....#.
    .####.#.######.
    #.##.#..##..##.
    #....#..##..##.
    #....##.##..##.
    ######.##.##.##
    .####..###..###
    """.trimIndent().split("\n")
    "part one" should ({

        "ranges for position 0 in a list containing 6 is " {
            val (r1, r2) = getRanges(0,5)
            r1 shouldBe 0..0
            r2 shouldBe 1..1
        }
        "ranges for position 1 in a list containing 6 is " {
            val (r1, r2) = getRanges(1,5)
            r1 shouldBe 0..1
            r2 shouldBe 2..3
        }
        "ranges for position 2 in a list containing 6 is " {
            val (r1, r2) = getRanges(2,5)
            r1 shouldBe 0..2
            r2 shouldBe 3..5
        }
        "ranges for position 3 in a list containing 6 is " {
            val (r1, r2) = getRanges(3,5)
            r1 shouldBe 0..2
            r2 shouldBe 3..5
        }
        "ranges for position 4 in a list containing 6 is " {
            val (r1, r2) = getRanges(4,5)
            r1 shouldBe 2..3
            r2 shouldBe 4..5
        }
        "ranges for position 5 in a list containing 6 is " {
            val (r1, r2) = getRanges(5,5)
            r1 shouldBe 4..4
            r2 shouldBe 5..5
        }
        "ranges for position 1 in a list containing 7 is " {
            val (r1, r2) = getRanges(1,6)
            r1 shouldBe 0..1
            r2 shouldBe 2..3
        }
        "ranges for position 2 in a list containing 7 is " {
            val (r1, r2) = getRanges(2,6)
            r1 shouldBe 0..2
            r2 shouldBe 3..5
        }
        "ranges for position 3 in a list containing 7 is " {
            val (r1, r2) = getRanges(3,6)
            r1 shouldBe 0..3
            r2 shouldBe 4..7
        }
        "ranges for position 4 in a list containing 7 is " {
            val (r1, r2) = getRanges(4,6)
            r1 shouldBe 1..3
            r2 shouldBe 4..6
        }
        "ranges for position 5 in a list containing 7 is " {
            val (r1, r2) = getRanges(5,6)
            r1 shouldBe 3..4
            r2 shouldBe 5..6
        }
        "test data splits into two mirrors" {
            val mirrors = testdata1.toMirrors()
            mirrors.size shouldBe 2
            mirrors[0].size shouldBe 7
            mirrors[1].size shouldBe 7
        }
        "vertical ranges for first mirror in test data" {
            val mirror = testdata1.toMirrors().first()
            mirror.verticalRanges() shouldBe listOf(
                Pair(0..0, 1..1),
                Pair(0..1, 2..3),
                Pair(0..2, 3..5),
                Pair(1..3, 4..6),
                Pair(3..4, 5..6),
                Pair(5..5, 6..6))
        }

        "transpose [abcd],[efgh] is [ae,bf,cg,dh]" {
            listOf("abcd","efgh").transpose() shouldBe listOf("ae","bf","cg","dh")
        }

        "mirrored col on 1st test data" {
            val mirror = testdata1.toMirrors()[0]
            mirror.mirrorOnColumn() shouldBe 5
        }

        "mirrored row in 2nd test data" {
            val mirror = testdata1.toMirrors()[1]
            mirror.mirrorOnRow() shouldBe 4
        }

        "part one with test data should be 405" {
            partOne(testdata1) shouldBe 405
        }
        "part one with sample data should be 0" {
            partOne(sampleData) shouldBe 33780
        }
    })
    "part two" should ({
        "replace 0,0 in test data " {
            testdata1.replace(0,0)[0][0] shouldBe '.'
        }
        "replace 1,0 in test data " {
            testdata1.replace(1,0)[0][1] shouldBe '#'
        }
        "alternate mirror for first mirror on testdata1" {
            testdata1.toMirrors()[0].alternateMirror() shouldBe Pair(3,0)
        }
        "alternate mirror for 2nd mirror on testdata1" {
            testdata1.toMirrors()[1].alternateMirror() shouldBe Pair(1,0)
        }
        "part two with testdata1 should be 400" {
            partTwo(testdata1) shouldBe 400
        }
        "part two with sample data should be 400" {
            partTwo(sampleData) shouldBe 23479
        }
    })
})

