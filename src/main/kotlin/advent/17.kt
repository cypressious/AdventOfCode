package advent

fun main(args: Array<String>) {
    //    foo(25, listOf(20, 15, 10, 5, 5), 0).print()
    val fit = fit(150, input, 0)
    fit.filter{it.size == fit.map { it.size }.min()}.size.print()
}

private fun fit(needed: Int, available: List<Int>, searchFrom: Int): List<List<Int>> {
    if (available.isEmpty()) {
        return emptyList()
    }

    val perfectMatch = available
            .drop(searchFrom)
            .filter { it == needed }
            .map { listOf(it) }

    val possibleMatch = arrayListOf<List<Int>>()

    var i = searchFrom
    while (i < available.size) {
        if (available[i] < needed) {
            possibleMatch.addAll(fit(needed - available[i], available, i + 1).map { listOf(available[i]) + it })
        }
        i++
    }

    return perfectMatch + possibleMatch
}

private val input = """43
3
4
10
21
44
4
6
47
41
34
17
17
44
36
31
46
9
27
38""".splitLines().map(String::toInt).sortedDescending()