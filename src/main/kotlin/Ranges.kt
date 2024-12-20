import java.math.BigInteger

typealias DoubleRange = ClosedFloatingPointRange<Double>

val CharProgression.count: Long
  get() = if (isEmpty()) {
    0L
  } else {
    ((last.code - first.code) / step.toLong()) + 1L
  }

val DoubleRange.length: Double
  get() = if (isEmpty()) 0.0 else endInclusive - start

val IntProgression.count: Long
  get() = if (isEmpty()) {
    0L
  } else {
    ((last.toLong() - first.toLong()) / step.toLong()) + 1L
  }

val LongProgression.count: BigInteger
  get() = if (isEmpty()) {
    BigInteger.ZERO
  } else {
    ((last.toBigInteger() - first.toBigInteger()) / step.toBigInteger()) + BigInteger.ONE
  }

fun CharRange.containsAny(other: CharRange): Boolean = !(this intersect other).isEmpty()

fun DoubleRange.containsAny(other: DoubleRange): Boolean = !(this intersect other).isEmpty()

fun IntRange.containsAny(other: IntRange): Boolean = !(this intersect other).isEmpty()

fun LongRange.containsAny(other: LongRange): Boolean = !(this intersect other).isEmpty()

fun CharRange.containsAny(elements: Iterable<Char>): Boolean = elements.any { it in this }

fun DoubleRange.containsAny(elements: Iterable<Double>): Boolean = elements.any { it in this }

fun IntRange.containsAny(elements: Iterable<Int>): Boolean = elements.any { it in this }

fun LongRange.containsAny(elements: Iterable<Long>): Boolean = elements.any { it in this }

fun CharRange.containsAll(other: CharRange): Boolean = (this intersect other).count == other.count

fun DoubleRange.containsAll(other: DoubleRange): Boolean =
  (this intersect other).length == other.length

fun IntRange.containsAll(other: IntRange): Boolean = (this intersect other).count == other.count

fun LongRange.containsAll(other: LongRange): Boolean = (this intersect other).count == other.count

fun CharRange.containsAll(elements: Iterable<Char>): Boolean = elements.all { it in this }

fun DoubleRange.containsAll(elements: Iterable<Double>): Boolean = elements.all { it in this }

fun IntRange.containsAll(elements: Iterable<Int>): Boolean = elements.all { it in this }

fun LongRange.containsAll(elements: Iterable<Long>): Boolean = elements.all { it in this }

infix fun CharRange.intersect(other: CharRange): CharRange =
  maxOf(first, other.first)..minOf(last, other.last)

infix fun DoubleRange.intersect(other: DoubleRange): DoubleRange =
  maxOf(start, other.start)..minOf(endInclusive, other.endInclusive)

infix fun IntRange.intersect(other: IntRange): IntRange =
  maxOf(first, other.first)..minOf(last, other.last)

infix fun LongRange.intersect(other: LongRange): LongRange =
  maxOf(first, other.first)..minOf(last, other.last)

fun CharRange.inverted(): CharRangeSet = toCharRangeSet().inverted()

fun IntRange.inverted(): IntRangeSet = toIntRangeSet().inverted()

fun LongRange.inverted(): LongRangeSet = toLongRangeSet().inverted()

fun CharRange.moveToEndAt(end: Char): CharRange {
  val start = end.code - count + 1
  check(start >= Char.MIN_VALUE.code)
  return start.toInt().toChar()..end
}

fun IntRange.moveToEndAt(end: Int): IntRange {
  val start = end - count + 1
  check(start >= Int.MIN_VALUE)
  return start.toInt()..end
}

fun LongRange.moveToEndAt(end: Long): LongRange {
  val start = end.toBigInteger() - count + BigInteger.ONE
  check(start >= Long.MIN_VALUE.toBigInteger())
  return start.toLong()..end
}

fun CharRange.moveToStartAt(start: Char): CharRange {
  val end = start.code + count - 1
  check(end <= Char.MAX_VALUE.code)
  return start..end.toInt().toChar()
}

fun IntRange.moveToStartAt(start: Int): IntRange {
  val end = start + count - 1
  check(end <= Int.MAX_VALUE)
  return start..end.toInt()
}

fun LongRange.moveToStartAt(start: Long): LongRange {
  val end = start.toBigInteger() + count - BigInteger.ONE
  check(end <= Long.MAX_VALUE.toBigInteger())
  return start..end.toLong()
}

fun Double.remap(from: DoubleRange, to: DoubleRange): Double {
  val steps = (this - from.start) / from.length
  return to.start + (steps * to.length)
}

fun Int.remap(from: IntProgression, to: IntProgression): Int {
  check(from.count == to.count)
  val steps = (this - from.first) / from.step
  return to.first + (steps * to.step)
}

fun Long.remap(from: LongProgression, to: LongProgression): Long {
  check(from.count == to.count)
  val steps = (this - from.first) / from.step
  return to.first + (steps * to.step)
}

infix fun CharRange.unionOrNull(other: CharRange): CharRange? {
  val joined = minOf(first, other.first)..maxOf(last, other.last)
  return if (joined.count - other.count <= count) joined else null
}

infix fun DoubleRange.unionOrNull(other: DoubleRange): DoubleRange? {
  val joined = minOf(start, other.start)..maxOf(endInclusive, other.endInclusive)
  return if (joined.length - other.length <= length) joined else null
}

infix fun IntRange.unionOrNull(other: IntRange): IntRange? {
  val joined = minOf(first, other.first)..maxOf(last, other.last)
  return if (joined.count - other.count <= count) joined else null
}

infix fun LongRange.unionOrNull(other: LongRange): LongRange? {
  val joined = minOf(first, other.first)..maxOf(last, other.last)
  return if (joined.count - other.count <= count) joined else null
}

infix fun Char.upOrDownTo(other: Char): CharProgression =
  if (other >= this) this..other else this downTo other

infix fun Int.upOrDownTo(other: Int): IntProgression =
  if (other >= this) this..other else this downTo other

infix fun Long.upOrDownTo(other: Long): LongProgression =
  if (other >= this) this..other else this downTo other

fun List<Char>.toCharRange(): CharRange {
  require(size == 2) { "Must contain exactly two elements." }
  return this[0]..this[1]
}

fun List<Double>.toDoubleRange(): DoubleRange {
  require(size == 2) { "Must contain exactly two elements." }
  return this[0]..this[1]
}

fun List<Int>.toIntRange(): IntRange {
  require(size == 2) { "Must contain exactly two elements." }
  return this[0]..this[1]
}

fun List<Long>.toLongRange(): LongRange {
  require(size == 2) { "Must contain exactly two elements." }
  return this[0]..this[1]
}

fun String.toCharRange(): CharRange {
  val (from, to) = Regex("""([a-zA-Z])(?:\.\.|,|-|\s+)([a-zA-Z])""").matchEntire(this)?.destructured
    ?: throw IllegalArgumentException("Invalid format for range: $this")
  require(from.single().isUpperCase() == to.single().isUpperCase()) {
    "Invalid format for range: $this"
  }
  return from.single()..to.single()
}

fun String.toDoubleRange(): DoubleRange {
  val (from, to) = Regex("""(.+?)(?:\.\.|,|-|\s+)(.+?)""").matchEntire(this)?.destructured
    ?: throw IllegalArgumentException("Invalid format for range: $this")
  return from.toDouble()..to.toDouble()
}

fun String.toIntRange(): IntRange {
  val (from, to) = Regex("""(-?\d+)(?:\.\.|,|-|\s+)(-?\d+)""").matchEntire(this)?.destructured
    ?: throw IllegalArgumentException("Invalid format for range: $this")
  return from.toInt()..to.toInt()
}

fun String.toLongRange(): LongRange {
  val (from, to) = Regex("""(-?\d+)(?:\.\.|,|-|\s+)(-?\d+)""").matchEntire(this)?.destructured
    ?: throw IllegalArgumentException("Invalid format for range: $this")
  return from.toLong()..to.toLong()
}
