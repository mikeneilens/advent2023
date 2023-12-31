fun partOne(sampleData:String) :Int {
    return sampleData.split(",").sumOf(String::hashAlgorithm)
}

fun String.hashAlgorithm() = fold(0){current, char -> ((current + char.code) * 17).rem(256) }

fun partTwo(sampleData:String):Int {
    val map:MutableMap<Int, Lens> = (0..255).map{Pair(it, Lens("Root", 0))}.toMap().toMutableMap()
    sampleData.toLensInstruction().forEach { it.execute(map) }
    return map.toList().sumOf {(key, value) -> (key + 1) * value.power(0) }
}

fun String.toLensInstruction() =
    split(",").map{string ->
        if (string.last() == '-') RemoveLens(string.dropLast(1))
        else UpsertLens(string.split("=").first(), string.split("=").last().toInt() )
    }

class Lens(val name:String, val focalLength:Int, var prev:Lens? = null, var next:Lens? = null) {

    fun findLensOrNull(name:String):Lens? = if (name == this.name) this else if (next == null) null else next?.findLensOrNull(name)

    fun lastLens():Lens? = if (next == null) this else next?.lastLens()

    fun removeLens(name:String) {
        findLensOrNull(name)?.let { lens ->
            lens.prev?.next = lens.next
            lens.next?.prev = lens.prev
            lens.prev = null
            lens.next = null
        }
    }
    fun upsertLens(newLens:Lens) {
        findLensOrNull(newLens.name)?.replaceLensWith(newLens) ?: lastLens()?.insertLens(newLens)
    }

    fun replaceLensWith(newLens:Lens) {
        newLens.prev = prev
        newLens.next = next
        prev?.next = newLens
        next?.prev = newLens
        prev = null
        next = null
    }
    fun insertLens(newLens:Lens) {
        next = newLens
        newLens.prev = this
    }

    fun power(num:Int = 1):Int = num * focalLength + (next?.power(num + 1) ?: 0)

    override fun toString() = "Lens(name=\"$name\", focalLength=$focalLength, next=$next)"
}

sealed class LensInstruction(val name:String) {
    val box = name.hashAlgorithm()
    abstract fun execute(map: MutableMap<Int, Lens>)
}

class UpsertLens(name:String, val focalLength:Int):LensInstruction(name){

    override fun execute(map: MutableMap<Int, Lens>) { map[box]?.upsertLens(Lens(name, focalLength)) }

    override fun toString() = "UpsertLens(name=\"$name\", focalLength=$focalLength)"
    override fun equals(other: Any?) = other is UpsertLens && name == other.name
}

class RemoveLens(name:String):LensInstruction(name) {

    override fun execute(map: MutableMap<Int, Lens>) { map[box]?.removeLens(name) }

    override fun toString() = "RemoveLens(name=\"$name\")"
    override fun equals(other: Any?) = other is RemoveLens && name == other.name
}