package advent

fun main(args: Array<String>) {
    val deers = input.map {
        DeerStat(it.splitBy(" can fly ", " km/s for ", " seconds, but then must rest for ", " seconds.").dropLast(1))
    }

    val progress = deers.map { Deer(it.name, it.flyTime) to it }.toMap()

    repeat(2503) {
        progress.forEach {
            val (deer, stat) = it
            if (deer.canFly > 0) {
                deer.canFly--
                deer.progress += stat.speed

                if (deer.canFly == 0) {
                    deer.needsToRest = stat.restTime
                }
            } else {
                deer.needsToRest--

                if (deer.needsToRest == 0) {
                    deer.canFly = stat.flyTime
                }
            }
        }

        progress.keys.findMaxBy { it.progress }.points++
    }

    progress.keys.sortedByDescending { it.points }.joinToString(separator = "\n").print()
}


private data class DeerStat(
        val name: String,
        val speed: Int,
        val flyTime: Int,
        val restTime: Int
) {
    constructor(list: List<String>) : this(list[0], list[1].toInt(), list[2].toInt(), list[3].toInt())
}

private data class Deer(
        val name: String,
        var canFly: Int,
        var progress: Int = 0,
        var needsToRest: Int = 0,
        var points: Int = 0
)

fun <T, R : Comparable<R>> Iterable<T>.findMaxBy(fn: (T) -> R) = sortedByDescending { fn(it) }.first()

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

private val input = """Rudolph can fly 22 km/s for 8 seconds, but then must rest for 165 seconds.
Cupid can fly 8 km/s for 17 seconds, but then must rest for 114 seconds.
Prancer can fly 18 km/s for 6 seconds, but then must rest for 103 seconds.
Donner can fly 25 km/s for 6 seconds, but then must rest for 145 seconds.
Dasher can fly 11 km/s for 12 seconds, but then must rest for 125 seconds.
Comet can fly 21 km/s for 6 seconds, but then must rest for 121 seconds.
Blitzen can fly 18 km/s for 3 seconds, but then must rest for 50 seconds.
Vixen can fly 20 km/s for 4 seconds, but then must rest for 75 seconds.
Dancer can fly 7 km/s for 20 seconds, but then must rest for 119 seconds.""".splitLines()