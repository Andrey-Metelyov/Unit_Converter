package converter

enum class UnitType {
    DISTANCE,
    LENGTH,
    UNKNOWN
}

val lengthUnits = arrayOf(
    Triple(arrayOf("m", "meter", "meters"), UnitType.DISTANCE, 1.0),
    Triple(arrayOf("km", "kilometer", "kilometers"), UnitType.DISTANCE, 1000.0),
    Triple(arrayOf("cm", "centimeter", "centimeters"), UnitType.DISTANCE, 0.01),
    Triple(arrayOf("mm", "millimeter", "millimeters"), UnitType.DISTANCE, 0.001),
    Triple(arrayOf("mi", "mile", "miles"), UnitType.DISTANCE, 1609.35),
    Triple(arrayOf("yd", "yard", "yards"), UnitType.DISTANCE, 0.9144),
    Triple(arrayOf("ft", "foot", "feet"), UnitType.DISTANCE, 0.3048),
    Triple(arrayOf("in", "inch", "inches"), UnitType.DISTANCE, 0.0254),
)

val UNKNOWN = Triple(arrayOf("", "", ""), UnitType.UNKNOWN, 0.0)

val weightUnits = arrayOf(
    Triple(arrayOf("g", "gram", "grams"), UnitType.LENGTH, 1.0),
    Triple(arrayOf("kg", "kilogram", "kilograms"), UnitType.LENGTH, 1000.0),
    Triple(arrayOf("mg", "milligram", "milligrams"), UnitType.LENGTH, 0.001),
    Triple(arrayOf("lb", "pound", "pounds"), UnitType.LENGTH, 453.592),
    Triple(arrayOf("oz", "ounce", "ounces"), UnitType.LENGTH, 28.3495),
)

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val response = readLine()!!
        if (response.lowercase() == "exit") {
            break
        }
        val (num, unitFrom, _, unitTo) = response.split(" ")
        try {
            val value = num.toDouble()
            val from = getUnitCoef(unitFrom.lowercase())
            val to = getUnitCoef(unitTo.lowercase())
            if (from.second == UnitType.UNKNOWN || to.second == UnitType.UNKNOWN) {
                println("Conversion from ${if (from.second == UnitType.UNKNOWN) "???" else from.first[2]} to ${if (to.second == UnitType.UNKNOWN) "???" else to.first[2]} is impossible")
                continue
            }
            if (from.second != to.second) {
                println("Conversion from ${from.first[2]} to ${to.first[2]} is impossible")
                continue
            }

            val hub = from.third * value
            System.err.println(hub)
            val res = hub / to.third

            val unitName = if (value != 1.0) from.first[2] else from.first[1]
            val resUnitName = if (res != 1.0) to.first[2] else to.first[1]
            println("$value $unitName is $res $resUnitName")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

fun getUnitCoef(unit: String): Triple<Array<String>, UnitType, Double> {
    for (u in lengthUnits) {
        if (unit in u.first) {
            return u
        }
    }
    for (u in weightUnits) {
        if (unit in u.first) {
            return u
        }
    }
    return UNKNOWN
}
