
fun main() {
    val currentDay = "DayXX"
    createFileForDay(currentDay)

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${currentDay}_test")
    check(part1(testInput) == 1)

    val input = readInput(currentDay)
    part1(input).println()

    check(part2(testInput) == 1)
    part2(input).println()
}
