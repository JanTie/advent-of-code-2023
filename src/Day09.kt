
fun main() {
    val currentDay = "Day09"
    createFileForDay(currentDay)

    fun nextItem(list: List<Int>): Int {
        if(list.all { it == 0 }) {
            return 0
        }

        val newList = list.zipWithNext { a, b -> b - a }
        return (list.last() + nextItem(newList))
    }

    fun part1(input: List<String>): Int {
        val inputNumbers = input.map { it.split(" ").map { it.toInt() } }
        return inputNumbers.sumOf { nextItem(it) }
    }

    fun part2(input: List<String>): Int {
        val inputNumbers = input.map { it.split(" ").map { it.toInt() } }
        return inputNumbers.sumOf { nextItem(it.reversed()) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${currentDay}_test")
    check(part1(testInput) == 114)

    val input = readInput(currentDay)
    part1(input).println()

    check(part2(testInput) == 2)
    part2(input).println()
}
