package advent

fun main(args: Array<String>) {
    val rules = rules.map { it.split(" => ").toPair() }.apply { print() }
    getSteps(input, rules)!!.print()
}

private val cache = hashMapOf<String, Int?>()

private fun getSteps(output: String, rules: List<Pair<String, String>>): Int? {
    //    output.print()

    if (output == "e") {
        return 0
    }

    return output.indices.flatMap { i ->
        rules.filter { output.substring(i).startsWith(it.second) }
                .map {
                    val newOutput = output.substring(0, i) + it.first + output.substring(i + it.second.length)

                    if (cache.containsKey(newOutput)) {
                        println("Found in cache")
                        cache[newOutput]
                    } else {
                        getSteps(newOutput, rules).apply {
                            println(this)
                            cache.put(newOutput, this@apply)
                        }
                    }
                }
    }.filterNotNull().min()?.plus(1)
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
O => HH""".splitLines()

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
e => OMg""".splitLines()