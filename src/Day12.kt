import kotlin.math.pow

fun main() {
    val currentDay = "Day12"
    createFileForDay(currentDay)

    fun IntRange.size() = (last - first + 1)

    fun part1(input: List<String>): Int {
        return input.map { line ->
            val (input, pattern) = line.split(" ")
            val parsedPattern = pattern.split(",").map { it.toInt() }

            val replacements = "[?]+".toRegex().findAll(input)
                .flatMap { it.range.toList() }
                .toList()
                .fold(listOf(input)) { acc, index ->
                    acc.flatMap {
                        listOf(
                            it.replaceRange(index, index + 1, "#"),
                            it.replaceRange(index, index + 1, "."),
                        )
                    }
                }

            replacements.filter {
                val results = Regex("[#]+").findAll(it).toList()

                results.size == parsedPattern.size
                        && (parsedPattern.indices).all { index ->
                    results[index].range.size() == parsedPattern[index]
                }
            }
        }.flatten().size
    }

    fun part2(input: List<String>): Long {
        // TODO
        return 0L
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${currentDay}_test")
    check(part1(testInput) == 21)

    val input = readInput(currentDay)
    part1(input).println()

    check(part2(testInput) == 525152L)
    part2(input).println()
}
