fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

data class NumberChunk(val startCol:Int, var endCol:Int, val row:Int, val data:List<String>) {

    fun adjacentPositions() =
        ((startCol - 1)..(endCol + 1 )).flatMap{ x ->
            ((row - 1)..(row + 1 )).map{ y -> Position(y,x) }
        }.filter {it.isInRange(data.maxRow, data.maxCol) && !it.overlapsNumberChunk(this) }

    fun value() = data[row].substring(startCol..endCol).toInt()

    fun isAdjacentToSymbol() = adjacentPositions().any { it.containsSymbol(data) }
}

data class Position(val row:Int, val col:Int) {

    fun isInRange(maxRow:Int, maxCol:Int) = row in 0..maxRow && col in 0..maxCol

    fun overlapsNumberChunk(numberChunk: NumberChunk) = row == numberChunk.row && col in (numberChunk.startCol..numberChunk.endCol)

    fun containsSymbol(data:List<String>) = data[row][col] !in '0'..'9' && data[row][col] != '.'
}

val List<String>.maxCol get() = first().length - 1
val List<String>.maxRow get() = size - 1

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toNumberChunks().adjacentToSymbols().sumOf { it.value() }
}

fun List<String>.toNumberChunks() = flatMapIndexed{ i, s -> s.toNumberChunks(i, this)}

fun String.toNumberChunks(row:Int, data:List<String>) =
    foldIndexed(listOf(null)){col, list:List<NumberChunk?>, newItem ->
        if (newItem in '0'..'9') {
            val lastItem:NumberChunk? = list.last()
            if (lastItem == null) {
                list + NumberChunk(startCol = col, endCol = col, row = row, data = data)
            } else {
                lastItem.endCol = col
                list
            }
        } else {
            list + null
        }
    }.filterNotNull()

fun List<NumberChunk>.adjacentToSymbols() = filter{it.isAdjacentToSymbol() }

fun partTwo(sampleData:List<String>):Int {
    return 0
}