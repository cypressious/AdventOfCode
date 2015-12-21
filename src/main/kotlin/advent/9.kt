package advent

fun main(args: Array<String>) {
    val cities = hashSetOf<String>()
    val distances = hashMapOf<Pair<String, String>, Int>()

    input.forEach {
        val parts = it.split(" = ")
        val ends = parts[0].split(" to ")
        ends.forEach { cities.add(it) }
        distances.put(ends[0] to ends[1], parts[1].toInt())
        distances.put(ends[1] to ends[0], parts[1].toInt())
    }

    JavaUtils.generatePerm(cities.toArrayList()).map {
        it.windowed(2).map { distances[it[0] to it[1]]!! }.sum()
    }.max()!!.print()
}

fun Any.print() = println(this)

fun <T> List<T>.windowed(size: Int) = dropLast(size - 1).mapIndexed { i, c -> this@windowed.subList(i, i + size) }

private val input = """AlphaCentauri to Snowdin = 66
AlphaCentauri to Tambi = 28
AlphaCentauri to Faerun = 60
AlphaCentauri to Norrath = 34
AlphaCentauri to Straylight = 34
AlphaCentauri to Tristram = 3
AlphaCentauri to Arbre = 108
Snowdin to Tambi = 22
Snowdin to Faerun = 12
Snowdin to Norrath = 91
Snowdin to Straylight = 121
Snowdin to Tristram = 111
Snowdin to Arbre = 71
Tambi to Faerun = 39
Tambi to Norrath = 113
Tambi to Straylight = 130
Tambi to Tristram = 35
Tambi to Arbre = 40
Faerun to Norrath = 63
Faerun to Straylight = 21
Faerun to Tristram = 57
Faerun to Arbre = 83
Norrath to Straylight = 9
Norrath to Tristram = 50
Norrath to Arbre = 60
Straylight to Tristram = 27
Straylight to Arbre = 81
Tristram to Arbre = 90""".split("\n")