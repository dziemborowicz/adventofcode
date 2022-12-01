fun String.parseBigDecimals() = parseStrings(String::toBigDecimal)
fun String.parseBigIntegers() = parseStrings(String::toBigInteger)
fun String.parseDoubles() = parseStrings(String::toDouble)
fun String.parseInts() = parseStrings(String::toInt)
fun String.parseLongs() = parseStrings(String::toLong)
fun String.parseStrings() = lines().filter { it.isNotEmpty() }

fun String.parseBigDecimalBigDecimals() = parseStrings(String::toBigDecimal, String::toBigDecimal)
fun String.parseBigDecimalBigIntegers() = parseStrings(String::toBigDecimal, String::toBigInteger)
fun String.parseBigDecimalDoubles() = parseStrings(String::toBigDecimal, String::toDouble)
fun String.parseBigDecimalInts() = parseStrings(String::toBigDecimal, String::toInt)
fun String.parseBigDecimalLongs() = parseStrings(String::toBigDecimal, String::toLong)
fun String.parseBigDecimalStrings() = parseStrings(String::toBigDecimal, String::toString)
fun String.parseBigIntegerBigDecimals() = parseStrings(String::toBigInteger, String::toBigDecimal)
fun String.parseBigIntegerBigIntegers() = parseStrings(String::toBigInteger, String::toBigInteger)
fun String.parseBigIntegerDoubles() = parseStrings(String::toBigInteger, String::toDouble)
fun String.parseBigIntegerInts() = parseStrings(String::toBigInteger, String::toInt)
fun String.parseBigIntegerLongs() = parseStrings(String::toBigInteger, String::toLong)
fun String.parseBigIntegerStrings() = parseStrings(String::toBigInteger, String::toString)
fun String.parseDoubleBigDecimals() = parseStrings(String::toDouble, String::toBigDecimal)
fun String.parseDoubleBigIntegers() = parseStrings(String::toDouble, String::toBigInteger)
fun String.parseDoubleDoubles() = parseStrings(String::toDouble, String::toDouble)
fun String.parseDoubleInts() = parseStrings(String::toDouble, String::toInt)
fun String.parseDoubleLongs() = parseStrings(String::toDouble, String::toLong)
fun String.parseDoubleStrings() = parseStrings(String::toDouble, String::toString)
fun String.parseIntBigDecimals() = parseStrings(String::toInt, String::toBigDecimal)
fun String.parseIntBigIntegers() = parseStrings(String::toInt, String::toBigInteger)
fun String.parseIntDoubles() = parseStrings(String::toInt, String::toDouble)
fun String.parseIntInts() = parseStrings(String::toInt, String::toInt)
fun String.parseIntLongs() = parseStrings(String::toInt, String::toLong)
fun String.parseIntStrings() = parseStrings(String::toInt, String::toString)
fun String.parseLongBigDecimals() = parseStrings(String::toLong, String::toBigDecimal)
fun String.parseLongBigIntegers() = parseStrings(String::toLong, String::toBigInteger)
fun String.parseLongDoubles() = parseStrings(String::toLong, String::toDouble)
fun String.parseLongInts() = parseStrings(String::toLong, String::toInt)
fun String.parseLongLongs() = parseStrings(String::toLong, String::toLong)
fun String.parseLongStrings() = parseStrings(String::toLong, String::toString)
fun String.parseStringBigDecimals() = parseStrings(String::toString, String::toBigDecimal)
fun String.parseStringBigIntegers() = parseStrings(String::toString, String::toBigInteger)
fun String.parseStringDoubles() = parseStrings(String::toString, String::toDouble)
fun String.parseStringInts() = parseStrings(String::toString, String::toInt)
fun String.parseStringLongs() = parseStrings(String::toString, String::toLong)
fun String.parseStringStrings() = parseStrings(String::toString, String::toString)

fun String.parseBigDecimalBigDecimalBigDecimals() =
  parseStrings(String::toBigDecimal, String::toBigDecimal, String::toBigDecimal)

fun String.parseBigDecimalBigDecimalBigIntegers() =
  parseStrings(String::toBigDecimal, String::toBigDecimal, String::toBigInteger)

fun String.parseBigDecimalBigDecimalDoubles() =
  parseStrings(String::toBigDecimal, String::toBigDecimal, String::toDouble)

fun String.parseBigDecimalBigDecimalInts() =
  parseStrings(String::toBigDecimal, String::toBigDecimal, String::toInt)

fun String.parseBigDecimalBigDecimalLongs() =
  parseStrings(String::toBigDecimal, String::toBigDecimal, String::toLong)

fun String.parseBigDecimalBigDecimalStrings() =
  parseStrings(String::toBigDecimal, String::toBigDecimal, String::toString)

fun String.parseBigDecimalBigIntegerBigDecimals() =
  parseStrings(String::toBigDecimal, String::toBigInteger, String::toBigDecimal)

fun String.parseBigDecimalBigIntegerBigIntegers() =
  parseStrings(String::toBigDecimal, String::toBigInteger, String::toBigInteger)

fun String.parseBigDecimalBigIntegerDoubles() =
  parseStrings(String::toBigDecimal, String::toBigInteger, String::toDouble)

fun String.parseBigDecimalBigIntegerInts() =
  parseStrings(String::toBigDecimal, String::toBigInteger, String::toInt)

fun String.parseBigDecimalBigIntegerLongs() =
  parseStrings(String::toBigDecimal, String::toBigInteger, String::toLong)

fun String.parseBigDecimalBigIntegerStrings() =
  parseStrings(String::toBigDecimal, String::toBigInteger, String::toString)

fun String.parseBigDecimalDoubleBigDecimals() =
  parseStrings(String::toBigDecimal, String::toDouble, String::toBigDecimal)

fun String.parseBigDecimalDoubleBigIntegers() =
  parseStrings(String::toBigDecimal, String::toDouble, String::toBigInteger)

fun String.parseBigDecimalDoubleDoubles() =
  parseStrings(String::toBigDecimal, String::toDouble, String::toDouble)

fun String.parseBigDecimalDoubleInts() =
  parseStrings(String::toBigDecimal, String::toDouble, String::toInt)

fun String.parseBigDecimalDoubleLongs() =
  parseStrings(String::toBigDecimal, String::toDouble, String::toLong)

fun String.parseBigDecimalDoubleStrings() =
  parseStrings(String::toBigDecimal, String::toDouble, String::toString)

fun String.parseBigDecimalIntBigDecimals() =
  parseStrings(String::toBigDecimal, String::toInt, String::toBigDecimal)

fun String.parseBigDecimalIntBigIntegers() =
  parseStrings(String::toBigDecimal, String::toInt, String::toBigInteger)

fun String.parseBigDecimalIntDoubles() =
  parseStrings(String::toBigDecimal, String::toInt, String::toDouble)

fun String.parseBigDecimalIntInts() =
  parseStrings(String::toBigDecimal, String::toInt, String::toInt)

fun String.parseBigDecimalIntLongs() =
  parseStrings(String::toBigDecimal, String::toInt, String::toLong)

fun String.parseBigDecimalIntStrings() =
  parseStrings(String::toBigDecimal, String::toInt, String::toString)

fun String.parseBigDecimalLongBigDecimals() =
  parseStrings(String::toBigDecimal, String::toLong, String::toBigDecimal)

fun String.parseBigDecimalLongBigIntegers() =
  parseStrings(String::toBigDecimal, String::toLong, String::toBigInteger)

fun String.parseBigDecimalLongDoubles() =
  parseStrings(String::toBigDecimal, String::toLong, String::toDouble)

fun String.parseBigDecimalLongInts() =
  parseStrings(String::toBigDecimal, String::toLong, String::toInt)

fun String.parseBigDecimalLongLongs() =
  parseStrings(String::toBigDecimal, String::toLong, String::toLong)

fun String.parseBigDecimalLongStrings() =
  parseStrings(String::toBigDecimal, String::toLong, String::toString)

fun String.parseBigDecimalStringBigDecimals() =
  parseStrings(String::toBigDecimal, String::toString, String::toBigDecimal)

fun String.parseBigDecimalStringBigIntegers() =
  parseStrings(String::toBigDecimal, String::toString, String::toBigInteger)

fun String.parseBigDecimalStringDoubles() =
  parseStrings(String::toBigDecimal, String::toString, String::toDouble)

fun String.parseBigDecimalStringInts() =
  parseStrings(String::toBigDecimal, String::toString, String::toInt)

fun String.parseBigDecimalStringLongs() =
  parseStrings(String::toBigDecimal, String::toString, String::toLong)

fun String.parseBigDecimalStringStrings() =
  parseStrings(String::toBigDecimal, String::toString, String::toString)

fun String.parseBigIntegerBigDecimalBigDecimals() =
  parseStrings(String::toBigInteger, String::toBigDecimal, String::toBigDecimal)

fun String.parseBigIntegerBigDecimalBigIntegers() =
  parseStrings(String::toBigInteger, String::toBigDecimal, String::toBigInteger)

fun String.parseBigIntegerBigDecimalDoubles() =
  parseStrings(String::toBigInteger, String::toBigDecimal, String::toDouble)

fun String.parseBigIntegerBigDecimalInts() =
  parseStrings(String::toBigInteger, String::toBigDecimal, String::toInt)

fun String.parseBigIntegerBigDecimalLongs() =
  parseStrings(String::toBigInteger, String::toBigDecimal, String::toLong)

fun String.parseBigIntegerBigDecimalStrings() =
  parseStrings(String::toBigInteger, String::toBigDecimal, String::toString)

fun String.parseBigIntegerBigIntegerBigDecimals() =
  parseStrings(String::toBigInteger, String::toBigInteger, String::toBigDecimal)

fun String.parseBigIntegerBigIntegerBigIntegers() =
  parseStrings(String::toBigInteger, String::toBigInteger, String::toBigInteger)

fun String.parseBigIntegerBigIntegerDoubles() =
  parseStrings(String::toBigInteger, String::toBigInteger, String::toDouble)

fun String.parseBigIntegerBigIntegerInts() =
  parseStrings(String::toBigInteger, String::toBigInteger, String::toInt)

fun String.parseBigIntegerBigIntegerLongs() =
  parseStrings(String::toBigInteger, String::toBigInteger, String::toLong)

fun String.parseBigIntegerBigIntegerStrings() =
  parseStrings(String::toBigInteger, String::toBigInteger, String::toString)

fun String.parseBigIntegerDoubleBigDecimals() =
  parseStrings(String::toBigInteger, String::toDouble, String::toBigDecimal)

fun String.parseBigIntegerDoubleBigIntegers() =
  parseStrings(String::toBigInteger, String::toDouble, String::toBigInteger)

fun String.parseBigIntegerDoubleDoubles() =
  parseStrings(String::toBigInteger, String::toDouble, String::toDouble)

fun String.parseBigIntegerDoubleInts() =
  parseStrings(String::toBigInteger, String::toDouble, String::toInt)

fun String.parseBigIntegerDoubleLongs() =
  parseStrings(String::toBigInteger, String::toDouble, String::toLong)

fun String.parseBigIntegerDoubleStrings() =
  parseStrings(String::toBigInteger, String::toDouble, String::toString)

fun String.parseBigIntegerIntBigDecimals() =
  parseStrings(String::toBigInteger, String::toInt, String::toBigDecimal)

fun String.parseBigIntegerIntBigIntegers() =
  parseStrings(String::toBigInteger, String::toInt, String::toBigInteger)

fun String.parseBigIntegerIntDoubles() =
  parseStrings(String::toBigInteger, String::toInt, String::toDouble)

fun String.parseBigIntegerIntInts() =
  parseStrings(String::toBigInteger, String::toInt, String::toInt)

fun String.parseBigIntegerIntLongs() =
  parseStrings(String::toBigInteger, String::toInt, String::toLong)

fun String.parseBigIntegerIntStrings() =
  parseStrings(String::toBigInteger, String::toInt, String::toString)

fun String.parseBigIntegerLongBigDecimals() =
  parseStrings(String::toBigInteger, String::toLong, String::toBigDecimal)

fun String.parseBigIntegerLongBigIntegers() =
  parseStrings(String::toBigInteger, String::toLong, String::toBigInteger)

fun String.parseBigIntegerLongDoubles() =
  parseStrings(String::toBigInteger, String::toLong, String::toDouble)

fun String.parseBigIntegerLongInts() =
  parseStrings(String::toBigInteger, String::toLong, String::toInt)

fun String.parseBigIntegerLongLongs() =
  parseStrings(String::toBigInteger, String::toLong, String::toLong)

fun String.parseBigIntegerLongStrings() =
  parseStrings(String::toBigInteger, String::toLong, String::toString)

fun String.parseBigIntegerStringBigDecimals() =
  parseStrings(String::toBigInteger, String::toString, String::toBigDecimal)

fun String.parseBigIntegerStringBigIntegers() =
  parseStrings(String::toBigInteger, String::toString, String::toBigInteger)

fun String.parseBigIntegerStringDoubles() =
  parseStrings(String::toBigInteger, String::toString, String::toDouble)

fun String.parseBigIntegerStringInts() =
  parseStrings(String::toBigInteger, String::toString, String::toInt)

fun String.parseBigIntegerStringLongs() =
  parseStrings(String::toBigInteger, String::toString, String::toLong)

fun String.parseBigIntegerStringStrings() =
  parseStrings(String::toBigInteger, String::toString, String::toString)

fun String.parseDoubleBigDecimalBigDecimals() =
  parseStrings(String::toDouble, String::toBigDecimal, String::toBigDecimal)

fun String.parseDoubleBigDecimalBigIntegers() =
  parseStrings(String::toDouble, String::toBigDecimal, String::toBigInteger)

fun String.parseDoubleBigDecimalDoubles() =
  parseStrings(String::toDouble, String::toBigDecimal, String::toDouble)

fun String.parseDoubleBigDecimalInts() =
  parseStrings(String::toDouble, String::toBigDecimal, String::toInt)

fun String.parseDoubleBigDecimalLongs() =
  parseStrings(String::toDouble, String::toBigDecimal, String::toLong)

fun String.parseDoubleBigDecimalStrings() =
  parseStrings(String::toDouble, String::toBigDecimal, String::toString)

fun String.parseDoubleBigIntegerBigDecimals() =
  parseStrings(String::toDouble, String::toBigInteger, String::toBigDecimal)

fun String.parseDoubleBigIntegerBigIntegers() =
  parseStrings(String::toDouble, String::toBigInteger, String::toBigInteger)

fun String.parseDoubleBigIntegerDoubles() =
  parseStrings(String::toDouble, String::toBigInteger, String::toDouble)

fun String.parseDoubleBigIntegerInts() =
  parseStrings(String::toDouble, String::toBigInteger, String::toInt)

fun String.parseDoubleBigIntegerLongs() =
  parseStrings(String::toDouble, String::toBigInteger, String::toLong)

fun String.parseDoubleBigIntegerStrings() =
  parseStrings(String::toDouble, String::toBigInteger, String::toString)

fun String.parseDoubleDoubleBigDecimals() =
  parseStrings(String::toDouble, String::toDouble, String::toBigDecimal)

fun String.parseDoubleDoubleBigIntegers() =
  parseStrings(String::toDouble, String::toDouble, String::toBigInteger)

fun String.parseDoubleDoubleDoubles() =
  parseStrings(String::toDouble, String::toDouble, String::toDouble)

fun String.parseDoubleDoubleInts() =
  parseStrings(String::toDouble, String::toDouble, String::toInt)

fun String.parseDoubleDoubleLongs() =
  parseStrings(String::toDouble, String::toDouble, String::toLong)

fun String.parseDoubleDoubleStrings() =
  parseStrings(String::toDouble, String::toDouble, String::toString)

fun String.parseDoubleIntBigDecimals() =
  parseStrings(String::toDouble, String::toInt, String::toBigDecimal)

fun String.parseDoubleIntBigIntegers() =
  parseStrings(String::toDouble, String::toInt, String::toBigInteger)

fun String.parseDoubleIntDoubles() =
  parseStrings(String::toDouble, String::toInt, String::toDouble)

fun String.parseDoubleIntInts() =
  parseStrings(String::toDouble, String::toInt, String::toInt)

fun String.parseDoubleIntLongs() =
  parseStrings(String::toDouble, String::toInt, String::toLong)

fun String.parseDoubleIntStrings() =
  parseStrings(String::toDouble, String::toInt, String::toString)

fun String.parseDoubleLongBigDecimals() =
  parseStrings(String::toDouble, String::toLong, String::toBigDecimal)

fun String.parseDoubleLongBigIntegers() =
  parseStrings(String::toDouble, String::toLong, String::toBigInteger)

fun String.parseDoubleLongDoubles() =
  parseStrings(String::toDouble, String::toLong, String::toDouble)

fun String.parseDoubleLongInts() =
  parseStrings(String::toDouble, String::toLong, String::toInt)

fun String.parseDoubleLongLongs() =
  parseStrings(String::toDouble, String::toLong, String::toLong)

fun String.parseDoubleLongStrings() =
  parseStrings(String::toDouble, String::toLong, String::toString)

fun String.parseDoubleStringBigDecimals() =
  parseStrings(String::toDouble, String::toString, String::toBigDecimal)

fun String.parseDoubleStringBigIntegers() =
  parseStrings(String::toDouble, String::toString, String::toBigInteger)

fun String.parseDoubleStringDoubles() =
  parseStrings(String::toDouble, String::toString, String::toDouble)

fun String.parseDoubleStringInts() =
  parseStrings(String::toDouble, String::toString, String::toInt)

fun String.parseDoubleStringLongs() =
  parseStrings(String::toDouble, String::toString, String::toLong)

fun String.parseDoubleStringStrings() =
  parseStrings(String::toDouble, String::toString, String::toString)

fun String.parseIntBigDecimalBigDecimals() =
  parseStrings(String::toInt, String::toBigDecimal, String::toBigDecimal)

fun String.parseIntBigDecimalBigIntegers() =
  parseStrings(String::toInt, String::toBigDecimal, String::toBigInteger)

fun String.parseIntBigDecimalDoubles() =
  parseStrings(String::toInt, String::toBigDecimal, String::toDouble)

fun String.parseIntBigDecimalInts() =
  parseStrings(String::toInt, String::toBigDecimal, String::toInt)

fun String.parseIntBigDecimalLongs() =
  parseStrings(String::toInt, String::toBigDecimal, String::toLong)

fun String.parseIntBigDecimalStrings() =
  parseStrings(String::toInt, String::toBigDecimal, String::toString)

fun String.parseIntBigIntegerBigDecimals() =
  parseStrings(String::toInt, String::toBigInteger, String::toBigDecimal)

fun String.parseIntBigIntegerBigIntegers() =
  parseStrings(String::toInt, String::toBigInteger, String::toBigInteger)

fun String.parseIntBigIntegerDoubles() =
  parseStrings(String::toInt, String::toBigInteger, String::toDouble)

fun String.parseIntBigIntegerInts() =
  parseStrings(String::toInt, String::toBigInteger, String::toInt)

fun String.parseIntBigIntegerLongs() =
  parseStrings(String::toInt, String::toBigInteger, String::toLong)

fun String.parseIntBigIntegerStrings() =
  parseStrings(String::toInt, String::toBigInteger, String::toString)

fun String.parseIntDoubleBigDecimals() =
  parseStrings(String::toInt, String::toDouble, String::toBigDecimal)

fun String.parseIntDoubleBigIntegers() =
  parseStrings(String::toInt, String::toDouble, String::toBigInteger)

fun String.parseIntDoubleDoubles() =
  parseStrings(String::toInt, String::toDouble, String::toDouble)

fun String.parseIntDoubleInts() =
  parseStrings(String::toInt, String::toDouble, String::toInt)

fun String.parseIntDoubleLongs() =
  parseStrings(String::toInt, String::toDouble, String::toLong)

fun String.parseIntDoubleStrings() =
  parseStrings(String::toInt, String::toDouble, String::toString)

fun String.parseIntIntBigDecimals() =
  parseStrings(String::toInt, String::toInt, String::toBigDecimal)

fun String.parseIntIntBigIntegers() =
  parseStrings(String::toInt, String::toInt, String::toBigInteger)

fun String.parseIntIntDoubles() =
  parseStrings(String::toInt, String::toInt, String::toDouble)

fun String.parseIntIntInts() =
  parseStrings(String::toInt, String::toInt, String::toInt)

fun String.parseIntIntLongs() =
  parseStrings(String::toInt, String::toInt, String::toLong)

fun String.parseIntIntStrings() =
  parseStrings(String::toInt, String::toInt, String::toString)

fun String.parseIntLongBigDecimals() =
  parseStrings(String::toInt, String::toLong, String::toBigDecimal)

fun String.parseIntLongBigIntegers() =
  parseStrings(String::toInt, String::toLong, String::toBigInteger)

fun String.parseIntLongDoubles() =
  parseStrings(String::toInt, String::toLong, String::toDouble)

fun String.parseIntLongInts() =
  parseStrings(String::toInt, String::toLong, String::toInt)

fun String.parseIntLongLongs() =
  parseStrings(String::toInt, String::toLong, String::toLong)

fun String.parseIntLongStrings() =
  parseStrings(String::toInt, String::toLong, String::toString)

fun String.parseIntStringBigDecimals() =
  parseStrings(String::toInt, String::toString, String::toBigDecimal)

fun String.parseIntStringBigIntegers() =
  parseStrings(String::toInt, String::toString, String::toBigInteger)

fun String.parseIntStringDoubles() =
  parseStrings(String::toInt, String::toString, String::toDouble)

fun String.parseIntStringInts() =
  parseStrings(String::toInt, String::toString, String::toInt)

fun String.parseIntStringLongs() =
  parseStrings(String::toInt, String::toString, String::toLong)

fun String.parseIntStringStrings() =
  parseStrings(String::toInt, String::toString, String::toString)

fun String.parseLongBigDecimalBigDecimals() =
  parseStrings(String::toLong, String::toBigDecimal, String::toBigDecimal)

fun String.parseLongBigDecimalBigIntegers() =
  parseStrings(String::toLong, String::toBigDecimal, String::toBigInteger)

fun String.parseLongBigDecimalDoubles() =
  parseStrings(String::toLong, String::toBigDecimal, String::toDouble)

fun String.parseLongBigDecimalInts() =
  parseStrings(String::toLong, String::toBigDecimal, String::toInt)

fun String.parseLongBigDecimalLongs() =
  parseStrings(String::toLong, String::toBigDecimal, String::toLong)

fun String.parseLongBigDecimalStrings() =
  parseStrings(String::toLong, String::toBigDecimal, String::toString)

fun String.parseLongBigIntegerBigDecimals() =
  parseStrings(String::toLong, String::toBigInteger, String::toBigDecimal)

fun String.parseLongBigIntegerBigIntegers() =
  parseStrings(String::toLong, String::toBigInteger, String::toBigInteger)

fun String.parseLongBigIntegerDoubles() =
  parseStrings(String::toLong, String::toBigInteger, String::toDouble)

fun String.parseLongBigIntegerInts() =
  parseStrings(String::toLong, String::toBigInteger, String::toInt)

fun String.parseLongBigIntegerLongs() =
  parseStrings(String::toLong, String::toBigInteger, String::toLong)

fun String.parseLongBigIntegerStrings() =
  parseStrings(String::toLong, String::toBigInteger, String::toString)

fun String.parseLongDoubleBigDecimals() =
  parseStrings(String::toLong, String::toDouble, String::toBigDecimal)

fun String.parseLongDoubleBigIntegers() =
  parseStrings(String::toLong, String::toDouble, String::toBigInteger)

fun String.parseLongDoubleDoubles() =
  parseStrings(String::toLong, String::toDouble, String::toDouble)

fun String.parseLongDoubleInts() =
  parseStrings(String::toLong, String::toDouble, String::toInt)

fun String.parseLongDoubleLongs() =
  parseStrings(String::toLong, String::toDouble, String::toLong)

fun String.parseLongDoubleStrings() =
  parseStrings(String::toLong, String::toDouble, String::toString)

fun String.parseLongIntBigDecimals() =
  parseStrings(String::toLong, String::toInt, String::toBigDecimal)

fun String.parseLongIntBigIntegers() =
  parseStrings(String::toLong, String::toInt, String::toBigInteger)

fun String.parseLongIntDoubles() =
  parseStrings(String::toLong, String::toInt, String::toDouble)

fun String.parseLongIntInts() =
  parseStrings(String::toLong, String::toInt, String::toInt)

fun String.parseLongIntLongs() =
  parseStrings(String::toLong, String::toInt, String::toLong)

fun String.parseLongIntStrings() =
  parseStrings(String::toLong, String::toInt, String::toString)

fun String.parseLongLongBigDecimals() =
  parseStrings(String::toLong, String::toLong, String::toBigDecimal)

fun String.parseLongLongBigIntegers() =
  parseStrings(String::toLong, String::toLong, String::toBigInteger)

fun String.parseLongLongDoubles() =
  parseStrings(String::toLong, String::toLong, String::toDouble)

fun String.parseLongLongInts() =
  parseStrings(String::toLong, String::toLong, String::toInt)

fun String.parseLongLongLongs() =
  parseStrings(String::toLong, String::toLong, String::toLong)

fun String.parseLongLongStrings() =
  parseStrings(String::toLong, String::toLong, String::toString)

fun String.parseLongStringBigDecimals() =
  parseStrings(String::toLong, String::toString, String::toBigDecimal)

fun String.parseLongStringBigIntegers() =
  parseStrings(String::toLong, String::toString, String::toBigInteger)

fun String.parseLongStringDoubles() =
  parseStrings(String::toLong, String::toString, String::toDouble)

fun String.parseLongStringInts() =
  parseStrings(String::toLong, String::toString, String::toInt)

fun String.parseLongStringLongs() =
  parseStrings(String::toLong, String::toString, String::toLong)

fun String.parseLongStringStrings() =
  parseStrings(String::toLong, String::toString, String::toString)

fun String.parseStringBigDecimalBigDecimals() =
  parseStrings(String::toString, String::toBigDecimal, String::toBigDecimal)

fun String.parseStringBigDecimalBigIntegers() =
  parseStrings(String::toString, String::toBigDecimal, String::toBigInteger)

fun String.parseStringBigDecimalDoubles() =
  parseStrings(String::toString, String::toBigDecimal, String::toDouble)

fun String.parseStringBigDecimalInts() =
  parseStrings(String::toString, String::toBigDecimal, String::toInt)

fun String.parseStringBigDecimalLongs() =
  parseStrings(String::toString, String::toBigDecimal, String::toLong)

fun String.parseStringBigDecimalStrings() =
  parseStrings(String::toString, String::toBigDecimal, String::toString)

fun String.parseStringBigIntegerBigDecimals() =
  parseStrings(String::toString, String::toBigInteger, String::toBigDecimal)

fun String.parseStringBigIntegerBigIntegers() =
  parseStrings(String::toString, String::toBigInteger, String::toBigInteger)

fun String.parseStringBigIntegerDoubles() =
  parseStrings(String::toString, String::toBigInteger, String::toDouble)

fun String.parseStringBigIntegerInts() =
  parseStrings(String::toString, String::toBigInteger, String::toInt)

fun String.parseStringBigIntegerLongs() =
  parseStrings(String::toString, String::toBigInteger, String::toLong)

fun String.parseStringBigIntegerStrings() =
  parseStrings(String::toString, String::toBigInteger, String::toString)

fun String.parseStringDoubleBigDecimals() =
  parseStrings(String::toString, String::toDouble, String::toBigDecimal)

fun String.parseStringDoubleBigIntegers() =
  parseStrings(String::toString, String::toDouble, String::toBigInteger)

fun String.parseStringDoubleDoubles() =
  parseStrings(String::toString, String::toDouble, String::toDouble)

fun String.parseStringDoubleInts() =
  parseStrings(String::toString, String::toDouble, String::toInt)

fun String.parseStringDoubleLongs() =
  parseStrings(String::toString, String::toDouble, String::toLong)

fun String.parseStringDoubleStrings() =
  parseStrings(String::toString, String::toDouble, String::toString)

fun String.parseStringIntBigDecimals() =
  parseStrings(String::toString, String::toInt, String::toBigDecimal)

fun String.parseStringIntBigIntegers() =
  parseStrings(String::toString, String::toInt, String::toBigInteger)

fun String.parseStringIntDoubles() =
  parseStrings(String::toString, String::toInt, String::toDouble)

fun String.parseStringIntInts() =
  parseStrings(String::toString, String::toInt, String::toInt)

fun String.parseStringIntLongs() =
  parseStrings(String::toString, String::toInt, String::toLong)

fun String.parseStringIntStrings() =
  parseStrings(String::toString, String::toInt, String::toString)

fun String.parseStringLongBigDecimals() =
  parseStrings(String::toString, String::toLong, String::toBigDecimal)

fun String.parseStringLongBigIntegers() =
  parseStrings(String::toString, String::toLong, String::toBigInteger)

fun String.parseStringLongDoubles() =
  parseStrings(String::toString, String::toLong, String::toDouble)

fun String.parseStringLongInts() =
  parseStrings(String::toString, String::toLong, String::toInt)

fun String.parseStringLongLongs() =
  parseStrings(String::toString, String::toLong, String::toLong)

fun String.parseStringLongStrings() =
  parseStrings(String::toString, String::toLong, String::toString)

fun String.parseStringStringBigDecimals() =
  parseStrings(String::toString, String::toString, String::toBigDecimal)

fun String.parseStringStringBigIntegers() =
  parseStrings(String::toString, String::toString, String::toBigInteger)

fun String.parseStringStringDoubles() =
  parseStrings(String::toString, String::toString, String::toDouble)

fun String.parseStringStringInts() =
  parseStrings(String::toString, String::toString, String::toInt)

fun String.parseStringStringLongs() =
  parseStrings(String::toString, String::toString, String::toLong)

fun String.parseStringStringStrings() =
  parseStrings(String::toString, String::toString, String::toString)

fun <A> String.parseStrings(a: (String) -> A) = parseStrings().map { a(it) }
fun <A, B> String.parseStrings(a: (String) -> A, b: (String) -> B) =
  parseStringLists().map {
    require(it.size == 2)
    Pair(a(it[0]), b(it[1]))
  }

fun <A, B, C> String.parseStrings(a: (String) -> A, b: (String) -> B, c: (String) -> C) =
  parseStringLists().map {
    require(it.size == 3)
    Triple(a(it[0]), b(it[1]), c(it[2]))
  }

fun String.parseBigDecimalLists() = parseStringLists { it.toBigDecimal() }
fun String.parseBigIntegerLists() = parseStringLists { it.toBigInteger() }
fun String.parseDoubleLists() = parseStringLists { it.toDouble() }
fun String.parseIntLists() = parseStringLists { it.toInt() }
fun String.parseLongLists() = parseStringLists { it.toLong() }
fun String.parseStringLists() = parseStrings().map { it.split(Regex("\\s+")) }
fun <T> String.parseStringLists(transform: (String) -> T) =
  parseStringLists().map { it.map(transform) }
