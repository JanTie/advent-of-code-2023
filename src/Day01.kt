enum class Digits(val value: Int) {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9),
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map {
                val digits = it.filter { it.isDigit() }.map { it.digitToInt() }
                "${digits.first()}${digits.last()}".toInt()
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line ->
                val map = mutableMapOf<Int, Int>()
                Digits.values().forEach { digit ->
                    var count = 0
                    var startIndex = 0

                    while (startIndex < line.length) {
                        val index = line.indexOf(digit.toString(), startIndex)
                        if (index >= 0) {
                            map[index] = digit.value
                            count++
                            startIndex = index + digit.toString().length
                        } else {
                            break
                        }
                    }
                }

                (1..9).forEach {digit ->
                    var count = 0
                    var startIndex = 0

                    while (startIndex < line.length) {
                        val index = line.indexOf(digit.toString(), startIndex)
                        if (index >= 0) {
                            map[index] = digit
                            count++
                            startIndex = index + digit.toString().length
                        } else {
                            break
                        }
                    }
                }

                "${map[map.keys.min()]}${map[map.keys.max()]}".toInt()
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
