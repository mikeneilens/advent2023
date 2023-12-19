
fun partOne(sampleData:List<String>) :Int {
    val parts = sampleData.toParts()
    val workflows = sampleData.toWorkFlows()
    return parts.filter { part -> part.isValid("in", workflows) == "A" }.sumOf { it.minProperties.sum() }
}
data class PotentialPart(val minProperties:List<Int> = listOf(1,1,1,1), val maxProperties:List<Int> = listOf(4000,4000, 4000,4000))

fun PotentialPart.isValid(workflow:String, workFlows:Map<String, List<Rule>>):String {
    if (workflow == "A" || workflow == "R") return workflow
    val rules = workFlows.getValue(workflow)
    return isValid(rules.first{rule -> rule.isPotentialPart(this)}.workflow, workFlows)
}

fun List<String>.toParts() = joinToString("\n").split("\n\n")[1].split("\n")
    .map(String::toPart)

fun String.toPart() = PotentialPart(partNos(), partNos())

fun String.partNos() = removePrefix("{").removeSuffix("}").split(",").map{it.split("=").last().toInt()}

data class Rule(val value:Int, val workflow:String, val propertyIndex:Int, val isPotentialPart:(PotentialPart)->Boolean, val validPart:(PotentialPart)->PotentialPart, val invalidPart:(PotentialPart)->PotentialPart)

fun List<String>.toWorkFlows() =
    joinToString("\n").split("\n\n")[0].split("\n").associate { s -> Pair(s.split("{").first(), s.toRules()) }

fun String.toRules() = split("{")[1].removeSuffix("}").split(",").map { it.toRule() }

fun String.toRule() =
    if (!containsACondition()) Rule(0, workflow, 0, {true},{ p -> p},{ p -> p} )
    else Rule(
        value = value,
        workflow = workflow,
        propertyIndex = propertyIndex,
        isPotentialPart = if (get(1) == '<') potentialPartLessThanRule(propertyIndex, value) else potentialPartGreaterThanRule(propertyIndex, value),
        validPart = if (get(1) == '<') potentialPartForLessThan(propertyIndex, value) else potentialPartForGreaterThan(propertyIndex, value),
        invalidPart = if (get(1) == '<') invalidPartForLessThan(propertyIndex ,value) else invalidPartForGreaterThan(propertyIndex ,value)
    )

fun String.containsACondition() = (length > 1 && listOf('<','>').any{it == get(1)})
val String.value get() = drop(2).split(":").first().toInt()
val String.workflow get() = split(":").last()
val String.propertyIndex get() = listOf('x', 'm', 'a', 's').indexOfFirst { it == first() }

fun potentialPartLessThanRule(propertyIndex:Int, value:Int) = { part:PotentialPart -> part.minProperties[propertyIndex] < value }

fun potentialPartGreaterThanRule(propertyIndex:Int, value:Int) = { part:PotentialPart -> part.maxProperties[propertyIndex] > value }

fun potentialPartForLessThan(propertyIndex:Int, value:Int) =
    { part:PotentialPart ->
        if (part.maxProperties[propertyIndex] >= value) part.copy(maxProperties = part.maxProperties.replaceValueAt(propertyIndex, value - 1))
        else part}

fun potentialPartForGreaterThan(propertyIndex:Int, value:Int) =
    { part:PotentialPart ->
        if(part.minProperties[propertyIndex] <= value) part.copy(minProperties = part.minProperties.replaceValueAt(propertyIndex, value + 1))
        else part}

fun invalidPartForLessThan(propertyIndex:Int, value:Int) =
    { part:PotentialPart ->
        part.copy(minProperties = part.minProperties.replaceValueAt(propertyIndex, maxOf(part.minProperties[propertyIndex], value)))}

fun invalidPartForGreaterThan(propertyIndex:Int, value:Int) =
    { part:PotentialPart ->
        part.copy(maxProperties = part.maxProperties.replaceValueAt(propertyIndex, minOf(part.maxProperties[propertyIndex], value)))}

fun List<Int>.replaceValueAt(index:Int, new:Int) = mapIndexed { i, v -> if(i == index ) new else v }

fun partTwo(sampleData:List<String>):Long {
    val workflows = sampleData.toWorkFlows()
    return PotentialPart().findParts("in", workflows).ratings()
}

fun List<PotentialPart>.ratings() = sumOf {part ->
    part.maxProperties.mapIndexed {i, maxProperties -> (maxProperties - part.minProperties[i] + 1).toLong()}.reduce(Long::times)
}

fun PotentialPart.findParts(workflowId: String, workFlows: Map<String, List<Rule>>):List<PotentialPart> {
    if (workflowId == "A") return listOf(this)
    if (workflowId == "R") return listOf()
    val rules = workFlows.getValue(workflowId)

    val outputs = rules.fold(Pair(this, listOf<Pair<PotentialPart, String>>())){ (part, output), rule ->
        if (rule.isPotentialPart(part)) Pair(rule.invalidPart(part), output + listOf(Pair(rule.validPart(part), rule.workflow)))
        else Pair(rule.invalidPart(part), output)
    }.second

    return outputs.flatMap {(part, workflow) -> part.findParts(workflow, workFlows)  }
}
