class CharRangeSet : MutableSet<Char> {

  private val ranges = mutableListOf<CharRange>()

  override val size: Int
    get() {
      val count = count()
      if (count > Int.MAX_VALUE) throw ArithmeticException("Overflow")
      return count.toInt()
    }

  fun count(): Long = ranges.sumOf { it.count }

  override fun contains(element: Char): Boolean = ranges.any { element in it }

  override fun containsAll(elements: Collection<Char>): Boolean = elements.all { contains(it) }

  fun containsAll(elements: CharRange): Boolean {
    if (elements.isEmpty()) return true
    return ranges.any { it.containsAll(elements) }
  }

  fun containsAll(elements: Iterable<Char>): Boolean = elements.all { contains(it) }

  @JvmName("containsAllCharRangeIterable")
  fun containsAll(elements: Iterable<CharRange>): Boolean = elements.all { containsAll(it) }

  fun containsAll(elements: CharRangeSet): Boolean = containsAll(elements.ranges)

  fun containsAny(elements: Collection<Char>): Boolean = elements.any { contains(it) }

  fun containsAny(elements: CharRange): Boolean = ranges.any { it.containsAny(elements) }

  fun containsAny(elements: Iterable<Char>): Boolean = elements.any { contains(it) }

  @JvmName("containsAnyCharRangeIterable")
  fun containsAny(elements: Iterable<CharRange>): Boolean = elements.any { containsAny(it) }

  fun containsAny(elements: CharRangeSet): Boolean = containsAny(elements.ranges)

  override fun isEmpty(): Boolean = ranges.isEmpty()

  override fun iterator(): MutableIterator<Char> {
    val sequence = sequence { ranges.forEach { yieldAll(it) } }.iterator()
    return object : MutableIterator<Char> {
      override fun hasNext(): Boolean = sequence.hasNext()
      override fun next(): Char = sequence.next()
      override fun remove() = throw UnsupportedOperationException()
    }
  }

  fun ranges(): List<CharRange> = ranges.toList()

  override fun add(element: Char): Boolean = addAll(element..element)

  override fun addAll(elements: Collection<Char>): Boolean = elements.map { add(it) }.any { it }

  fun addAll(elements: CharRange): Boolean {
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

  fun addAll(elements: Iterable<Char>): Boolean = elements.map { add(it) }.any { it }

  @JvmName("addAllCharRangeIterable")
  fun addAll(elements: Iterable<CharRange>): Boolean = elements.map { addAll(it) }.any { it }

  fun addAll(elements: CharRangeSet): Boolean = elements.ranges.map { addAll(it) }.any { it }

  override fun clear(): Unit = ranges.clear()

  override fun remove(element: Char): Boolean = removeAll(element..element)

  override fun removeAll(elements: Collection<Char>): Boolean =
    elements.map { remove(it) }.any { it }

  fun removeAll(elements: CharRange): Boolean {
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

  fun removeAll(elements: Iterable<Char>): Boolean = elements.map { remove(it) }.any { it }

  @JvmName("removeAllCharRangeIterable")
  fun removeAll(elements: Iterable<CharRange>): Boolean = elements.map { removeAll(it) }.any { it }

  fun removeAll(elements: CharRangeSet): Boolean = elements.ranges.map { removeAll(it) }.any { it }

  fun retain(element: Char): Boolean = retainAll(CharRangeSet(element))

  override fun retainAll(elements: Collection<Char>): Boolean = retainAll(CharRangeSet(elements))

  fun retainAll(elements: CharRange): Boolean = retainAll(CharRangeSet(elements))

  fun retainAll(elements: Iterable<Char>): Boolean = retainAll(CharRangeSet(elements))

  @JvmName("retainAllCharRangeIterable")
  fun retainAll(elements: Iterable<CharRange>): Boolean = retainAll(CharRangeSet(elements))

  fun retainAll(elements: CharRangeSet): Boolean = removeAll(elements.inverted())

  fun invert() {
    val oldRanges = ranges.toList()
    ranges.clear()
    ranges.add(Char.MIN_VALUE..Char.MAX_VALUE)
    oldRanges.forEach { removeAll(it) }
  }

  fun inverted(): CharRangeSet = CharRangeSet(this).also { it.invert() }

  infix fun intersect(other: Char): CharRangeSet =
    CharRangeSet(this).also { it.retain(other) }

  infix fun intersect(other: Collection<Char>): CharRangeSet =
    CharRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: CharRange): CharRangeSet =
    CharRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: Iterable<Char>): CharRangeSet =
    CharRangeSet(this).also { it.retainAll(other) }

  @JvmName("intersectCharRangeIterable")
  infix fun intersect(other: Iterable<CharRange>): CharRangeSet =
    CharRangeSet(this).also { it.retainAll(other) }

  infix fun intersect(other: CharRangeSet): CharRangeSet =
    CharRangeSet(this).also { it.retainAll(other) }

  fun max(): Char = ranges.last().last

  fun maxOrNull(): Char? = ranges.lastOrNull()?.last

  fun min(): Char = ranges.first().first

  fun minOrNull(): Char? = ranges.firstOrNull()?.first

  operator fun minusAssign(other: Char) {
    remove(other)
  }

  operator fun minusAssign(other: Collection<Char>) {
    removeAll(other)
  }

  operator fun minusAssign(other: CharRange) {
    removeAll(other)
  }

  operator fun minusAssign(other: Iterable<Char>) {
    removeAll(other)
  }

  @JvmName("minusAssignCharRangeIterable")
  operator fun minusAssign(other: Iterable<CharRange>) {
    removeAll(other)
  }

  operator fun minusAssign(other: CharRangeSet) {
    removeAll(other)
  }

  operator fun minus(other: Char): CharRangeSet =
    CharRangeSet(this).also { it.remove(other) }

  operator fun minus(other: Collection<Char>): CharRangeSet =
    CharRangeSet(this).also { it.removeAll(other) }

  operator fun minus(other: CharRange): CharRangeSet =
    CharRangeSet(this).also { it.removeAll(other) }

  operator fun minus(other: Iterable<Char>): CharRangeSet =
    CharRangeSet(this).also { it.removeAll(other) }

  @JvmName("minusCharRangeIterable")
  operator fun minus(other: Iterable<CharRange>): CharRangeSet =
    CharRangeSet(this).also { it.removeAll(other) }

  operator fun minus(other: CharRangeSet): CharRangeSet =
    CharRangeSet(this).also { it.removeAll(other) }

  operator fun plusAssign(other: Char) {
    add(other)
  }

  operator fun plusAssign(other: Collection<Char>) {
    addAll(other)
  }

  operator fun plusAssign(other: CharRange) {
    addAll(other)
  }

  operator fun plusAssign(other: Iterable<Char>) {
    addAll(other)
  }

  @JvmName("plusAssignCharRangeIterable")
  operator fun plusAssign(other: Iterable<CharRange>) {
    addAll(other)
  }

  operator fun plusAssign(other: CharRangeSet) {
    addAll(other)
  }

  operator fun plus(other: Char): CharRangeSet =
    CharRangeSet(this).also { it.add(other) }

  operator fun plus(other: Collection<Char>): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  operator fun plus(other: CharRange): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  operator fun plus(other: Iterable<Char>): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  @JvmName("plusCharRangeIterable")
  operator fun plus(other: Iterable<CharRange>): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  operator fun plus(other: CharRangeSet): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  infix fun subtract(other: Char): CharRangeSet = this - other

  infix fun subtract(other: Collection<Char>): CharRangeSet = this - other

  infix fun subtract(other: CharRange): CharRangeSet = this - other

  infix fun subtract(other: Iterable<Char>): CharRangeSet = this - other

  @JvmName("subtractCharRangeIterable")
  infix fun subtract(other: Iterable<CharRange>): CharRangeSet = this - other

  infix fun subtract(other: CharRangeSet): CharRangeSet = this - other

  infix fun union(other: Char): CharRangeSet =
    CharRangeSet(this).also { it.add(other) }

  infix fun union(other: Collection<Char>): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  infix fun union(other: CharRange): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  infix fun union(other: Iterable<Char>): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  @JvmName("unionCharRangeIterable")
  infix fun union(other: Iterable<CharRange>): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  @JvmName("unionCharRangeSetIterable")
  infix fun union(other: Iterable<CharRangeSet>): CharRangeSet =
    CharRangeSet(this).also { set -> other.forEach { set.addAll(it) } }

  infix fun union(other: CharRangeSet): CharRangeSet =
    CharRangeSet(this).also { it.addAll(other) }

  override fun equals(other: Any?): Boolean {
    if (other !is CharRangeSet) return false
    return ranges == other.ranges
  }

  override fun hashCode(): Int = ranges.hashCode()

  override fun toString(): String = ranges.toString()
}

fun CharRangeSet(element: Char): CharRangeSet =
  CharRangeSet().also { it.add(element) }

fun CharRangeSet(elements: Collection<Char>): CharRangeSet =
  CharRangeSet().also { it.addAll(elements) }

fun CharRangeSet(elements: CharRange): CharRangeSet =
  CharRangeSet().also { it.addAll(elements) }

fun CharRangeSet(elements: Iterable<Char>): CharRangeSet =
  CharRangeSet().also { it.addAll(elements) }

@JvmName("CharRangeSetOfCharRangeIterable")
fun CharRangeSet(elements: Iterable<CharRange>): CharRangeSet =
  CharRangeSet().also { it.addAll(elements) }

@JvmName("CharRangeSetOfCharRangeSetIterable")
fun CharRangeSet(elements: Iterable<CharRangeSet>): CharRangeSet =
  CharRangeSet().also { set -> elements.forEach { set.addAll(it) } }

fun CharRangeSet(elements: CharRangeSet): CharRangeSet =
  CharRangeSet().also { it.addAll(elements) }

fun Char.toCharRangeSet(): CharRangeSet =
  CharRangeSet().also { it.add(this) }

fun Collection<Char>.toCharRangeSet(): CharRangeSet =
  CharRangeSet().also { it.addAll(this) }

fun CharRange.toCharRangeSet(): CharRangeSet =
  CharRangeSet().also { it.addAll(this) }

fun Iterable<Char>.toCharRangeSet(): CharRangeSet =
  CharRangeSet().also { it.addAll(this) }

@JvmName("CharRangeIterableToCharRangeSet")
fun Iterable<CharRange>.toCharRangeSet(): CharRangeSet =
  CharRangeSet().also { it.addAll(this) }

fun CharRangeSet.toCharRangeSet(): CharRangeSet =
  CharRangeSet().also { it.addAll(this) }

infix fun CharRange.intersect(other: Char): CharRangeSet =
  CharRangeSet(this).also { it.retain(other) }

infix fun CharRange.intersect(other: Collection<Char>): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun CharRange.intersect(other: Iterable<Char>): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

@JvmName("CharRangeIntersectWithCharRangeIterable")
infix fun CharRange.intersect(other: Iterable<CharRange>): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun CharRange.intersect(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun Char.intersect(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun Collection<Char>.intersect(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun Iterable<Char>.intersect(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

@JvmName("CharRangeIterableCharersectWithCharRange")
infix fun Iterable<CharRange>.intersect(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun Char.intersect(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun Collection<Char>.intersect(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun Iterable<Char>.intersect(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

@JvmName("CharRangeIterableCharersectWithCharRangeSet")
infix fun Iterable<CharRange>.intersect(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.retainAll(other) }

infix fun CharRange.subtract(other: Char): CharRangeSet =
  CharRangeSet(this).also { it.remove(other) }

infix fun CharRange.subtract(other: Collection<Char>): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun CharRange.subtract(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun CharRange.subtract(other: Iterable<Char>): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

@JvmName("CharRangeSubtractCharRangeIterable")
infix fun CharRange.subtract(other: Iterable<CharRange>): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun CharRange.subtract(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun Char.subtract(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun Collection<Char>.subtract(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun Iterable<Char>.subtract(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

@JvmName("CharRangeIterableSubtractCharRange")
infix fun Iterable<CharRange>.subtract(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun Char.subtract(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun Collection<Char>.subtract(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun Iterable<Char>.subtract(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

@JvmName("CharRangeIterableSubtractCharRangeSet")
infix fun Iterable<CharRange>.subtract(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.removeAll(other) }

infix fun CharRange.union(other: Char): CharRangeSet =
  CharRangeSet(this).also { it.add(other) }

infix fun CharRange.union(other: Collection<Char>): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun CharRange.union(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun CharRange.union(other: Iterable<Char>): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

@JvmName("CharRangeUnionWithCharRangeIterable")
infix fun CharRange.union(other: Iterable<CharRange>): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun CharRange.union(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun Char.union(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun Collection<Char>.union(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun Iterable<Char>.union(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

@JvmName("CharRangeIterableUnionWithCharRange")
infix fun Iterable<CharRange>.union(other: CharRange): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun Char.union(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun Collection<Char>.union(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

infix fun Iterable<Char>.union(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

@JvmName("CharRangeIterableUnionWithCharRangeSet")
infix fun Iterable<CharRange>.union(other: CharRangeSet): CharRangeSet =
  CharRangeSet(this).also { it.addAll(other) }

@JvmName("CharRangeIterableUnion")
fun Iterable<CharRange>.union(): CharRangeSet =
  CharRangeSet(this)

@JvmName("CharRangeSetIterableUnion")
fun Iterable<CharRangeSet>.union(): CharRangeSet =
  CharRangeSet(this)
