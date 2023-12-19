fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun partOne(sampleData:List<String>) :Int {
    val parts = sampleData.toParts()
    val workflows = sampleData.toWorkFlows()
    return parts.filter { part -> part.isValid("in", workflows) == "A" }.sumOf { it.m + it.a + it.s + it.x }
}

fun Part.isValid(workflow:String, workFlows:Map<String, List<Rule>>):String {
    if (workflow == "A" || workflow == "R") return workflow
    val rules = workFlows.getValue(workflow)
    return isValid(rules.first{rule -> rule.isValid(this)}.workflow, workFlows)
}

fun List<String>.toParts() = joinToString("\n").split("\n\n")[1].split("\n")
    .map(String::toPart)

fun List<String>.toWorkFlows() = joinToString("\n").split("\n\n")[0].split("\n")
    .map{ s -> Pair( s.split("{").first(), s.toRules()) }.toMap()

data class Part(val x:Int, val m:Int, val a:Int, val s:Int)

fun String.toPart() = Part(partNos()[0], partNos()[1], partNos()[2], partNos()[3])

fun String.partNos() = removePrefix("{").removeSuffix("}").split(",").map{it.split("=").last().toInt()}

fun String.toRules() =
    split("{")[1].removeSuffix("}").split(",").map(String::toRule)

fun String.toRule() = when (take(2)) {
        "x<" -> XLessThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "x>" -> XGreaterThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "m<" -> MLessThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "m>" -> MGreaterThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "a<" -> ALessThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "a>" -> AGreaterThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "s<" -> SLessThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        "s>" -> SGreaterThan(drop(2).split(":").first().toInt(), drop(2).split(":").last())
        else -> Default(0, this)
    }

interface Rule {
    val value:Int
    val workflow:String
    fun isValid(part:Part):Boolean
    fun isValid(part:PotentialPart):Boolean
    fun validPart(part:PotentialPart):PotentialPart
    fun invalidPart(part:PotentialPart):PotentialPart
}
data class XLessThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.x < value)
    override fun isValid(part: PotentialPart) = (part.minX < value)
    override fun validPart(part: PotentialPart) = if ( part.maxX >= value) part.copy(maxX = value - 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(minX = maxOf(part.minX, value))
}
data class XGreaterThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.x > value)
    override fun isValid(part: PotentialPart) = (part.maxX > value)
    override fun validPart(part: PotentialPart) = if ( part.minX <= value) part.copy(minX = value + 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(maxX = minOf(part.maxX, value))
}
data class MLessThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.m < value)
    override fun isValid(part: PotentialPart) = (part.minM < value)
    override fun validPart(part: PotentialPart) = if ( part.maxM >= value) part.copy(maxM = value - 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(minM = maxOf(part.minM, value))
}
data class MGreaterThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.m > value)
    override fun isValid(part: PotentialPart) = (part.maxM > value)
    override fun validPart(part: PotentialPart) = if ( part.minM <= value) part.copy(minM = value + 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(maxM = minOf(part.maxM, value))
}
data class ALessThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.a < value)
    override fun isValid(part: PotentialPart) = (part.minA < value)
    override fun validPart(part: PotentialPart) = if ( part.maxA >= value) part.copy(maxA = value - 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(minA = maxOf(part.minA, value))
}
data class AGreaterThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.a > value)
    override fun isValid(part: PotentialPart) = (part.maxA > value)
    override fun validPart(part: PotentialPart) = if ( part.minA <= value) part.copy(minA = value + 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(maxA = minOf(part.maxA, value))
}
data class SLessThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.s < value)
    override fun isValid(part: PotentialPart) = (part.minS < value)
    override fun validPart(part: PotentialPart) = if ( part.maxS >= value) part.copy(maxS = value - 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(minS = maxOf(part.minS, value))
}
data class SGreaterThan(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = (part.s > value)
    override fun isValid(part: PotentialPart) = (part.maxS > value)
    override fun validPart(part: PotentialPart) = if ( part.minS <= value) part.copy(minS = value + 1) else part
    override fun invalidPart(part: PotentialPart) = part.copy(maxS = minOf(part.maxS, value))
}
data class Default(override val value: Int, override val workflow: String) :Rule {
    override fun isValid(part: Part) = true
    override fun isValid(part: PotentialPart) = true
    override fun validPart(part: PotentialPart) =  part
    override fun invalidPart(part: PotentialPart) =  part
}

fun partTwo(sampleData:List<String>):Long {
    val workflows = sampleData.toWorkFlows()
    val results = PotentialPart().findParts("in", workflows)
    return results.sumOf {result->
        (result.maxA - result.minA + 1).toLong() *
                (result.maxX - result.minX + 1 ).toLong() *
                (result.maxM - result.minM  +1).toLong() *
                (result.maxS - result.minS + 1).toLong() }
}

fun PotentialPart.findParts(workflowId: String, workFlows: Map<String, List<Rule>>):List<PotentialPart> {
    if (workflowId == "A") return listOf(this)
    if (workflowId == "R") return listOf()
    val rules = workFlows.getValue(workflowId)

    val outputs = rules.fold(Pair(this, listOf<Pair<PotentialPart, String>>())){ (part, output), rule ->
        if (rule.isValid(part)) Pair(rule.invalidPart(part), output + listOf(Pair(rule.validPart(part), rule.workflow)))
        else Pair(rule.invalidPart(part), output)
    }.second

    return outputs.flatMap {(part, workflow) -> part.findParts(workflow, workFlows)  }
}

data class PotentialPart(val minX:Int = 1, val maxX:Int = 4000,
                         val minM:Int = 1, val maxM:Int = 4000,
                         val minA:Int = 1,val maxA:Int = 4000,
                         val minS:Int = 1, val maxS:Int = 4000)