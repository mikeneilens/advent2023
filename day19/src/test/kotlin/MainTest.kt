import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({
    val testData = """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}

        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}
    """.trimIndent().split("\n")
    "part one" should ({
        "parse string into a Part" {
            "{x=787,m=2655,a=1222,s=2876}".toPart() shouldBe Part(listOf(787,2655,1222,2876))
        }
        "parse string into a list of rules" {
            val rules = "px{a<2006:qkq,m>2090:A,rfg}".toRules()
            rules[0].value shouldBe 2006
            rules[0].workflow shouldBe "qkq"
            rules[1].value shouldBe 2090
            rules[1].workflow shouldBe "A"
            rules[2].value shouldBe 0
            rules[2].workflow shouldBe "rfg"
        }
        "parse test data into 11 workflows and 5 parts" {
            testData.toParts().size shouldBe 5
            testData.toWorkFlows().size shouldBe 11
        }
        "processing first part in test data gives A" {
            val parts = testData.toParts()
            val workflows = testData.toWorkFlows()
            parts.first().isValid("in", workflows) shouldBe "A"
        }
        "processing 2nd part in test data gives R" {
            val parts = testData.toParts()
            val workflows = testData.toWorkFlows()
            parts[1].isValid("in", workflows) shouldBe "R"
        }
        "part one with test data should be 19114" {
            partOne(testData) shouldBe 19114
        }
        "part one with sample data should be 532551" {
            partOne(sampleData) shouldBe 532551
        }
    })
    "part two" should ({
        "find potential part for test data" {
            val workflows = testData.toWorkFlows()
            val results = PotentialPart().findParts("in", workflows)
            results.sumOf{result->
                (result.maxProperties[0] - result.minProperties[0] + 1).toLong() *
                        (result.maxProperties[1] - result.minProperties[1] + 1).toLong() *
                        (result.maxProperties[2] - result.minProperties[2] + 1).toLong() *
                        (result.maxProperties[3] - result.minProperties[3] + 1).toLong()
            } shouldBe 167409079868000L
        }
        "part two should be 134343280273968L" {
            partTwo(sampleData) shouldBe 134343280273968L
        }
    })
})