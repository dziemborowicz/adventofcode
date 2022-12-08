import java.math.BigInteger

fun Int.pow(exponent: Int): Int {
  var base = this
  var exponentLeft = exponent
  var result = 1
  while (exponentLeft > 0) {
    if (exponentLeft % 2 == 0) {
      exponentLeft /= 2
      base *= base
    } else {
      exponentLeft -= 1
      result *= base
    }
  }
  return result
}

fun Long.pow(exponent: Int): Long {
  var base = this
  var exponentLeft = exponent
  var result = 1L
  while (exponentLeft > 0) {
    if (exponentLeft % 2 == 0) {
      exponentLeft /= 2
      base *= base
    } else {
      exponentLeft -= 1
      result *= base
    }
  }
  return result
}

val Int.isOdd: Boolean
  get() = this % 2 == 1

val Int.isEven: Boolean
  get() = this % 2 == 0

val Long.isOdd: Boolean
  get() = this % 2 == 1L

val Long.isEven: Boolean
  get() = this % 2 == 0L

fun sumOfConsecutiveNumbers(range: IntRange) = sumOfConsecutiveNumbers(range.first, range.last)

fun sumOfConsecutiveNumbers(firstInclusive: Int, lastInclusive: Int): Int {
  require(firstInclusive <= lastInclusive)
  return ((lastInclusive - firstInclusive + 1) * (firstInclusive + lastInclusive)) / 2
}

fun sumOfConsecutiveNumbersUpTo(lastInclusive: Int) = sumOfConsecutiveNumbers(0, lastInclusive)

fun sumOfConsecutiveNumbers(range: LongRange) = sumOfConsecutiveNumbers(range.first, range.last)

fun sumOfConsecutiveNumbers(firstInclusive: Long, lastInclusive: Long): Long {
  require(firstInclusive <= lastInclusive)
  return ((lastInclusive - firstInclusive + 1) * (firstInclusive + lastInclusive)) / 2
}

fun sumOfConsecutiveNumbersUpTo(lastInclusive: Long) = sumOfConsecutiveNumbers(0, lastInclusive)

fun sumOfConsecutiveNumbers(range: ClosedFloatingPointRange<Double>) =
  sumOfConsecutiveNumbers(range.start, range.endInclusive)

fun sumOfConsecutiveNumbers(firstInclusive: Double, lastInclusive: Double): Double {
  require(firstInclusive <= lastInclusive)
  return ((lastInclusive - firstInclusive + 1) * (firstInclusive + lastInclusive)) / 2
}

fun sumOfConsecutiveNumbersUpTo(lastInclusive: Double) = sumOfConsecutiveNumbers(0.0, lastInclusive)

fun Boolean.toInt(): Int = if (this) 1 else 0

fun Boolean.toLong(): Long = if (this) 1L else 0L

fun Boolean.toBigInteger(): BigInteger = if (this) BigInteger.ONE else BigInteger.ZERO

fun Int.toBoolean(): Boolean = this != 0

fun Long.toBoolean(): Boolean = this != 0L

fun BigInteger.toBoolean(): Boolean = this != BigInteger.ZERO
