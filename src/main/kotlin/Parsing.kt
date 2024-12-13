@file:Suppress("UNCHECKED_CAST")

import java.math.BigDecimal
import java.math.BigInteger

fun String.toChar(): Char = single()

fun String.extractStrings(): List<String> {
  return Regex("""[^\s,-]+""").findAll(this).map { it.value }.toList()
}

fun String.extractChars(): List<Char> {
  return Regex("""[^\s,-]""").findAll(this).map { it.value.single() }.toList()
}

fun String.extractDoubles(): List<Double> {
  return Regex("""((?<!\d)-)?\d+(\.\d+)?([eE][+-]?\d+)?""").findAll(this)
    .map { it.value.toDouble() }.toList()
}

fun String.extractBigDecimals(): List<BigDecimal> {
  return Regex("""((?<!\d)-)?\d+(\.\d+)?([eE][+-]?\d+)?""").findAll(this)
    .map { it.value.toBigDecimal() }.toList()
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

fun <A> String.extract(a: (String) -> A): A = a(extractStrings().single())

fun <A, B> String.extract(a: (String) -> A, b: (String) -> B): Pair<A, B> =
  extractStrings().also { check(it.size == 2) }.let { a(it.first()) to b(it.second()) }

fun <A, B, C> String.extract(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
): Triple<A, B, C> {
  return extractStrings().also { check(it.size == 3) }
    .let { Triple(a(it.first()), b(it.second()), c(it.third())) }
}

fun <A, B, C, D> String.extract(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
): Quadruple<A, B, C, D> {
  return extractStrings().also { check(it.size == 4) }
    .let { Quadruple(a(it.first()), b(it.second()), c(it.third()), d(it.fourth())) }
}

fun <A, B, C, D, E> String.extract(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
  e: (String) -> E,
): Quintuple<A, B, C, D, E> {
  return extractStrings().also { check(it.size == 5) }
    .let { Quintuple(a(it.first()), b(it.second()), c(it.third()), d(it.fourth()), e(it.fifth())) }
}

fun String.extractStringLists(): List<List<String>> = lines().extractStringLists()
fun String.extractCharLists(): List<List<Char>> = lines().extractCharLists()
fun String.extractDoubleLists(): List<List<Double>> = lines().extractDoubleLists()
fun String.extractBigDecimalLists(): List<List<BigDecimal>> = lines().extractBigDecimalLists()
fun String.extractBigIntegerLists(): List<List<BigInteger>> = lines().extractBigIntegerLists()
fun String.extractIntLists(): List<List<Int>> = lines().extractIntLists()
fun String.extractLongLists(): List<List<Long>> = lines().extractLongLists()
fun String.extractRationalLists(): List<List<Rational>> = lines().extractRationalLists()

fun <A> String.extractList(a: (String) -> A): List<A> = lines().extractList(a)

fun <A, B> String.extractList(
  a: (String) -> A,
  b: (String) -> B,
): List<Pair<A, B>> = lines().extractList(a, b)

fun <A, B, C> String.extractList(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
): List<Triple<A, B, C>> {
  return lines().extractList(a, b, c)
}

fun <A, B, C, D> String.extractList(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
): List<Quadruple<A, B, C, D>> {
  return lines().extractList(a, b, c, d)
}

fun <A, B, C, D, E> String.extractList(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
  e: (String) -> E,
): List<Quintuple<A, B, C, D, E>> {
  return lines().extractList(a, b, c, d, e)
}

fun Iterable<String>.extractStrings(): List<String> = extractStringLists().flatten()
fun Iterable<String>.extractChars(): List<Char> = extractCharLists().flatten()
fun Iterable<String>.extractDoubles(): List<Double> = extractDoubleLists().flatten()
fun Iterable<String>.extractBigDecimals(): List<BigDecimal> = extractBigDecimalLists().flatten()
fun Iterable<String>.extractBigIntegers(): List<BigInteger> = extractBigIntegerLists().flatten()
fun Iterable<String>.extractInts(): List<Int> = extractIntLists().flatten()
fun Iterable<String>.extractLongs(): List<Long> = extractLongLists().flatten()
fun Iterable<String>.extractRationals(): List<Rational> = extractRationalLists().flatten()

fun <A> Iterable<String>.extract(a: (String) -> A): A = extractList(a).single()

fun <A, B> Iterable<String>.extract(
  a: (String) -> A,
  b: (String) -> B,
): Pair<A, B> = extractList(a, b).single()

fun <A, B, C> Iterable<String>.extract(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
): Triple<A, B, C> {
  return extractList(a, b, c).single()
}

fun <A, B, C, D> Iterable<String>.extract(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
): Quadruple<A, B, C, D> {
  return extractList(a, b, c, d).single()
}

fun <A, B, C, D, E> Iterable<String>.extract(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
  e: (String) -> E,
): Quintuple<A, B, C, D, E> {
  return extractList(a, b, c, d, e).single()
}

fun Iterable<String>.extractStringLists(): List<List<String>> = map { it.extractStrings() }
fun Iterable<String>.extractCharLists(): List<List<Char>> = map { it.extractChars() }
fun Iterable<String>.extractDoubleLists(): List<List<Double>> = map { it.extractDoubles() }
fun Iterable<String>.extractBigDecimalLists(): List<List<BigDecimal>> =
  map { it.extractBigDecimals() }
fun Iterable<String>.extractBigIntegerLists(): List<List<BigInteger>> =
  map { it.extractBigIntegers() }
fun Iterable<String>.extractIntLists(): List<List<Int>> = map { it.extractInts() }
fun Iterable<String>.extractLongLists(): List<List<Long>> = map { it.extractLongs() }
fun Iterable<String>.extractRationalLists(): List<List<Rational>> = map { it.extractRationals() }

fun <A> Iterable<String>.extractList(a: (String) -> A): List<A> = map { it.extract(a) }

fun <A, B> Iterable<String>.extractList(
  a: (String) -> A,
  b: (String) -> B,
): List<Pair<A, B>> = map { it.extract(a, b) }

fun <A, B, C> Iterable<String>.extractList(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
): List<Triple<A, B, C>> {
  return map { it.extract(a, b, c) }
}

fun <A, B, C, D> Iterable<String>.extractList(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
): List<Quadruple<A, B, C, D>> {
  return map { it.extract(a, b, c, d) }
}

fun <A, B, C, D, E> Iterable<String>.extractList(
  a: (String) -> A,
  b: (String) -> B,
  c: (String) -> C,
  d: (String) -> D,
  e: (String) -> E,
): List<Quintuple<A, B, C, D, E>> {
  return map { it.extract(a, b, c, d, e) }
}

fun String.parseBigDecimalGrid() = Grid(extractBigDecimalLists())
fun String.parseBigIntegerGrid() = Grid(extractBigIntegerLists())
fun String.parseCharGrid() = Grid(extractCharLists())
fun String.parseDoubleGrid() = Grid(extractDoubleLists())
fun String.parseIntGrid() = Grid(extractIntLists())
fun String.parseLongGrid() = Grid(extractLongLists())
fun String.parseRationalGrid() = Grid(extractRationalLists())
fun String.parseStringGrid() = Grid(extractStringLists())

fun Iterable<String>.parseBigDecimalGrid() = Grid(extractBigDecimalLists())
fun Iterable<String>.parseBigIntegerGrid() = Grid(extractBigIntegerLists())
fun Iterable<String>.parseCharGrid() = Grid(extractCharLists())
fun Iterable<String>.parseDoubleGrid() = Grid(extractDoubleLists())
fun Iterable<String>.parseIntGrid() = Grid(extractIntLists())
fun Iterable<String>.parseLongGrid() = Grid(extractLongLists())
fun Iterable<String>.parseRationalGrid() = Grid(extractRationalLists())
fun Iterable<String>.parseStringGrid() = Grid(extractStringLists())

fun String.parseDenseBigDecimalGrid() = lines().parseDenseBigDecimalGrid()
fun String.parseDenseBigIntegerGrid() = lines().parseDenseBigIntegerGrid()
fun String.parseDenseCharGrid() = lines().parseDenseCharGrid()
fun String.parseDenseDoubleGrid() = lines().parseDenseDoubleGrid()
fun String.parseDenseIntGrid() = lines().parseDenseIntGrid()
fun String.parseDenseLongGrid() = lines().parseDenseLongGrid()
fun String.parseDenseRationalGrid() = lines().parseDenseRationalGrid()
fun String.parseDenseStringGrid() = lines().parseDenseStringGrid()

fun Iterable<String>.parseDenseBigDecimalGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toBigDecimal) })
fun Iterable<String>.parseDenseBigIntegerGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toBigInteger) })
fun Iterable<String>.parseDenseCharGrid(): Grid<Char> {
  val numColumns = maxOf { it.length }
  return Grid(map { it.padEnd(numColumns, ' ').toList() })
}
fun Iterable<String>.parseDenseDoubleGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toDouble) })
fun Iterable<String>.parseDenseIntGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toInt) })
fun Iterable<String>.parseDenseLongGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toLong) })
fun Iterable<String>.parseDenseRationalGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toRational) })
fun Iterable<String>.parseDenseStringGrid() =
  Grid(map { it.toList().map(Char::toString).map(String::toString) })

fun String.parseNullableBigDecimalGrid() = parseBigDecimalGrid() as Grid<BigDecimal?>
fun String.parseNullableBigIntegerGrid() = parseBigIntegerGrid() as Grid<BigInteger?>
fun String.parseNullableCharGrid() = parseCharGrid() as Grid<Char?>
fun String.parseNullableDoubleGrid() = parseDoubleGrid() as Grid<Double?>
fun String.parseNullableIntGrid() = parseIntGrid() as Grid<Int?>
fun String.parseNullableLongGrid() = parseLongGrid() as Grid<Long?>
fun String.parseNullableRationalGrid() = parseRationalGrid() as Grid<Rational?>
fun String.parseNullableStringGrid() = parseStringGrid() as Grid<String?>

fun Iterable<String>.parseNullableBigDecimalGrid() = parseBigDecimalGrid() as Grid<BigDecimal?>
fun Iterable<String>.parseNullableBigIntegerGrid() = parseBigIntegerGrid() as Grid<BigInteger?>
fun Iterable<String>.parseNullableCharGrid() = parseCharGrid() as Grid<Char?>
fun Iterable<String>.parseNullableDoubleGrid() = parseDoubleGrid() as Grid<Double?>
fun Iterable<String>.parseNullableIntGrid() = parseIntGrid() as Grid<Int?>
fun Iterable<String>.parseNullableLongGrid() = parseLongGrid() as Grid<Long?>
fun Iterable<String>.parseNullableRationalGrid() = parseRationalGrid() as Grid<Rational?>
fun Iterable<String>.parseNullableStringGrid() = parseStringGrid() as Grid<String?>

fun String.parseNullableDenseBigDecimalGrid() = parseDenseBigDecimalGrid() as Grid<BigDecimal?>
fun String.parseNullableDenseBigIntegerGrid() = parseDenseBigIntegerGrid() as Grid<BigInteger?>
fun String.parseNullableDenseCharGrid() = parseDenseCharGrid() as Grid<Char?>
fun String.parseNullableDenseDoubleGrid() = parseDenseDoubleGrid() as Grid<Double?>
fun String.parseNullableDenseIntGrid() = parseDenseIntGrid() as Grid<Int?>
fun String.parseNullableDenseLongGrid() = parseDenseLongGrid() as Grid<Long?>
fun String.parseNullableDenseRationalGrid() = parseDenseRationalGrid() as Grid<Rational?>
fun String.parseNullableDenseStringGrid() = parseDenseStringGrid() as Grid<String?>

fun Iterable<String>.parseNullableDenseBigDecimalGrid() = parseDenseBigDecimalGrid() as Grid<BigDecimal?>
fun Iterable<String>.parseNullableDenseBigIntegerGrid() = parseDenseBigIntegerGrid() as Grid<BigInteger?>
fun Iterable<String>.parseNullableDenseCharGrid() = parseDenseCharGrid() as Grid<Char?>
fun Iterable<String>.parseNullableDenseDoubleGrid() = parseDenseDoubleGrid() as Grid<Double?>
fun Iterable<String>.parseNullableDenseIntGrid() = parseDenseIntGrid() as Grid<Int?>
fun Iterable<String>.parseNullableDenseLongGrid() = parseDenseLongGrid() as Grid<Long?>
fun Iterable<String>.parseNullableDenseRationalGrid() = parseDenseRationalGrid() as Grid<Rational?>
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
