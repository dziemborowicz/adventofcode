@file:Suppress("UNCHECKED_CAST")

import java.math.BigDecimal
import java.math.BigInteger

fun String.toChar(): Char {
  require(length == 1) { "Must contain exactly one character." }
  return first()
}

fun String.extractBigIntegers(): List<BigInteger> {
  return Regex("""((?<!\d)-)?\d+""").findAll(this).map { it.value.toBigInteger() }.toList()
}

fun String.extractInts(): List<Int> {
  return Regex("""((?<!\d)-)?\d+""").findAll(this).map { it.value.toInt() }.toList()
}

fun String.extractLongs(): List<Long> {
  return Regex("""((?<!\d)-)?\d+""").findAll(this).map { it.value.toLong() }.toList()
}

fun String.extractRationals(): List<Rational> {
  return Regex("""((?<!\d)-)?\d+(/\d+)?""").findAll(this).map { it.value.toRational() }.toList()
}

fun String.extractBigIntegerLists(): List<List<BigInteger>> = lines().extractBigIntegerLists()

fun String.extractIntLists(): List<List<Int>> = lines().extractIntLists()

fun String.extractLongLists(): List<List<Long>> = lines().extractLongLists()

fun String.extractRationalLists(): List<List<Rational>> = lines().extractRationalLists()

fun Iterable<String>.extractBigIntegerLists(): List<List<BigInteger>> =
  map { it.extractBigIntegers() }

fun Iterable<String>.extractIntLists(): List<List<Int>> = map { it.extractInts() }

fun Iterable<String>.extractLongLists(): List<List<Long>> = map { it.extractLongs() }

fun Iterable<String>.extractRationalLists(): List<List<Rational>> = map { it.extractRationals() }

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
fun String.parseRationals() = parse(String::toRational)
fun String.parseStrings() = parse(String::toString)

fun Iterable<String>.parseBigDecimals() = parse(String::toBigDecimal)
fun Iterable<String>.parseBigIntegers() = parse(String::toBigInteger)
fun Iterable<String>.parseChars() = parse(String::toChar)
fun Iterable<String>.parseDoubles() = parse(String::toDouble)
fun Iterable<String>.parseInts() = parse(String::toInt)
fun Iterable<String>.parseLongs() = parse(String::toLong)
fun Iterable<String>.parseRationals() = parse(String::toRational)
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
