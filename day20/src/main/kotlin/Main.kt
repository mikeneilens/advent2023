
fun partOne(sampleData:List<String>) :Long {
    val modules = mutableMapOf<String, Module>()
    sampleData.updateModules(modules)
    repeat(1000){modules["button"]?.send(Pulse.Low)}
    val pulsesSent = Pair(modules.values.sumOf { it.pulsesSent.filter { it == Pulse.Low }.size }.toLong(),
                          modules.values.sumOf { it.pulsesSent.filter { it == Pulse.High }.size }.toLong())
    return pulsesSent.first * pulsesSent.second
}

fun List<String>.updateModules(modules:MutableMap<String, Module>) {
    filter(String::isBroadcaster).forEach { modules["broadcaster"] = it.toBroadcaster(modules) }
    filter(String::isFlipFlop).forEach { modules[it.flipFlopName] = it.toFlipFlop(modules) }
    filter(String::isConjunction).forEach { modules[it.conjunctionName] = it.toConjunction(modules) }
    filter(String::isConjunction).forEach { modules[it.conjunctionName] = it.toConjunction(modules) }
    modules["button"] = Button(modules, listOf("broadcaster"))
}

fun String.isBroadcaster() = startsWith("broad")
fun String.isFlipFlop() = startsWith("%")
fun String.isConjunction() = startsWith("&")

fun String.toBroadcaster(modules:MutableMap<String, Module>) = Broadcaster(modules, destinations)

fun String.toFlipFlop(modules:MutableMap<String, Module>) = FlipFlop(modules = modules, destinations =  destinations)

fun String.toConjunction(modules:MutableMap<String, Module>) =
    Conjunction(modules.withDestinationOf(conjunctionName), modules, destinations)

val String.conjunctionName get() = drop(1).split(" -> ")[0]
val String.flipFlopName get() = drop(1).split(" -> ")[0]
val String.destinations get() = split(" -> ")[1].split(", ")

fun MutableMap<String, Module>.withDestinationOf(s:String) = filter { (_, value) -> value.destinations.contains(s) }.keys.toList()

enum class Pulse{High, Low}
enum class Status{On, Off}

interface Module {
    val modules:Map<String, Module>
    val destinations:List<String>
    val pulsesSent:MutableList<Pulse>

    fun receive(pulse:Pulse)
    fun send(pulse:Pulse) {
        destinations.forEach {pulsesSent.add(pulse); modules[it]?.receive(pulse) }
    }
}

data class Button(override val modules:Map<String, Module>, override val destinations:List<String>, override val pulsesSent:MutableList<Pulse> = mutableListOf()):Module {
    override fun receive(pulse: Pulse) {
        send(pulse)
    }
    override fun toString() = "Button( destinations = $destinations pulsesSent = $pulsesSent)"
}

data class Broadcaster(override val modules:Map<String, Module>, override val destinations:List<String>, override val pulsesSent:MutableList<Pulse> = mutableListOf()):Module {
    override fun receive(pulse: Pulse) {
        send(pulse)
    }
    override fun toString() = "Broadcaster( destinations = $destinations pulsesSent = $pulsesSent)"
}

data class FlipFlop(var status:Status = Status.Off, override val modules:Map<String, Module>, override val destinations:List<String>, override val pulsesSent:MutableList<Pulse> = mutableListOf()):Module {
    override fun receive(pulse: Pulse) {
        if (pulse == Pulse.High) return
        if (status == Status.Off) {
            status = Status.On
            send(Pulse.High)
        } else {
            status = Status.Off
            send(Pulse.Low)
        }
    }
    override fun toString() = "FlipFlop( status $status destinations = $destinations pulsesSent = $pulsesSent)"
}

data class Conjunction(val inputs:List<String>, override val modules:Map<String, Module>, override val destinations:List<String>, override val pulsesSent:MutableList<Pulse> = mutableListOf()):Module {
    override fun receive(pulse: Pulse) {
        if (inputs.isNotEmpty() && inputs.all{s -> if(modules[s]?.pulsesSent?.size == 0) false else modules[s]?.pulsesSent?.last() == Pulse.High})
            send(Pulse.Low)
        else
            send(Pulse.High)
    }
    override fun toString() = "Conjunction( destinations = $destinations pulsesSent = $pulsesSent)"
}

fun partTwo(sampleData:List<String>):Long {
    val modules = mutableMapOf<String, Module>()
    sampleData.updateModules(modules)
    val pressesForFT = buttonPressesToPulseHigh(modules, "ft")
    val pressesForJZ = buttonPressesToPulseHigh(modules, "jz")
    val pressesForSV = buttonPressesToPulseHigh(modules, "sv")
    val pressesForNG = buttonPressesToPulseHigh(modules, "ng")

    return lowestCommonMultiple(listOf( pressesForFT, pressesForJZ, pressesForNG, pressesForSV))
}

fun buttonPressesToPulseHigh(modules:MutableMap<String, Module>,module:String):Long {
    sampleData.updateModules(modules)
    var count = 0
    while ( Pulse.High !in  (modules[module]?.pulsesSent ?: listOf())) {
        modules["button"]?.send(Pulse.Low)
        count++
    }
    return count.toLong()
}

fun lowestCommonMultiple(numbers: List<Long>): Long =
    numbers.fold(1L) { x, y -> x * (y / greatestCommonDenominator(x, y)) }

fun greatestCommonDenominator(x: Long, y: Long):Long =
    if (y == 0L) x else greatestCommonDenominator(y, x % y)