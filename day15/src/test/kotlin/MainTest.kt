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
        "add lens to list of lenses" {
            val lens = Lens("l1",1)
            val newLens = Lens("l2",2)
            lens.upsertLens(newLens)
            lens.next shouldBe newLens
            newLens.prev shouldBe lens
            newLens.next shouldBe null
        }
        "update lens in a list of lenses" {
            val lens1 = Lens("l1",1)
            val lens2 = Lens("l2",2, prev = lens1); lens1.next = lens2
            val lens3 = Lens("l3",3, prev = lens2); lens2.next = lens3
            val newLens2 = Lens("l2",4)
            lens1.upsertLens(newLens2)
            lens1.next shouldBe newLens2
            lens3.prev shouldBe newLens2
            lens2.prev shouldBe null
            lens2.next shouldBe null
            newLens2.prev shouldBe lens1
            newLens2.next shouldBe lens3
        }
        "update first lens in a list of lenses" {
            val lens1 = Lens("l1",1)
            val lens2 = Lens("l2",2, prev = lens1); lens1.next = lens2
            val lens3 = Lens("l3",3, prev = lens2); lens2.next = lens3
            val newLens1 = Lens("l1",4)
            lens1.upsertLens(newLens1)
            lens2.prev shouldBe newLens1
            newLens1.prev shouldBe null
            newLens1.next shouldBe lens2
        }

        "remove lens in a list of lenses" {
            val lens1 = Lens("l1",1)
            val lens2 = Lens("l2",2, prev = lens1); lens1.next = lens2
            val lens3 = Lens("l3",3, prev = lens2); lens2.next = lens3
            lens1.removeLens("l2")
            lens1.next shouldBe lens3
            lens3.prev shouldBe lens1
            lens2.prev shouldBe null
            lens2.next shouldBe null
        }
        "parse test data" {
            testData.toLensInstruction() shouldBe listOf(
                UpsertLens(name="rn", focalLength=1),
                RemoveLens(name="cm"),
                UpsertLens(name="qp", focalLength=3),
                UpsertLens(name="cm", focalLength=2),
                RemoveLens(name="qp"),
                UpsertLens(name="pc", focalLength=4),
                UpsertLens(name="ot", focalLength=9),
                UpsertLens(name="ab", focalLength=5),
                RemoveLens(name="pc"),
                UpsertLens(name="pc", focalLength=6),
                UpsertLens(name="ot", focalLength=7)
            )
        }
        "power of two lenses" {
            val lens1 = Lens("l1",1)
            val lens2 = Lens("l2",2, prev = lens1); lens1.next = lens2
            lens1.power() shouldBe 5
        }
        "part two with test data should be 145" {
            partTwo(testData) shouldBe 145
        }
        "part two with sample data should be 230197" {
            partTwo(sampleData) shouldBe 230197
        }
    })
})