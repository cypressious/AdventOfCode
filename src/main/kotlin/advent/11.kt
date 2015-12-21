package advent

fun main(args: Array<String>) {
    var password = input

    do {
        password = password.increment()
    } while (!password.check())

    password.print()
}

private fun String.check() = has3Increasing() && none { it in forbidden } && has2Pairs()

private fun String.increment(): String = if (last() != 'z') {
    dropLast(1) + (last() + 1)
} else {
    dropLast(1).increment() + 'a'
}

private fun String.has3Increasing() = windowed(3).any {
    it[2] == it[1] + 1 && it[1] == it[0] + 1
}

private val forbidden = "iol"

private fun String.has2Pairs() = windowed(2).filter { it[0] == it[1] }.distinct().size >= 2

//private val input = "cqjxjnds"
private val input = "cqjxxyzz"