package advent

import java.security.MessageDigest

fun main(args: Array<String>) {
    val md = MessageDigest.getInstance("MD5")
    var i = 0

    while (true) {
        md.reset()
        val hashBytes = md.digest((input + i).toByteArray())

        if (hashBytes[0] eq 0 && hashBytes[1] eq 0
                && (hashBytes[2] eq 0)) {
            //                && (hashBytes[2].toInt().shr(4) == 0)) {
            println(i)
            return
        }

        i++
    }
}

private infix fun Byte.eq(other: Int) = this.toInt() == other

private val input = "bgvyzdsv"