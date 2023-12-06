fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

data class RangeOffset(val range:LongRange, val indexOffset:Long)

data class ResourceMap(val rangeOffsets:List<RangeOffset>) {
    fun destinationIndex(sourceIndex:Long) = sourceIndex + (rangeOffsets.firstOrNull { sourceIndex in it.range }?.indexOffset ?: 0)

    fun destinationRanges(sourceRanges:List<LongRange>):List<LongRange> {
        val segmentedRanges = sourceRanges.segmentUsing(rangeOffsets)
        return segmentedRanges.map{ segmentedRange ->
            val indexOfOffset = rangeOffsets.indexOfFirst { segmentedRange inside it.range }
            if (indexOfOffset >= 0) segmentedRange.start + rangeOffsets[indexOfOffset].indexOffset .. segmentedRange.last + rangeOffsets[indexOfOffset].indexOffset
            else segmentedRange
        }
    }
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
    ResourceMap(joinToString("\n").split("\n\n")[n + 1].split("\n").drop(1).map(String::toRangeOffset).sortedBy { it.range.start })

tailrec fun seedToLocation(source:Long, resourceMaps:List<ResourceMap>):Long =
    if (resourceMaps.isEmpty()) source
    else seedToLocation(resourceMaps.first().destinationIndex(source),resourceMaps. drop(1))

fun partTwo(sampleData:List<String>):Long {
    return seedToLocation(sampleData.toSeedRanges(), sampleData.toResourceMaps()).minOf { it.first }
}

fun List<String>.toSeedRanges() = first().removePrefix("seeds: ").split(" ")
    .map(String::toLong).chunked(2).map{ LongRange(it.first(),(it.first() + it.last() - 1)) }

fun seedToLocation(source:List<LongRange>, resourceMaps:List<ResourceMap>):List<LongRange> {
    return if (resourceMaps.isEmpty()) source
    else source.flatMap{range -> seedToLocation(resourceMaps.first().destinationRanges(listOf(range)),resourceMaps.drop(1))}
}

fun LongRange.segmentUsing(targetRange:LongRange):List<LongRange> {
    if (before(targetRange) || after(targetRange) || inside(targetRange)) return listOf(this)
    if (targetRange inside this) return listOf( start..(targetRange.start -1), targetRange, (targetRange.last + 1)..last )
    if (start < targetRange.start) return listOf( start..(targetRange.start -1), targetRange.start..last  )
    if (start > targetRange.start) return listOf( start..(targetRange.last), (targetRange.last + 1)..last  )
    return listOf()
}

fun List<LongRange>.segmentUsing(rangeOffsets:List<RangeOffset>):List<LongRange> {
    if (rangeOffsets.isEmpty()) return this
    else return flatMap{it.segmentUsing(rangeOffsets.first().range)}.segmentUsing(rangeOffsets.drop(1))
}

infix fun LongRange.before(other:LongRange) = last < other.start
infix fun LongRange.after(other:LongRange) = start > other.last
infix fun LongRange.inside(other:LongRange) = start >= other.start && last <= other.last