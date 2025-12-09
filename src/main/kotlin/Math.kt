import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue

fun List<Index>.area(): Int {
  if (first() != last()) return (this + first()).area()
  return windowed(2).sumOf { (a, b) -> a.row * b.column - b.row * a.column }.absoluteValue / 2
}

fun List<Index>.areaEnclosedByIntegralBoundary(): Int {
  if (first() != last()) return (this + first()).areaEnclosedByIntegralBoundary()
  val boundaryPoints = numIntegralBoundaryPoints()
  return areaIncludingIntegralBoundary() - boundaryPoints
}

fun List<Index>.areaIncludingIntegralBoundary(): Int {
  if (first() != last()) return (this + first()).areaIncludingIntegralBoundary()
  val boundaryPoints = numIntegralBoundaryPoints()
  val interiorPoints = area() - (boundaryPoints / 2) + 1
  return boundaryPoints + interiorPoints
}

fun List<Index>.numIntegralBoundaryPoints(): Int {
  if (first() != last()) return (this + first()).numIntegralBoundaryPoints()
  check(windowed(2).all { (a, b) -> a.row == b.row || a.column == b.column }) { "The polygon must be rectilinear." }
  return windowed(2).sumOf { (a, b) -> a manhattanDistanceTo b }
}

@JvmName("areaOfPointList")
fun List<Point>.area(): Int {
  if (first() != last()) return (this + first()).area()
  return windowed(2).sumOf { (a, b) -> a.x * b.y - b.x * a.y }.absoluteValue / 2
}

@JvmName("areaEnclosedByIntegralBoundaryOfPointList")
fun List<Point>.areaEnclosedByIntegralBoundary(): Int {
  if (first() != last()) return (this + first()).areaEnclosedByIntegralBoundary()
  val boundaryPoints = numIntegralBoundaryPoints()
  return areaIncludingIntegralBoundary() - boundaryPoints
}

@JvmName("areaIncludingIntegralBoundaryOfPointList")
fun List<Point>.areaIncludingIntegralBoundary(): Int {
  if (first() != last()) return (this + first()).areaIncludingIntegralBoundary()
  val boundaryPoints = numIntegralBoundaryPoints()
  val interiorPoints = area() - (boundaryPoints / 2) + 1
  return boundaryPoints + interiorPoints
}

@JvmName("numIntegralBoundaryPointsOfPointList")
fun List<Point>.numIntegralBoundaryPoints(): Int {
  if (first() != last()) return (this + first()).numIntegralBoundaryPoints()
  check(windowed(2).all { (a, b) -> a.x == b.x || a.y == b.y }) { "The polygon must be rectilinear." }
  return windowed(2).sumOf { (a, b) -> a manhattanDistanceTo b }
}

@JvmName("areaOfLongPointList")
fun List<LongPoint>.area(): Long {
  if (first() != last()) return (this + first()).area()
  return windowed(2).sumOf { (a, b) -> a.x * b.y - b.x * a.y }.absoluteValue / 2L
}

@JvmName("areaEnclosedByIntegralBoundaryOfLongPointList")
fun List<LongPoint>.areaEnclosedByIntegralBoundary(): Long {
  if (first() != last()) return (this + first()).areaEnclosedByIntegralBoundary()
  val boundaryPoints = numIntegralBoundaryPoints()
  return areaIncludingIntegralBoundary() - boundaryPoints
}

@JvmName("areaIncludingIntegralBoundaryOfLongPointList")
fun List<LongPoint>.areaIncludingIntegralBoundary(): Long {
  if (first() != last()) return (this + first()).areaIncludingIntegralBoundary()
  val boundaryPoints = numIntegralBoundaryPoints()
  val interiorPoints = area() - (boundaryPoints / 2L) + 1L
  return boundaryPoints + interiorPoints
}

@JvmName("numIntegralBoundaryPointsOfLongPointList")
fun List<LongPoint>.numIntegralBoundaryPoints(): Long {
  if (first() != last()) return (this + first()).numIntegralBoundaryPoints()
  check(windowed(2).all { (a, b) -> a.x == b.x || a.y == b.y }) { "The polygon must be rectilinear." }
  return windowed(2).sumOf { (a, b) -> a manhattanDistanceTo b }
}

@JvmName("areaOfDoublePointList")
fun List<DoublePoint>.area(): Double {
  if (first() != last()) return (this + first()).area()
  return windowed(2).sumOf { (a, b) -> a.x * b.y - b.x * a.y }.absoluteValue / 2.0
}

fun Index.isInsidePolygon(polygon: List<Index>): Boolean =
  Point(column, row).isInsidePolygon(polygon.map { Point(it.column, it.row) })

fun Point.isInsidePolygon(polygon: List<Point>): Boolean =
  toDoublePoint().isInsidePolygon(polygon.map { it.toDoublePoint() })

fun LongPoint.isInsidePolygon(polygon: List<LongPoint>): Boolean =
  toDoublePoint().isInsidePolygon(polygon.map { it.toDoublePoint() })

@JvmName("isPointInsidePolygon")
fun DoublePoint.isInsidePolygon(polygon: List<Point>): Boolean =
  isInsidePolygon(polygon.map { it.toDoublePoint() })

@JvmName("isLongPointInsidePolygon")
fun DoublePoint.isInsidePolygon(polygon: List<LongPoint>): Boolean =
  isInsidePolygon(polygon.map { it.toDoublePoint() })

fun DoublePoint.isInsidePolygon(polygon: List<DoublePoint>): Boolean {
  var inside = false
  for ((a, b) in (polygon + polygon.first()).windowed(2)) {
    if ((a.y > y) != (b.y > y)) {
      val intersectX = a.x + (y - a.y) * (b.x - a.x) / (b.y - a.y)
      if (x < intersectX) {
        inside = !inside
      }
    }
  }
  return inside
}

fun Index.isOnBoundaryOfPolygon(polygon: List<Index>): Boolean =
  Point(column, row).isOnBoundaryOfPolygon(polygon.map { Point(it.column, it.row) })

fun Point.isOnBoundaryOfPolygon(polygon: List<Point>): Boolean {
  for ((a, b) in (polygon + polygon.first()).windowed(2)) {
    when {
      a.x == b.x -> {
        if (a.x == x) {
          val min = minOf(a.y, b.y)
          val max = maxOf(a.y, b.y)
          if (y in min..max) return true
        }
      }

      a.y == b.y -> {
        if (a.y == y) {
          val min = minOf(a.x, b.x)
          val max = maxOf(a.x, b.x)
          if (x in min..max) return true
        }
      }

      else -> fail("The polygon must be rectilinear.")
    }
  }
  return false
}

fun LongPoint.isOnBoundaryOfPolygon(polygon: List<LongPoint>): Boolean {
  for ((a, b) in (polygon + polygon.first()).windowed(2)) {
    when {
      a.x == b.x -> {
        if (a.x == x) {
          val min = minOf(a.y, b.y)
          val max = maxOf(a.y, b.y)
          if (y in min..max) return true
        }
      }

      a.y == b.y -> {
        if (a.y == y) {
          val min = minOf(a.x, b.x)
          val max = maxOf(a.x, b.x)
          if (x in min..max) return true
        }
      }

      else -> fail("The polygon must be rectilinear.")
    }
  }
  return false
}

@JvmName("isOnBoundaryOfPointPolygon")
fun DoublePoint.isOnBoundaryOfPolygon(polygon: List<Point>): Boolean =
  isOnBoundaryOfPolygon(polygon.map { it.toDoublePoint() })

@JvmName("isOnBoundaryOfLongPointPolygon")
fun DoublePoint.isOnBoundaryOfPolygon(polygon: List<LongPoint>): Boolean =
  isOnBoundaryOfPolygon(polygon.map { it.toDoublePoint() })

fun DoublePoint.isOnBoundaryOfPolygon(polygon: List<DoublePoint>): Boolean {
  for ((a, b) in (polygon + polygon.first()).windowed(2)) {
    when {
      a.x == b.x -> {
        if ((a.x - x).absoluteValue < 1e-9) {
          val min = minOf(a.y, b.y)
          val max = maxOf(a.y, b.y)
          if (y in min..max) return true
        }
      }

      a.y == b.y -> {
        if ((a.y - y).absoluteValue < 1e-9) {
          val min = minOf(a.x, b.x)
          val max = maxOf(a.x, b.x)
          if (x in min..max) return true
        }
      }

      else -> fail("The polygon must be rectilinear.")
    }
  }
  return false
}

fun BigInteger.ceilDiv(other: BigInteger): BigInteger = (this + other - BigInteger.ONE) / other

fun Int.ceilDiv(other: Int): Int = (this + other - 1) / other

fun Long.ceilDiv(other: Long): Long = (this + other - 1L) / other

fun Long.coerceToInt(): Int {
  return when {
    this < Int.MIN_VALUE -> Int.MIN_VALUE
    this > Int.MAX_VALUE -> Int.MAX_VALUE
    else -> this.toInt()
  }
}

fun BigInteger.coerceToInt(): Int {
  return when {
    this < Int.MIN_VALUE.toBigInteger() -> Int.MIN_VALUE
    this > Int.MAX_VALUE.toBigInteger() -> Int.MAX_VALUE
    else -> this.toInt()
  }
}

fun BigInteger.coerceToLong(): Long {
  return when {
    this < Long.MIN_VALUE.toBigInteger() -> Long.MIN_VALUE
    this > Long.MAX_VALUE.toBigInteger() -> Long.MAX_VALUE
    else -> this.toLong()
  }
}

val BigInteger.digits: List<Int>
  get() = toString().map { it.digitToInt() }

val Int.digits: List<Int>
  get() = toString().map { it.digitToInt() }

val Long.digits: List<Int>
  get() = toString().map { it.digitToInt() }

fun Long.digitToChar(): Char = toInt().digitToChar()

fun Long.digitToChar(radix: Int): Char = toInt().digitToChar(radix)

infix fun BigInteger.isDivisibleBy(other: BigInteger): Boolean = this % other == BigInteger.ZERO

infix fun Int.isDivisibleBy(other: Int): Boolean = this % other == 0

infix fun Long.isDivisibleBy(other: Long): Boolean = this % other == 0L

infix fun BigInteger.isNotDivisibleBy(other: BigInteger): Boolean = this % other != BigInteger.ZERO

infix fun Int.isNotDivisibleBy(other: Int): Boolean = this % other != 0

infix fun Long.isNotDivisibleBy(other: Long): Boolean = this % other != 0L

val BigInteger.isEven: Boolean
  get() = !testBit(0)

val Int.isEven: Boolean
  get() = this % 2 == 0

val Long.isEven: Boolean
  get() = this % 2 == 0L

val BigInteger.isOdd: Boolean
  get() = testBit(0)

val Int.isOdd: Boolean
  get() = this % 2 == 1

val Long.isOdd: Boolean
  get() = this % 2 == 1L

tailrec fun gcd(a: BigInteger, b: BigInteger): BigInteger =
  if (b == BigInteger.ZERO) a else gcd(b, a % b)

tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun gcd(vararg values: BigInteger) = values.reduce { a, b -> gcd(a, b) }

fun gcd(vararg values: Int) = values.reduce { a, b -> gcd(a, b) }

fun gcd(vararg values: Long) = values.reduce { a, b -> gcd(a, b) }

fun Iterable<BigInteger>.gcd() = reduce { a, b -> gcd(a, b) }

fun Iterable<Int>.gcd() = reduce { a, b -> gcd(a, b) }

fun Iterable<Long>.gcd() = reduce { a, b -> gcd(a, b) }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.gcdOf(selector: (T) -> BigInteger): BigInteger = map(selector).gcd()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.gcdOf(selector: (T) -> Int): Int = map(selector).gcd()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.gcdOf(selector: (T) -> Long): Long = map(selector).gcd()

fun lcm(a: BigInteger, b: BigInteger): BigInteger = a / gcd(a, b) * b

fun lcm(a: Int, b: Int): Int = a / gcd(a, b) * b

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun lcm(vararg values: BigInteger) = values.reduce { a, b -> lcm(a, b) }

fun lcm(vararg values: Int) = values.reduce { a, b -> lcm(a, b) }

fun lcm(vararg values: Long) = values.reduce { a, b -> lcm(a, b) }

fun Iterable<BigInteger>.lcm() = reduce { a, b -> lcm(a, b) }

fun Iterable<Int>.lcm() = reduce { a, b -> lcm(a, b) }

fun Iterable<Long>.lcm() = reduce { a, b -> lcm(a, b) }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.lcmOf(selector: (T) -> BigInteger): BigInteger = map(selector).lcm()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.lcmOf(selector: (T) -> Int): Int = map(selector).lcm()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.lcmOf(selector: (T) -> Long): Long = map(selector).lcm()

fun Int.modInverse(m: Int): Int = toBigInteger().modInverse(m.toBigInteger()).toInt()
fun Long.modInverse(m: Long): Long = toBigInteger().modInverse(m.toBigInteger()).toLong()

infix fun Int.pow(exponent: Int): Int {
  if (exponent < 0) throw ArithmeticException("Negative exponent")
  var base = this
  var exponentLeft = exponent
  var result = 1
  while (exponentLeft > 0) {
    if (exponentLeft.isEven) {
      exponentLeft /= 2
      base *= base
    } else {
      exponentLeft -= 1
      result *= base
    }
  }
  return result
}

infix fun Long.pow(exponent: Int): Long {
  if (exponent < 0) throw ArithmeticException("Negative exponent")
  var base = this
  var exponentLeft = exponent
  var result = 1L
  while (exponentLeft > 0) {
    if (exponentLeft.isEven) {
      exponentLeft /= 2
      base *= base
    } else {
      exponentLeft -= 1
      result *= base
    }
  }
  return result
}

fun Iterable<BigDecimal>.product(): BigDecimal = reduce { a, b -> a * b }

fun Iterable<BigInteger>.product(): BigInteger = reduce { a, b -> a * b }

fun Iterable<Double>.product(): Double = reduce { a, b -> a * b }

fun Iterable<Int>.product(): Int = reduce { a, b -> a * b }

fun Iterable<Long>.product(): Long = reduce { a, b -> a * b }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> BigDecimal): BigDecimal =
  map(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> BigInteger): BigInteger =
  map(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Double): Double = map(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int = map(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long = map(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexed(selector: (Int, T) -> BigDecimal): BigDecimal =
  mapIndexed(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexed(selector: (Int, T) -> BigInteger): BigInteger =
  mapIndexed(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexed(selector: (Int, T) -> Double): Double =
  mapIndexed(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexed(selector: (Int, T) -> Int): Int =
  mapIndexed(selector).product()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexed(selector: (Int, T) -> Long): Long =
  mapIndexed(selector).product()

fun Iterable<BigDecimal>.productOrOne(): BigDecimal = fold(BigDecimal.ONE) { a, b -> a * b }

fun Iterable<BigInteger>.productOrOne(): BigInteger = fold(BigInteger.ONE) { a, b -> a * b }

fun Iterable<Double>.productOrOne(): Double = fold(1.0) { a, b -> a * b }

fun Iterable<Int>.productOrOne(): Int = fold(1) { a, b -> a * b }

fun Iterable<Long>.productOrOne(): Long = fold(1L) { a, b -> a * b }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfOrOne(selector: (T) -> BigDecimal): BigDecimal =
  map(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfOrOne(selector: (T) -> BigInteger): BigInteger =
  map(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfOrOne(selector: (T) -> Double): Double =
  map(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfOrOne(selector: (T) -> Int): Int = map(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfOrOne(selector: (T) -> Long): Long =
  map(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexedOrOne(selector: (Int, T) -> BigDecimal): BigDecimal =
  mapIndexed(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexedOrOne(selector: (Int, T) -> BigInteger): BigInteger =
  mapIndexed(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexedOrOne(selector: (Int, T) -> Double): Double =
  mapIndexed(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexedOrOne(selector: (Int, T) -> Int): Int =
  mapIndexed(selector).productOrOne()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOfIndexedOrOne(selector: (Int, T) -> Long): Long =
  mapIndexed(selector).productOrOne()

val Int.Companion.RANGE: IntRange
  get() = Int.MIN_VALUE..Int.MAX_VALUE

val Long.Companion.RANGE: LongRange
  get() = Long.MIN_VALUE..Long.MAX_VALUE

fun sqrt(x: Int): Int = sqrt(x.toDouble()).toInt()

fun sqrt(x: Double): Double = kotlin.math.sqrt(x)

fun sqrt(x: Float): Float = kotlin.math.sqrt(x)

fun Iterable<BigDecimal>.sum(): BigDecimal = fold(BigDecimal.ZERO) { a, b -> a + b }

fun Iterable<BigInteger>.sum(): BigInteger = fold(BigInteger.ZERO) { a, b -> a + b }

fun IntRange.sum(): Long = sumOfRange(first, last)

fun LongRange.sum(): BigInteger = sumOfRange(first, last)

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> BigDecimal): BigDecimal =
  mapIndexed(selector).sum()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> BigInteger): BigInteger =
  mapIndexed(selector).sum()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Double): Double =
  mapIndexed(selector).sum()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int = mapIndexed(selector).sum()

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Long): Long =
  mapIndexed(selector).sum()

fun sumOfRange(firstInclusive: Int, lastInclusive: Int): Long {
  if (firstInclusive > lastInclusive) return 0
  return ((lastInclusive.toLong() - firstInclusive.toLong() + 1) * (firstInclusive.toLong() + lastInclusive.toLong())) / 2
}

fun sumOfRange(firstInclusive: Long, lastInclusive: Long): BigInteger {
  if (firstInclusive > lastInclusive) return BigInteger.ZERO
  return ((lastInclusive.toBigInteger() - firstInclusive.toBigInteger() + BigInteger.ONE) * (firstInclusive.toBigInteger() + lastInclusive.toBigInteger())) / BigInteger.TWO
}

fun sumOfRangeUpTo(lastInclusive: Int) = sumOfRange(0, lastInclusive)

fun sumOfRangeUpTo(lastInclusive: Long) = sumOfRange(0L, lastInclusive)
