fun String.toIntRange(): IntRange {
  val (from, to) = Regex("""(-?\d+)(?:\.\.|,|-|\s+)(-?\d+)""").matchEntire(this)!!.destructured
  return from.toInt()..to.toInt()
}

fun String.toLongRange(): LongRange {
  val (from, to) = Regex("""(-?\d+)(?:\.\.|,|-|\s+)(-?\d+)""").matchEntire(this)!!.destructured
  return from.toLong()..to.toLong()
}

fun String.toDoubleRange(): ClosedFloatingPointRange<Double> {
  val (from, to) = Regex("""(.+?)(?:\.\.|,|-|\s+)(.+?)""").matchEntire(this)!!.destructured
  return from.toDouble()..to.toDouble()
}

val IntRange.size: Int
  get() = if (isEmpty()) 0 else last - first + 1

infix fun IntRange.intersect(other: IntRange): IntRange =
  maxOf(first, other.first)..minOf(last, other.last)

infix fun IntRange.union(other: IntRange): IntRange =
  (this unionOrNull other) ?: throw IllegalArgumentException("Ranges must touch.")

infix fun IntRange.unionOrNull(other: IntRange): IntRange? {
  val joined = minOf(first, other.first)..maxOf(last, other.last)
  return if (joined.size <= size + other.size) {
    joined
  } else {
    null
  }
}

fun IntRange.containsAny(other: IntRange): Boolean =
  !(this intersect other).isEmpty()

fun IntRange.containsAny(other: Iterable<Int>): Boolean =
  other.any { it in this }

fun IntRange.containsAll(other: IntRange): Boolean =
  (this intersect other).size == other.size

fun IntRange.containsAll(other: Iterable<Int>): Boolean =
  other.all { it in this }

infix fun Int.upOrDownTo(other: Int): IntProgression =
  if (other >= this) {
    this..other
  } else {
    other downTo this
  }

val LongRange.size: Long
  get() = if (isEmpty()) 0 else last - first + 1

infix fun LongRange.intersect(other: LongRange): LongRange =
  maxOf(first, other.first)..minOf(last, other.last)

infix fun LongRange.union(other: LongRange): LongRange =
  (this unionOrNull other) ?: throw IllegalArgumentException("Ranges must touch.")

infix fun LongRange.unionOrNull(other: LongRange): LongRange? {
  val joined = minOf(first, other.first)..maxOf(last, other.last)
  return if (joined.size <= size + other.size) {
    joined
  } else {
    null
  }
}

fun LongRange.containsAny(other: LongRange): Boolean =
  !(this intersect other).isEmpty()

fun LongRange.containsAny(other: Iterable<Long>): Boolean =
  other.any { it in this }

fun LongRange.containsAll(other: LongRange): Boolean =
  (this intersect other).size == other.size

fun LongRange.containsAll(other: Iterable<Long>): Boolean =
  other.all { it in this }

infix fun Long.upOrDownTo(other: Long): LongProgression =
  if (other >= this) {
    this..other
  } else {
    other downTo this
  }

val CharRange.size: Int
  get() = if (isEmpty()) 0 else last - first + 1

infix fun CharRange.intersect(other: CharRange): CharRange =
  maxOf(first, other.first)..minOf(last, other.last)

infix fun CharRange.union(other: CharRange): CharRange =
  (this unionOrNull other) ?: throw IllegalArgumentException("Ranges must touch.")

infix fun CharRange.unionOrNull(other: CharRange): CharRange? {
  val joined = minOf(first, other.first)..maxOf(last, other.last)
  return if (joined.size <= size + other.size) {
    joined
  } else {
    null
  }
}

fun CharRange.containsAny(other: CharRange): Boolean =
  !(this intersect other).isEmpty()

fun CharRange.containsAny(other: Iterable<Char>): Boolean =
  other.any { it in this }

fun CharRange.containsAll(other: CharRange): Boolean =
  (this intersect other).size == other.size

fun CharRange.containsAll(other: Iterable<Char>): Boolean =
  other.all { it in this }

infix fun Char.upOrDownTo(other: Char): CharProgression =
  if (other >= this) {
    this..other
  } else {
    other downTo this
  }

val ClosedFloatingPointRange<Double>.length: Double
  get() = if (isEmpty()) 0.0 else endInclusive - start

infix fun ClosedFloatingPointRange<Double>.intersect(other: ClosedFloatingPointRange<Double>): ClosedFloatingPointRange<Double> =
  maxOf(start, other.start)..minOf(endInclusive, other.endInclusive)

infix fun ClosedFloatingPointRange<Double>.union(other: ClosedFloatingPointRange<Double>): ClosedFloatingPointRange<Double> =
  (this unionOrNull other) ?: throw IllegalArgumentException("Ranges must touch.")

infix fun ClosedFloatingPointRange<Double>.unionOrNull(other: ClosedFloatingPointRange<Double>): ClosedFloatingPointRange<Double>? {
  val joined = minOf(start, other.start)..maxOf(endInclusive, other.endInclusive)
  return if (joined.length <= length + other.length) {
    joined
  } else {
    null
  }
}

fun ClosedFloatingPointRange<Double>.containsAny(other: ClosedFloatingPointRange<Double>): Boolean =
  !(this intersect other).isEmpty()

fun ClosedFloatingPointRange<Double>.containsAny(other: Iterable<Double>): Boolean =
  other.any { it in this }

fun ClosedFloatingPointRange<Double>.containsAll(other: ClosedFloatingPointRange<Double>): Boolean =
  (this intersect other).length == other.length

fun ClosedFloatingPointRange<Double>.containsAll(other: Iterable<Double>): Boolean =
  other.all { it in this }
