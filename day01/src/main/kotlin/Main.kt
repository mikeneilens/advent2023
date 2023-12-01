fun main(args: Array<String>) {
    partOne(sampleData)
    partTwo(sampleData)
}

fun String.firstDigit() = first{ it.isDigit() }.digitToInt()

fun String.lastDigit() = last{ it.isDigit() }.digitToInt()

fun String.calibrationValue() = firstDigit() * 10 + lastDigit()

fun List<String>.calibrationValue() = sumOf(String::calibrationValue)

fun partOne(sampleData:List<String>) :Int {
    return sampleData.calibrationValue()
}

fun String.replaceNumericalText(numberMap:Pair<String, String>) = replace(numberMap.first, numberMap.second)

fun String.replaceNumericalText(numberMap:List<Pair<String, String>> =
                                    listOf("zero" to "0zero",
                                        "one" to "o1ne",
                                        "two" to "t2wo",
                                        "three" to "t3hree",
                                        "four" to "f4our",
                                        "five" to "f5ive",
                                        "six" to "s6ix",
                                        "seven" to "s7even",
                                        "eight" to "e8ight",
                                        "nine" to "n9ine")
):String =
    if (numberMap.isEmpty()) this
    else replaceNumericalText(numberMap.first()).replaceNumericalText(numberMap.drop(1))

fun String.adjustedCalibrationValue() = replaceNumericalText().calibrationValue()

fun List<String>.adjustedCalibrationValue() = sumOf(String::adjustedCalibrationValue)

fun partTwo(sampleData:List<String>):Int {
    return sampleData.adjustedCalibrationValue()
}