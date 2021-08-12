package converter

enum class UnitType {
    DISTANCE,
    WEIGHT,
    TEMPERATURE,
    UNKNOWN,
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
    Triple(arrayOf("g", "gram", "grams"), UnitType.WEIGHT, 1.0),
    Triple(arrayOf("kg", "kilogram", "kilograms"), UnitType.WEIGHT, 1000.0),
    Triple(arrayOf("mg", "milligram", "milligrams"), UnitType.WEIGHT, 0.001),
    Triple(arrayOf("lb", "pound", "pounds"), UnitType.WEIGHT, 453.592),
    Triple(arrayOf("oz", "ounce", "ounces"), UnitType.WEIGHT, 28.3495),
)

val tempUnits = arrayOf(
    Triple(arrayOf("c", "degree Celsius", "degrees Celsius", "celsius", "dc"), UnitType.TEMPERATURE, 1.0),
    Triple(arrayOf("f", "degree Fahrenheit", "degrees Fahrenheit", "fahrenheit", "df"), UnitType.TEMPERATURE, 1.0),
    Triple(arrayOf("k", "kelvin", "kelvins"), UnitType.TEMPERATURE, 1.0),
)

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val response = readLine()!!.lowercase()
        if (response.lowercase() == "exit") {
            break
        }
        val words = response.split(" ")
        System.err.println(words)
        var counter = 0
        val num = words[counter++]
        val unitFrom = if (words[counter] in listOf("degree", "degrees")) words[counter++] + " " +  words[counter++] else words[counter++]
        // skip random word
        counter++
        val unitTo = if (words[counter] in listOf("degree", "degrees")) words[counter++] + " " + words[counter++] else words[counter++]
        System.err.println("$num from $unitFrom to $unitTo")
        try {
            val value = num.toDouble()
            val from = getUnitCoef(unitFrom.lowercase())
            val to = getUnitCoef(unitTo.lowercase())
            System.err.println("converting $from -> $to")
            if (from.second == UnitType.UNKNOWN || to.second == UnitType.UNKNOWN) {
                println("Conversion from ${if (from.second == UnitType.UNKNOWN) "???" else from.first[2]} to ${if (to.second == UnitType.UNKNOWN) "???" else to.first[2]} is impossible")
                continue
            }
            if (from.second != to.second) {
                println("Conversion from ${from.first[2]} to ${to.first[2]} is impossible")
                continue
            }

            var res: Double
            if (from.second != UnitType.TEMPERATURE) {
                if (value < 0.0) {
                    println((if (from.second == UnitType.DISTANCE) "Length" else "Weight") + " shouldn't be negative")
                    continue
                }
                val hub = from.third * value
                System.err.println(hub)
                res = hub / to.third
            } else {
                res = when (from.first[0]) {
                    "c" -> when (to.first[0]) {
                        "f" -> value * 9 / 5 + 32
                        "k" -> value + 273.15
                        else -> value
                    }
                    "f" -> when (to.first[0]) {
                        "c" -> (value - 32) * 5 / 9
                        "k" -> (value + 459.67) * 5 / 9
                        else -> value
                    }
                    "k" -> when (to.first[0]) {
                        "c" -> value - 273.15
                        "f" -> value * 9 / 5 - 459.67
                        else -> value
                    }
                    else -> value
                }
            }

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
        if (unit in u.first.map { it -> it.lowercase() }) {
            return u
        }
    }
    for (u in weightUnits) {
        if (unit in u.first.map { it -> it.lowercase() }) {
            return u
        }
    }
    for (u in tempUnits) {
        if (unit in u.first.map { it -> it.lowercase() }) {
            return u
        }
    }
    return UNKNOWN
}
