import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class MainTest:WordSpec({

    val testdata1 = """
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """.trimIndent().split("\n")

    val testdata2 = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent().split("\n")

    "part one" should ({
        "parse broadcaster -> a, b, c into a broadcast module" {
            val modules = mutableMapOf<String, Module>()
            "broadcaster -> a, b, c".toBroadcaster(modules) shouldBe Broadcaster(modules, listOf("a","b","c"))
        }
        "parse %b -> c into a flip-flop module "  {
            val modules = mutableMapOf<String, Module>()
            "%b -> c".toFlipFlop(modules) shouldBe FlipFlop(Status.Off, modules, listOf("c"))
        }
        "parse &inv -> a into a conjunction module "  {
            val modules = mutableMapOf<String, Module>()
            modules["c"] = Broadcaster(modules, listOf("a","inv","c"))
            modules["d"] = Broadcaster(modules, listOf("a","c"))
            modules.withDestinationOf("inv") shouldBe listOf("c")
            val module = "&inv -> a".toConjunction(modules)
            module.inputs shouldBe listOf("c")
        }
        "update module map with test data" {
            val modules = mutableMapOf<String, Module>()
            testdata1.updateModules(modules)
            val broadcaster =  modules["broadcaster"]
            val a = modules["a"]
            val b = modules["b"]
            val c = modules["c"]
            val inv = modules["inv"]
            broadcaster?.destinations shouldBe listOf("a","b","c")
            a?.destinations shouldBe listOf("b")
            b?.destinations shouldBe listOf("c")
            c?.destinations shouldBe listOf("inv")
            inv?.destinations shouldBe listOf("a")
        }
        "sending a low pulse to broadcast in testdata1" {
            val modules = mutableMapOf<String,Module>()
            testdata1.updateModules(modules)
            modules["button"]?.send(Pulse.Low)
            val lowPulseSent =  modules.values.sumOf { it.pulsesSent.filter { it == Pulse.Low }.size }
            val highPulseSent =  modules.values.sumOf { it.pulsesSent.filter { it == Pulse.High }.size }
            lowPulseSent shouldBe 8
            highPulseSent shouldBe 4
            modules.forEach { println("${it.key} ${it.value.pulsesSent}") }
        }
        "sending a low pulse to broadcast in testdata2" {
            val modules = mutableMapOf<String,Module>()
            testdata2.updateModules(modules)
            modules["button"]?.send(Pulse.Low)
//            val lowPulseSent =  modules.values.sumOf { it.pulsesSent.filter { it == Pulse.Low }.size }
//            val highPulseSent =  modules.values.sumOf { it.pulsesSent.filter { it == Pulse.High }.size }
//            lowPulseSent shouldBe 8
//            highPulseSent shouldBe 4
//            modules.forEach { println("${it.key} ${it.value.pulsesSent}") }
        }
        "part one with testdata1 should be 32000000L" {
            partOne(testdata1) shouldBe 32000000L
        }
        "part one with testdata2 should be 11687500L" {
            partOne(testdata2) shouldBe 11687500L
        }
        "part one with sample data should be 788081152L" {
            partOne(sampleData) shouldBe 788081152L
        }
    })
    "part two" should ({
        "part two should be 224602011344203" {
            partTwo(sampleData) shouldBe 224602011344203L
        }
    })
})