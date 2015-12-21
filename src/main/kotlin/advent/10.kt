package advent

fun main(args: Array<String>) {
    var word = input

    repeat(50) {
        word = word.lookAndSay()
    }
    word.length.print()
}

private fun String.lookAndSay(): String {
    val iter = iterator()
    val nextWord = buildString {
        var c = iter.nextChar()
        var count = 1

        while (iter.hasNext()) {
            val next = iter.nextChar()

            if (next != c) {
                append(count)
                append(c)

                c = next
                count = 1
            } else {
                count++
            }
        }

        append(count)
        append(c)
    }
    return nextWord
}

private val input = "1113122113"