import java.math.BigInteger

class LongRangeSet : MutableSet<Long> {

  private val ranges = mutableListOf<LongRange>()

  override val size: Int
    get() {
      val count = count()
      if (count > Int.MAX_VALUE.toBigInteger()) throw ArithmeticException("Overflow")
      return count.toInt()
    }

  fun count(): BigInteger = ranges.sumOf { it.count }

  override fun contains(element: Long): Boolean = ranges.any { element in it }

  override fun containsAll(elements: Collection<Long>): Boolean = elements.all { contains(it) }

  fun containsAll(elements: LongRange): Boolean {
    if (elements.isEmpty()) return true
    return ranges.any { it.containsAll(elements) }
  }

  fun containsAll(elements: Iterable<Long>): Boolean = elements.all { contains(it) }

  @JvmName("containsAllLongRangeIterable")
  fun containsAll(elements: Iterable<LongRange>): Boolean = elements.all { containsAll(it) }

  fun containsAll(elements: LongRangeSet): Boolean = containsAll(elements.ranges)

  fun containsAny(elements: Collection<Long>): Boolean = elements.any { contains(it) }

  fun containsAny(elements: LongRange): Boolean = ranges.any { it.containsAny(elements) }

  fun containsAny(elements: Iterable<Long>): Boolean = elements.any { contains(it) }

  @JvmName("containsAnyLongRangeIterable")
  fun containsAny(elements: Iterable<LongRange>): Boolean = elements.any { containsAny(it) }

  fun containsAny(elements: LongRangeSet): Boolean = containsAny(elements.ranges)

  override fun isEmpty(): Boolean = ranges.isEmpty()

  override fun iterator(): MutableIterator<Long> {
    val sequence = sequence { ranges.forEach { yieldAll(it) } }.iterator()
    return object : MutableIterator<Long> {
      override fun hasNext(): Boolean = sequence.hasNext()
      override fun next(): Long = sequence.next()
      override fun remove() = throw UnsupportedOperationException()
    }
  }

  fun ranges(): List<LongRange> = ranges.toList()

  override fun add(element: Long): Boolean = addAll(element..element)

  override fun addAll(elements: Collection<Long>): Boolean = elements.map { add(it) }.any { it }

  fun addAll(elements: LongRange): Boolean {
    if (containsAll(elements)) return false

    val insertionIndex = ranges.binarySearchBy(elements.first) { it.first }.let {
      when {
        it < 0 -> -it - 1
        elements.first < ranges[it].first -> it
        else -> it + 1
      }
    }
    ranges.add(insertionIndex, elements)

    var i = maxOf(1, insertionIndex)
    while (i in ranges.indices) {
      val union = ranges[i - 1] unionOrNull ranges[i]
      if (union != null) {
        ranges[i - 1] = union
        ranges.removeAt(i)
      } else {
        i++
      }
    }
    return true
  }

  fun addAll(elements: Iterable<Long>): Boolean = elements.map { add(it) }.any { it }

  @JvmName("addAllLongRangeIterable")
  fun addAll(elements: Iterable<LongRange>): Boolean = elements.map { addAll(it) }.any { it }

  fun addAll(elements: LongRangeSet): Boolean = elements.ranges.map { addAll(it) }.any { it }

  override fun clear(): Unit = ranges.clear()

  override fun remove(element: Long): Boolean = removeAll(element..element)

  override fun removeAll(elements: Collection<Long>): Boolean =
    elements.map { remove(it) }.any { it }

  fun removeAll(elements: LongRange): Boolean {
    if (!containsAny(elements)) return false
    var i = 0
    while (i in ranges.indices) {
      val intersection = ranges[i] intersect elements
      when {
        intersection.isEmpty() -> i++
        intersection.count == ranges[i].count -> ranges.removeAt(i)
        intersection.first == ranges[i].first -> {
          ranges[i] = intersection.last + 1..ranges[i].last
          i++
        }
        intersection.last == ranges[i].last -> {
          ranges[i] = ranges[i].first..intersection.first - 1
          i++
        }
        else -> {
          val first = ranges[i].first..intersection.first - 1
          val second = intersection.last + 1..ranges[i].last
          ranges[i] = first
          ranges.add(i + 1, second)
          i += 2
        }
      }
    }
    return true
  }

  fun removeAll(elements: Iterable<Long>): Boolean = elements.map { remove(it) }.any { it }

  @JvmName("removeAllLongRangeIterable")
  fun removeAll(elements: Iterable<LongRange>): Boolean = elements.map { removeAll(it) }.any { it }

  fun removeAll(elements: LongRangeSet): Boolean = elements.ranges.map { removeAll(it) }.any { it }

  fun retain(element: Long): Boolean = retainAll(LongRangeSet(element))

  override fun retainAll(elements: Collection<Long>): Boolean = retainAll(LongRangeSet(elements))

  fun retainAll(elements: LongRange): Boolean = retainAll(LongRangeSet(elements))

  fun retainAll(elements: Iterable<Long>): Boolean = retainAll(LongRangeSet(elements))

  @JvmName("retainAllLongRangeIterable")
  fun retainAll(elements: Iterable<LongRange>): Boolean = retainAll(LongRangeSet(elements))

  fun retainAll(elements: LongRangeSet): Boolean = removeAll(elements.inverted())

  fun invert() {
    val oldRanges = ranges.toList()
    ranges.clear()
    ranges.add(Long.MIN_VALUE..Long.MAX_VALUE)
    oldRanges.forEach { removeAll(it) }
  }

  fun inverted(): LongRangeSet = LongRangeSet(this).also { it.invert() }

  infix fun intersect(other: Long): LongRangeSet =
    LongRangeSet(this).also { it.retain(other) }

  infix fun intersect(other: Collection<Long>): LongRangeSet =
    LongRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: LongRange): LongRangeSet =
    LongRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: Iterable<Long>): LongRangeSet =
    LongRangeSet(this).also { it.retainAll(other) }

  @JvmName("intersectLongRangeIterable")
  infix fun intersect(other: Iterable<LongRange>): LongRangeSet =
    LongRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: LongRangeSet): LongRangeSet =
    LongRangeSet(this).also { it.retainAll(other) }

  infix fun subtract(other: Long): LongRangeSet =
    LongRangeSet(this).also { it.remove(other) }

  infix fun subtract(other: Collection<Long>): LongRangeSet =
    LongRangeSet(this).also { it.removeAll(other) }

  infix fun subtract(other: LongRange): LongRangeSet =
    LongRangeSet(this).also { it.removeAll(other) }

  infix fun subtract(other: Iterable<Long>): LongRangeSet =
    LongRangeSet(this).also { it.removeAll(other) }

  @JvmName("subtractLongRangeIterable")
  infix fun subtract(other: Iterable<LongRange>): LongRangeSet =
    LongRangeSet(this).also { it.removeAll(other) }

  infix fun subtract(other: LongRangeSet): LongRangeSet =
    LongRangeSet(this).also { it.removeAll(other) }

  infix fun union(other: Long): LongRangeSet =
    LongRangeSet(this).also { it.add(other) }

  infix fun union(other: Collection<Long>): LongRangeSet =
    LongRangeSet(this).also { it.addAll(other) }

  infix fun union(other: LongRange): LongRangeSet =
    LongRangeSet(this).also { it.addAll(other) }

  infix fun union(other: Iterable<Long>): LongRangeSet =
    LongRangeSet(this).also { it.addAll(other) }

  @JvmName("unionLongRangeIterable")
  infix fun union(other: Iterable<LongRange>): LongRangeSet =
    LongRangeSet(this).also { it.addAll(other) }

  infix fun union(other: LongRangeSet): LongRangeSet =
    LongRangeSet(this).also { it.addAll(other) }

  override fun equals(other: Any?): Boolean {
    if (other !is LongRangeSet) return false
    return ranges == other.ranges
  }

  override fun hashCode(): Int = ranges.hashCode()

  override fun toString(): String = ranges.toString()
}

fun LongRangeSet(element: Long): LongRangeSet =
  LongRangeSet().also { it.add(element) }

fun LongRangeSet(elements: Collection<Long>): LongRangeSet =
  LongRangeSet().also { it.addAll(elements) }

fun LongRangeSet(elements: LongRange): LongRangeSet =
  LongRangeSet().also { it.addAll(elements) }

fun LongRangeSet(elements: Iterable<Long>): LongRangeSet =
  LongRangeSet().also { it.addAll(elements) }

@JvmName("LongRangeSetOfLongRangeIterable")
fun LongRangeSet(elements: Iterable<LongRange>): LongRangeSet =
  LongRangeSet().also { it.addAll(elements) }

fun LongRangeSet(elements: LongRangeSet): LongRangeSet =
  LongRangeSet().also { it.addAll(elements) }

infix fun LongRange.intersect(other: Long): LongRangeSet =
  LongRangeSet(this).also { it.retain(other) }

infix fun LongRange.intersect(other: Collection<Long>): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun LongRange.intersect(other: Iterable<Long>): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

@JvmName("LongRangeLongersectWithLongRangeIterable")
infix fun LongRange.intersect(other: Iterable<LongRange>): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun LongRange.intersect(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun Long.intersect(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun Collection<Long>.intersect(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun Iterable<Long>.intersect(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

@JvmName("LongRangeIterableLongersectWithLongRange")
infix fun Iterable<LongRange>.intersect(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun Long.intersect(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun Collection<Long>.intersect(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun Iterable<Long>.intersect(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

@JvmName("LongRangeIterableLongersectWithLongRangeSet")
infix fun Iterable<LongRange>.intersect(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.retainAll(other) }

infix fun LongRange.subtract(other: Long): LongRangeSet =
  LongRangeSet(this).also { it.remove(other) }

infix fun LongRange.subtract(other: Collection<Long>): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun LongRange.subtract(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun LongRange.subtract(other: Iterable<Long>): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

@JvmName("LongRangeSubtractLongRangeIterable")
infix fun LongRange.subtract(other: Iterable<LongRange>): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun LongRange.subtract(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun Long.subtract(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun Collection<Long>.subtract(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun Iterable<Long>.subtract(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

@JvmName("LongRangeIterableSubtractLongRange")
infix fun Iterable<LongRange>.subtract(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun Long.subtract(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun Collection<Long>.subtract(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun Iterable<Long>.subtract(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

@JvmName("LongRangeIterableSubtractLongRangeSet")
infix fun Iterable<LongRange>.subtract(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.removeAll(other) }

infix fun LongRange.union(other: Long): LongRangeSet =
  LongRangeSet(this).also { it.add(other) }

infix fun LongRange.union(other: Collection<Long>): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun LongRange.union(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun LongRange.union(other: Iterable<Long>): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

@JvmName("LongRangeUnionWithLongRangeIterable")
infix fun LongRange.union(other: Iterable<LongRange>): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun LongRange.union(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun Long.union(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun Collection<Long>.union(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun Iterable<Long>.union(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

@JvmName("LongRangeIterableUnionWithLongRange")
infix fun Iterable<LongRange>.union(other: LongRange): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun Long.union(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun Collection<Long>.union(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

infix fun Iterable<Long>.union(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }

@JvmName("LongRangeIterableUnionWithLongRangeSet")
infix fun Iterable<LongRange>.union(other: LongRangeSet): LongRangeSet =
  LongRangeSet(this).also { it.addAll(other) }
