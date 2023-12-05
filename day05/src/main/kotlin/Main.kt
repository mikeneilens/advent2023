fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

data class RangeOffset(val range:LongRange, val indexOffset:Long)

data class ResourceMap(val rangeOffsets:List<RangeOffset>) {
    fun destinationIndex(sourceIndex:Long) = sourceIndex + (rangeOffsets.firstOrNull { sourceIndex in it.range }?.indexOffset ?: 0)
}

fun String.toRangeOffset():RangeOffset = RangeOffset(range(), offset())

fun String.offset() = split(" ")[0].toLong() - split(" ")[1].toLong()
fun String.rangeStart() = split(" ")[1].toLong()
fun String.rangeSize() = split(" ")[2].toLong()
fun String.range() = rangeStart() until rangeStart() + rangeSize()

fun partOne(sampleData:List<String>) :Long {
    return sampleData.toSeeds().minOf { seedToLocation(it, sampleData.toResourceMaps()) }
}

fun List<String>.toSeeds() = first().removePrefix("seeds: ").split(" ").map(String::toLong)

fun List<String>.toResourceMaps() = (0..6).map{toResourceMap(it)}

fun List<String>.toResourceMap(n:Int):ResourceMap =
    ResourceMap(joinToString("\n").split("\n\n")[n + 1].split("\n").drop(1).map(String::toRangeOffset))

tailrec fun seedToLocation(source:Long, resourceMaps:List<ResourceMap>):Long =
    if (resourceMaps.isEmpty()) source
    else seedToLocation(resourceMaps.first().destinationIndex(source),resourceMaps. drop(1))

fun partTwo(sampleData:List<String>):Long {
    return if (sampleData.isEmpty()) 0L else 0L - 0L
}