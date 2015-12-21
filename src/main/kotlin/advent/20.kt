package advent

import java.util.stream.IntStream

fun main(args: Array<String>) {
    sequence(500000) { it + 1 }
            .map { house ->
                val sum = computePresents2(house)

                if (house % 10000 == 0) {
                    println("House $house gets $sum presents.")
                }

                house to sum
            }
            .first { it.second >= input }.print()
}

private fun computePresents(house: Int) = IntStream.range(1, house + 1)
        .parallel()
        .map { elf ->
            if (house % elf == 0) {
                elf * 10
            } else {
                0
            }
        }
        .sum()

private fun computePresents2(house: Int) = IntStream.range(1, house + 1)
        .parallel()
        .map { elf ->
            if (house % elf == 0 && house / elf <= 50) {
                elf * 11
            } else {
                0
            }
        }
        .sum()


private val input = 29000000