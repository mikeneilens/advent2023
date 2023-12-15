import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
    "part one" should ({
        "value of HASH is 52" {
            "HASH".hashAlgorithm() shouldBe 52
        }
        "part one for testdata should be 1320" {
            partOne(testData) shouldBe 1320
        }
        "part one for sample data should be 509784" {
            partOne(sampleData) shouldBe 509784
        }
    })
    "part two" should ({
        "part two should be 0" {
            partTwo(sampleData) shouldBe 0
        }
    })
})