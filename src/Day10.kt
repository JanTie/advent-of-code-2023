fun main() {
    val currentDay = "Day10"
    createFileForDay(currentDay)

    fun Char.hasNorthOpening(): Boolean = this in setOf('|', 'L', 'J', 'S')
    fun Char.hasEastOpening(): Boolean = this in setOf('-', 'L', 'F', 'S')
    fun Char.hasSouthOpening(): Boolean = this in setOf('|', '7', 'F', 'S')
    fun Char.hasWestOpening(): Boolean = this in setOf('-', 'J', '7', 'S')

    fun findWay(input: List<String>): List<Pair<Int, Int>> {
        val (startY, startX) = input
            .indexOfFirst { it.contains('S') }
            .let { it to input[it].indexOf('S') }

        var currentX = startX
        var currentY = startY
        val way = mutableListOf(currentY to currentX)

        while (true) {
            val currentTile = input[currentY][currentX]
            when {
                currentTile.hasNorthOpening()
                        && currentY > 0
                        && input[currentY - 1][currentX].hasSouthOpening()
                        && !way.contains(currentY - 1 to currentX) -> {
                    currentY -= 1
                }

                currentTile.hasEastOpening()
                        && currentX < input[currentY].lastIndex
                        && input[currentY][currentX + 1].hasWestOpening()
                        && !way.contains(currentY to currentX + 1) -> {
                    currentX += 1
                }

                currentTile.hasSouthOpening()
                        && currentY < input.lastIndex
                        && input[currentY + 1][currentX].hasNorthOpening()
                        && !way.contains(currentY + 1 to currentX) -> {
                    currentY += 1
                }

                currentTile.hasWestOpening()
                        && currentX > 0
                        && input[currentY][currentX - 1].hasEastOpening()
                        && !way.contains(currentY to currentX - 1) -> {
                    currentX -= 1
                }

                else -> {
                    println("No movement possibilities found at y: $currentY x: $currentX index: ${way.size}")
                    break
                }
            }
            way.add(currentY to currentX)
        }

        return way
    }

    fun isEnclosed(input: List<String>, way: List<Pair<Int, Int>>, x: Int, y: Int): Boolean {
        println("-----------")
        val wayPointsOnX = way.filter { y == it.first }
            .sortedBy { (_, x) -> x }
            .also { it.map { (y, x) -> input[y][x] }.println() }
            .filter { (y, x) -> input[y][x].hasNorthOpening() || input[y][x].hasSouthOpening() }
            //.filter { (y, x) -> input[y][x].hasEastOpening() || (input[y][x].hasNorthOpening() && input[y][x].hasSouthOpening()) }
            .also { it.map { (y, x) -> input[y][x] }.println() }
        val wayPointsOnY = way.filter { x == it.second }
            .sortedBy { (y, _) -> y }
            .also { it.map { (y, x) -> input[y][x] }.println() }
            .filter { (y, x) -> input[y][x].hasEastOpening() || input[y][x].hasWestOpening() }
            //.filter { (y, x) -> input[y][x].hasSouthOpening() || (input[y][x].hasEastOpening() && input[y][x].hasWestOpening()) }
            .also { it.map { (y, x) -> input[y][x] }.println() }

        println("----")

        println("x: $x y: $y")

        val previousY = wayPointsOnY.filter { it.first < y }
        val nextY = wayPointsOnY.filter { it.first > y }
        val previousX = wayPointsOnX.filter { it.second < x }
        val nextX = wayPointsOnX.filter { it.second > x }

        previousY.println()
        nextY.println()
        previousX.println()
        nextX.println()

        return (previousY.size % 2 == 1 && previousX.size % 2 == 1 && nextY.size % 2 == 1 && nextX.size % 2 == 1).also { it.println() }
    }

    fun part1(input: List<String>): Int {
        return findWay(input).size / 2
    }

    fun part2(input: List<String>): Int {
        val way = findWay(input)

        way.println()
        return input.mapIndexed { yIndex, yLine ->
            yLine.mapIndexed { xIndex, _ ->
                val isContainedInWay = way.any { (y, x) -> y == yIndex && x == xIndex }
                if (!isContainedInWay && isEnclosed(input, way, xIndex, yIndex)) 1 else 0
            }
        }.flatten().sum().also { it.println() }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${currentDay}_test")
    val testInput2 = readInput("${currentDay}_test2")
    check(part1(testInput) == 4)
    check(part1(testInput2) == 8)

    val input = readInput(currentDay)
    part1(input).println()

    val testInput3 = readInput("${currentDay}_test3")
    val testInput4 = readInput("${currentDay}_test4")
    val testInput5 = readInput("${currentDay}_test5")
    check(part2(testInput3) == 4)
    check(part2(testInput4) == 8)
    check(part2(testInput5) == 10)
    part2(input).println()
}
