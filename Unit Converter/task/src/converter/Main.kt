package converter

val units = arrayOf(
    Pair(arrayOf("m", "meter", "meters"), 1.0),
    Pair(arrayOf("km", "kilometer", "kilometers"), 1000.0),
    Pair(arrayOf("cm", "centimeter", "centimeters"), 0.01),
    Pair(arrayOf("mm", "millimeter", "millimeters"), 0.001),
    Pair(arrayOf("mi", "mile", "miles"), 1609.35),
    Pair(arrayOf("yd", "yard", "yards"), 0.9144),
    Pair(arrayOf("ft", "foot", "feet"), 0.3048),
    Pair(arrayOf("in", "inch", "inches"), 0.0254)
)

fun main() {
    println("Enter a number and a measure of length: ")
    val (num, unit) = readLine()!!.lowercase().split(" ")
    try {
        val value = num.toDouble()
        val (units, coef) = getUnitCoef(unit)
        val unitName = if (value != 1.0) units[2] else units[1]
        val resultValue = value * coef
        var resultUnitName = "meter"
        if (resultValue != 1.0) {
            resultUnitName += "s"
        }
        println("$value $unitName is $resultValue $resultUnitName")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}

fun getUnitCoef(unit: String): Pair<Array<String>, Double> {
    for (u in units) {
        if (unit in u.first) {
            return u
        }
    }
    throw Exception("Unknown unit $unit")
}
