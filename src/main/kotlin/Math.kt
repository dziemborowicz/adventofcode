import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.ExperimentalTypeInference

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

fun Iterable<BigDecimal>.product(): BigDecimal = fold(BigDecimal.ONE) { a, b -> a * b }

fun Iterable<BigInteger>.product(): BigInteger = fold(BigInteger.ONE) { a, b -> a * b }

fun Iterable<Double>.product(): Double = fold(1.0) { a, b -> a * b }

fun Iterable<Int>.product(): Int = fold(1) { a, b -> a * b }

fun Iterable<Long>.product(): Long = fold(1L) { a, b -> a * b }

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

fun IntRange.sum(): Int = sumOfRange(first, last)

fun LongRange.sum(): Long = sumOfRange(first, last)

fun sumOfRange(firstInclusive: Int, lastInclusive: Int): Int {
  if (firstInclusive > lastInclusive) return 0
  return ((lastInclusive - firstInclusive + 1) * (firstInclusive + lastInclusive)) / 2
}

fun sumOfRange(firstInclusive: Long, lastInclusive: Long): Long {
  if (firstInclusive > lastInclusive) return 0
  return ((lastInclusive - firstInclusive + 1) * (firstInclusive + lastInclusive)) / 2
}

fun sumOfRangeUpTo(lastInclusive: Int) = sumOfRange(0, lastInclusive)

fun sumOfRangeUpTo(lastInclusive: Long) = sumOfRange(0L, lastInclusive)
