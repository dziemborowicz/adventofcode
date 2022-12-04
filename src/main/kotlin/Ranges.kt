fun String.toIntRange(): IntRange {
  val parts = split("..")
  require(parts.size == 2) { "Invalid format for range: $this" }
  return parts[0].toInt()..parts[1].toInt()
}

fun String.toLongRange(): LongRange {
  val parts = split("..")
  require(parts.size == 2) { "Invalid format for range: $this" }
  return parts[0].toLong()..parts[1].toLong()
}

fun String.toDoubleRange(): ClosedFloatingPointRange<Double> {
  val parts = split("..")
  require(parts.size == 2) { "Invalid format for range: $this" }
  return parts[0].toDouble()..parts[1].toDouble()
}
