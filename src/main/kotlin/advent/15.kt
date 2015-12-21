package advent

fun main(args: Array<String>) {
    val ingredients = input.map {
        val split = it.splitBy(": capacity ", ", durability ", ", flavor ", ", texture ", ", calories ")
        split.drop(1).map(String::toInt)
    }

    var possible = arrayListOf<IntArray>()

    for (i1 in 0..100) {
        for (i2 in 0..100) {
            for (i3 in 0..100) {
                for (i4 in 0..100) {
                    if (i1 + i2 + i3 + i4 != 100) {
                        continue
                    }

                    possible.add(intArrayOf(i1, i2, i3, i4))
                }
            }
        }
    }

    possible
            .map { amounts ->
                ingredients.mapIndexed { i, properties ->
                    properties.map { it * amounts[i] }
                }
            }
            .map {
                it.reduce { ingr1, ingr2 -> ingr1.zip(ingr2) { i1, i2 -> i1 + i2 } }
            }
            .filter { it.last() == 500 }
            .map { it.dropLast(1) }
            .map {
                it.map { if (it < 0) 0 else it }
            }
            .map {
                it.reduce { i1, i2 -> i1 * i2 }
            }
            .max()!!
            .print()
}

private val testInput = """Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3""".splitLines()

private val input = """Frosting: capacity 4, durability -2, flavor 0, texture 0, calories 5
Candy: capacity 0, durability 5, flavor -1, texture 0, calories 8
Butterscotch: capacity -1, durability 0, flavor 5, texture 0, calories 6
Sugar: capacity 0, durability 0, flavor -2, texture 2, calories 1""".splitLines()