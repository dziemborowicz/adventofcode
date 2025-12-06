class DoubleCounter<T> {

  data class Count<T>(val element: T, val count: Double)

  private val counts = hashMapOf<T, Double>()

  fun clear(): Unit = counts.clear()

  fun countAll(): Double = counts.values.sum()

  fun countUnique(): Double = counts.values.count { it != 0.0 }.toDouble()

  fun counts(): List<Count<T>> =
    counts.entries.filter { it.value != 0.0 }.map { (element, count) -> Count(element, count) }

  fun elements(): List<T> = buildList {
    for ((element, count) in counts.entries) {
      check(count >= 0) { "Counts must be non-negative. Count for $element was $count." }
      val c = count.toLong()
      check(c.toDouble() == count) { "Counts must be integers. Count for $element was $count." }
      (1L..c).forEach { _ -> add(element) }
    }
  }

  fun uniqueElements(): Set<T> = counts.keys.toSet()

  fun min(): Count<T> = counts().minBy { it.count }

  fun max(): Count<T> = counts().maxBy { it.count }

  fun minOrNull(): Count<T>? = counts().minByOrNull { it.count }

  fun maxOrNull(): Count<T>? = counts().maxByOrNull { it.count }

  operator fun contains(element: T): Boolean = this[element] != 0.0

  operator fun get(element: T): Double = counts[element] ?: 0.0

  operator fun set(element: T, count: Double) {
    counts[element] = count
  }

  operator fun plus(element: T): DoubleCounter<T> =
    DoubleCounter(this).also { it += element }

  operator fun plus(counts: DoubleCounter<T>): DoubleCounter<T> =
    DoubleCounter(this).also { it += counts }

  operator fun plus(elements: Iterable<T>): DoubleCounter<T> =
    DoubleCounter(this).also { it += elements }

  @JvmName("plusMapEntries")
  operator fun plus(counts: Iterable<Map.Entry<T, Double>>): DoubleCounter<T> =
    DoubleCounter(this).also { it += counts }

  @JvmName("plusPairs")
  operator fun plus(counts: Iterable<Pair<T, Double>>): DoubleCounter<T> =
    DoubleCounter(this).also { it += counts }

  operator fun plus(counts: Map<T, Double>): DoubleCounter<T> =
    DoubleCounter(this).also { it += counts }

  operator fun plusAssign(element: T) {
    increment(element)
  }

  operator fun plusAssign(counts: DoubleCounter<T>): Unit = incrementAll(counts.counts)

  operator fun plusAssign(elements: Iterable<T>): Unit = incrementAll(elements)

  @JvmName("plusAssignMapEntries")
  operator fun plusAssign(counts: Iterable<Map.Entry<T, Double>>): Unit = incrementAll(counts)

  @JvmName("plusAssignPairs")
  operator fun plusAssign(counts: Iterable<Pair<T, Double>>): Unit = incrementAll(counts)

  operator fun plusAssign(counts: Map<T, Double>): Unit = incrementAll(counts)

  operator fun minus(element: T): DoubleCounter<T> =
    DoubleCounter(this).also { it -= element }

  operator fun minus(counts: DoubleCounter<T>): DoubleCounter<T> =
    DoubleCounter(this).also { it -= counts }

  operator fun minus(elements: Iterable<T>): DoubleCounter<T> =
    DoubleCounter(this).also { it -= elements }

  @JvmName("minusMapEntries")
  operator fun minus(counts: Iterable<Map.Entry<T, Double>>): DoubleCounter<T> =
    DoubleCounter(this).also { it -= counts }

  @JvmName("minusPairs")
  operator fun minus(counts: Iterable<Pair<T, Double>>): DoubleCounter<T> =
    DoubleCounter(this).also { it -= counts }

  operator fun minus(counts: Map<T, Double>): DoubleCounter<T> =
    DoubleCounter(this).also { it -= counts }

  operator fun minusAssign(element: T) {
    decrement(element)
  }

  operator fun minusAssign(counts: DoubleCounter<T>): Unit = decrementAll(counts.counts)

  operator fun minusAssign(elements: Iterable<T>): Unit = decrementAll(elements)

  @JvmName("minusAssignMapEntries")
  operator fun minusAssign(counts: Iterable<Map.Entry<T, Double>>): Unit = decrementAll(counts)

  @JvmName("minusAssignPairs")
  operator fun minusAssign(counts: Iterable<Pair<T, Double>>): Unit = decrementAll(counts)

  operator fun minusAssign(counts: Map<T, Double>): Unit = decrementAll(counts)

  fun increment(element: T, count: Double = 1.0): Double = update(element) { it + count }

  fun incrementAll(counts: DoubleCounter<T>): Unit = incrementAll(counts.counts)

  fun incrementAll(elements: Iterable<T>) {
    elements.forEach { increment(it) }
  }

  @JvmName("incrementMapEntries")
  fun incrementAll(counts: Iterable<Map.Entry<T, Double>>) {
    counts.forEach { (element, count) -> increment(element, count) }
  }

  @JvmName("incrementPairs")
  fun incrementAll(counts: Iterable<Pair<T, Double>>) {
    counts.forEach { (element, count) -> increment(element, count) }
  }

  fun incrementAll(counts: Map<T, Double>): Unit = incrementAll(counts.entries)

  fun decrement(element: T, count: Double = 1.0): Double = increment(element, -count)

  fun decrementAll(counts: DoubleCounter<T>): Unit = decrementAll(counts.counts)

  fun decrementAll(elements: Iterable<T>) {
    elements.forEach { decrement(it) }
  }

  @JvmName("decrementMapEntries")
  fun decrementAll(counts: Iterable<Map.Entry<T, Double>>) {
    counts.forEach { (element, count) -> decrement(element, count) }
  }

  @JvmName("decrementPairs")
  fun decrementAll(counts: Iterable<Pair<T, Double>>) {
    counts.forEach { (element, count) -> decrement(element, count) }
  }

  fun decrementAll(counts: Map<T, Double>): Unit = decrementAll(counts.entries)

  fun incrementAllBy(n: Double): Unit = counts.replaceAll { _, count -> count + n }

  fun decrementAllBy(n: Double): Unit = counts.replaceAll { _, count -> count - n }

  fun multiplyAllBy(n: Double): Unit = counts.replaceAll { _, count -> count * n }

  fun divideAllBy(n: Double): Unit = counts.replaceAll { _, count -> count / n }

  fun modAllBy(n: Double): Unit = counts.replaceAll { _, count -> count % n }

  fun update(element: T, transformer: (Double) -> Double): Double =
    counts.compute(element) { _, prev -> transformer(prev ?: 0.0) }!!

  fun updateAll(transformer: (T, Double) -> Double): Unit = counts.replaceAll(transformer)

  override fun equals(other: Any?): Boolean {
    if (other !is DoubleCounter<*>) return false
    return counts.entries == other.counts.entries
  }

  override fun hashCode(): Int = counts.hashCode()

  fun toMap(): Map<T, Double> = counts.toMap()

  fun toMutableMap(): MutableMap<T, Double> = counts.toMutableMap()

  override fun toString(): String = counts.toString()
}

fun <T> DoubleCounter(element: T): DoubleCounter<T> = DoubleCounter<T>().also { it.increment(element) }

fun <T> DoubleCounter(element: T, count: Double): DoubleCounter<T> =
  DoubleCounter<T>().also { it.increment(element, count) }

fun <T> DoubleCounter(counts: DoubleCounter<T>): DoubleCounter<T> = DoubleCounter<T>().also { it.incrementAll(counts) }

fun <T> DoubleCounter(elements: Iterable<T>): DoubleCounter<T> =
  DoubleCounter<T>().also { it.incrementAll(elements) }

@JvmName("DoubleCounterFromMapEntries")
fun <T> DoubleCounter(counts: Iterable<Map.Entry<T, Double>>): DoubleCounter<T> =
  DoubleCounter<T>().also { it.incrementAll(counts) }

@JvmName("DoubleCounterFromPairs")
fun <T> DoubleCounter(counts: Iterable<Pair<T, Double>>): DoubleCounter<T> =
  DoubleCounter<T>().also { it.incrementAll(counts) }

fun <T> DoubleCounter(counts: Map<T, Double>): DoubleCounter<T> =
  DoubleCounter<T>().also { it.incrementAll(counts) }

fun <T> T.toDoubleCounter(): DoubleCounter<T> = DoubleCounter(this)

fun <T> DoubleCounter<T>.toDoubleCounter(): DoubleCounter<T> = DoubleCounter(this)

fun <T> Iterable<T>.toDoubleCounter(): DoubleCounter<T> = DoubleCounter(this)

@JvmName("mapEntriesToDoubleCounter")
fun <T> Iterable<Map.Entry<T, Double>>.toDoubleCounter(): DoubleCounter<T> = DoubleCounter(this)

@JvmName("pairsToDoubleCounter")
fun <T> Iterable<Pair<T, Double>>.toDoubleCounter(): DoubleCounter<T> = DoubleCounter(this)

fun <T> Map<T, Double>.toDoubleCounter(): DoubleCounter<T> = DoubleCounter(this)
