fun main() {
    fun String.parse(): List<Int> = this.split(" ")
        .filter { it.isNotEmpty() }
        .mapNotNull { it.toIntOrNull() }

    fun solve(distance: Long, timeToBeat: Long): Int {
        return (0 until timeToBeat)
            .map { it * (timeToBeat - it) }
            .filter { it > distance  }
            .size
    }

    fun part1(input: List<String>): Int {
        val times = input.first().parse()
        val distances = input.last().parse()

        return distances.mapIndexed { index, distance ->
            val timeToBeat = times[index]
            solve(distance.toLong(), timeToBeat.toLong())
        }
            .fold(1) { product, element -> product * element }
    }

    fun part2(input: List<String>): Int {
        val timeToBeat = input.first().parse().joinToString("").toLong()
        val distance = input.last().parse().joinToString("").toLong()

        return solve(distance, timeToBeat)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    val input = readInput("Day06")
    part1(input).println()

    check(part2(testInput) == 71503)
    part2(input).println()
}
