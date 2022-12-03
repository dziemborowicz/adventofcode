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
