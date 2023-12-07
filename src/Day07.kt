fun main() {

    fun Char.toPokerNumber(jValue: Int = 11) = when {
        isDigit() -> this.digitToInt()
        this == 'T' -> 10
        this == 'J' -> jValue
        this == 'Q' -> 12
        this == 'K' -> 13
        this == 'A' -> 14
        else -> -1
    }

    data class Hand(
        val bid: Int,
        val handValues: List<Int>,
        val strength: List<Pair<Int, Int>>
    )

    fun sortHands(hands: List<Pair<Int, List<Int>>>, jokerValue: Int = 0) = hands.map { (bid, hand) ->
        Hand(
            bid = bid,
            handValues = hand,
            strength = hand.groupBy { it }.mapValues { it.value.size }.toList()
                .sortedWith(
                    compareBy(
                        { it.second },
                        { it.first },
                    )
                )
                .reversed()
                .toMap().toMutableMap()
                .let { map ->
                    val count = map.getOrDefault(jokerValue, 0)
                    // Edge-case check for 5 times J
                    if (count != 5) {
                        map.remove(jokerValue)
                        map[map.keys.first()] = map[map.keys.first()]!! + count
                    }

                    map
                }.toList()
        )
    }
        .sortedWith(
            compareBy(
                { it.strength[0].second },
                {
                    if (it.strength[0].second == 3 && it.strength[1].second == 2) 1
                    else 0
                },
                {
                    if (it.strength[0].second == 2 && it.strength[1].second == 2) 1
                    else 0
                },
                { it.handValues[0] },
                { it.handValues[1] },
                { it.handValues[2] },
                { it.handValues[3] },
                { it.handValues[4] },
            )
        )

    fun part1(input: List<String>): Int {
        val hands = input.map {
            val (hand, bid) = it.split(" ")
            bid.toInt() to hand.map { it.toPokerNumber() }
        }
        val sortedHands = sortHands(hands)
            .foldIndexed(0) { index, acc, (bid, _) ->
                acc + bid * (index + 1)
            }
        return sortedHands
    }

    fun part2(input: List<String>): Int {
        val hands = input.map {
            val (hand, bid) = it.split(" ")
            bid.toInt() to hand.map { it.toPokerNumber(jValue = 0) }
        }
        val sortedHands = sortHands(hands)
            .foldIndexed(0) { index, acc, (bid, _) ->
                acc + bid * (index + 1)
            }
        return sortedHands

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()

    check(part2(testInput) == 5905)
    part2(input).println()
}
