fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}


fun getRanges(n:Int, max:Int) =
    if (n <= max/2) Pair(0..n, (n + 1) ..(2*n + 1))
    else Pair( (n - (max + 1 - n) )..n - 1   ,n .. max)

fun List<String>.toMirrors() = joinToString("\n").split("\n\n").map{it.split("\n")}

fun List<String>.verticalRanges() =
    (0..lastIndex).map{i -> getRanges(i, lastIndex)}.filter{it.second.last <= lastIndex}


fun List<String>.mirrorOnRow(filter:List<Int> = listOf(-1)):Int {
    val ranges = verticalRanges()
    val matchingRow =  ranges.mapIndexed {i, (r1,r2) -> if (r1.zip(r2.reversed()).all{this[it.first] == this[it.second]}) i else -1}.filter{it !in filter}.maxOrNull()
    return if (matchingRow!= null) ranges[matchingRow].first.last + 1 else 0
}

fun List<String>.transpose() =  first().indices.map{i -> map{ it[i]}.joinToString("") }

fun List<String>.mirrorOnColumn(filter:List<Int> = listOf(-1)):Int {
    return transpose().mirrorOnRow(filter)
}

fun partOne(sampleData:List<String>) :Int {
    return sampleData.toMirrors().map{Pair(it.mirrorOnRow(),it.mirrorOnColumn())}.sumOf { it.first *100 + it.second }
}
fun partTwo(sampleData:List<String>):Int {
    return sampleData.toMirrors().map{it.alternateMirror()}.sumOf { it.first *100 + it.second }
}

fun List<String>.alternateMirror():Pair<Int, Int> {
    val currentMirrorOnRow = mirrorOnRow()
    val currentMirrorOnCol = mirrorOnColumn()
    (0..(first().lastIndex)).forEach{ x->
        (0..lastIndex).forEach{ y ->
            val updatedWindow = replace(x,y)
            updatedWindow.mirrorOnLine(currentMirrorOnRow,List<String>::mirrorOnRow)?.let { return Pair(it,0) }
            updatedWindow.mirrorOnLine(currentMirrorOnCol,List<String>::mirrorOnColumn)?.let { return Pair(0,it) }
        }
    }
    return Pair(0,0 )
}

fun List<String>.mirrorOnLine(current:Int, mirrorOnRowOrColumn:List<String>.(List<Int>)->Int ):Int? {
    val mirrorOnLine = mirrorOnRowOrColumn(listOf(-1, current - 1 ))
    if (mirrorOnLine > 0 && mirrorOnLine != current) return mirrorOnLine else return null
}

fun List<String>.replace(x:Int, y:Int) =
    mapIndexed{row, string -> string.mapIndexed{col, c-> if (row == y && col == x) c.swapChar() else c }.joinToString("")  }

fun Char.swapChar() = if (this == '.') '#' else '.'
