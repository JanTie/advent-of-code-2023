fun parse(input: List<String>, isValidCharacter: (Char) -> Boolean): List<List<Int>> {
    val numbers = input.map { string -> Regex("[0-9]+").findAll(string) }

    return input.mapIndexed { y, string ->
        string.flatMapIndexed { x, char ->
            if (isValidCharacter(char)) {
                val sameLine = numbers[y].filter { it.range.first == x + 1 || it.range.last == x - 1 }

                val previousLine = if (y > 0) {
                    numbers[y - 1].filter { it.range.contains(x) || it.range.contains(x + 1) || it.range.contains(x - 1) }
                } else {
                    emptySequence()
                }

                val nextLine = if (y < input.lastIndex) {
                    numbers[y + 1].filter { it.range.contains(x) || it.range.contains(x + 1) || it.range.contains(x - 1) }
                } else {
                    emptySequence()
                }

                (previousLine + sameLine + nextLine).map { it.value.toInt() }.toList()
            } else {
                emptyList()
            }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return parse(input) { char -> !char.isDigit() && char != '.' }
            .flatten()
            .sum()
    }

    fun part2(input: List<String>): Int {
        return parse(input) { char -> char == '*' }
            .filter { it.size == 2 }
            .sumOf { it.fold(1) { product, element -> product * element }.toInt() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
