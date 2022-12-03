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
