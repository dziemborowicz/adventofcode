class IntRangeSet : MutableSet<Int> {

  private val ranges = mutableListOf<IntRange>()

  override val size: Int
    get() {
      val count = count()
      if (count > Int.MAX_VALUE) throw ArithmeticException("Overflow")
      return count.toInt()
    }

  fun count(): Long = ranges.sumOf { it.count }

  override fun contains(element: Int): Boolean = ranges.any { element in it }

  override fun containsAll(elements: Collection<Int>): Boolean = elements.all { contains(it) }

  fun containsAll(elements: IntRange): Boolean {
    if (elements.isEmpty()) return true
    return ranges.any { it.containsAll(elements) }
  }

  fun containsAll(elements: Iterable<Int>): Boolean = elements.all { contains(it) }

  @JvmName("containsAllIntRangeIterable")
  fun containsAll(elements: Iterable<IntRange>): Boolean = elements.all { containsAll(it) }

  fun containsAll(elements: IntRangeSet): Boolean = containsAll(elements.ranges)

  fun containsAny(elements: Collection<Int>): Boolean = elements.any { contains(it) }

  fun containsAny(elements: IntRange): Boolean = ranges.any { it.containsAny(elements) }

  fun containsAny(elements: Iterable<Int>): Boolean = elements.any { contains(it) }

  @JvmName("containsAnyIntRangeIterable")
  fun containsAny(elements: Iterable<IntRange>): Boolean = elements.any { containsAny(it) }

  fun containsAny(elements: IntRangeSet): Boolean = containsAny(elements.ranges)

  override fun isEmpty(): Boolean = ranges.isEmpty()

  override fun iterator(): MutableIterator<Int> {
    val sequence = sequence { ranges.forEach { yieldAll(it) } }.iterator()
    return object : MutableIterator<Int> {
      override fun hasNext(): Boolean = sequence.hasNext()
      override fun next(): Int = sequence.next()
      override fun remove() = throw UnsupportedOperationException()
    }
  }

  fun ranges(): List<IntRange> = ranges.toList()

  override fun add(element: Int): Boolean = addAll(element..element)

  override fun addAll(elements: Collection<Int>): Boolean = elements.map { add(it) }.any { it }

  fun addAll(elements: IntRange): Boolean {
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

  fun addAll(elements: Iterable<Int>): Boolean = elements.map { add(it) }.any { it }

  @JvmName("addAllIntRangeIterable")
  fun addAll(elements: Iterable<IntRange>): Boolean = elements.map { addAll(it) }.any { it }

  fun addAll(elements: IntRangeSet): Boolean = elements.ranges.map { addAll(it) }.any { it }

  override fun clear(): Unit = ranges.clear()

  override fun remove(element: Int): Boolean = removeAll(element..element)

  override fun removeAll(elements: Collection<Int>): Boolean =
    elements.map { remove(it) }.any { it }

  fun removeAll(elements: IntRange): Boolean {
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

  fun removeAll(elements: Iterable<Int>): Boolean = elements.map { remove(it) }.any { it }

  @JvmName("removeAllIntRangeIterable")
  fun removeAll(elements: Iterable<IntRange>): Boolean = elements.map { removeAll(it) }.any { it }

  fun removeAll(elements: IntRangeSet): Boolean = elements.ranges.map { removeAll(it) }.any { it }

  fun retain(element: Int): Boolean = retainAll(IntRangeSet(element))

  override fun retainAll(elements: Collection<Int>): Boolean = retainAll(IntRangeSet(elements))

  fun retainAll(elements: IntRange): Boolean = retainAll(IntRangeSet(elements))

  fun retainAll(elements: Iterable<Int>): Boolean = retainAll(IntRangeSet(elements))

  @JvmName("retainAllIntRangeIterable")
  fun retainAll(elements: Iterable<IntRange>): Boolean = retainAll(IntRangeSet(elements))

  fun retainAll(elements: IntRangeSet): Boolean = removeAll(elements.inverted())

  fun invert() {
    val oldRanges = ranges.toList()
    ranges.clear()
    ranges.add(Int.MIN_VALUE..Int.MAX_VALUE)
    oldRanges.forEach { removeAll(it) }
  }

  fun inverted(): IntRangeSet = IntRangeSet(this).also { it.invert() }

  infix fun intersect(other: Int): IntRangeSet =
    IntRangeSet(this).also { it.retain(other) }

  infix fun intersect(other: Collection<Int>): IntRangeSet =
    IntRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: IntRange): IntRangeSet =
    IntRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: Iterable<Int>): IntRangeSet =
    IntRangeSet(this).also { it.retainAll(other) }

  @JvmName("intersectIntRangeIterable")
  infix fun intersect(other: Iterable<IntRange>): IntRangeSet =
    IntRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: IntRangeSet): IntRangeSet =
    IntRangeSet(this).also { it.retainAll(other) }

  infix fun subtract(other: Int): IntRangeSet =
    IntRangeSet(this).also { it.remove(other) }

  infix fun subtract(other: Collection<Int>): IntRangeSet =
    IntRangeSet(this).also { it.removeAll(other) }

  infix fun subtract(other: IntRange): IntRangeSet =
    IntRangeSet(this).also { it.removeAll(other) }

  infix fun subtract(other: Iterable<Int>): IntRangeSet =
    IntRangeSet(this).also { it.removeAll(other) }

  @JvmName("subtractIntRangeIterable")
  infix fun subtract(other: Iterable<IntRange>): IntRangeSet =
    IntRangeSet(this).also { it.removeAll(other) }

  infix fun subtract(other: IntRangeSet): IntRangeSet =
    IntRangeSet(this).also { it.removeAll(other) }

  infix fun union(other: Int): IntRangeSet =
    IntRangeSet(this).also { it.add(other) }

  infix fun union(other: Collection<Int>): IntRangeSet =
    IntRangeSet(this).also { it.addAll(other) }

  infix fun union(other: IntRange): IntRangeSet =
    IntRangeSet(this).also { it.addAll(other) }

  infix fun union(other: Iterable<Int>): IntRangeSet =
    IntRangeSet(this).also { it.addAll(other) }

  @JvmName("unionIntRangeIterable")
  infix fun union(other: Iterable<IntRange>): IntRangeSet =
    IntRangeSet(this).also { it.addAll(other) }

  infix fun union(other: IntRangeSet): IntRangeSet =
    IntRangeSet(this).also { it.addAll(other) }

  override fun equals(other: Any?): Boolean {
    if (other !is IntRangeSet) return false
    return ranges == other.ranges
  }

  override fun hashCode(): Int = ranges.hashCode()

  override fun toString(): String = ranges.toString()
}

fun IntRangeSet(element: Int): IntRangeSet =
  IntRangeSet().also { it.add(element) }

fun IntRangeSet(elements: Collection<Int>): IntRangeSet =
  IntRangeSet().also { it.addAll(elements) }

fun IntRangeSet(elements: IntRange): IntRangeSet =
  IntRangeSet().also { it.addAll(elements) }

fun IntRangeSet(elements: Iterable<Int>): IntRangeSet =
  IntRangeSet().also { it.addAll(elements) }

@JvmName("IntRangeSetOfIntRangeIterable")
fun IntRangeSet(elements: Iterable<IntRange>): IntRangeSet =
  IntRangeSet().also { it.addAll(elements) }

fun IntRangeSet(elements: IntRangeSet): IntRangeSet =
  IntRangeSet().also { it.addAll(elements) }

infix fun IntRange.intersect(other: Int): IntRangeSet =
  IntRangeSet(this).also { it.retain(other) }

infix fun IntRange.intersect(other: Collection<Int>): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun IntRange.intersect(other: Iterable<Int>): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

@JvmName("IntRangeIntersectWithIntRangeIterable")
infix fun IntRange.intersect(other: Iterable<IntRange>): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun IntRange.intersect(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun Int.intersect(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun Collection<Int>.intersect(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun Iterable<Int>.intersect(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

@JvmName("IntRangeIterableIntersectWithIntRange")
infix fun Iterable<IntRange>.intersect(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun Int.intersect(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun Collection<Int>.intersect(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun Iterable<Int>.intersect(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

@JvmName("IntRangeIterableIntersectWithIntRangeSet")
infix fun Iterable<IntRange>.intersect(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.retainAll(other) }

infix fun IntRange.subtract(other: Int): IntRangeSet =
  IntRangeSet(this).also { it.remove(other) }

infix fun IntRange.subtract(other: Collection<Int>): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun IntRange.subtract(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun IntRange.subtract(other: Iterable<Int>): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

@JvmName("IntRangeSubtractIntRangeIterable")
infix fun IntRange.subtract(other: Iterable<IntRange>): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun IntRange.subtract(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun Int.subtract(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun Collection<Int>.subtract(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun Iterable<Int>.subtract(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

@JvmName("IntRangeIterableSubtractIntRange")
infix fun Iterable<IntRange>.subtract(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun Int.subtract(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun Collection<Int>.subtract(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun Iterable<Int>.subtract(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

@JvmName("IntRangeIterableSubtractIntRangeSet")
infix fun Iterable<IntRange>.subtract(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.removeAll(other) }

infix fun IntRange.union(other: Int): IntRangeSet =
  IntRangeSet(this).also { it.add(other) }

infix fun IntRange.union(other: Collection<Int>): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun IntRange.union(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun IntRange.union(other: Iterable<Int>): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

@JvmName("IntRangeUnionWithIntRangeIterable")
infix fun IntRange.union(other: Iterable<IntRange>): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun IntRange.union(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun Int.union(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun Collection<Int>.union(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun Iterable<Int>.union(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

@JvmName("IntRangeIterableUnionWithIntRange")
infix fun Iterable<IntRange>.union(other: IntRange): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun Int.union(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun Collection<Int>.union(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

infix fun Iterable<Int>.union(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }

@JvmName("IntRangeIterableUnionWithIntRangeSet")
infix fun Iterable<IntRange>.union(other: IntRangeSet): IntRangeSet =
  IntRangeSet(this).also { it.addAll(other) }
