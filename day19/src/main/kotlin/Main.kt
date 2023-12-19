
fun partOne(sampleData:List<String>) :Int {
    val parts = sampleData.toParts()
    val workflows = sampleData.toWorkFlows()
    return parts.filter { part -> part.isValid("in", workflows) == "A" }.sumOf { it.properties.sum() }
}

data class Part(val properties:List<Int>)

fun Part.isValid(workflow:String, workFlows:Map<String, List<Rule>>):String {
    if (workflow == "A" || workflow == "R") return workflow
    val rules = workFlows.getValue(workflow)
    return isValid(rules.first{rule -> rule.isValid(this)}.workflow, workFlows)
}

fun List<String>.toParts() = joinToString("\n").split("\n\n")[1].split("\n")
    .map(String::toPart)

fun List<String>.toWorkFlows() = joinToString("\n").split("\n\n")[0].split("\n")
    .map{ s -> Pair( s.split("{").first(), s.toRules()) }.toMap()

fun String.toPart() = Part(partNos())

data class Rule(val value:Int, val workflow:String, val propertyIndex:Int, val isValid:(Part)->Boolean, val isRange:(PotentialPart)->Boolean, val validPart:(PotentialPart)->PotentialPart, val invalidPart:(PotentialPart)->PotentialPart)

fun String.toRules() = split("{")[1].removeSuffix("}").split(",").map { it.toRule() }

fun String.toRule():Rule {
    val workflow = split(":").last()
    return if (containsAConditon()) {
        if (get(1) == '<') {
            Rule(
                value = value,
                workflow = workflow,
                propertyIndex = propertyIndex,
                isValid = partLessThanRule(propertyIndex, value),
                isRange = potentialPartLessThanRule(propertyIndex, value),
                validPart = potentialPartForLessThan(propertyIndex, value),
                invalidPart = invalidPartForLessThan(propertyIndex ,value)
            )
        } else {
            Rule(
                value = value,
                workflow = workflow,
                propertyIndex = propertyIndex,
                isValid = partGreaterThanRule(propertyIndex, value),
                isRange = potentialPartGreaterThanRule(propertyIndex, value),
                validPart = potentialPartForGreaterThan(propertyIndex, value),
                invalidPart = invalidPartForGreaterThan(propertyIndex ,value)
            )
        }
    } else {
        Rule(0, workflow, 0, { true },{true},{ p -> p},{ p -> p} )
    }
}

fun String.containsAConditon() = (length > 1 && listOf('<','>').any{it == get(1)})
val String.propertyIndex get() = listOf('x', 'm', 'a', 's').indexOfFirst { it == first() }
val String.value get() = drop(2).split(":").first().toInt()

fun partLessThanRule(propertyIndex:Int, value:Int) = { part:Part -> part.properties[propertyIndex] < value }

fun partGreaterThanRule(propertyIndex:Int, value:Int) = { part:Part -> part.properties[propertyIndex] > value }

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

fun String.partNos() = removePrefix("{").removeSuffix("}").split(",").map{it.split("=").last().toInt()}

fun partTwo(sampleData:List<String>):Long {
    val workflows = sampleData.toWorkFlows()
    val results = PotentialPart().findParts("in", workflows)
    return results.sumOf {result->
        (result.maxProperties[0] - result.minProperties[0] + 1).toLong() *
                (result.maxProperties[1] - result.minProperties[1] + 1).toLong() *
                (result.maxProperties[2] - result.minProperties[2] + 1).toLong() *
                (result.maxProperties[3] - result.minProperties[3] + 1).toLong()
    }
}

data class PotentialPart(val minProperties:List<Int> = listOf(1,1,1,1), val maxProperties:List<Int> = listOf(4000,4000, 4000,4000))

fun PotentialPart.findParts(workflowId: String, workFlows: Map<String, List<Rule>>):List<PotentialPart> {
    if (workflowId == "A") return listOf(this)
    if (workflowId == "R") return listOf()
    val rules = workFlows.getValue(workflowId)

    val outputs = rules.fold(Pair(this, listOf<Pair<PotentialPart, String>>())){ (part, output), rule ->
        if (rule.isRange(part)) Pair(rule.invalidPart(part), output + listOf(Pair(rule.validPart(part), rule.workflow)))
        else Pair(rule.invalidPart(part), output)
    }.second

    return outputs.flatMap {(part, workflow) -> part.findParts(workflow, workFlows)  }
}
