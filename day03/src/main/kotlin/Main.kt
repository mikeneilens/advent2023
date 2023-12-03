fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

data class NumberChunk(val columnRange:IntRange, val row:Int, val data:List<String>) {

    fun adjacentPositions() =
        (columnRange.widenBy(1 )).flatMap{ x ->
            ((row..row).widenBy(1)).map{ y -> Position(y,x) }
        }.filter {position -> position.isInRange(data) && !position.overlapsNumberChunk(this) }

    fun extendWidth() = this.copy(columnRange = columnRange.first..(columnRange.last + 1))

    fun value() = data[row].substring(columnRange).toInt()

    fun isAdjacentToSymbol() = adjacentPositions().any {position -> position.containsSymbol(data) }

    fun adjacentPositionsContainingAsterisk() = adjacentPositions().filter {data[it.row][it.col] == '*'}
}

fun IntRange.widenBy(r:Int) = (this.first - r).. (this.last + r)

data class Position(val row:Int, val col:Int) {

    fun isInRange(data:List<String>) = row in data.rowRange && col in data.colRange

    fun overlapsNumberChunk(numberChunk: NumberChunk) = row == numberChunk.row && col in numberChunk.columnRange

    fun containsSymbol(data:List<String>) = data[row][col].isSymbol()
}

val List<String>.colRange get() = 0..(first().lastIndex)
val List<String>.rowRange get() = 0.. (lastIndex)

fun Char.isSymbol() = !(this.isDigit() || this == '.')

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toNumberChunks()
        .chunksAdjacentToSymbols()
        .sumOf { it.value() }
}

fun List<String>.toNumberChunks() = flatMapIndexed{ i, s -> s.toNumberChunks(i, this)}

fun String.toNumberChunks(row:Int, data:List<String>) =
    foldIndexed(mutableListOf<NumberChunk?>()){col, list, newItem ->
        if (newItem.isDigit()) {
            list.insertOrUpdateLastNumberChunk(col, row, data)
        } else {
            list.add(null)
        }
        list
    }.filterNotNull()

fun MutableList<NumberChunk?>.insertOrUpdateLastNumberChunk(col: Int, row: Int, data: List<String>) {
    if (isEmpty() || last() == null) {
        add(NumberChunk(columnRange = col.. col, row = row, data = data))
    } else {
        this[lastIndex] = last()?.extendWidth()
    }
}

fun List<NumberChunk>.chunksAdjacentToSymbols() = filter{it.isAdjacentToSymbol() }

fun partTwo(sampleData:List<String>):Int {
    val asteriskMap = mutableMapOf<Position,List<NumberChunk>>()
    asteriskMap.updateAsteriskMap(sampleData.toNumberChunks())
    return asteriskMap.gearRatios().sum()
}

fun MutableMap<Position, List<NumberChunk>>.updateAsteriskMap(numberChunks: List<NumberChunk>) {
    numberChunks.forEach { numberChunk ->
        numberChunk.adjacentPositionsContainingAsterisk().forEach {position ->
            val currentNumberChunks = get(position) ?: listOf()
            set(position, currentNumberChunks + numberChunk)
        }
    }
}

fun Map<Position, List<NumberChunk>>.gearRatios() =
    toList().mapNotNull{ (_, numberChunks) ->
        if (numberChunks.size == 2)
            numberChunks.fold(1){ result, numberChunk -> result * numberChunk.value()}
            else null
    }
