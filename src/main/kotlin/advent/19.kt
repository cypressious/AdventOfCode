package advent

fun main(args: Array<String>) {
    //    calibrate(input, rules).print()

    val max = getSteps(input, rules, Int.MAX_VALUE, true).print()
    //    getSteps(input, rules, max, false).print()

    checks.print()
}

private val cache = hashMapOf<String, Int>()
private var checks = 0

private fun getSteps(output: String, rules: List<Pair<String, String>>, availableSteps: Int, greedy: Boolean): Int {
    checks++

    if (output == "e") {
        return 0
    }

    var minSteps = availableSteps

    if (minSteps > 0) {
        rules.forEach { rule ->
            output.occurrencesOf(rule.second, matchOverlapping = true).forEach { i ->
                val newOutput = output.substring(0, i) + rule.first + output.substring(i + rule.second.length)

                val steps = cache.getOrPut(newOutput) { getSteps(newOutput, rules, minSteps - 1, greedy) + 1 }

                if (steps < minSteps) {
                    minSteps = steps
                    println("Found new local minimum $steps for output $output")

                    if (greedy) {
                        return minSteps
                    }
                }
            }
        }

    } else {
        println("Aborting recursion at $output")
    }

    return minSteps

}

private fun calibrate(input: String, rules: List<Pair<String, String>>): Int {
    val rulesByInput = rules.groupBy { it.first }

    return input.indices.flatMap { i ->
        rulesByInput.getOrElse(input.substring(i, i + 1)) { emptyList() }
                .map { input.substring(0, i) + it.second + input.substring(i + 1) } +
                if (i < input.length - 1) {
                    rulesByInput.getOrElse(input.substring(i, i + 2)) { emptyList() }
                            .map { input.substring(0, i) + it.second + input.substring(i + 2) }
                } else {
                    emptyList()
                }
    }.distinct().size
}

private val input = """CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF"""

private val testRules = """e => H
e => O
H => HO
H => OH
O => HH""".splitLines().map { it.split(" => ").toPair() }

private val rules = """Al => ThF
Al => ThRnFAr
B => BCa
B => TiB
B => TiRnFAr
Ca => CaCa
Ca => PB
Ca => PRnFAr
Ca => SiRnFYFAr
Ca => SiRnMgAr
Ca => SiTh
F => CaF
F => PMg
F => SiAl
H => CRnAlAr
H => CRnFYFYFAr
H => CRnFYMgAr
H => CRnMgYFAr
H => HCa
H => NRnFYFAr
H => NRnMgAr
H => NTh
H => OB
H => ORnFAr
Mg => BF
Mg => TiMg
N => CRnFAr
N => HSi
O => CRnFYFAr
O => CRnMgAr
O => HP
O => NRnFAr
O => OTi
P => CaP
P => PTi
P => SiRnFAr
Si => CaSi
Th => ThCa
Ti => BP
Ti => TiTi
e => HF
e => NAl
e => OMg""".splitLines().map { it.split(" => ").toPair() }.sortedByDescending { it.second.length }