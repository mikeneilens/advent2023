fun main() {
    partOne(sampleData)
    partTwo(sampleData)
}

interface CubeType  {
    val quantity:Int
    val maxQuantity:Int
    fun isValid() = quantity <= maxQuantity
}

data class RedCube(override val quantity:Int, override val maxQuantity:Int = 12):CubeType
data class BlueCube(override val quantity:Int, override val maxQuantity:Int = 14):CubeType
data class GreenCube(override val quantity:Int, override val maxQuantity: Int = 13):CubeType
data class UnknownCube(override val quantity:Int = 0, override val maxQuantity: Int = 0):CubeType

fun String.toCube() = when (this.split(" ").last()) {
    "red" -> RedCube(this.split(" ").first().toInt())
    "green" -> GreenCube(this.split(" ").first().toInt())
    "blue" -> BlueCube(this.split(" ").first().toInt())
    else -> UnknownCube()
}

data class Game(val gameNo:Int, val sets:List<List<CubeType>>) {
    fun isValid() = sets.all(List<CubeType>::isValid)
}

fun partOne(sampleData:List<String>) :Int {
    return sampleData.parse().validGameNumbers().sum()
}

fun List<String>.parse() = map(String::parse)

fun String.parse() = Game(gameNo = toGameNo(), sets = toSets())

fun String.toGameNo() = split(": ").first().removePrefix("Game ").toInt()

fun String.toSets() = split(": ").last().split("; ").map(String::toCubes)

fun String.toCubes():List<CubeType> = split(", ").map(String::toCube)

fun List<Game>.validGameNumbers() = filter(Game::isValid).map{it.gameNo}

fun List<CubeType>.isValid() = all(CubeType::isValid)

fun partTwo(sampleData:List<String>):Int {
    return sampleData.parse().power().sum()
}

fun List<Game>.power() = map(Game::power)

fun Game.power() = minimumNoOfReds() * minimumNoOfGreens() * minimumNoOfBlues()

fun Game.minimumNoOfReds() = sets.maxOf(List<CubeType>::minimumNoOfReds)

fun Game.minimumNoOfGreens() = sets.maxOf(List<CubeType>::minimumNoOfGreens)

fun Game.minimumNoOfBlues() = sets.maxOf(List<CubeType>::minimumNoOfBlues)

fun List<CubeType>.minimumNoOfReds():Int = filter{it is RedCube}.maxByOrNull { it.quantity }?.quantity ?: 0

fun List<CubeType>.minimumNoOfGreens():Int = filter{it is GreenCube}.maxByOrNull { it.quantity }?.quantity ?: 0

fun List<CubeType>.minimumNoOfBlues():Int = filter{it is BlueCube}.maxByOrNull { it.quantity }?.quantity ?: 0
