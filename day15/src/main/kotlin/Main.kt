fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun partOne(sampleData:String) :Int {
    return sampleData.split(",").sumOf(String::hashAlgorithm)
}

fun String.hashAlgorithm() = fold(0){current, char -> ((current + char.code) * 17).rem(256) }

fun partTwo(sampleData:String):Int {
    return 0
}