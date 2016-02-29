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