import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.ExperimentalTypeInference

/** Represents an `x ≡ a (mod n)` congruence. */
data class Congruence(val a: BigInteger, val n: BigInteger) {
  constructor(a: BigInteger, n: Int) : this(a, n.toBigInteger())
  constructor(a: BigInteger, n: Long) : this(a, n.toBigInteger())
  constructor(a: Int, n: BigInteger) : this(a.toBigInteger(), n)
  constructor(a: Int, n: Int) : this(a.toBigInteger(), n.toBigInteger())
  constructor(a: Int, n: Long) : this(a.toBigInteger(), n.toBigInteger())
  constructor(a: Long, n: BigInteger) : this(a.toBigInteger(), n)
  constructor(a: Long, n: Int) : this(a.toBigInteger(), n.toBigInteger())
  constructor(a: Long, n: Long) : this(a.toBigInteger(), n.toBigInteger())

  override fun toString() = "x ≡ $a (mod $n)"
}

@JvmName("IntListToCongruence")
fun List<Int>.toCongruence(): Congruence {
  check(size == 2)
  return Congruence(this[0], this[1])
}

@JvmName("LongListToCongruence")
fun List<Long>.toCongruence(): Congruence {
  check(size == 2)
  return Congruence(this[0], this[1])
}

@JvmName("BigIntegerListToCongruence")
fun List<BigInteger>.toCongruence(): Congruence {
  check(size == 2)
  return Congruence(this[0], this[1])
}

@JvmName("BigIntegerIntPairToCongruence")
fun Pair<BigInteger, Int>.toCongruence() = Congruence(first, second)

@JvmName("BigIntegerLongPairToCongruence")
fun Pair<BigInteger, Long>.toCongruence() = Congruence(first, second)

@JvmName("IntBigIntegerPairToCongruence")
fun Pair<Int, BigInteger>.toCongruence() = Congruence(first, second)

@JvmName("IntIntPairToCongruence")
fun Pair<Int, Int>.toCongruence() = Congruence(first, second)

@JvmName("IntLongPairToCongruence")
fun Pair<Int, Long>.toCongruence() = Congruence(first, second)

@JvmName("LongBigIntegerPairToCongruence")
fun Pair<Long, BigInteger>.toCongruence() = Congruence(first, second)

@JvmName("LongIntPairToCongruence")
fun Pair<Long, Int>.toCongruence() = Congruence(first, second)

@JvmName("LongLongPairToCongruence")
fun Pair<Long, Long>.toCongruence() = Congruence(first, second)

fun crt(congruences: List<Congruence>): BigInteger {
  val product = congruences.productOf { it.n }
  return congruences.sumOf {
    val n = product / it.n
    val m = n.modInverse(it.n)
    it.a * n * m
  }.mod(product)
}

@JvmName("crtOfCongruenceIterable")
fun crt(congruences: Iterable<Congruence>) = crt(congruences.toList())

@JvmName("crtOfIntListIterable")
fun crt(congruences: Iterable<List<Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongListIterable")
fun crt(congruences: Iterable<List<Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfBigIntegerListIterable")
fun crt(congruences: Iterable<List<BigInteger>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfBigIntegerIntPairIterable")
fun crt(congruences: Iterable<Pair<BigInteger, Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfBigIntegerLongPairIterable")
fun crt(congruences: Iterable<Pair<BigInteger, Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfIntBigIntegerPairIterable")
fun crt(congruences: Iterable<Pair<Int, BigInteger>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfIntIntPairIterable")
fun crt(congruences: Iterable<Pair<Int, Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfIntLongPairIterable")
fun crt(congruences: Iterable<Pair<Int, Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongBigIntegerPairIterable")
fun crt(congruences: Iterable<Pair<Long, BigInteger>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongIntPairIterable")
fun crt(congruences: Iterable<Pair<Long, Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongLongPairIterable")
fun crt(congruences: Iterable<Pair<Long, Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("CongruenceIterableCrt")
fun Iterable<Congruence>.crt() = crt(this)

@JvmName("IntListIterableCrt")
fun Iterable<List<Int>>.crt() = crt(this)

@JvmName("LongListIterableCrt")
fun Iterable<List<Long>>.crt() = crt(this)

@JvmName("BigIntegerListIterableCrt")
fun Iterable<List<BigInteger>>.crt() = crt(this)

@JvmName("BigIntegerIntPairIterableCrt")
fun Iterable<Pair<BigInteger, Int>>.crt() = crt(this)

@JvmName("BigIntegerLongPairIterableCrt")
fun Iterable<Pair<BigInteger, Long>>.crt() = crt(this)

@JvmName("IntBigIntegerPairIterableCrt")
fun Iterable<Pair<Int, BigInteger>>.crt() = crt(this)

@JvmName("IntIntPairIterableCrt")
fun Iterable<Pair<Int, Int>>.crt() = crt(this)

@JvmName("IntLongPairIterableCrt")
fun Iterable<Pair<Int, Long>>.crt() = crt(this)

@JvmName("LongBigIntegerPairIterableCrt")
fun Iterable<Pair<Long, BigInteger>>.crt() = crt(this)

@JvmName("LongIntPairIterableCrt")
fun Iterable<Pair<Long, Int>>.crt() = crt(this)

@JvmName("LongLongPairIterableCrt")
fun Iterable<Pair<Long, Long>>.crt() = crt(this)

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

fun sqrt(x: Int): Int = sqrt(x.toDouble()).toInt()

fun sqrt(x: Double): Double = kotlin.math.sqrt(x)

fun sqrt(x: Float): Float = kotlin.math.sqrt(x)

fun Iterable<BigDecimal>.sum(): BigDecimal = fold(BigDecimal.ZERO) { a, b -> a + b }

fun Iterable<BigInteger>.sum(): BigInteger = fold(BigInteger.ZERO) { a, b -> a + b }

fun IntRange.sum(): Int = sumOfRange(first, last)

fun LongRange.sum(): Long = sumOfRange(first, last)

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
