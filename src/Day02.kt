fun parse(input: List<String>): List<Pair<Int, List<Map<String, Int>>>> =
    input
        .map { line ->
            val (game, setList) = line.split(": ")
            val gameId = game.split(" ").last().toInt()
            val sets = setList.split("; ").map {
                it.split(", ")
                    .map {
                        val (amount, color) = it.split(" ")
                        color to amount.toInt()
                    }
                    .toMap()
            }

            gameId to sets
        }

fun main() {
    fun part1(input: List<String>): Int {
        return parse(input)
            .filter { (_, sets) ->
                sets.none {
                    it.getOrDefault("red", 0) > 12
                            || it.getOrDefault("green", 0) > 13
                            || it.getOrDefault("blue", 0) > 14
                }
            }
            .sumOf { (gameId, _) -> gameId }
    }

    fun part2(input: List<String>): Int {
        return parse(input)
            .map { (_, sets) ->
                sets.flatMap { it.keys }
                    .distinct()
                    .map { color -> sets.maxOf { set -> set[color] ?: 0 } }
                    .fold(1) { product, element -> product * element }
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
