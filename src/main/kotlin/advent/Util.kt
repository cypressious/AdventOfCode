package advent

fun <T> T.print() = apply { println(this) }

fun String.splitLines() = split("\n")

fun String.windowed(size: Int) = dropLast(size - 1).mapIndexed { i, c -> this@windowed.substring(i, i + size) }

fun <T> List<T>.windowed(size: Int)
        = dropLast(size - 1).mapIndexed { i, c -> this@windowed.subList(i, i + size) }

fun <T> List<T>.toPair() = this[0] to this[1]

fun String.splitBy(vararg splits: String): List<String> {
    val result = arrayListOf<String>()
    var str = this

    splits.forEach {
        val tmp = str.split(it)
        result += tmp[0]
        str = tmp[1]
    }

    result += str

    return result
}


/**
 * For each i in [0, length), this function computes
 * the length of the longest suffix of a substring of pattern from 0 to i
 * that is also a prefix of the pattern itself.
 */
private fun computePrefixFunction(pattern: CharSequence): IntArray {
    val resultTable = IntArray(pattern.length)

    var matches = 0
    for (i in 1..pattern.length - 1) {
        while (matches > 0 && pattern[matches] != pattern[i]) {
            matches = resultTable[matches]
        }

        if (pattern[matches] == pattern[i]) {
            matches++
        }
        resultTable[i] = matches
    }

    return resultTable
}

/**
 * Taken from https://github.com/JetBrains/kotlin/pull/821/files
 */
 fun CharSequence.occurrencesOf(pattern: CharSequence, ignoreCase: Boolean = false, matchOverlapping: Boolean = false): Sequence<Int> {

    if (pattern.length > this.length) {
        return emptySequence()
    }

    if (pattern.isEmpty()) {
        return indices.asSequence()
    }

    if (pattern.length == 1) {
        return indices.asSequence().filter { this[it].equals(pattern[0], ignoreCase) }
    }

    // Non-trivial pattern matching, perform computation
    // using Knuth-Morris-Pratt

    val prefixFunction = computePrefixFunction(pattern)

    var i = 0
    var matches = 0
    return generateSequence {
        while (i < length) {
            while (matches > 0 && !pattern[matches].equals(this[i], ignoreCase)) {
                matches = prefixFunction[matches - 1]
            }

            if (pattern[matches].equals(this[i], ignoreCase)) {
                matches++
            }
            if (matches == pattern.length) {
                matches = if (matchOverlapping) prefixFunction[matches - 1] else 0
                i++
                return@generateSequence i - pattern.length
            }

            i++
        }

        return@generateSequence null
    }
}