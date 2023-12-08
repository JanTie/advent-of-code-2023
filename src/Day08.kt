import kotlin.time.measureTime

fun main() {
    val currentDay = "Day08"
    createFileForDay(currentDay)

    fun buildMap(input: List<String>): Map<String, Pair<String, String>> {
        return (2..input.lastIndex).associate { index ->
            val (from, to) = input[index].split(" = ")
            val (left, right) = to.removeSurrounding("(", ")").split(", ")

            from to (left to right)
        }
    }

    fun part1(input: List<String>): Int {
        val nav = input.first()
        val navigationMap = buildMap(input)

        val destination = "ZZZ"
        var currentNode = "AAA"

        var index = 0

        while (currentNode != destination) {
            val currentMap = navigationMap[currentNode]!!
            currentNode = if (nav[index % nav.length] == 'L') {
                currentMap.first
            } else {
                currentMap.second
            }

            index++
        }

        return index
    }

    fun part2(input: List<String>): Long {
        val nav = input.first()
        val navigationMap = buildMap(input)

        val startNodes = navigationMap.keys.filter { it.endsWith("A") }

        val intervals = startNodes.map {
            var index = 0
            var firstIndex: Int? = null
            var currentNode = it
            while (!currentNode.endsWith("Z") || firstIndex == null) {
                if(currentNode.endsWith("Z") && firstIndex == null) {
                    firstIndex = index
                }
                val currentMap = navigationMap[currentNode]!!
                currentNode = if (nav[index % nav.length] == 'L') {
                    currentMap.first
                } else {
                    currentMap.second
                }

                index++
            }

            (index - firstIndex).toLong()
        }

        return lcm(intervals)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${currentDay}_test")
    val testInput2 = readInput("${currentDay}_test2")
    val testInputPart2 = readInput("${currentDay}_test_part2")
    check(part1(testInput) == 2)
    check(part1(testInput2) == 6)

    val input = readInput(currentDay)
    part1(input).println()

    check(part2(testInputPart2) == 6L)
    measureTime {
        part2(input).println()
    }.println()
}
