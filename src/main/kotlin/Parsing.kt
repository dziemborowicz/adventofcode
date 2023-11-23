@file:Suppress("UNCHECKED_CAST")

import java.math.BigDecimal
import java.math.BigInteger

fun String.toChar(): Char {
  require(length == 1) { "Must contain exactly one character." }
  return first()
}

fun String.extractInts(): List<Int> {
  return Regex("""((?<!\d)-)?\d+""").findAll(this).map { it.value.toInt() }.toList()
}

fun String.extractLongs(): List<Long> {
  return Regex("""((?<!\d)-)?\d+""").findAll(this).map { it.value.toLong() }.toList()
}

fun <A> String.parse(a: (String) -> A) = lines().parse(a)

fun <A, B> String.parse(a: (String) -> A, b: (String) -> B) = lines().parse(a, b)

fun <A, B, C> String.parse(a: (String) -> A, b: (String) -> B, c: (String) -> C) =
  lines().parse(a, b, c)

fun <A> Iterable<String>.parse(a: (String) -> A) = map { a(it) }

fun <A, B> Iterable<String>.parse(a: (String) -> A, b: (String) -> B) =
  parseStringLists().map {
    require(it.size == 2)
    Pair(a(it[0]), b(it[1]))
  }

fun <A, B, C> Iterable<String>.parse(a: (String) -> A, b: (String) -> B, c: (String) -> C) =
  parseStringLists().map {
    require(it.size == 3)
    Triple(a(it[0]), b(it[1]), c(it[2]))
  }

fun <T> String.parseLists(transform: (String) -> T) = lines().parseLists(transform)

fun <T> Iterable<String>.parseLists(transform: (String) -> T) =
  map { it.trim().split(Regex("[\\s,-]+")) }.map { it.map(transform) }

fun String.parseBigDecimals() = parse(String::toBigDecimal)
fun String.parseBigIntegers() = parse(String::toBigInteger)
fun String.parseChars() = parse(String::toChar)
fun String.parseDoubles() = parse(String::toDouble)
fun String.parseInts() = parse(String::toInt)
fun String.parseLongs() = parse(String::toLong)
fun String.parseStrings() = parse(String::toString)

fun Iterable<String>.parseBigDecimals() = parse(String::toBigDecimal)
fun Iterable<String>.parseBigIntegers() = parse(String::toBigInteger)
fun Iterable<String>.parseChars() = parse(String::toChar)
fun Iterable<String>.parseDoubles() = parse(String::toDouble)
fun Iterable<String>.parseInts() = parse(String::toInt)
fun Iterable<String>.parseLongs() = parse(String::toLong)
fun Iterable<String>.parseStrings() = parse(String::toString)

fun String.parseBigDecimalBigDecimals() = parse(String::toBigDecimal, String::toBigDecimal)
fun String.parseBigDecimalBigIntegers() = parse(String::toBigDecimal, String::toBigInteger)
fun String.parseBigDecimalChars() = parse(String::toBigDecimal, String::toChar)
fun String.parseBigDecimalDoubles() = parse(String::toBigDecimal, String::toDouble)
fun String.parseBigDecimalInts() = parse(String::toBigDecimal, String::toInt)
fun String.parseBigDecimalLongs() = parse(String::toBigDecimal, String::toLong)
fun String.parseBigDecimalStrings() = parse(String::toBigDecimal, String::toString)
fun String.parseBigIntegerBigDecimals() = parse(String::toBigInteger, String::toBigDecimal)
fun String.parseBigIntegerBigIntegers() = parse(String::toBigInteger, String::toBigInteger)
fun String.parseBigIntegerChars() = parse(String::toBigInteger, String::toChar)
fun String.parseBigIntegerDoubles() = parse(String::toBigInteger, String::toDouble)
fun String.parseBigIntegerInts() = parse(String::toBigInteger, String::toInt)
fun String.parseBigIntegerLongs() = parse(String::toBigInteger, String::toLong)
fun String.parseBigIntegerStrings() = parse(String::toBigInteger, String::toString)
fun String.parseCharBigDecimals() = parse(String::toChar, String::toBigDecimal)
fun String.parseCharBigIntegers() = parse(String::toChar, String::toBigInteger)
fun String.parseCharChars() = parse(String::toChar, String::toChar)
fun String.parseCharDoubles() = parse(String::toChar, String::toDouble)
fun String.parseCharInts() = parse(String::toChar, String::toInt)
fun String.parseCharLongs() = parse(String::toChar, String::toLong)
fun String.parseCharStrings() = parse(String::toChar, String::toString)
fun String.parseDoubleBigDecimals() = parse(String::toDouble, String::toBigDecimal)
fun String.parseDoubleBigIntegers() = parse(String::toDouble, String::toBigInteger)
fun String.parseDoubleChars() = parse(String::toDouble, String::toChar)
fun String.parseDoubleDoubles() = parse(String::toDouble, String::toDouble)
fun String.parseDoubleInts() = parse(String::toDouble, String::toInt)
fun String.parseDoubleLongs() = parse(String::toDouble, String::toLong)
fun String.parseDoubleStrings() = parse(String::toDouble, String::toString)
fun String.parseIntBigDecimals() = parse(String::toInt, String::toBigDecimal)
fun String.parseIntBigIntegers() = parse(String::toInt, String::toBigInteger)
fun String.parseIntChars() = parse(String::toInt, String::toChar)
fun String.parseIntDoubles() = parse(String::toInt, String::toDouble)
fun String.parseIntInts() = parse(String::toInt, String::toInt)
fun String.parseIntLongs() = parse(String::toInt, String::toLong)
fun String.parseIntStrings() = parse(String::toInt, String::toString)
fun String.parseLongBigDecimals() = parse(String::toLong, String::toBigDecimal)
fun String.parseLongBigIntegers() = parse(String::toLong, String::toBigInteger)
fun String.parseLongChars() = parse(String::toLong, String::toChar)
fun String.parseLongDoubles() = parse(String::toLong, String::toDouble)
fun String.parseLongInts() = parse(String::toLong, String::toInt)
fun String.parseLongLongs() = parse(String::toLong, String::toLong)
fun String.parseLongStrings() = parse(String::toLong, String::toString)
fun String.parseStringBigDecimals() = parse(String::toString, String::toBigDecimal)
fun String.parseStringBigIntegers() = parse(String::toString, String::toBigInteger)
fun String.parseStringChars() = parse(String::toString, String::toChar)
fun String.parseStringDoubles() = parse(String::toString, String::toDouble)
fun String.parseStringInts() = parse(String::toString, String::toInt)
fun String.parseStringLongs() = parse(String::toString, String::toLong)
fun String.parseStringStrings() = parse(String::toString, String::toString)

fun Iterable<String>.parseBigDecimalBigDecimals() = parse(String::toBigDecimal, String::toBigDecimal)
fun Iterable<String>.parseBigDecimalBigIntegers() = parse(String::toBigDecimal, String::toBigInteger)
fun Iterable<String>.parseBigDecimalChars() = parse(String::toBigDecimal, String::toChar)
fun Iterable<String>.parseBigDecimalDoubles() = parse(String::toBigDecimal, String::toDouble)
fun Iterable<String>.parseBigDecimalInts() = parse(String::toBigDecimal, String::toInt)
fun Iterable<String>.parseBigDecimalLongs() = parse(String::toBigDecimal, String::toLong)
fun Iterable<String>.parseBigDecimalStrings() = parse(String::toBigDecimal, String::toString)
fun Iterable<String>.parseBigIntegerBigDecimals() = parse(String::toBigInteger, String::toBigDecimal)
fun Iterable<String>.parseBigIntegerBigIntegers() = parse(String::toBigInteger, String::toBigInteger)
fun Iterable<String>.parseBigIntegerChars() = parse(String::toBigInteger, String::toChar)
fun Iterable<String>.parseBigIntegerDoubles() = parse(String::toBigInteger, String::toDouble)
fun Iterable<String>.parseBigIntegerInts() = parse(String::toBigInteger, String::toInt)
fun Iterable<String>.parseBigIntegerLongs() = parse(String::toBigInteger, String::toLong)
fun Iterable<String>.parseBigIntegerStrings() = parse(String::toBigInteger, String::toString)
fun Iterable<String>.parseCharBigDecimals() = parse(String::toChar, String::toBigDecimal)
fun Iterable<String>.parseCharBigIntegers() = parse(String::toChar, String::toBigInteger)
fun Iterable<String>.parseCharChars() = parse(String::toChar, String::toChar)
fun Iterable<String>.parseCharDoubles() = parse(String::toChar, String::toDouble)
fun Iterable<String>.parseCharInts() = parse(String::toChar, String::toInt)
fun Iterable<String>.parseCharLongs() = parse(String::toChar, String::toLong)
fun Iterable<String>.parseCharStrings() = parse(String::toChar, String::toString)
fun Iterable<String>.parseDoubleBigDecimals() = parse(String::toDouble, String::toBigDecimal)
fun Iterable<String>.parseDoubleBigIntegers() = parse(String::toDouble, String::toBigInteger)
fun Iterable<String>.parseDoubleChars() = parse(String::toDouble, String::toChar)
fun Iterable<String>.parseDoubleDoubles() = parse(String::toDouble, String::toDouble)
fun Iterable<String>.parseDoubleInts() = parse(String::toDouble, String::toInt)
fun Iterable<String>.parseDoubleLongs() = parse(String::toDouble, String::toLong)
fun Iterable<String>.parseDoubleStrings() = parse(String::toDouble, String::toString)
fun Iterable<String>.parseIntBigDecimals() = parse(String::toInt, String::toBigDecimal)
fun Iterable<String>.parseIntBigIntegers() = parse(String::toInt, String::toBigInteger)
fun Iterable<String>.parseIntChars() = parse(String::toInt, String::toChar)
fun Iterable<String>.parseIntDoubles() = parse(String::toInt, String::toDouble)
fun Iterable<String>.parseIntInts() = parse(String::toInt, String::toInt)
fun Iterable<String>.parseIntLongs() = parse(String::toInt, String::toLong)
fun Iterable<String>.parseIntStrings() = parse(String::toInt, String::toString)
fun Iterable<String>.parseLongBigDecimals() = parse(String::toLong, String::toBigDecimal)
fun Iterable<String>.parseLongBigIntegers() = parse(String::toLong, String::toBigInteger)
fun Iterable<String>.parseLongChars() = parse(String::toLong, String::toChar)
fun Iterable<String>.parseLongDoubles() = parse(String::toLong, String::toDouble)
fun Iterable<String>.parseLongInts() = parse(String::toLong, String::toInt)
fun Iterable<String>.parseLongLongs() = parse(String::toLong, String::toLong)
fun Iterable<String>.parseLongStrings() = parse(String::toLong, String::toString)
fun Iterable<String>.parseStringBigDecimals() = parse(String::toString, String::toBigDecimal)
fun Iterable<String>.parseStringBigIntegers() = parse(String::toString, String::toBigInteger)
fun Iterable<String>.parseStringChars() = parse(String::toString, String::toChar)
fun Iterable<String>.parseStringDoubles() = parse(String::toString, String::toDouble)
fun Iterable<String>.parseStringInts() = parse(String::toString, String::toInt)
fun Iterable<String>.parseStringLongs() = parse(String::toString, String::toLong)
fun Iterable<String>.parseStringStrings() = parse(String::toString, String::toString)

fun String.parseBigDecimalBigDecimalBigDecimals() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toBigDecimal)

fun String.parseBigDecimalBigDecimalBigIntegers() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toBigInteger)

fun String.parseBigDecimalBigDecimalChars() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toChar)

fun String.parseBigDecimalBigDecimalDoubles() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toDouble)

fun String.parseBigDecimalBigDecimalInts() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toInt)

fun String.parseBigDecimalBigDecimalLongs() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toLong)

fun String.parseBigDecimalBigDecimalStrings() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toString)

fun String.parseBigDecimalBigIntegerBigDecimals() =
  parse(String::toBigDecimal, String::toBigInteger, String::toBigDecimal)

fun String.parseBigDecimalBigIntegerBigIntegers() =
  parse(String::toBigDecimal, String::toBigInteger, String::toBigInteger)

fun String.parseBigDecimalBigIntegerChars() =
  parse(String::toBigDecimal, String::toBigInteger, String::toChar)

fun String.parseBigDecimalBigIntegerDoubles() =
  parse(String::toBigDecimal, String::toBigInteger, String::toDouble)

fun String.parseBigDecimalBigIntegerInts() =
  parse(String::toBigDecimal, String::toBigInteger, String::toInt)

fun String.parseBigDecimalBigIntegerLongs() =
  parse(String::toBigDecimal, String::toBigInteger, String::toLong)

fun String.parseBigDecimalBigIntegerStrings() =
  parse(String::toBigDecimal, String::toBigInteger, String::toString)

fun String.parseBigDecimalCharBigDecimals() =
  parse(String::toBigDecimal, String::toChar, String::toBigDecimal)

fun String.parseBigDecimalCharBigIntegers() =
  parse(String::toBigDecimal, String::toChar, String::toBigInteger)

fun String.parseBigDecimalCharChars() =
  parse(String::toBigDecimal, String::toChar, String::toChar)

fun String.parseBigDecimalCharDoubles() =
  parse(String::toBigDecimal, String::toChar, String::toDouble)

fun String.parseBigDecimalCharInts() =
  parse(String::toBigDecimal, String::toChar, String::toInt)

fun String.parseBigDecimalCharLongs() =
  parse(String::toBigDecimal, String::toChar, String::toLong)

fun String.parseBigDecimalCharStrings() =
  parse(String::toBigDecimal, String::toChar, String::toString)

fun String.parseBigDecimalDoubleBigDecimals() =
  parse(String::toBigDecimal, String::toDouble, String::toBigDecimal)

fun String.parseBigDecimalDoubleBigIntegers() =
  parse(String::toBigDecimal, String::toDouble, String::toBigInteger)

fun String.parseBigDecimalDoubleChars() =
  parse(String::toBigDecimal, String::toDouble, String::toChar)

fun String.parseBigDecimalDoubleDoubles() =
  parse(String::toBigDecimal, String::toDouble, String::toDouble)

fun String.parseBigDecimalDoubleInts() =
  parse(String::toBigDecimal, String::toDouble, String::toInt)

fun String.parseBigDecimalDoubleLongs() =
  parse(String::toBigDecimal, String::toDouble, String::toLong)

fun String.parseBigDecimalDoubleStrings() =
  parse(String::toBigDecimal, String::toDouble, String::toString)

fun String.parseBigDecimalIntBigDecimals() =
  parse(String::toBigDecimal, String::toInt, String::toBigDecimal)

fun String.parseBigDecimalIntBigIntegers() =
  parse(String::toBigDecimal, String::toInt, String::toBigInteger)

fun String.parseBigDecimalIntChars() =
  parse(String::toBigDecimal, String::toInt, String::toChar)

fun String.parseBigDecimalIntDoubles() =
  parse(String::toBigDecimal, String::toInt, String::toDouble)

fun String.parseBigDecimalIntInts() =
  parse(String::toBigDecimal, String::toInt, String::toInt)

fun String.parseBigDecimalIntLongs() =
  parse(String::toBigDecimal, String::toInt, String::toLong)

fun String.parseBigDecimalIntStrings() =
  parse(String::toBigDecimal, String::toInt, String::toString)

fun String.parseBigDecimalLongBigDecimals() =
  parse(String::toBigDecimal, String::toLong, String::toBigDecimal)

fun String.parseBigDecimalLongBigIntegers() =
  parse(String::toBigDecimal, String::toLong, String::toBigInteger)

fun String.parseBigDecimalLongChars() =
  parse(String::toBigDecimal, String::toLong, String::toChar)

fun String.parseBigDecimalLongDoubles() =
  parse(String::toBigDecimal, String::toLong, String::toDouble)

fun String.parseBigDecimalLongInts() =
  parse(String::toBigDecimal, String::toLong, String::toInt)

fun String.parseBigDecimalLongLongs() =
  parse(String::toBigDecimal, String::toLong, String::toLong)

fun String.parseBigDecimalLongStrings() =
  parse(String::toBigDecimal, String::toLong, String::toString)

fun String.parseBigDecimalStringBigDecimals() =
  parse(String::toBigDecimal, String::toString, String::toBigDecimal)

fun String.parseBigDecimalStringBigIntegers() =
  parse(String::toBigDecimal, String::toString, String::toBigInteger)

fun String.parseBigDecimalStringChars() =
  parse(String::toBigDecimal, String::toString, String::toChar)

fun String.parseBigDecimalStringDoubles() =
  parse(String::toBigDecimal, String::toString, String::toDouble)

fun String.parseBigDecimalStringInts() =
  parse(String::toBigDecimal, String::toString, String::toInt)

fun String.parseBigDecimalStringLongs() =
  parse(String::toBigDecimal, String::toString, String::toLong)

fun String.parseBigDecimalStringStrings() =
  parse(String::toBigDecimal, String::toString, String::toString)

fun String.parseBigIntegerBigDecimalBigDecimals() =
  parse(String::toBigInteger, String::toBigDecimal, String::toBigDecimal)

fun String.parseBigIntegerBigDecimalBigIntegers() =
  parse(String::toBigInteger, String::toBigDecimal, String::toBigInteger)

fun String.parseBigIntegerBigDecimalChars() =
  parse(String::toBigInteger, String::toBigDecimal, String::toChar)

fun String.parseBigIntegerBigDecimalDoubles() =
  parse(String::toBigInteger, String::toBigDecimal, String::toDouble)

fun String.parseBigIntegerBigDecimalInts() =
  parse(String::toBigInteger, String::toBigDecimal, String::toInt)

fun String.parseBigIntegerBigDecimalLongs() =
  parse(String::toBigInteger, String::toBigDecimal, String::toLong)

fun String.parseBigIntegerBigDecimalStrings() =
  parse(String::toBigInteger, String::toBigDecimal, String::toString)

fun String.parseBigIntegerBigIntegerBigDecimals() =
  parse(String::toBigInteger, String::toBigInteger, String::toBigDecimal)

fun String.parseBigIntegerBigIntegerBigIntegers() =
  parse(String::toBigInteger, String::toBigInteger, String::toBigInteger)

fun String.parseBigIntegerBigIntegerChars() =
  parse(String::toBigInteger, String::toBigInteger, String::toChar)

fun String.parseBigIntegerBigIntegerDoubles() =
  parse(String::toBigInteger, String::toBigInteger, String::toDouble)

fun String.parseBigIntegerBigIntegerInts() =
  parse(String::toBigInteger, String::toBigInteger, String::toInt)

fun String.parseBigIntegerBigIntegerLongs() =
  parse(String::toBigInteger, String::toBigInteger, String::toLong)

fun String.parseBigIntegerBigIntegerStrings() =
  parse(String::toBigInteger, String::toBigInteger, String::toString)

fun String.parseBigIntegerCharBigDecimals() =
  parse(String::toBigInteger, String::toChar, String::toBigDecimal)

fun String.parseBigIntegerCharBigIntegers() =
  parse(String::toBigInteger, String::toChar, String::toBigInteger)

fun String.parseBigIntegerCharChars() =
  parse(String::toBigInteger, String::toChar, String::toChar)

fun String.parseBigIntegerCharDoubles() =
  parse(String::toBigInteger, String::toChar, String::toDouble)

fun String.parseBigIntegerCharInts() =
  parse(String::toBigInteger, String::toChar, String::toInt)

fun String.parseBigIntegerCharLongs() =
  parse(String::toBigInteger, String::toChar, String::toLong)

fun String.parseBigIntegerCharStrings() =
  parse(String::toBigInteger, String::toChar, String::toString)

fun String.parseBigIntegerDoubleBigDecimals() =
  parse(String::toBigInteger, String::toDouble, String::toBigDecimal)

fun String.parseBigIntegerDoubleBigIntegers() =
  parse(String::toBigInteger, String::toDouble, String::toBigInteger)

fun String.parseBigIntegerDoubleChars() =
  parse(String::toBigInteger, String::toDouble, String::toChar)

fun String.parseBigIntegerDoubleDoubles() =
  parse(String::toBigInteger, String::toDouble, String::toDouble)

fun String.parseBigIntegerDoubleInts() =
  parse(String::toBigInteger, String::toDouble, String::toInt)

fun String.parseBigIntegerDoubleLongs() =
  parse(String::toBigInteger, String::toDouble, String::toLong)

fun String.parseBigIntegerDoubleStrings() =
  parse(String::toBigInteger, String::toDouble, String::toString)

fun String.parseBigIntegerIntBigDecimals() =
  parse(String::toBigInteger, String::toInt, String::toBigDecimal)

fun String.parseBigIntegerIntBigIntegers() =
  parse(String::toBigInteger, String::toInt, String::toBigInteger)

fun String.parseBigIntegerIntChars() =
  parse(String::toBigInteger, String::toInt, String::toChar)

fun String.parseBigIntegerIntDoubles() =
  parse(String::toBigInteger, String::toInt, String::toDouble)

fun String.parseBigIntegerIntInts() =
  parse(String::toBigInteger, String::toInt, String::toInt)

fun String.parseBigIntegerIntLongs() =
  parse(String::toBigInteger, String::toInt, String::toLong)

fun String.parseBigIntegerIntStrings() =
  parse(String::toBigInteger, String::toInt, String::toString)

fun String.parseBigIntegerLongBigDecimals() =
  parse(String::toBigInteger, String::toLong, String::toBigDecimal)

fun String.parseBigIntegerLongBigIntegers() =
  parse(String::toBigInteger, String::toLong, String::toBigInteger)

fun String.parseBigIntegerLongChars() =
  parse(String::toBigInteger, String::toLong, String::toChar)

fun String.parseBigIntegerLongDoubles() =
  parse(String::toBigInteger, String::toLong, String::toDouble)

fun String.parseBigIntegerLongInts() =
  parse(String::toBigInteger, String::toLong, String::toInt)

fun String.parseBigIntegerLongLongs() =
  parse(String::toBigInteger, String::toLong, String::toLong)

fun String.parseBigIntegerLongStrings() =
  parse(String::toBigInteger, String::toLong, String::toString)

fun String.parseBigIntegerStringBigDecimals() =
  parse(String::toBigInteger, String::toString, String::toBigDecimal)

fun String.parseBigIntegerStringBigIntegers() =
  parse(String::toBigInteger, String::toString, String::toBigInteger)

fun String.parseBigIntegerStringChars() =
  parse(String::toBigInteger, String::toString, String::toChar)

fun String.parseBigIntegerStringDoubles() =
  parse(String::toBigInteger, String::toString, String::toDouble)

fun String.parseBigIntegerStringInts() =
  parse(String::toBigInteger, String::toString, String::toInt)

fun String.parseBigIntegerStringLongs() =
  parse(String::toBigInteger, String::toString, String::toLong)

fun String.parseBigIntegerStringStrings() =
  parse(String::toBigInteger, String::toString, String::toString)

fun String.parseCharBigDecimalBigDecimals() =
  parse(String::toChar, String::toBigDecimal, String::toBigDecimal)

fun String.parseCharBigDecimalBigIntegers() =
  parse(String::toChar, String::toBigDecimal, String::toBigInteger)

fun String.parseCharBigDecimalChars() =
  parse(String::toChar, String::toBigDecimal, String::toChar)

fun String.parseCharBigDecimalDoubles() =
  parse(String::toChar, String::toBigDecimal, String::toDouble)

fun String.parseCharBigDecimalInts() =
  parse(String::toChar, String::toBigDecimal, String::toInt)

fun String.parseCharBigDecimalLongs() =
  parse(String::toChar, String::toBigDecimal, String::toLong)

fun String.parseCharBigDecimalStrings() =
  parse(String::toChar, String::toBigDecimal, String::toString)

fun String.parseCharBigIntegerBigDecimals() =
  parse(String::toChar, String::toBigInteger, String::toBigDecimal)

fun String.parseCharBigIntegerBigIntegers() =
  parse(String::toChar, String::toBigInteger, String::toBigInteger)

fun String.parseCharBigIntegerChars() =
  parse(String::toChar, String::toBigInteger, String::toChar)

fun String.parseCharBigIntegerDoubles() =
  parse(String::toChar, String::toBigInteger, String::toDouble)

fun String.parseCharBigIntegerInts() =
  parse(String::toChar, String::toBigInteger, String::toInt)

fun String.parseCharBigIntegerLongs() =
  parse(String::toChar, String::toBigInteger, String::toLong)

fun String.parseCharBigIntegerStrings() =
  parse(String::toChar, String::toBigInteger, String::toString)

fun String.parseCharCharBigDecimals() =
  parse(String::toChar, String::toChar, String::toBigDecimal)

fun String.parseCharCharBigIntegers() =
  parse(String::toChar, String::toChar, String::toBigInteger)

fun String.parseCharCharChars() =
  parse(String::toChar, String::toChar, String::toChar)

fun String.parseCharCharDoubles() =
  parse(String::toChar, String::toChar, String::toDouble)

fun String.parseCharCharInts() =
  parse(String::toChar, String::toChar, String::toInt)

fun String.parseCharCharLongs() =
  parse(String::toChar, String::toChar, String::toLong)

fun String.parseCharCharStrings() =
  parse(String::toChar, String::toChar, String::toString)

fun String.parseCharDoubleBigDecimals() =
  parse(String::toChar, String::toDouble, String::toBigDecimal)

fun String.parseCharDoubleBigIntegers() =
  parse(String::toChar, String::toDouble, String::toBigInteger)

fun String.parseCharDoubleChars() =
  parse(String::toChar, String::toDouble, String::toChar)

fun String.parseCharDoubleDoubles() =
  parse(String::toChar, String::toDouble, String::toDouble)

fun String.parseCharDoubleInts() =
  parse(String::toChar, String::toDouble, String::toInt)

fun String.parseCharDoubleLongs() =
  parse(String::toChar, String::toDouble, String::toLong)

fun String.parseCharDoubleStrings() =
  parse(String::toChar, String::toDouble, String::toString)

fun String.parseCharIntBigDecimals() =
  parse(String::toChar, String::toInt, String::toBigDecimal)

fun String.parseCharIntBigIntegers() =
  parse(String::toChar, String::toInt, String::toBigInteger)

fun String.parseCharIntChars() =
  parse(String::toChar, String::toInt, String::toChar)

fun String.parseCharIntDoubles() =
  parse(String::toChar, String::toInt, String::toDouble)

fun String.parseCharIntInts() =
  parse(String::toChar, String::toInt, String::toInt)

fun String.parseCharIntLongs() =
  parse(String::toChar, String::toInt, String::toLong)

fun String.parseCharIntStrings() =
  parse(String::toChar, String::toInt, String::toString)

fun String.parseCharLongBigDecimals() =
  parse(String::toChar, String::toLong, String::toBigDecimal)

fun String.parseCharLongBigIntegers() =
  parse(String::toChar, String::toLong, String::toBigInteger)

fun String.parseCharLongChars() =
  parse(String::toChar, String::toLong, String::toChar)

fun String.parseCharLongDoubles() =
  parse(String::toChar, String::toLong, String::toDouble)

fun String.parseCharLongInts() =
  parse(String::toChar, String::toLong, String::toInt)

fun String.parseCharLongLongs() =
  parse(String::toChar, String::toLong, String::toLong)

fun String.parseCharLongStrings() =
  parse(String::toChar, String::toLong, String::toString)

fun String.parseCharStringBigDecimals() =
  parse(String::toChar, String::toString, String::toBigDecimal)

fun String.parseCharStringBigIntegers() =
  parse(String::toChar, String::toString, String::toBigInteger)

fun String.parseCharStringChars() =
  parse(String::toChar, String::toString, String::toChar)

fun String.parseCharStringDoubles() =
  parse(String::toChar, String::toString, String::toDouble)

fun String.parseCharStringInts() =
  parse(String::toChar, String::toString, String::toInt)

fun String.parseCharStringLongs() =
  parse(String::toChar, String::toString, String::toLong)

fun String.parseCharStringStrings() =
  parse(String::toChar, String::toString, String::toString)

fun String.parseDoubleBigDecimalBigDecimals() =
  parse(String::toDouble, String::toBigDecimal, String::toBigDecimal)

fun String.parseDoubleBigDecimalBigIntegers() =
  parse(String::toDouble, String::toBigDecimal, String::toBigInteger)

fun String.parseDoubleBigDecimalChars() =
  parse(String::toDouble, String::toBigDecimal, String::toChar)

fun String.parseDoubleBigDecimalDoubles() =
  parse(String::toDouble, String::toBigDecimal, String::toDouble)

fun String.parseDoubleBigDecimalInts() =
  parse(String::toDouble, String::toBigDecimal, String::toInt)

fun String.parseDoubleBigDecimalLongs() =
  parse(String::toDouble, String::toBigDecimal, String::toLong)

fun String.parseDoubleBigDecimalStrings() =
  parse(String::toDouble, String::toBigDecimal, String::toString)

fun String.parseDoubleBigIntegerBigDecimals() =
  parse(String::toDouble, String::toBigInteger, String::toBigDecimal)

fun String.parseDoubleBigIntegerBigIntegers() =
  parse(String::toDouble, String::toBigInteger, String::toBigInteger)

fun String.parseDoubleBigIntegerChars() =
  parse(String::toDouble, String::toBigInteger, String::toChar)

fun String.parseDoubleBigIntegerDoubles() =
  parse(String::toDouble, String::toBigInteger, String::toDouble)

fun String.parseDoubleBigIntegerInts() =
  parse(String::toDouble, String::toBigInteger, String::toInt)

fun String.parseDoubleBigIntegerLongs() =
  parse(String::toDouble, String::toBigInteger, String::toLong)

fun String.parseDoubleBigIntegerStrings() =
  parse(String::toDouble, String::toBigInteger, String::toString)

fun String.parseDoubleCharBigDecimals() =
  parse(String::toDouble, String::toChar, String::toBigDecimal)

fun String.parseDoubleCharBigIntegers() =
  parse(String::toDouble, String::toChar, String::toBigInteger)

fun String.parseDoubleCharChars() =
  parse(String::toDouble, String::toChar, String::toChar)

fun String.parseDoubleCharDoubles() =
  parse(String::toDouble, String::toChar, String::toDouble)

fun String.parseDoubleCharInts() =
  parse(String::toDouble, String::toChar, String::toInt)

fun String.parseDoubleCharLongs() =
  parse(String::toDouble, String::toChar, String::toLong)

fun String.parseDoubleCharStrings() =
  parse(String::toDouble, String::toChar, String::toString)

fun String.parseDoubleDoubleBigDecimals() =
  parse(String::toDouble, String::toDouble, String::toBigDecimal)

fun String.parseDoubleDoubleBigIntegers() =
  parse(String::toDouble, String::toDouble, String::toBigInteger)

fun String.parseDoubleDoubleChars() =
  parse(String::toDouble, String::toDouble, String::toChar)

fun String.parseDoubleDoubleDoubles() =
  parse(String::toDouble, String::toDouble, String::toDouble)

fun String.parseDoubleDoubleInts() =
  parse(String::toDouble, String::toDouble, String::toInt)

fun String.parseDoubleDoubleLongs() =
  parse(String::toDouble, String::toDouble, String::toLong)

fun String.parseDoubleDoubleStrings() =
  parse(String::toDouble, String::toDouble, String::toString)

fun String.parseDoubleIntBigDecimals() =
  parse(String::toDouble, String::toInt, String::toBigDecimal)

fun String.parseDoubleIntBigIntegers() =
  parse(String::toDouble, String::toInt, String::toBigInteger)

fun String.parseDoubleIntChars() =
  parse(String::toDouble, String::toInt, String::toChar)

fun String.parseDoubleIntDoubles() =
  parse(String::toDouble, String::toInt, String::toDouble)

fun String.parseDoubleIntInts() =
  parse(String::toDouble, String::toInt, String::toInt)

fun String.parseDoubleIntLongs() =
  parse(String::toDouble, String::toInt, String::toLong)

fun String.parseDoubleIntStrings() =
  parse(String::toDouble, String::toInt, String::toString)

fun String.parseDoubleLongBigDecimals() =
  parse(String::toDouble, String::toLong, String::toBigDecimal)

fun String.parseDoubleLongBigIntegers() =
  parse(String::toDouble, String::toLong, String::toBigInteger)

fun String.parseDoubleLongChars() =
  parse(String::toDouble, String::toLong, String::toChar)

fun String.parseDoubleLongDoubles() =
  parse(String::toDouble, String::toLong, String::toDouble)

fun String.parseDoubleLongInts() =
  parse(String::toDouble, String::toLong, String::toInt)

fun String.parseDoubleLongLongs() =
  parse(String::toDouble, String::toLong, String::toLong)

fun String.parseDoubleLongStrings() =
  parse(String::toDouble, String::toLong, String::toString)

fun String.parseDoubleStringBigDecimals() =
  parse(String::toDouble, String::toString, String::toBigDecimal)

fun String.parseDoubleStringBigIntegers() =
  parse(String::toDouble, String::toString, String::toBigInteger)

fun String.parseDoubleStringChars() =
  parse(String::toDouble, String::toString, String::toChar)

fun String.parseDoubleStringDoubles() =
  parse(String::toDouble, String::toString, String::toDouble)

fun String.parseDoubleStringInts() =
  parse(String::toDouble, String::toString, String::toInt)

fun String.parseDoubleStringLongs() =
  parse(String::toDouble, String::toString, String::toLong)

fun String.parseDoubleStringStrings() =
  parse(String::toDouble, String::toString, String::toString)

fun String.parseIntBigDecimalBigDecimals() =
  parse(String::toInt, String::toBigDecimal, String::toBigDecimal)

fun String.parseIntBigDecimalBigIntegers() =
  parse(String::toInt, String::toBigDecimal, String::toBigInteger)

fun String.parseIntBigDecimalChars() =
  parse(String::toInt, String::toBigDecimal, String::toChar)

fun String.parseIntBigDecimalDoubles() =
  parse(String::toInt, String::toBigDecimal, String::toDouble)

fun String.parseIntBigDecimalInts() =
  parse(String::toInt, String::toBigDecimal, String::toInt)

fun String.parseIntBigDecimalLongs() =
  parse(String::toInt, String::toBigDecimal, String::toLong)

fun String.parseIntBigDecimalStrings() =
  parse(String::toInt, String::toBigDecimal, String::toString)

fun String.parseIntBigIntegerBigDecimals() =
  parse(String::toInt, String::toBigInteger, String::toBigDecimal)

fun String.parseIntBigIntegerBigIntegers() =
  parse(String::toInt, String::toBigInteger, String::toBigInteger)

fun String.parseIntBigIntegerChars() =
  parse(String::toInt, String::toBigInteger, String::toChar)

fun String.parseIntBigIntegerDoubles() =
  parse(String::toInt, String::toBigInteger, String::toDouble)

fun String.parseIntBigIntegerInts() =
  parse(String::toInt, String::toBigInteger, String::toInt)

fun String.parseIntBigIntegerLongs() =
  parse(String::toInt, String::toBigInteger, String::toLong)

fun String.parseIntBigIntegerStrings() =
  parse(String::toInt, String::toBigInteger, String::toString)

fun String.parseIntCharBigDecimals() =
  parse(String::toInt, String::toChar, String::toBigDecimal)

fun String.parseIntCharBigIntegers() =
  parse(String::toInt, String::toChar, String::toBigInteger)

fun String.parseIntCharChars() =
  parse(String::toInt, String::toChar, String::toChar)

fun String.parseIntCharDoubles() =
  parse(String::toInt, String::toChar, String::toDouble)

fun String.parseIntCharInts() =
  parse(String::toInt, String::toChar, String::toInt)

fun String.parseIntCharLongs() =
  parse(String::toInt, String::toChar, String::toLong)

fun String.parseIntCharStrings() =
  parse(String::toInt, String::toChar, String::toString)

fun String.parseIntDoubleBigDecimals() =
  parse(String::toInt, String::toDouble, String::toBigDecimal)

fun String.parseIntDoubleBigIntegers() =
  parse(String::toInt, String::toDouble, String::toBigInteger)

fun String.parseIntDoubleChars() =
  parse(String::toInt, String::toDouble, String::toChar)

fun String.parseIntDoubleDoubles() =
  parse(String::toInt, String::toDouble, String::toDouble)

fun String.parseIntDoubleInts() =
  parse(String::toInt, String::toDouble, String::toInt)

fun String.parseIntDoubleLongs() =
  parse(String::toInt, String::toDouble, String::toLong)

fun String.parseIntDoubleStrings() =
  parse(String::toInt, String::toDouble, String::toString)

fun String.parseIntIntBigDecimals() =
  parse(String::toInt, String::toInt, String::toBigDecimal)

fun String.parseIntIntBigIntegers() =
  parse(String::toInt, String::toInt, String::toBigInteger)

fun String.parseIntIntChars() =
  parse(String::toInt, String::toInt, String::toChar)

fun String.parseIntIntDoubles() =
  parse(String::toInt, String::toInt, String::toDouble)

fun String.parseIntIntInts() =
  parse(String::toInt, String::toInt, String::toInt)

fun String.parseIntIntLongs() =
  parse(String::toInt, String::toInt, String::toLong)

fun String.parseIntIntStrings() =
  parse(String::toInt, String::toInt, String::toString)

fun String.parseIntLongBigDecimals() =
  parse(String::toInt, String::toLong, String::toBigDecimal)

fun String.parseIntLongBigIntegers() =
  parse(String::toInt, String::toLong, String::toBigInteger)

fun String.parseIntLongChars() =
  parse(String::toInt, String::toLong, String::toChar)

fun String.parseIntLongDoubles() =
  parse(String::toInt, String::toLong, String::toDouble)

fun String.parseIntLongInts() =
  parse(String::toInt, String::toLong, String::toInt)

fun String.parseIntLongLongs() =
  parse(String::toInt, String::toLong, String::toLong)

fun String.parseIntLongStrings() =
  parse(String::toInt, String::toLong, String::toString)

fun String.parseIntStringBigDecimals() =
  parse(String::toInt, String::toString, String::toBigDecimal)

fun String.parseIntStringBigIntegers() =
  parse(String::toInt, String::toString, String::toBigInteger)

fun String.parseIntStringChars() =
  parse(String::toInt, String::toString, String::toChar)

fun String.parseIntStringDoubles() =
  parse(String::toInt, String::toString, String::toDouble)

fun String.parseIntStringInts() =
  parse(String::toInt, String::toString, String::toInt)

fun String.parseIntStringLongs() =
  parse(String::toInt, String::toString, String::toLong)

fun String.parseIntStringStrings() =
  parse(String::toInt, String::toString, String::toString)

fun String.parseLongBigDecimalBigDecimals() =
  parse(String::toLong, String::toBigDecimal, String::toBigDecimal)

fun String.parseLongBigDecimalBigIntegers() =
  parse(String::toLong, String::toBigDecimal, String::toBigInteger)

fun String.parseLongBigDecimalChars() =
  parse(String::toLong, String::toBigDecimal, String::toChar)

fun String.parseLongBigDecimalDoubles() =
  parse(String::toLong, String::toBigDecimal, String::toDouble)

fun String.parseLongBigDecimalInts() =
  parse(String::toLong, String::toBigDecimal, String::toInt)

fun String.parseLongBigDecimalLongs() =
  parse(String::toLong, String::toBigDecimal, String::toLong)

fun String.parseLongBigDecimalStrings() =
  parse(String::toLong, String::toBigDecimal, String::toString)

fun String.parseLongBigIntegerBigDecimals() =
  parse(String::toLong, String::toBigInteger, String::toBigDecimal)

fun String.parseLongBigIntegerBigIntegers() =
  parse(String::toLong, String::toBigInteger, String::toBigInteger)

fun String.parseLongBigIntegerChars() =
  parse(String::toLong, String::toBigInteger, String::toChar)

fun String.parseLongBigIntegerDoubles() =
  parse(String::toLong, String::toBigInteger, String::toDouble)

fun String.parseLongBigIntegerInts() =
  parse(String::toLong, String::toBigInteger, String::toInt)

fun String.parseLongBigIntegerLongs() =
  parse(String::toLong, String::toBigInteger, String::toLong)

fun String.parseLongBigIntegerStrings() =
  parse(String::toLong, String::toBigInteger, String::toString)

fun String.parseLongCharBigDecimals() =
  parse(String::toLong, String::toChar, String::toBigDecimal)

fun String.parseLongCharBigIntegers() =
  parse(String::toLong, String::toChar, String::toBigInteger)

fun String.parseLongCharChars() =
  parse(String::toLong, String::toChar, String::toChar)

fun String.parseLongCharDoubles() =
  parse(String::toLong, String::toChar, String::toDouble)

fun String.parseLongCharInts() =
  parse(String::toLong, String::toChar, String::toInt)

fun String.parseLongCharLongs() =
  parse(String::toLong, String::toChar, String::toLong)

fun String.parseLongCharStrings() =
  parse(String::toLong, String::toChar, String::toString)

fun String.parseLongDoubleBigDecimals() =
  parse(String::toLong, String::toDouble, String::toBigDecimal)

fun String.parseLongDoubleBigIntegers() =
  parse(String::toLong, String::toDouble, String::toBigInteger)

fun String.parseLongDoubleChars() =
  parse(String::toLong, String::toDouble, String::toChar)

fun String.parseLongDoubleDoubles() =
  parse(String::toLong, String::toDouble, String::toDouble)

fun String.parseLongDoubleInts() =
  parse(String::toLong, String::toDouble, String::toInt)

fun String.parseLongDoubleLongs() =
  parse(String::toLong, String::toDouble, String::toLong)

fun String.parseLongDoubleStrings() =
  parse(String::toLong, String::toDouble, String::toString)

fun String.parseLongIntBigDecimals() =
  parse(String::toLong, String::toInt, String::toBigDecimal)

fun String.parseLongIntBigIntegers() =
  parse(String::toLong, String::toInt, String::toBigInteger)

fun String.parseLongIntChars() =
  parse(String::toLong, String::toInt, String::toChar)

fun String.parseLongIntDoubles() =
  parse(String::toLong, String::toInt, String::toDouble)

fun String.parseLongIntInts() =
  parse(String::toLong, String::toInt, String::toInt)

fun String.parseLongIntLongs() =
  parse(String::toLong, String::toInt, String::toLong)

fun String.parseLongIntStrings() =
  parse(String::toLong, String::toInt, String::toString)

fun String.parseLongLongBigDecimals() =
  parse(String::toLong, String::toLong, String::toBigDecimal)

fun String.parseLongLongBigIntegers() =
  parse(String::toLong, String::toLong, String::toBigInteger)

fun String.parseLongLongChars() =
  parse(String::toLong, String::toLong, String::toChar)

fun String.parseLongLongDoubles() =
  parse(String::toLong, String::toLong, String::toDouble)

fun String.parseLongLongInts() =
  parse(String::toLong, String::toLong, String::toInt)

fun String.parseLongLongLongs() =
  parse(String::toLong, String::toLong, String::toLong)

fun String.parseLongLongStrings() =
  parse(String::toLong, String::toLong, String::toString)

fun String.parseLongStringBigDecimals() =
  parse(String::toLong, String::toString, String::toBigDecimal)

fun String.parseLongStringBigIntegers() =
  parse(String::toLong, String::toString, String::toBigInteger)

fun String.parseLongStringChars() =
  parse(String::toLong, String::toString, String::toChar)

fun String.parseLongStringDoubles() =
  parse(String::toLong, String::toString, String::toDouble)

fun String.parseLongStringInts() =
  parse(String::toLong, String::toString, String::toInt)

fun String.parseLongStringLongs() =
  parse(String::toLong, String::toString, String::toLong)

fun String.parseLongStringStrings() =
  parse(String::toLong, String::toString, String::toString)

fun String.parseStringBigDecimalBigDecimals() =
  parse(String::toString, String::toBigDecimal, String::toBigDecimal)

fun String.parseStringBigDecimalBigIntegers() =
  parse(String::toString, String::toBigDecimal, String::toBigInteger)

fun String.parseStringBigDecimalChars() =
  parse(String::toString, String::toBigDecimal, String::toChar)

fun String.parseStringBigDecimalDoubles() =
  parse(String::toString, String::toBigDecimal, String::toDouble)

fun String.parseStringBigDecimalInts() =
  parse(String::toString, String::toBigDecimal, String::toInt)

fun String.parseStringBigDecimalLongs() =
  parse(String::toString, String::toBigDecimal, String::toLong)

fun String.parseStringBigDecimalStrings() =
  parse(String::toString, String::toBigDecimal, String::toString)

fun String.parseStringBigIntegerBigDecimals() =
  parse(String::toString, String::toBigInteger, String::toBigDecimal)

fun String.parseStringBigIntegerBigIntegers() =
  parse(String::toString, String::toBigInteger, String::toBigInteger)

fun String.parseStringBigIntegerChars() =
  parse(String::toString, String::toBigInteger, String::toChar)

fun String.parseStringBigIntegerDoubles() =
  parse(String::toString, String::toBigInteger, String::toDouble)

fun String.parseStringBigIntegerInts() =
  parse(String::toString, String::toBigInteger, String::toInt)

fun String.parseStringBigIntegerLongs() =
  parse(String::toString, String::toBigInteger, String::toLong)

fun String.parseStringBigIntegerStrings() =
  parse(String::toString, String::toBigInteger, String::toString)

fun String.parseStringCharBigDecimals() =
  parse(String::toString, String::toChar, String::toBigDecimal)

fun String.parseStringCharBigIntegers() =
  parse(String::toString, String::toChar, String::toBigInteger)

fun String.parseStringCharChars() =
  parse(String::toString, String::toChar, String::toChar)

fun String.parseStringCharDoubles() =
  parse(String::toString, String::toChar, String::toDouble)

fun String.parseStringCharInts() =
  parse(String::toString, String::toChar, String::toInt)

fun String.parseStringCharLongs() =
  parse(String::toString, String::toChar, String::toLong)

fun String.parseStringCharStrings() =
  parse(String::toString, String::toChar, String::toString)

fun String.parseStringDoubleBigDecimals() =
  parse(String::toString, String::toDouble, String::toBigDecimal)

fun String.parseStringDoubleBigIntegers() =
  parse(String::toString, String::toDouble, String::toBigInteger)

fun String.parseStringDoubleChars() =
  parse(String::toString, String::toDouble, String::toChar)

fun String.parseStringDoubleDoubles() =
  parse(String::toString, String::toDouble, String::toDouble)

fun String.parseStringDoubleInts() =
  parse(String::toString, String::toDouble, String::toInt)

fun String.parseStringDoubleLongs() =
  parse(String::toString, String::toDouble, String::toLong)

fun String.parseStringDoubleStrings() =
  parse(String::toString, String::toDouble, String::toString)

fun String.parseStringIntBigDecimals() =
  parse(String::toString, String::toInt, String::toBigDecimal)

fun String.parseStringIntBigIntegers() =
  parse(String::toString, String::toInt, String::toBigInteger)

fun String.parseStringIntChars() =
  parse(String::toString, String::toInt, String::toChar)

fun String.parseStringIntDoubles() =
  parse(String::toString, String::toInt, String::toDouble)

fun String.parseStringIntInts() =
  parse(String::toString, String::toInt, String::toInt)

fun String.parseStringIntLongs() =
  parse(String::toString, String::toInt, String::toLong)

fun String.parseStringIntStrings() =
  parse(String::toString, String::toInt, String::toString)

fun String.parseStringLongBigDecimals() =
  parse(String::toString, String::toLong, String::toBigDecimal)

fun String.parseStringLongBigIntegers() =
  parse(String::toString, String::toLong, String::toBigInteger)

fun String.parseStringLongChars() =
  parse(String::toString, String::toLong, String::toChar)

fun String.parseStringLongDoubles() =
  parse(String::toString, String::toLong, String::toDouble)

fun String.parseStringLongInts() =
  parse(String::toString, String::toLong, String::toInt)

fun String.parseStringLongLongs() =
  parse(String::toString, String::toLong, String::toLong)

fun String.parseStringLongStrings() =
  parse(String::toString, String::toLong, String::toString)

fun String.parseStringStringBigDecimals() =
  parse(String::toString, String::toString, String::toBigDecimal)

fun String.parseStringStringBigIntegers() =
  parse(String::toString, String::toString, String::toBigInteger)

fun String.parseStringStringChars() =
  parse(String::toString, String::toString, String::toChar)

fun String.parseStringStringDoubles() =
  parse(String::toString, String::toString, String::toDouble)

fun String.parseStringStringInts() =
  parse(String::toString, String::toString, String::toInt)

fun String.parseStringStringLongs() =
  parse(String::toString, String::toString, String::toLong)

fun String.parseStringStringStrings() =
  parse(String::toString, String::toString, String::toString)

fun Iterable<String>.parseBigDecimalBigDecimalBigDecimals() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalBigDecimalBigIntegers() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseBigDecimalBigDecimalChars() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseBigDecimalBigDecimalDoubles() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseBigDecimalBigDecimalInts() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseBigDecimalBigDecimalLongs() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseBigDecimalBigDecimalStrings() =
  parse(String::toBigDecimal, String::toBigDecimal, String::toString)

fun Iterable<String>.parseBigDecimalBigIntegerBigDecimals() =
  parse(String::toBigDecimal, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalBigIntegerBigIntegers() =
  parse(String::toBigDecimal, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseBigDecimalBigIntegerChars() =
  parse(String::toBigDecimal, String::toBigInteger, String::toChar)

fun Iterable<String>.parseBigDecimalBigIntegerDoubles() =
  parse(String::toBigDecimal, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseBigDecimalBigIntegerInts() =
  parse(String::toBigDecimal, String::toBigInteger, String::toInt)

fun Iterable<String>.parseBigDecimalBigIntegerLongs() =
  parse(String::toBigDecimal, String::toBigInteger, String::toLong)

fun Iterable<String>.parseBigDecimalBigIntegerStrings() =
  parse(String::toBigDecimal, String::toBigInteger, String::toString)

fun Iterable<String>.parseBigDecimalCharBigDecimals() =
  parse(String::toBigDecimal, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalCharBigIntegers() =
  parse(String::toBigDecimal, String::toChar, String::toBigInteger)

fun Iterable<String>.parseBigDecimalCharChars() =
  parse(String::toBigDecimal, String::toChar, String::toChar)

fun Iterable<String>.parseBigDecimalCharDoubles() =
  parse(String::toBigDecimal, String::toChar, String::toDouble)

fun Iterable<String>.parseBigDecimalCharInts() =
  parse(String::toBigDecimal, String::toChar, String::toInt)

fun Iterable<String>.parseBigDecimalCharLongs() =
  parse(String::toBigDecimal, String::toChar, String::toLong)

fun Iterable<String>.parseBigDecimalCharStrings() =
  parse(String::toBigDecimal, String::toChar, String::toString)

fun Iterable<String>.parseBigDecimalDoubleBigDecimals() =
  parse(String::toBigDecimal, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalDoubleBigIntegers() =
  parse(String::toBigDecimal, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseBigDecimalDoubleChars() =
  parse(String::toBigDecimal, String::toDouble, String::toChar)

fun Iterable<String>.parseBigDecimalDoubleDoubles() =
  parse(String::toBigDecimal, String::toDouble, String::toDouble)

fun Iterable<String>.parseBigDecimalDoubleInts() =
  parse(String::toBigDecimal, String::toDouble, String::toInt)

fun Iterable<String>.parseBigDecimalDoubleLongs() =
  parse(String::toBigDecimal, String::toDouble, String::toLong)

fun Iterable<String>.parseBigDecimalDoubleStrings() =
  parse(String::toBigDecimal, String::toDouble, String::toString)

fun Iterable<String>.parseBigDecimalIntBigDecimals() =
  parse(String::toBigDecimal, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalIntBigIntegers() =
  parse(String::toBigDecimal, String::toInt, String::toBigInteger)

fun Iterable<String>.parseBigDecimalIntChars() =
  parse(String::toBigDecimal, String::toInt, String::toChar)

fun Iterable<String>.parseBigDecimalIntDoubles() =
  parse(String::toBigDecimal, String::toInt, String::toDouble)

fun Iterable<String>.parseBigDecimalIntInts() =
  parse(String::toBigDecimal, String::toInt, String::toInt)

fun Iterable<String>.parseBigDecimalIntLongs() =
  parse(String::toBigDecimal, String::toInt, String::toLong)

fun Iterable<String>.parseBigDecimalIntStrings() =
  parse(String::toBigDecimal, String::toInt, String::toString)

fun Iterable<String>.parseBigDecimalLongBigDecimals() =
  parse(String::toBigDecimal, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalLongBigIntegers() =
  parse(String::toBigDecimal, String::toLong, String::toBigInteger)

fun Iterable<String>.parseBigDecimalLongChars() =
  parse(String::toBigDecimal, String::toLong, String::toChar)

fun Iterable<String>.parseBigDecimalLongDoubles() =
  parse(String::toBigDecimal, String::toLong, String::toDouble)

fun Iterable<String>.parseBigDecimalLongInts() =
  parse(String::toBigDecimal, String::toLong, String::toInt)

fun Iterable<String>.parseBigDecimalLongLongs() =
  parse(String::toBigDecimal, String::toLong, String::toLong)

fun Iterable<String>.parseBigDecimalLongStrings() =
  parse(String::toBigDecimal, String::toLong, String::toString)

fun Iterable<String>.parseBigDecimalStringBigDecimals() =
  parse(String::toBigDecimal, String::toString, String::toBigDecimal)

fun Iterable<String>.parseBigDecimalStringBigIntegers() =
  parse(String::toBigDecimal, String::toString, String::toBigInteger)

fun Iterable<String>.parseBigDecimalStringChars() =
  parse(String::toBigDecimal, String::toString, String::toChar)

fun Iterable<String>.parseBigDecimalStringDoubles() =
  parse(String::toBigDecimal, String::toString, String::toDouble)

fun Iterable<String>.parseBigDecimalStringInts() =
  parse(String::toBigDecimal, String::toString, String::toInt)

fun Iterable<String>.parseBigDecimalStringLongs() =
  parse(String::toBigDecimal, String::toString, String::toLong)

fun Iterable<String>.parseBigDecimalStringStrings() =
  parse(String::toBigDecimal, String::toString, String::toString)

fun Iterable<String>.parseBigIntegerBigDecimalBigDecimals() =
  parse(String::toBigInteger, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerBigDecimalBigIntegers() =
  parse(String::toBigInteger, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseBigIntegerBigDecimalChars() =
  parse(String::toBigInteger, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseBigIntegerBigDecimalDoubles() =
  parse(String::toBigInteger, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseBigIntegerBigDecimalInts() =
  parse(String::toBigInteger, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseBigIntegerBigDecimalLongs() =
  parse(String::toBigInteger, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseBigIntegerBigDecimalStrings() =
  parse(String::toBigInteger, String::toBigDecimal, String::toString)

fun Iterable<String>.parseBigIntegerBigIntegerBigDecimals() =
  parse(String::toBigInteger, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerBigIntegerBigIntegers() =
  parse(String::toBigInteger, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseBigIntegerBigIntegerChars() =
  parse(String::toBigInteger, String::toBigInteger, String::toChar)

fun Iterable<String>.parseBigIntegerBigIntegerDoubles() =
  parse(String::toBigInteger, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseBigIntegerBigIntegerInts() =
  parse(String::toBigInteger, String::toBigInteger, String::toInt)

fun Iterable<String>.parseBigIntegerBigIntegerLongs() =
  parse(String::toBigInteger, String::toBigInteger, String::toLong)

fun Iterable<String>.parseBigIntegerBigIntegerStrings() =
  parse(String::toBigInteger, String::toBigInteger, String::toString)

fun Iterable<String>.parseBigIntegerCharBigDecimals() =
  parse(String::toBigInteger, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerCharBigIntegers() =
  parse(String::toBigInteger, String::toChar, String::toBigInteger)

fun Iterable<String>.parseBigIntegerCharChars() =
  parse(String::toBigInteger, String::toChar, String::toChar)

fun Iterable<String>.parseBigIntegerCharDoubles() =
  parse(String::toBigInteger, String::toChar, String::toDouble)

fun Iterable<String>.parseBigIntegerCharInts() =
  parse(String::toBigInteger, String::toChar, String::toInt)

fun Iterable<String>.parseBigIntegerCharLongs() =
  parse(String::toBigInteger, String::toChar, String::toLong)

fun Iterable<String>.parseBigIntegerCharStrings() =
  parse(String::toBigInteger, String::toChar, String::toString)

fun Iterable<String>.parseBigIntegerDoubleBigDecimals() =
  parse(String::toBigInteger, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerDoubleBigIntegers() =
  parse(String::toBigInteger, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseBigIntegerDoubleChars() =
  parse(String::toBigInteger, String::toDouble, String::toChar)

fun Iterable<String>.parseBigIntegerDoubleDoubles() =
  parse(String::toBigInteger, String::toDouble, String::toDouble)

fun Iterable<String>.parseBigIntegerDoubleInts() =
  parse(String::toBigInteger, String::toDouble, String::toInt)

fun Iterable<String>.parseBigIntegerDoubleLongs() =
  parse(String::toBigInteger, String::toDouble, String::toLong)

fun Iterable<String>.parseBigIntegerDoubleStrings() =
  parse(String::toBigInteger, String::toDouble, String::toString)

fun Iterable<String>.parseBigIntegerIntBigDecimals() =
  parse(String::toBigInteger, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerIntBigIntegers() =
  parse(String::toBigInteger, String::toInt, String::toBigInteger)

fun Iterable<String>.parseBigIntegerIntChars() =
  parse(String::toBigInteger, String::toInt, String::toChar)

fun Iterable<String>.parseBigIntegerIntDoubles() =
  parse(String::toBigInteger, String::toInt, String::toDouble)

fun Iterable<String>.parseBigIntegerIntInts() =
  parse(String::toBigInteger, String::toInt, String::toInt)

fun Iterable<String>.parseBigIntegerIntLongs() =
  parse(String::toBigInteger, String::toInt, String::toLong)

fun Iterable<String>.parseBigIntegerIntStrings() =
  parse(String::toBigInteger, String::toInt, String::toString)

fun Iterable<String>.parseBigIntegerLongBigDecimals() =
  parse(String::toBigInteger, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerLongBigIntegers() =
  parse(String::toBigInteger, String::toLong, String::toBigInteger)

fun Iterable<String>.parseBigIntegerLongChars() =
  parse(String::toBigInteger, String::toLong, String::toChar)

fun Iterable<String>.parseBigIntegerLongDoubles() =
  parse(String::toBigInteger, String::toLong, String::toDouble)

fun Iterable<String>.parseBigIntegerLongInts() =
  parse(String::toBigInteger, String::toLong, String::toInt)

fun Iterable<String>.parseBigIntegerLongLongs() =
  parse(String::toBigInteger, String::toLong, String::toLong)

fun Iterable<String>.parseBigIntegerLongStrings() =
  parse(String::toBigInteger, String::toLong, String::toString)

fun Iterable<String>.parseBigIntegerStringBigDecimals() =
  parse(String::toBigInteger, String::toString, String::toBigDecimal)

fun Iterable<String>.parseBigIntegerStringBigIntegers() =
  parse(String::toBigInteger, String::toString, String::toBigInteger)

fun Iterable<String>.parseBigIntegerStringChars() =
  parse(String::toBigInteger, String::toString, String::toChar)

fun Iterable<String>.parseBigIntegerStringDoubles() =
  parse(String::toBigInteger, String::toString, String::toDouble)

fun Iterable<String>.parseBigIntegerStringInts() =
  parse(String::toBigInteger, String::toString, String::toInt)

fun Iterable<String>.parseBigIntegerStringLongs() =
  parse(String::toBigInteger, String::toString, String::toLong)

fun Iterable<String>.parseBigIntegerStringStrings() =
  parse(String::toBigInteger, String::toString, String::toString)

fun Iterable<String>.parseCharBigDecimalBigDecimals() =
  parse(String::toChar, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseCharBigDecimalBigIntegers() =
  parse(String::toChar, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseCharBigDecimalChars() =
  parse(String::toChar, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseCharBigDecimalDoubles() =
  parse(String::toChar, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseCharBigDecimalInts() =
  parse(String::toChar, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseCharBigDecimalLongs() =
  parse(String::toChar, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseCharBigDecimalStrings() =
  parse(String::toChar, String::toBigDecimal, String::toString)

fun Iterable<String>.parseCharBigIntegerBigDecimals() =
  parse(String::toChar, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseCharBigIntegerBigIntegers() =
  parse(String::toChar, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseCharBigIntegerChars() =
  parse(String::toChar, String::toBigInteger, String::toChar)

fun Iterable<String>.parseCharBigIntegerDoubles() =
  parse(String::toChar, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseCharBigIntegerInts() =
  parse(String::toChar, String::toBigInteger, String::toInt)

fun Iterable<String>.parseCharBigIntegerLongs() =
  parse(String::toChar, String::toBigInteger, String::toLong)

fun Iterable<String>.parseCharBigIntegerStrings() =
  parse(String::toChar, String::toBigInteger, String::toString)

fun Iterable<String>.parseCharCharBigDecimals() =
  parse(String::toChar, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseCharCharBigIntegers() =
  parse(String::toChar, String::toChar, String::toBigInteger)

fun Iterable<String>.parseCharCharChars() =
  parse(String::toChar, String::toChar, String::toChar)

fun Iterable<String>.parseCharCharDoubles() =
  parse(String::toChar, String::toChar, String::toDouble)

fun Iterable<String>.parseCharCharInts() =
  parse(String::toChar, String::toChar, String::toInt)

fun Iterable<String>.parseCharCharLongs() =
  parse(String::toChar, String::toChar, String::toLong)

fun Iterable<String>.parseCharCharStrings() =
  parse(String::toChar, String::toChar, String::toString)

fun Iterable<String>.parseCharDoubleBigDecimals() =
  parse(String::toChar, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseCharDoubleBigIntegers() =
  parse(String::toChar, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseCharDoubleChars() =
  parse(String::toChar, String::toDouble, String::toChar)

fun Iterable<String>.parseCharDoubleDoubles() =
  parse(String::toChar, String::toDouble, String::toDouble)

fun Iterable<String>.parseCharDoubleInts() =
  parse(String::toChar, String::toDouble, String::toInt)

fun Iterable<String>.parseCharDoubleLongs() =
  parse(String::toChar, String::toDouble, String::toLong)

fun Iterable<String>.parseCharDoubleStrings() =
  parse(String::toChar, String::toDouble, String::toString)

fun Iterable<String>.parseCharIntBigDecimals() =
  parse(String::toChar, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseCharIntBigIntegers() =
  parse(String::toChar, String::toInt, String::toBigInteger)

fun Iterable<String>.parseCharIntChars() =
  parse(String::toChar, String::toInt, String::toChar)

fun Iterable<String>.parseCharIntDoubles() =
  parse(String::toChar, String::toInt, String::toDouble)

fun Iterable<String>.parseCharIntInts() =
  parse(String::toChar, String::toInt, String::toInt)

fun Iterable<String>.parseCharIntLongs() =
  parse(String::toChar, String::toInt, String::toLong)

fun Iterable<String>.parseCharIntStrings() =
  parse(String::toChar, String::toInt, String::toString)

fun Iterable<String>.parseCharLongBigDecimals() =
  parse(String::toChar, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseCharLongBigIntegers() =
  parse(String::toChar, String::toLong, String::toBigInteger)

fun Iterable<String>.parseCharLongChars() =
  parse(String::toChar, String::toLong, String::toChar)

fun Iterable<String>.parseCharLongDoubles() =
  parse(String::toChar, String::toLong, String::toDouble)

fun Iterable<String>.parseCharLongInts() =
  parse(String::toChar, String::toLong, String::toInt)

fun Iterable<String>.parseCharLongLongs() =
  parse(String::toChar, String::toLong, String::toLong)

fun Iterable<String>.parseCharLongStrings() =
  parse(String::toChar, String::toLong, String::toString)

fun Iterable<String>.parseCharStringBigDecimals() =
  parse(String::toChar, String::toString, String::toBigDecimal)

fun Iterable<String>.parseCharStringBigIntegers() =
  parse(String::toChar, String::toString, String::toBigInteger)

fun Iterable<String>.parseCharStringChars() =
  parse(String::toChar, String::toString, String::toChar)

fun Iterable<String>.parseCharStringDoubles() =
  parse(String::toChar, String::toString, String::toDouble)

fun Iterable<String>.parseCharStringInts() =
  parse(String::toChar, String::toString, String::toInt)

fun Iterable<String>.parseCharStringLongs() =
  parse(String::toChar, String::toString, String::toLong)

fun Iterable<String>.parseCharStringStrings() =
  parse(String::toChar, String::toString, String::toString)

fun Iterable<String>.parseDoubleBigDecimalBigDecimals() =
  parse(String::toDouble, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseDoubleBigDecimalBigIntegers() =
  parse(String::toDouble, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseDoubleBigDecimalChars() =
  parse(String::toDouble, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseDoubleBigDecimalDoubles() =
  parse(String::toDouble, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseDoubleBigDecimalInts() =
  parse(String::toDouble, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseDoubleBigDecimalLongs() =
  parse(String::toDouble, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseDoubleBigDecimalStrings() =
  parse(String::toDouble, String::toBigDecimal, String::toString)

fun Iterable<String>.parseDoubleBigIntegerBigDecimals() =
  parse(String::toDouble, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseDoubleBigIntegerBigIntegers() =
  parse(String::toDouble, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseDoubleBigIntegerChars() =
  parse(String::toDouble, String::toBigInteger, String::toChar)

fun Iterable<String>.parseDoubleBigIntegerDoubles() =
  parse(String::toDouble, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseDoubleBigIntegerInts() =
  parse(String::toDouble, String::toBigInteger, String::toInt)

fun Iterable<String>.parseDoubleBigIntegerLongs() =
  parse(String::toDouble, String::toBigInteger, String::toLong)

fun Iterable<String>.parseDoubleBigIntegerStrings() =
  parse(String::toDouble, String::toBigInteger, String::toString)

fun Iterable<String>.parseDoubleCharBigDecimals() =
  parse(String::toDouble, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseDoubleCharBigIntegers() =
  parse(String::toDouble, String::toChar, String::toBigInteger)

fun Iterable<String>.parseDoubleCharChars() =
  parse(String::toDouble, String::toChar, String::toChar)

fun Iterable<String>.parseDoubleCharDoubles() =
  parse(String::toDouble, String::toChar, String::toDouble)

fun Iterable<String>.parseDoubleCharInts() =
  parse(String::toDouble, String::toChar, String::toInt)

fun Iterable<String>.parseDoubleCharLongs() =
  parse(String::toDouble, String::toChar, String::toLong)

fun Iterable<String>.parseDoubleCharStrings() =
  parse(String::toDouble, String::toChar, String::toString)

fun Iterable<String>.parseDoubleDoubleBigDecimals() =
  parse(String::toDouble, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseDoubleDoubleBigIntegers() =
  parse(String::toDouble, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseDoubleDoubleChars() =
  parse(String::toDouble, String::toDouble, String::toChar)

fun Iterable<String>.parseDoubleDoubleDoubles() =
  parse(String::toDouble, String::toDouble, String::toDouble)

fun Iterable<String>.parseDoubleDoubleInts() =
  parse(String::toDouble, String::toDouble, String::toInt)

fun Iterable<String>.parseDoubleDoubleLongs() =
  parse(String::toDouble, String::toDouble, String::toLong)

fun Iterable<String>.parseDoubleDoubleStrings() =
  parse(String::toDouble, String::toDouble, String::toString)

fun Iterable<String>.parseDoubleIntBigDecimals() =
  parse(String::toDouble, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseDoubleIntBigIntegers() =
  parse(String::toDouble, String::toInt, String::toBigInteger)

fun Iterable<String>.parseDoubleIntChars() =
  parse(String::toDouble, String::toInt, String::toChar)

fun Iterable<String>.parseDoubleIntDoubles() =
  parse(String::toDouble, String::toInt, String::toDouble)

fun Iterable<String>.parseDoubleIntInts() =
  parse(String::toDouble, String::toInt, String::toInt)

fun Iterable<String>.parseDoubleIntLongs() =
  parse(String::toDouble, String::toInt, String::toLong)

fun Iterable<String>.parseDoubleIntStrings() =
  parse(String::toDouble, String::toInt, String::toString)

fun Iterable<String>.parseDoubleLongBigDecimals() =
  parse(String::toDouble, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseDoubleLongBigIntegers() =
  parse(String::toDouble, String::toLong, String::toBigInteger)

fun Iterable<String>.parseDoubleLongChars() =
  parse(String::toDouble, String::toLong, String::toChar)

fun Iterable<String>.parseDoubleLongDoubles() =
  parse(String::toDouble, String::toLong, String::toDouble)

fun Iterable<String>.parseDoubleLongInts() =
  parse(String::toDouble, String::toLong, String::toInt)

fun Iterable<String>.parseDoubleLongLongs() =
  parse(String::toDouble, String::toLong, String::toLong)

fun Iterable<String>.parseDoubleLongStrings() =
  parse(String::toDouble, String::toLong, String::toString)

fun Iterable<String>.parseDoubleStringBigDecimals() =
  parse(String::toDouble, String::toString, String::toBigDecimal)

fun Iterable<String>.parseDoubleStringBigIntegers() =
  parse(String::toDouble, String::toString, String::toBigInteger)

fun Iterable<String>.parseDoubleStringChars() =
  parse(String::toDouble, String::toString, String::toChar)

fun Iterable<String>.parseDoubleStringDoubles() =
  parse(String::toDouble, String::toString, String::toDouble)

fun Iterable<String>.parseDoubleStringInts() =
  parse(String::toDouble, String::toString, String::toInt)

fun Iterable<String>.parseDoubleStringLongs() =
  parse(String::toDouble, String::toString, String::toLong)

fun Iterable<String>.parseDoubleStringStrings() =
  parse(String::toDouble, String::toString, String::toString)

fun Iterable<String>.parseIntBigDecimalBigDecimals() =
  parse(String::toInt, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseIntBigDecimalBigIntegers() =
  parse(String::toInt, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseIntBigDecimalChars() =
  parse(String::toInt, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseIntBigDecimalDoubles() =
  parse(String::toInt, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseIntBigDecimalInts() =
  parse(String::toInt, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseIntBigDecimalLongs() =
  parse(String::toInt, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseIntBigDecimalStrings() =
  parse(String::toInt, String::toBigDecimal, String::toString)

fun Iterable<String>.parseIntBigIntegerBigDecimals() =
  parse(String::toInt, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseIntBigIntegerBigIntegers() =
  parse(String::toInt, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseIntBigIntegerChars() =
  parse(String::toInt, String::toBigInteger, String::toChar)

fun Iterable<String>.parseIntBigIntegerDoubles() =
  parse(String::toInt, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseIntBigIntegerInts() =
  parse(String::toInt, String::toBigInteger, String::toInt)

fun Iterable<String>.parseIntBigIntegerLongs() =
  parse(String::toInt, String::toBigInteger, String::toLong)

fun Iterable<String>.parseIntBigIntegerStrings() =
  parse(String::toInt, String::toBigInteger, String::toString)

fun Iterable<String>.parseIntCharBigDecimals() =
  parse(String::toInt, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseIntCharBigIntegers() =
  parse(String::toInt, String::toChar, String::toBigInteger)

fun Iterable<String>.parseIntCharChars() =
  parse(String::toInt, String::toChar, String::toChar)

fun Iterable<String>.parseIntCharDoubles() =
  parse(String::toInt, String::toChar, String::toDouble)

fun Iterable<String>.parseIntCharInts() =
  parse(String::toInt, String::toChar, String::toInt)

fun Iterable<String>.parseIntCharLongs() =
  parse(String::toInt, String::toChar, String::toLong)

fun Iterable<String>.parseIntCharStrings() =
  parse(String::toInt, String::toChar, String::toString)

fun Iterable<String>.parseIntDoubleBigDecimals() =
  parse(String::toInt, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseIntDoubleBigIntegers() =
  parse(String::toInt, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseIntDoubleChars() =
  parse(String::toInt, String::toDouble, String::toChar)

fun Iterable<String>.parseIntDoubleDoubles() =
  parse(String::toInt, String::toDouble, String::toDouble)

fun Iterable<String>.parseIntDoubleInts() =
  parse(String::toInt, String::toDouble, String::toInt)

fun Iterable<String>.parseIntDoubleLongs() =
  parse(String::toInt, String::toDouble, String::toLong)

fun Iterable<String>.parseIntDoubleStrings() =
  parse(String::toInt, String::toDouble, String::toString)

fun Iterable<String>.parseIntIntBigDecimals() =
  parse(String::toInt, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseIntIntBigIntegers() =
  parse(String::toInt, String::toInt, String::toBigInteger)

fun Iterable<String>.parseIntIntChars() =
  parse(String::toInt, String::toInt, String::toChar)

fun Iterable<String>.parseIntIntDoubles() =
  parse(String::toInt, String::toInt, String::toDouble)

fun Iterable<String>.parseIntIntInts() =
  parse(String::toInt, String::toInt, String::toInt)

fun Iterable<String>.parseIntIntLongs() =
  parse(String::toInt, String::toInt, String::toLong)

fun Iterable<String>.parseIntIntStrings() =
  parse(String::toInt, String::toInt, String::toString)

fun Iterable<String>.parseIntLongBigDecimals() =
  parse(String::toInt, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseIntLongBigIntegers() =
  parse(String::toInt, String::toLong, String::toBigInteger)

fun Iterable<String>.parseIntLongChars() =
  parse(String::toInt, String::toLong, String::toChar)

fun Iterable<String>.parseIntLongDoubles() =
  parse(String::toInt, String::toLong, String::toDouble)

fun Iterable<String>.parseIntLongInts() =
  parse(String::toInt, String::toLong, String::toInt)

fun Iterable<String>.parseIntLongLongs() =
  parse(String::toInt, String::toLong, String::toLong)

fun Iterable<String>.parseIntLongStrings() =
  parse(String::toInt, String::toLong, String::toString)

fun Iterable<String>.parseIntStringBigDecimals() =
  parse(String::toInt, String::toString, String::toBigDecimal)

fun Iterable<String>.parseIntStringBigIntegers() =
  parse(String::toInt, String::toString, String::toBigInteger)

fun Iterable<String>.parseIntStringChars() =
  parse(String::toInt, String::toString, String::toChar)

fun Iterable<String>.parseIntStringDoubles() =
  parse(String::toInt, String::toString, String::toDouble)

fun Iterable<String>.parseIntStringInts() =
  parse(String::toInt, String::toString, String::toInt)

fun Iterable<String>.parseIntStringLongs() =
  parse(String::toInt, String::toString, String::toLong)

fun Iterable<String>.parseIntStringStrings() =
  parse(String::toInt, String::toString, String::toString)

fun Iterable<String>.parseLongBigDecimalBigDecimals() =
  parse(String::toLong, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseLongBigDecimalBigIntegers() =
  parse(String::toLong, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseLongBigDecimalChars() =
  parse(String::toLong, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseLongBigDecimalDoubles() =
  parse(String::toLong, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseLongBigDecimalInts() =
  parse(String::toLong, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseLongBigDecimalLongs() =
  parse(String::toLong, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseLongBigDecimalStrings() =
  parse(String::toLong, String::toBigDecimal, String::toString)

fun Iterable<String>.parseLongBigIntegerBigDecimals() =
  parse(String::toLong, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseLongBigIntegerBigIntegers() =
  parse(String::toLong, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseLongBigIntegerChars() =
  parse(String::toLong, String::toBigInteger, String::toChar)

fun Iterable<String>.parseLongBigIntegerDoubles() =
  parse(String::toLong, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseLongBigIntegerInts() =
  parse(String::toLong, String::toBigInteger, String::toInt)

fun Iterable<String>.parseLongBigIntegerLongs() =
  parse(String::toLong, String::toBigInteger, String::toLong)

fun Iterable<String>.parseLongBigIntegerStrings() =
  parse(String::toLong, String::toBigInteger, String::toString)

fun Iterable<String>.parseLongCharBigDecimals() =
  parse(String::toLong, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseLongCharBigIntegers() =
  parse(String::toLong, String::toChar, String::toBigInteger)

fun Iterable<String>.parseLongCharChars() =
  parse(String::toLong, String::toChar, String::toChar)

fun Iterable<String>.parseLongCharDoubles() =
  parse(String::toLong, String::toChar, String::toDouble)

fun Iterable<String>.parseLongCharInts() =
  parse(String::toLong, String::toChar, String::toInt)

fun Iterable<String>.parseLongCharLongs() =
  parse(String::toLong, String::toChar, String::toLong)

fun Iterable<String>.parseLongCharStrings() =
  parse(String::toLong, String::toChar, String::toString)

fun Iterable<String>.parseLongDoubleBigDecimals() =
  parse(String::toLong, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseLongDoubleBigIntegers() =
  parse(String::toLong, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseLongDoubleChars() =
  parse(String::toLong, String::toDouble, String::toChar)

fun Iterable<String>.parseLongDoubleDoubles() =
  parse(String::toLong, String::toDouble, String::toDouble)

fun Iterable<String>.parseLongDoubleInts() =
  parse(String::toLong, String::toDouble, String::toInt)

fun Iterable<String>.parseLongDoubleLongs() =
  parse(String::toLong, String::toDouble, String::toLong)

fun Iterable<String>.parseLongDoubleStrings() =
  parse(String::toLong, String::toDouble, String::toString)

fun Iterable<String>.parseLongIntBigDecimals() =
  parse(String::toLong, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseLongIntBigIntegers() =
  parse(String::toLong, String::toInt, String::toBigInteger)

fun Iterable<String>.parseLongIntChars() =
  parse(String::toLong, String::toInt, String::toChar)

fun Iterable<String>.parseLongIntDoubles() =
  parse(String::toLong, String::toInt, String::toDouble)

fun Iterable<String>.parseLongIntInts() =
  parse(String::toLong, String::toInt, String::toInt)

fun Iterable<String>.parseLongIntLongs() =
  parse(String::toLong, String::toInt, String::toLong)

fun Iterable<String>.parseLongIntStrings() =
  parse(String::toLong, String::toInt, String::toString)

fun Iterable<String>.parseLongLongBigDecimals() =
  parse(String::toLong, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseLongLongBigIntegers() =
  parse(String::toLong, String::toLong, String::toBigInteger)

fun Iterable<String>.parseLongLongChars() =
  parse(String::toLong, String::toLong, String::toChar)

fun Iterable<String>.parseLongLongDoubles() =
  parse(String::toLong, String::toLong, String::toDouble)

fun Iterable<String>.parseLongLongInts() =
  parse(String::toLong, String::toLong, String::toInt)

fun Iterable<String>.parseLongLongLongs() =
  parse(String::toLong, String::toLong, String::toLong)

fun Iterable<String>.parseLongLongStrings() =
  parse(String::toLong, String::toLong, String::toString)

fun Iterable<String>.parseLongStringBigDecimals() =
  parse(String::toLong, String::toString, String::toBigDecimal)

fun Iterable<String>.parseLongStringBigIntegers() =
  parse(String::toLong, String::toString, String::toBigInteger)

fun Iterable<String>.parseLongStringChars() =
  parse(String::toLong, String::toString, String::toChar)

fun Iterable<String>.parseLongStringDoubles() =
  parse(String::toLong, String::toString, String::toDouble)

fun Iterable<String>.parseLongStringInts() =
  parse(String::toLong, String::toString, String::toInt)

fun Iterable<String>.parseLongStringLongs() =
  parse(String::toLong, String::toString, String::toLong)

fun Iterable<String>.parseLongStringStrings() =
  parse(String::toLong, String::toString, String::toString)

fun Iterable<String>.parseStringBigDecimalBigDecimals() =
  parse(String::toString, String::toBigDecimal, String::toBigDecimal)

fun Iterable<String>.parseStringBigDecimalBigIntegers() =
  parse(String::toString, String::toBigDecimal, String::toBigInteger)

fun Iterable<String>.parseStringBigDecimalChars() =
  parse(String::toString, String::toBigDecimal, String::toChar)

fun Iterable<String>.parseStringBigDecimalDoubles() =
  parse(String::toString, String::toBigDecimal, String::toDouble)

fun Iterable<String>.parseStringBigDecimalInts() =
  parse(String::toString, String::toBigDecimal, String::toInt)

fun Iterable<String>.parseStringBigDecimalLongs() =
  parse(String::toString, String::toBigDecimal, String::toLong)

fun Iterable<String>.parseStringBigDecimalStrings() =
  parse(String::toString, String::toBigDecimal, String::toString)

fun Iterable<String>.parseStringBigIntegerBigDecimals() =
  parse(String::toString, String::toBigInteger, String::toBigDecimal)

fun Iterable<String>.parseStringBigIntegerBigIntegers() =
  parse(String::toString, String::toBigInteger, String::toBigInteger)

fun Iterable<String>.parseStringBigIntegerChars() =
  parse(String::toString, String::toBigInteger, String::toChar)

fun Iterable<String>.parseStringBigIntegerDoubles() =
  parse(String::toString, String::toBigInteger, String::toDouble)

fun Iterable<String>.parseStringBigIntegerInts() =
  parse(String::toString, String::toBigInteger, String::toInt)

fun Iterable<String>.parseStringBigIntegerLongs() =
  parse(String::toString, String::toBigInteger, String::toLong)

fun Iterable<String>.parseStringBigIntegerStrings() =
  parse(String::toString, String::toBigInteger, String::toString)

fun Iterable<String>.parseStringCharBigDecimals() =
  parse(String::toString, String::toChar, String::toBigDecimal)

fun Iterable<String>.parseStringCharBigIntegers() =
  parse(String::toString, String::toChar, String::toBigInteger)

fun Iterable<String>.parseStringCharChars() =
  parse(String::toString, String::toChar, String::toChar)

fun Iterable<String>.parseStringCharDoubles() =
  parse(String::toString, String::toChar, String::toDouble)

fun Iterable<String>.parseStringCharInts() =
  parse(String::toString, String::toChar, String::toInt)

fun Iterable<String>.parseStringCharLongs() =
  parse(String::toString, String::toChar, String::toLong)

fun Iterable<String>.parseStringCharStrings() =
  parse(String::toString, String::toChar, String::toString)

fun Iterable<String>.parseStringDoubleBigDecimals() =
  parse(String::toString, String::toDouble, String::toBigDecimal)

fun Iterable<String>.parseStringDoubleBigIntegers() =
  parse(String::toString, String::toDouble, String::toBigInteger)

fun Iterable<String>.parseStringDoubleChars() =
  parse(String::toString, String::toDouble, String::toChar)

fun Iterable<String>.parseStringDoubleDoubles() =
  parse(String::toString, String::toDouble, String::toDouble)

fun Iterable<String>.parseStringDoubleInts() =
  parse(String::toString, String::toDouble, String::toInt)

fun Iterable<String>.parseStringDoubleLongs() =
  parse(String::toString, String::toDouble, String::toLong)

fun Iterable<String>.parseStringDoubleStrings() =
  parse(String::toString, String::toDouble, String::toString)

fun Iterable<String>.parseStringIntBigDecimals() =
  parse(String::toString, String::toInt, String::toBigDecimal)

fun Iterable<String>.parseStringIntBigIntegers() =
  parse(String::toString, String::toInt, String::toBigInteger)

fun Iterable<String>.parseStringIntChars() =
  parse(String::toString, String::toInt, String::toChar)

fun Iterable<String>.parseStringIntDoubles() =
  parse(String::toString, String::toInt, String::toDouble)

fun Iterable<String>.parseStringIntInts() =
  parse(String::toString, String::toInt, String::toInt)

fun Iterable<String>.parseStringIntLongs() =
  parse(String::toString, String::toInt, String::toLong)

fun Iterable<String>.parseStringIntStrings() =
  parse(String::toString, String::toInt, String::toString)

fun Iterable<String>.parseStringLongBigDecimals() =
  parse(String::toString, String::toLong, String::toBigDecimal)

fun Iterable<String>.parseStringLongBigIntegers() =
  parse(String::toString, String::toLong, String::toBigInteger)

fun Iterable<String>.parseStringLongChars() =
  parse(String::toString, String::toLong, String::toChar)

fun Iterable<String>.parseStringLongDoubles() =
  parse(String::toString, String::toLong, String::toDouble)

fun Iterable<String>.parseStringLongInts() =
  parse(String::toString, String::toLong, String::toInt)

fun Iterable<String>.parseStringLongLongs() =
  parse(String::toString, String::toLong, String::toLong)

fun Iterable<String>.parseStringLongStrings() =
  parse(String::toString, String::toLong, String::toString)

fun Iterable<String>.parseStringStringBigDecimals() =
  parse(String::toString, String::toString, String::toBigDecimal)

fun Iterable<String>.parseStringStringBigIntegers() =
  parse(String::toString, String::toString, String::toBigInteger)

fun Iterable<String>.parseStringStringChars() =
  parse(String::toString, String::toString, String::toChar)

fun Iterable<String>.parseStringStringDoubles() =
  parse(String::toString, String::toString, String::toDouble)

fun Iterable<String>.parseStringStringInts() =
  parse(String::toString, String::toString, String::toInt)

fun Iterable<String>.parseStringStringLongs() =
  parse(String::toString, String::toString, String::toLong)

fun Iterable<String>.parseStringStringStrings() =
  parse(String::toString, String::toString, String::toString)

fun String.parseBigDecimalLists() = parseLists(String::toBigDecimal)
fun String.parseBigIntegerLists() = parseLists(String::toBigInteger)
fun String.parseCharLists() = parseLists(String::toChar)
fun String.parseDoubleLists() = parseLists(String::toDouble)
fun String.parseIntLists() = parseLists(String::toInt)
fun String.parseLongLists() = parseLists(String::toLong)
fun String.parseStringLists() = parseLists(String::toString)

fun Iterable<String>.parseBigDecimalLists() = parseLists(String::toBigDecimal)
fun Iterable<String>.parseBigIntegerLists() = parseLists(String::toBigInteger)
fun Iterable<String>.parseCharLists() = parseLists(String::toChar)
fun Iterable<String>.parseDoubleLists() = parseLists(String::toDouble)
fun Iterable<String>.parseIntLists() = parseLists(String::toInt)
fun Iterable<String>.parseLongLists() = parseLists(String::toLong)
fun Iterable<String>.parseStringLists() = parseLists(String::toString)

fun String.parseBigDecimalGrid() = Grid(parseLists(String::toBigDecimal))
fun String.parseBigIntegerGrid() = Grid(parseLists(String::toBigInteger))
fun String.parseCharGrid() = Grid(parseLists(String::toChar))
fun String.parseDoubleGrid() = Grid(parseLists(String::toDouble))
fun String.parseIntGrid() = Grid(parseLists(String::toInt))
fun String.parseLongGrid() = Grid(parseLists(String::toLong))
fun String.parseStringGrid() = Grid(parseLists(String::toString))

fun Iterable<String>.parseBigDecimalGrid() = Grid(parseLists(String::toBigDecimal))
fun Iterable<String>.parseBigIntegerGrid() = Grid(parseLists(String::toBigInteger))
fun Iterable<String>.parseCharGrid() = Grid(parseLists(String::toChar))
fun Iterable<String>.parseDoubleGrid() = Grid(parseLists(String::toDouble))
fun Iterable<String>.parseIntGrid() = Grid(parseLists(String::toInt))
fun Iterable<String>.parseLongGrid() = Grid(parseLists(String::toLong))
fun Iterable<String>.parseStringGrid() = Grid(parseLists(String::toString))

fun String.parseDenseBigDecimalGrid() = lines().parseDenseBigDecimalGrid()
fun String.parseDenseBigIntegerGrid() = lines().parseDenseBigIntegerGrid()
fun String.parseDenseCharGrid() = lines().parseDenseCharGrid()
fun String.parseDenseDoubleGrid() = lines().parseDenseDoubleGrid()
fun String.parseDenseIntGrid() = lines().parseDenseIntGrid()
fun String.parseDenseLongGrid() = lines().parseDenseLongGrid()
fun String.parseDenseStringGrid() = lines().parseDenseStringGrid()

fun Iterable<String>.parseDenseBigDecimalGrid() = Grid(map { it.toList().map(Char::toString).map(String::toBigDecimal) })
fun Iterable<String>.parseDenseBigIntegerGrid() = Grid(map { it.toList().map(Char::toString).map(String::toBigInteger) })
fun Iterable<String>.parseDenseCharGrid(): Grid<Char> {
  val numColumns = maxOf { it.length }
  return Grid(map { it.padEnd(numColumns, ' ').toList() })
}
fun Iterable<String>.parseDenseDoubleGrid() = Grid(map { it.toList().map(Char::toString).map(String::toDouble) })
fun Iterable<String>.parseDenseIntGrid() = Grid(map { it.toList().map(Char::toString).map(String::toInt) })
fun Iterable<String>.parseDenseLongGrid() = Grid(map { it.toList().map(Char::toString).map(String::toLong) })
fun Iterable<String>.parseDenseStringGrid() = Grid(map { it.toList().map(Char::toString).map(String::toString) })

fun String.parseNullableBigDecimalGrid() = parseBigDecimalGrid() as Grid<BigDecimal?>
fun String.parseNullableBigIntegerGrid() = parseBigIntegerGrid() as Grid<BigInteger?>
fun String.parseNullableCharGrid() = parseCharGrid() as Grid<Char?>
fun String.parseNullableDoubleGrid() = parseDoubleGrid() as Grid<Double?>
fun String.parseNullableIntGrid() = parseIntGrid() as Grid<Int?>
fun String.parseNullableLongGrid() = parseLongGrid() as Grid<Long?>
fun String.parseNullableStringGrid() = parseStringGrid() as Grid<String?>

fun Iterable<String>.parseNullableBigDecimalGrid() = parseBigDecimalGrid() as Grid<BigDecimal?>
fun Iterable<String>.parseNullableBigIntegerGrid() = parseBigIntegerGrid() as Grid<BigInteger?>
fun Iterable<String>.parseNullableCharGrid() = parseCharGrid() as Grid<Char?>
fun Iterable<String>.parseNullableDoubleGrid() = parseDoubleGrid() as Grid<Double?>
fun Iterable<String>.parseNullableIntGrid() = parseIntGrid() as Grid<Int?>
fun Iterable<String>.parseNullableLongGrid() = parseLongGrid() as Grid<Long?>
fun Iterable<String>.parseNullableStringGrid() = parseStringGrid() as Grid<String?>

fun String.parseNullableDenseBigDecimalGrid() = parseDenseBigDecimalGrid() as Grid<BigDecimal?>
fun String.parseNullableDenseBigIntegerGrid() = parseDenseBigIntegerGrid() as Grid<BigInteger?>
fun String.parseNullableDenseCharGrid() = parseDenseCharGrid() as Grid<Char?>
fun String.parseNullableDenseDoubleGrid() = parseDenseDoubleGrid() as Grid<Double?>
fun String.parseNullableDenseIntGrid() = parseDenseIntGrid() as Grid<Int?>
fun String.parseNullableDenseLongGrid() = parseDenseLongGrid() as Grid<Long?>
fun String.parseNullableDenseStringGrid() = parseDenseStringGrid() as Grid<String?>

fun Iterable<String>.parseNullableDenseBigDecimalGrid() = parseDenseBigDecimalGrid() as Grid<BigDecimal?>
fun Iterable<String>.parseNullableDenseBigIntegerGrid() = parseDenseBigIntegerGrid() as Grid<BigInteger?>
fun Iterable<String>.parseNullableDenseCharGrid() = parseDenseCharGrid() as Grid<Char?>
fun Iterable<String>.parseNullableDenseDoubleGrid() = parseDenseDoubleGrid() as Grid<Double?>
fun Iterable<String>.parseNullableDenseIntGrid() = parseDenseIntGrid() as Grid<Int?>
fun Iterable<String>.parseNullableDenseLongGrid() = parseDenseLongGrid() as Grid<Long?>
fun Iterable<String>.parseNullableDenseStringGrid() = parseDenseStringGrid() as Grid<String?>

fun String.parseNestedList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
  parser: (String) -> Any,
): List<Any> {
  val stack = mutableListOf(mutableListOf<Any>())
  var string = ""
  for (c in this) {
    when (c) {
      start -> {
        stack.add(mutableListOf())
      }
      end -> {
        if (string.isNotEmpty()) {
          stack.last().add(parser(string))
          string = ""
        }
        stack.removeLast().also { stack.last().add(it) }
      }
      separator -> {
        if (string.isNotEmpty()) {
          stack.last().add(parser(string))
          string = ""
        }
      }
      else -> {
        string += c
      }
    }
  }
  return stack.single()
}

fun String.parseNestedBigDecimalList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toBigDecimal)

fun String.parseNestedBigIntegerList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toBigInteger)

fun String.parseNestedCharList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toChar)

fun String.parseNestedDoubleList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toDouble)

fun String.parseNestedIntList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toInt)

fun String.parseNestedLongList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toLong)

fun String.parseNestedStringList(
  start: Char = '[',
  end: Char = ']',
  separator: Char = ',',
): List<Any> = parseNestedList(start, end, separator, String::toString)
