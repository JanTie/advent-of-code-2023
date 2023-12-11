import kotlin.math.max
import kotlin.math.min

fun main() {
    val currentDay = "Day11"
    createFileForDay(currentDay)

    data class Coord(
        val x: Int,
        val y: Int,
    ) {
        fun distanceTo(other: Coord, input: List<String>, multiplicator: Long = 2): Long =
            (min(this.x, other.x) until max(this.x, other.x))
                .sumOf { index ->
                    if (input.all { it[index] == '.' }) {
                        multiplicator
                    } else {
                        1
                    }
                } +
                    (min(this.y, other.y) until max(this.y, other.y))
                        .sumOf { index ->
                            if (input[index].all { it == '.' }) {
                                multiplicator
                            } else {
                                1
                            }
                        }
    }

    fun generateGalaxyPairs(input: List<String>): List<Pair<Coord, Coord>> {
        val galaxies = input.flatMapIndexed { yIndex, yLine ->
            yLine.mapIndexedNotNull { xIndex, c ->
                if (c == '#') {
                    Coord(xIndex, yIndex)
                } else {
                    null
                }
            }
        }

        return galaxies.flatMapIndexed { index, coord ->
            ((index + 1) until galaxies.size).map {
                coord to galaxies[it]
            }
        }
    }

    fun part1(input: List<String>): Int {
        return generateGalaxyPairs(input).sumOf { it.first.distanceTo(it.second, input) }.toInt()
    }

    fun part2(input: List<String>, multiplier: Long): Long {
        return generateGalaxyPairs(input).sumOf { it.first.distanceTo(it.second, input, multiplier) }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${currentDay}_test")
    check(part1(testInput) == 374)

    val input = readInput(currentDay)
    part1(input).println()

    check(part2(testInput, 10L) == 1030L)
    check(part2(testInput, 100L) == 8410L)
    part2(input, 1_000_000L).println()
}
