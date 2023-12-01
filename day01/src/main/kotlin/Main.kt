fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun partOne(sampleData:List<String>) :Int {
    return sampleData.calculateCalibrationValue()
}

fun List<String>.calculateCalibrationValue() = sumOf(String::calculateCalibrationValue)

fun String.calculateCalibrationValue() = firstDigit() * 10 + lastDigit()

fun String.firstDigit() = first(Char::isDigit).digitToInt()

fun String.lastDigit() = last(Char::isDigit).digitToInt()

fun partTwo(sampleData:List<String>):Int {
    return sampleData.calculateAdjustedCalibrationValue()
}
fun List<String>.calculateAdjustedCalibrationValue() = sumOf(String::calculateAdjustedCalibrationValue)

fun String.calculateAdjustedCalibrationValue() = replaceNumericalText().calculateCalibrationValue()

fun String.replaceNumericalText(numberMaps:List<Pair<String, String>> =
                                    listOf("zero" to "z0ero",
                                        "one" to "o1ne",
                                        "two" to "t2wo",
                                        "three" to "t3hree",
                                        "four" to "f4our",
                                        "five" to "f5ive",
                                        "six" to "s6ix",
                                        "seven" to "s7even",
                                        "eight" to "e8ight",
                                        "nine" to "n9ine")
):String = numberMaps.fold(this, ::replaceNumericalText)

fun replaceNumericalText(s:String, numberMap:Pair<String, String>) = s.replace(numberMap.first, numberMap.second)