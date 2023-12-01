import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent().split("\n")

    "part one" should ({
        "first digit in string ab1c is 1" {
            "ab1c".firstDigit() shouldBe  1
        }
        "last digit in string ab1c2d3e is 3" {
            "ab1c2d3e".lastDigit() shouldBe  3
        }
        "calculating calibration value in string ab1c2d3e should give 13" {
            "ab1c2d3e".calculateCalibrationValue() shouldBe  13
        }
        "calculating calibration value for test data should give 142" {
            testData.calculateCalibrationValue() shouldBe  142
        }
        "part one should be 53921" {
            partOne(sampleData) shouldBe  53921
        }
    })

    val testData2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent().split("\n")

    "part two" should ({

        "replacing digits in 4nineeightseven2 is 49nine8eight7seven2" {
            "4nineeightseven2".replaceNumericalText() shouldBe "4n9inee8ights7even2"
        }

        "replacing digits in eightwothree is 49nine8eight7seven2" {
            "eightwothree".replaceNumericalText() shouldBe "e8ight2wot3hree"
        }

        "calculating adjusted calibration value for abcone2threexyz should give 13 " {
            "bcone2threexyz".calculateAdjustedCalibrationValue() shouldBe 13
        }
        "calculating adjusted calibration value for eightwothree should give 83 " {
            "eightwothree".calculateAdjustedCalibrationValue() shouldBe 83
        }
        "calculating adjusted calibration value for test data should give 281" {
            testData2.forEach { println(it.calculateAdjustedCalibrationValue()) }
            testData2.calculateAdjustedCalibrationValue() shouldBe  281
        }

        "part two should be 54676" {
            partTwo(sampleData) shouldBe  54676
        }
    })
})