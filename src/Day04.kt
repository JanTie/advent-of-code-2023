import kotlin.math.pow

fun String.splitScratchCards(): List<List<Int>> = this.split(": ")
    .last()
    .split(" | ")
    .map {
        it.split(" ").mapNotNull { it.trim().takeIf { it.isNotEmpty() }?.toInt() }
    }

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (winningNumbers, myNumbers) = line.splitScratchCards()

            myNumbers.filter { winningNumbers.contains(it) }
                .takeIf { it.isNotEmpty() }
                ?.let { 2.0.pow(it.size - 1).toInt() }
                ?: 0
        }
    }

    fun part2(input: List<String>): Int {
        val map = List(input.size) { index -> index to 1 }.toMap().toMutableMap()

        input.forEachIndexed { index, line ->
            repeat(map[index]!!) {
                val (winningNumbers, myNumbers) = line.splitScratchCards()

                for (i in 1..myNumbers.filter { winningNumbers.contains(it) }.size) {
                    if(map.containsKey(index + i)) {
                        map[index + i] = map[index + i]!! + 1
                    }
                }

            }
        }

        return map.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
