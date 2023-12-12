import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent().split("\n")

    "part one" should ({
        "#.#.### has pattern 1,1,3" {
            "#.#.###".matches(listOf(1,1,3)) shouldBe true
        }
        ".###.##....# has pattern 3,2,1" {
            ".###.##....#".matches(listOf(3,2,1)) shouldBe true
        }
        "missing springs in .??..??...?##. 1,1,3 is  3" {
            val springs = ".??..??...?##. 1,1,3".toSprings()
            springs.missingSprings shouldBe 3
        }
        "positions of question marks in .??..??...?##. are 1,2,5,6,10 " {
            ".??..??...?##.".qPositions() shouldBe listOf(1,2,5,6,10)
        }
        "12 as a pattern with length 8 is " {
            12.toPattern(8) shouldBe "....##.."
        }
        "replace q marks in .??..??...?##. with #.##. gives .#...##....## " {
            val qPositions = ".??..??...?##.".qPositions()
            val pattern = "#.##."
                ".??..??...?##.".replaceQMarks(qPositions, pattern) shouldBe ".#...##....##."
        }
        "binary patterns for .??....?##. are '...', '..#', '.#.', '.##', '#..', '#.#', '##.', '###' " {
            val qPositions = ".??....?##.".qPositions()
            binaryPatterns(qPositions,2) shouldBe listOf(".##", "#.#","##.")
        }
        "variants of .??....?##. are ['........##', '.......###', '..#.....##', '..#....###', '.#......##', '.#.....###', '.##.....##', '.##....###']" {
            ".??....?##".variants(2) shouldBe listOf("..#....###", ".#.....###", ".##.....##")
        }
        "valid variants of .??..??...?##. [1,1,3] should be 4 " {
            ".??..??...?##.".variants(3).count{it.matches(listOf(1,1,3))} shouldBe 4
        }
        "valid variants of ?###???????? listOf(3,2,1) should be 10 " {
            "?###????????".variants(3).count{it.matches(listOf(3,2,1))} shouldBe 10
        }
        "part one using testdata be 21" {
            partOne(testData) shouldBe 21
        }
        "part one should be 6827" {
            partOne(sampleData) shouldBe 6827
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})