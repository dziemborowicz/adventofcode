fun Int.pow(exponent: Int): Int {
  if (exponent == 0) return 1
  var result = this
  repeat(exponent - 1) { result *= this }
  return result
}

fun Long.pow(exponent: Int): Long {
  if (exponent == 0) return 1
  var result = this
  repeat(exponent - 1) { result *= this }
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
