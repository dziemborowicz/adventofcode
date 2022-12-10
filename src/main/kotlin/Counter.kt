class Counter<T> {

  data class Count<T>(val element: T, val count: Int)

  private val counts = hashMapOf<T, Int>()

  fun countAll(): Int = counts.values.sum()

  fun countUnique(): Int = counts.values.count { it != 0 }

  fun counts(): List<Count<T>> =
    counts.entries.filter { it.value != 0 }.map { (element, count) -> Count(element, count) }

  fun elements(): List<T> = buildList {
    for ((element, count) in counts.entries) {
      check(count >= 0) { "Counts must be non-negative. Count for $element was $count." }
      repeat(count) { add(element) }
    }
  }

  fun uniqueElements(): Set<T> = counts.keys.toSet()

  fun min(): Count<T> = counts().minBy { it.count }

  fun max(): Count<T> = counts().maxBy { it.count }

  fun minOrNull(): Count<T>? = counts().minByOrNull { it.count }

  fun maxOrNull(): Count<T>? = counts().maxByOrNull { it.count }

  operator fun contains(element: T): Boolean = this[element] != 0

  operator fun get(element: T): Int = counts[element] ?: 0

  operator fun set(element: T, count: Int) {
    counts[element] = count
  }

  operator fun plus(element: T): Counter<T> =
    Counter(this).also { it += element }

  operator fun plus(counts: Counter<T>): Counter<T> =
    Counter(this).also { it += counts }

  operator fun plus(elements: Iterable<T>): Counter<T> =
    Counter(this).also { it += elements }

  @JvmName("plusMapEntries")
  operator fun plus(counts: Iterable<Map.Entry<T, Int>>): Counter<T> =
    Counter(this).also { it += counts }

  @JvmName("plusPairs")
  operator fun plus(counts: Iterable<Pair<T, Int>>): Counter<T> =
    Counter(this).also { it += counts }

  operator fun plus(counts: Map<T, Int>): Counter<T> =
    Counter(this).also { it += counts }

  operator fun plusAssign(element: T) {
    increment(element)
  }

  operator fun plusAssign(counts: Counter<T>): Unit = incrementAll(counts.counts)

  operator fun plusAssign(elements: Iterable<T>): Unit = incrementAll(elements)

  @JvmName("plusAssignMapEntries")
  operator fun plusAssign(counts: Iterable<Map.Entry<T, Int>>): Unit = incrementAll(counts)

  @JvmName("plusAssignPairs")
  operator fun plusAssign(counts: Iterable<Pair<T, Int>>): Unit = incrementAll(counts)

  operator fun plusAssign(counts: Map<T, Int>): Unit = incrementAll(counts)

  operator fun minus(element: T): Counter<T> =
    Counter(this).also { it -= element }

  operator fun minus(counts: Counter<T>): Counter<T> =
    Counter(this).also { it -= counts }

  operator fun minus(elements: Iterable<T>): Counter<T> =
    Counter(this).also { it -= elements }

  @JvmName("minusMapEntries")
  operator fun minus(counts: Iterable<Map.Entry<T, Int>>): Counter<T> =
    Counter(this).also { it -= counts }

  @JvmName("minusPairs")
  operator fun minus(counts: Iterable<Pair<T, Int>>): Counter<T> =
    Counter(this).also { it -= counts }

  operator fun minus(counts: Map<T, Int>): Counter<T> =
    Counter(this).also { it -= counts }

  operator fun minusAssign(element: T) {
    decrement(element)
  }

  operator fun minusAssign(counts: Counter<T>): Unit = decrementAll(counts.counts)

  operator fun minusAssign(elements: Iterable<T>): Unit = decrementAll(elements)

  @JvmName("minusAssignMapEntries")
  operator fun minusAssign(counts: Iterable<Map.Entry<T, Int>>): Unit = decrementAll(counts)

  @JvmName("minusAssignPairs")
  operator fun minusAssign(counts: Iterable<Pair<T, Int>>): Unit = decrementAll(counts)

  operator fun minusAssign(counts: Map<T, Int>): Unit = decrementAll(counts)

  fun increment(element: T, count: Int = 1): Int = update(element) { it + count }

  fun incrementAll(counts: Counter<T>): Unit = incrementAll(counts.counts)

  fun incrementAll(elements: Iterable<T>) {
    elements.forEach { increment(it) }
  }

  @JvmName("incrementMapEntries")
  fun incrementAll(counts: Iterable<Map.Entry<T, Int>>) {
    counts.forEach { (element, count) -> increment(element, count) }
  }

  @JvmName("incrementPairs")
  fun incrementAll(counts: Iterable<Pair<T, Int>>) {
    counts.forEach { (element, count) -> increment(element, count) }
  }

  fun incrementAll(counts: Map<T, Int>): Unit = incrementAll(counts.entries)

  fun decrement(element: T, count: Int = 1): Int = increment(element, -count)

  fun decrementAll(counts: Counter<T>): Unit = decrementAll(counts.counts)

  fun decrementAll(elements: Iterable<T>) {
    elements.forEach { decrement(it) }
  }

  @JvmName("decrementMapEntries")
  fun decrementAll(counts: Iterable<Map.Entry<T, Int>>) {
    counts.forEach { (element, count) -> decrement(element, count) }
  }

  @JvmName("decrementPairs")
  fun decrementAll(counts: Iterable<Pair<T, Int>>) {
    counts.forEach { (element, count) -> decrement(element, count) }
  }

  fun decrementAll(counts: Map<T, Int>): Unit = decrementAll(counts.entries)

  fun incrementAllBy(n: Int): Unit = counts.replaceAll { _, count -> count + n }

  fun decrementAllBy(n: Int): Unit = counts.replaceAll { _, count -> count - n }

  fun multiplyAllBy(n: Int): Unit = counts.replaceAll { _, count -> count * n }

  fun divideAllBy(n: Int): Unit = counts.replaceAll { _, count -> count / n }

  fun modAllBy(n: Int): Unit = counts.replaceAll { _, count -> count % n }

  fun update(element: T, transformer: (Int) -> Int): Int =
    counts.compute(element) { _, prev -> transformer(prev ?: 0) }!!

  fun updateAll(transformer: (T, Int) -> Int): Unit = counts.replaceAll(transformer)

  override fun equals(other: Any?): Boolean {
    if (other !is Counter<*>) return false
    return counts.entries == other.counts.entries
  }

  override fun hashCode(): Int = counts.hashCode()

  fun toMap(): Map<T, Int> = counts.toMap()

  fun toMutableMap(): MutableMap<T, Int> = counts.toMutableMap()

  override fun toString(): String = counts.toString()
}

fun <T> Counter(element: T): Counter<T> = Counter<T>().also { it.increment(element) }

fun <T> Counter(element: T, count: Int): Counter<T> =
  Counter<T>().also { it.increment(element, count) }

fun <T> Counter(counts: Counter<T>): Counter<T> = Counter<T>().also { it.incrementAll(counts) }

fun <T> Counter(elements: Iterable<T>): Counter<T> =
  Counter<T>().also { it.incrementAll(elements) }

@JvmName("CounterFromMapEntries")
fun <T> Counter(counts: Iterable<Map.Entry<T, Int>>): Counter<T> =
  Counter<T>().also { it.incrementAll(counts) }

@JvmName("CounterFromPairs")
fun <T> Counter(counts: Iterable<Pair<T, Int>>): Counter<T> =
  Counter<T>().also { it.incrementAll(counts) }

fun <T> Counter(counts: Map<T, Int>): Counter<T> =
  Counter<T>().also { it.incrementAll(counts) }

fun <T> T.toCounter(): Counter<T> = Counter(this)

fun <T> Counter<T>.toCounter(): Counter<T> = Counter(this)

fun <T> Iterable<T>.toCounter(): Counter<T> = Counter(this)

@JvmName("mapEntriesToCounter")
fun <T> Iterable<Map.Entry<T, Int>>.toCounter(): Counter<T> = Counter(this)

@JvmName("pairsToCounter")
fun <T> Iterable<Pair<T, Int>>.toCounter(): Counter<T> = Counter(this)

fun <T> Map<T, Int>.toCounter(): Counter<T> = Counter(this)
