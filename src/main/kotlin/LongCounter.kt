class LongCounter<T> {

  data class Count<T>(val element: T, val count: Long)

  private val counts = hashMapOf<T, Long>()

  fun clear(): Unit = counts.clear()

  fun countAll(): Long = counts.values.sum()

  fun countUnique(): Long = counts.values.count { it != 0L }.toLong()

  fun counts(): List<Count<T>> =
    counts.entries.filter { it.value != 0L }.map { (element, count) -> Count(element, count) }

  fun elements(): List<T> = buildList {
    for ((element, count) in counts.entries) {
      check(count >= 0) { "Counts must be non-negative. Count for $element was $count." }
      (1L..count).forEach { _ -> add(element) }
    }
  }

  fun uniqueElements(): Set<T> = counts.keys.toSet()

  fun min(): Count<T> = counts().minBy { it.count }

  fun max(): Count<T> = counts().maxBy { it.count }

  fun minOrNull(): Count<T>? = counts().minByOrNull { it.count }

  fun maxOrNull(): Count<T>? = counts().maxByOrNull { it.count }

  operator fun contains(element: T): Boolean = this[element] != 0L

  operator fun get(element: T): Long = counts[element] ?: 0

  operator fun set(element: T, count: Long) {
    counts[element] = count
  }

  operator fun plus(element: T): LongCounter<T> =
    LongCounter(this).also { it += element }

  operator fun plus(counts: LongCounter<T>): LongCounter<T> =
    LongCounter(this).also { it += counts }

  operator fun plus(elements: Iterable<T>): LongCounter<T> =
    LongCounter(this).also { it += elements }

  @JvmName("plusMapEntries")
  operator fun plus(counts: Iterable<Map.Entry<T, Long>>): LongCounter<T> =
    LongCounter(this).also { it += counts }

  @JvmName("plusPairs")
  operator fun plus(counts: Iterable<Pair<T, Long>>): LongCounter<T> =
    LongCounter(this).also { it += counts }

  operator fun plus(counts: Map<T, Long>): LongCounter<T> =
    LongCounter(this).also { it += counts }

  operator fun plusAssign(element: T) {
    increment(element)
  }

  operator fun plusAssign(counts: LongCounter<T>): Unit = incrementAll(counts.counts)

  operator fun plusAssign(elements: Iterable<T>): Unit = incrementAll(elements)

  @JvmName("plusAssignMapEntries")
  operator fun plusAssign(counts: Iterable<Map.Entry<T, Long>>): Unit = incrementAll(counts)

  @JvmName("plusAssignPairs")
  operator fun plusAssign(counts: Iterable<Pair<T, Long>>): Unit = incrementAll(counts)

  operator fun plusAssign(counts: Map<T, Long>): Unit = incrementAll(counts)

  operator fun minus(element: T): LongCounter<T> =
    LongCounter(this).also { it -= element }

  operator fun minus(counts: LongCounter<T>): LongCounter<T> =
    LongCounter(this).also { it -= counts }

  operator fun minus(elements: Iterable<T>): LongCounter<T> =
    LongCounter(this).also { it -= elements }

  @JvmName("minusMapEntries")
  operator fun minus(counts: Iterable<Map.Entry<T, Long>>): LongCounter<T> =
    LongCounter(this).also { it -= counts }

  @JvmName("minusPairs")
  operator fun minus(counts: Iterable<Pair<T, Long>>): LongCounter<T> =
    LongCounter(this).also { it -= counts }

  operator fun minus(counts: Map<T, Long>): LongCounter<T> =
    LongCounter(this).also { it -= counts }

  operator fun minusAssign(element: T) {
    decrement(element)
  }

  operator fun minusAssign(counts: LongCounter<T>): Unit = decrementAll(counts.counts)

  operator fun minusAssign(elements: Iterable<T>): Unit = decrementAll(elements)

  @JvmName("minusAssignMapEntries")
  operator fun minusAssign(counts: Iterable<Map.Entry<T, Long>>): Unit = decrementAll(counts)

  @JvmName("minusAssignPairs")
  operator fun minusAssign(counts: Iterable<Pair<T, Long>>): Unit = decrementAll(counts)

  operator fun minusAssign(counts: Map<T, Long>): Unit = decrementAll(counts)

  fun increment(element: T, count: Long = 1): Long = update(element) { it + count }

  fun incrementAll(counts: LongCounter<T>): Unit = incrementAll(counts.counts)

  fun incrementAll(elements: Iterable<T>) {
    elements.forEach { increment(it) }
  }

  @JvmName("incrementMapEntries")
  fun incrementAll(counts: Iterable<Map.Entry<T, Long>>) {
    counts.forEach { (element, count) -> increment(element, count) }
  }

  @JvmName("incrementPairs")
  fun incrementAll(counts: Iterable<Pair<T, Long>>) {
    counts.forEach { (element, count) -> increment(element, count) }
  }

  fun incrementAll(counts: Map<T, Long>): Unit = incrementAll(counts.entries)

  fun decrement(element: T, count: Long = 1): Long = increment(element, -count)

  fun decrementAll(counts: LongCounter<T>): Unit = decrementAll(counts.counts)

  fun decrementAll(elements: Iterable<T>) {
    elements.forEach { decrement(it) }
  }

  @JvmName("decrementMapEntries")
  fun decrementAll(counts: Iterable<Map.Entry<T, Long>>) {
    counts.forEach { (element, count) -> decrement(element, count) }
  }

  @JvmName("decrementPairs")
  fun decrementAll(counts: Iterable<Pair<T, Long>>) {
    counts.forEach { (element, count) -> decrement(element, count) }
  }

  fun decrementAll(counts: Map<T, Long>): Unit = decrementAll(counts.entries)

  fun incrementAllBy(n: Long): Unit = counts.replaceAll { _, count -> count + n }

  fun decrementAllBy(n: Long): Unit = counts.replaceAll { _, count -> count - n }

  fun multiplyAllBy(n: Long): Unit = counts.replaceAll { _, count -> count * n }

  fun divideAllBy(n: Long): Unit = counts.replaceAll { _, count -> count / n }

  fun modAllBy(n: Long): Unit = counts.replaceAll { _, count -> count % n }

  fun update(element: T, transformer: (Long) -> Long): Long =
    counts.compute(element) { _, prev -> transformer(prev ?: 0) }!!

  fun updateAll(transformer: (T, Long) -> Long): Unit = counts.replaceAll(transformer)

  override fun equals(other: Any?): Boolean {
    if (other !is LongCounter<*>) return false
    return counts.entries == other.counts.entries
  }

  override fun hashCode(): Int = counts.hashCode()

  fun toMap(): Map<T, Long> = counts.toMap()

  fun toMutableMap(): MutableMap<T, Long> = counts.toMutableMap()

  override fun toString(): String = counts.toString()
}

fun <T> LongCounter(element: T): LongCounter<T> = LongCounter<T>().also { it.increment(element) }

fun <T> LongCounter(element: T, count: Long): LongCounter<T> =
  LongCounter<T>().also { it.increment(element, count) }

fun <T> LongCounter(counts: LongCounter<T>): LongCounter<T> = LongCounter<T>().also { it.incrementAll(counts) }

fun <T> LongCounter(elements: Iterable<T>): LongCounter<T> =
  LongCounter<T>().also { it.incrementAll(elements) }

@JvmName("LongCounterFromMapEntries")
fun <T> LongCounter(counts: Iterable<Map.Entry<T, Long>>): LongCounter<T> =
  LongCounter<T>().also { it.incrementAll(counts) }

@JvmName("LongCounterFromPairs")
fun <T> LongCounter(counts: Iterable<Pair<T, Long>>): LongCounter<T> =
  LongCounter<T>().also { it.incrementAll(counts) }

fun <T> LongCounter(counts: Map<T, Long>): LongCounter<T> =
  LongCounter<T>().also { it.incrementAll(counts) }

fun <T> T.toLongCounter(): LongCounter<T> = LongCounter(this)

fun <T> LongCounter<T>.toLongCounter(): LongCounter<T> = LongCounter(this)

fun <T> Iterable<T>.toLongCounter(): LongCounter<T> = LongCounter(this)

@JvmName("mapEntriesToLongCounter")
fun <T> Iterable<Map.Entry<T, Long>>.toLongCounter(): LongCounter<T> = LongCounter(this)

@JvmName("pairsToLongCounter")
fun <T> Iterable<Pair<T, Long>>.toLongCounter(): LongCounter<T> = LongCounter(this)

fun <T> Map<T, Long>.toLongCounter(): LongCounter<T> = LongCounter(this)
