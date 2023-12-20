
fun partOne(sampleData:List<String>) :Long {
    val modules = sampleData.createModules()
    repeat(1000){modules["button"]?.send(Pulse.Low)}
    return countPulses(Pulse.Low, modules) * countPulses(Pulse.High, modules)
}

private fun countPulses(pulseType:Pulse, modules: Map<String, Module>) =
    modules.values.sumOf { it.pulsesSent.filter { it == pulseType }.size }.toLong()

fun List<String>.createModules(modules:MutableMap<String, Module> = mutableMapOf()):Map<String, Module> {
    filter(String::isBroadcaster).forEach { modules["broadcaster"] = it.toBroadcaster(modules) }
    filter(String::isFlipFlop).forEach { modules[it.moduleName] = it.toFlipFlop(modules) }
    filter(String::isConjunction).forEach { modules[it.moduleName] = it.toConjunction(modules) }
    filter(String::isConjunction).forEach { modules[it.moduleName] = it.toConjunction(modules) }
    modules["button"] = Button(modules, listOf("broadcaster"))
    return modules
}

fun String.isBroadcaster() = startsWith("broad")
fun String.isFlipFlop() = startsWith("%")
fun String.isConjunction() = startsWith("&")

fun String.toBroadcaster(modules:MutableMap<String, Module>) = Broadcaster(modules, destinations)

fun String.toFlipFlop(modules:MutableMap<String, Module>) = FlipFlop(modules = modules, destinations =  destinations)

fun String.toConjunction(modules:MutableMap<String, Module>) =
    Conjunction(modules.withDestinationOf(moduleName), modules, destinations)

val String.moduleName get() = drop(1).split(" -> ")[0]
val String.destinations get() = split(" -> ")[1].split(", ")

fun MutableMap<String, Module>.withDestinationOf(s:String) = filter { (_, value) -> value.destinations.contains(s) }.keys.toList()

enum class Pulse{High, Low}
enum class Status{On, Off}

interface Module {
    val modules:Map<String, Module>
    val destinations:List<String>
    val pulsesSent:MutableList<Pulse>

    fun receive(pulse:Pulse)

    fun send(pulse:Pulse) = destinations.forEach {pulsesSent.add(pulse); modules[it]?.receive(pulse) }

    fun lastPulseSent() = pulsesSent.lastOrNull()

    fun pulsesSentInclude(pulse:Pulse) = pulsesSent.contains(pulse)
}

data class Button(override val modules:Map<String, Module>, override val destinations:List<String>, override val pulsesSent:MutableList<Pulse> = mutableListOf()):Module {
    override fun receive(pulse: Pulse) = send(pulse)

    override fun toString() = "Button( destinations = $destinations pulsesSent = $pulsesSent)"
}

data class Broadcaster(override val modules:Map<String, Module>, override val destinations:List<String>, override val pulsesSent:MutableList<Pulse> = mutableListOf()):Module {
    override fun receive(pulse: Pulse) = send(pulse)

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
        if (inputs.all{moduleName -> modules[moduleName]?.lastPulseSent() == Pulse.High})
            send(Pulse.Low)
        else
            send(Pulse.High)
    }
    override fun toString() = "Conjunction( destinations = $destinations pulsesSent = $pulsesSent)"
}

fun partTwo(sampleData:List<String>):Long {
    val pressesForFT = buttonPressesToPulseHigh(sampleData.createModules(), "ft")
    val pressesForJZ = buttonPressesToPulseHigh(sampleData.createModules(), "jz")
    val pressesForSV = buttonPressesToPulseHigh(sampleData.createModules(), "sv")
    val pressesForNG = buttonPressesToPulseHigh(sampleData.createModules(), "ng")
    return lowestCommonMultiple(listOf( pressesForFT, pressesForJZ, pressesForNG, pressesForSV))
}

fun buttonPressesToPulseHigh(modules:Map<String, Module>, moduleName:String) =
     (1..100000L).first {
        modules["button"]?.send(Pulse.Low)
        modules.getValue(moduleName).pulsesSentInclude(Pulse.High)
    }

fun lowestCommonMultiple(numbers: List<Long>): Long =
    numbers.fold(1L) { x, y -> x * (y / greatestCommonDenominator(x, y)) }

fun greatestCommonDenominator(x: Long, y: Long):Long =
    if (y == 0L) x else greatestCommonDenominator(y, x % y)