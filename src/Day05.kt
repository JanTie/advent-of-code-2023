fun main() {
    data class Entry(
        val range: LongRange,
        val destNumber: Long,
    )

    fun buildMap(input: List<String>): List<List<Entry>> {
        val resultList = mutableListOf<List<Entry>>()

        var currentStep = "seed"
        var index = input.indexOfFirst { it.startsWith("$currentStep-to-") }

        while (index != -1) {
            val endIndex =
                input.subList(index + 1, input.size).indexOfFirst { it.isEmpty() }.takeIf { it != -1 }?.plus(index)
                    ?: input.lastIndex

            resultList.add(
                input.subList(index + 1, endIndex + 1).map {
                    val (destNumber, startNumber, startRange) = it.split(" ").map { it.toLong() }

                    Entry(
                        range = startNumber until startNumber + startRange,
                        destNumber = destNumber
                    )
                }
            )

            currentStep = input[index].split(" ").first().split("-").last()
            index = input.indexOfFirst { it.startsWith("$currentStep-to-") }
        }

        return resultList
    }


    fun findLocation(entries: List<List<Entry>>, seed: Long): Long {
        return entries.fold(seed) { currentId, map ->
            map.firstOrNull {
                currentId in it.range
            }?.let {
                currentId - it.range.first + it.destNumber
            } ?: currentId
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input.first().split(": ").last().split(" ").map { it.trim().toLong() }
        val map = buildMap(input)

        return seeds.minOf { findLocation(map, it) }
    }

    fun part2(input: List<String>): Long {
        val seeds = input.first()
            .split(": ")
            .last()
            .split(" ")
            .map { it.trim().toLong() }
            .windowed(2, step = 2)

        val map = buildMap(input)

        return seeds.minOf { (start, windowSize) ->
            (start until start + windowSize).minOf {
                findLocation(map, it)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()

    check(part2(testInput) == 46L)
    part2(input).println()
}
