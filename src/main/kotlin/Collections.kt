import java.math.BigDecimal
import java.math.BigInteger

infix fun Set<*>.equivalent(other: Set<*>): Boolean =
  this.containsAll(other) && other.containsAll(this)

infix fun List<*>.equivalent(other: List<*>): Boolean {
  if (this.size != other.size) return false
  for (i in this.indices) {
    if (this[i] != other[i]) return false
  }
  return true
}

infix fun List<*>.equivalentUnordered(other: List<*>) = this.toSet() equivalent other.toSet()

fun <T> List<T>.getWrapped(index: Int): T = get(index.mod(size))

fun <T> MutableList<T>.setWrapped(index: Int, element: T): T = set(index.mod(size), element)

fun <T> List<T>.middle(): T = this[middleIndex()]

fun List<*>.middleIndex(): Int {
  require(size % 2 == 1) { "List must have an odd number of elements." }
  return size / 2
}

fun <T> Iterable<T>.asDeque() = ArrayDeque<T>().also { it.addAll(this) }

fun <T> List<T>.asDeque() = ArrayDeque(this)

fun <T> Iterable<T>.asPair(): Pair<T, T> {
  require(count() == 2) { "Must contain exactly two elements." }
  val iterator = iterator()
  return Pair(iterator.next(), iterator.next())
}

fun <T> Iterable<T>.asTriple(): Triple<T, T, T> {
  require(count() == 3) { "Must contain exactly three elements." }
  val iterator = iterator()
  return Triple(iterator.next(), iterator.next(), iterator.next())
}

fun Iterable<Char>.asString(): String = joinToString("")

inline fun <T> Iterable<T>.indexOfSingle(predicate: (T) -> Boolean): Int {
  require(count(predicate) == 1) { "Must contain exactly one matching element." }
  return indexOfFirst(predicate)
}

fun <T> MutableList<T>.removeSingle(element: T): T = removeSingleIf { it == element }

inline fun <T> MutableList<T>.removeSingleIf(predicate: (T) -> Boolean): T {
  val index = indexOfSingle(predicate)
  if (index < 0) throw NoSuchElementException()
  return removeAt(index)
}

fun <T> MutableSet<T>.removeFirst(): T {
  val iterator = iterator()
  return iterator.next().also { iterator.remove() }
}

fun <T> MutableList<T>.removeFirst(element: T): T = removeFirstIf { it == element }

inline fun <T> MutableList<T>.removeFirstIf(predicate: (T) -> Boolean): T {
  val index = indexOfFirst(predicate)
  if (index < 0) throw NoSuchElementException()
  return removeAt(index)
}

fun <T> MutableList<T>.removeLast(element: T): T = removeLastIf { it == element }

inline fun <T> MutableList<T>.removeLastIf(predicate: (T) -> Boolean): T {
  val index = indexOfLast(predicate)
  if (index < 0) throw NoSuchElementException()
  return removeAt(index)
}

fun <T> ArrayDeque<T>.takeAndRemove(n: Int): List<T> {
  return mutableListOf<T>().also { result ->
    repeat(n) { result.add(removeFirst()) }
  }
}

fun <T> ArrayDeque<T>.takeAndRemoveWhile(predicate: (T) -> Boolean): List<T> {
  return mutableListOf<T>().also { result ->
    while (isNotEmpty()) {
      val element = first()
      if (!predicate(element)) break
      result.add(element)
      removeFirst()
    }
  }
}

fun <T> ArrayDeque<T>.takeAndRemoveLast(n: Int): List<T> {
  return mutableListOf<T>().also { result ->
    repeat(n) { result.add(removeLast()) }
    result.reverse()
  }
}

fun <T> ArrayDeque<T>.takeAndRemoveLastWhile(predicate: (T) -> Boolean): List<T> {
  return mutableListOf<T>().also { result ->
    while (isNotEmpty()) {
      val element = last()
      if (!predicate(element)) break
      result.add(element)
      removeLast()
    }
    result.reverse()
  }
}

fun <K> Map<K, Int>.getCount(key: K) = getOrDefault(key, 0)

fun <K> MutableMap<K, Int>.increment(key: K, amount: Int = 1) =
  compute(key) { _, prev -> (prev ?: 0) + amount }

fun <K> MutableMap<K, Int>.decrement(key: K, amount: Int = 1) = increment(key, -amount)

fun <K> MutableMap<K, Int>.incrementAll(other: MutableMap<K, Int>) =
  other.forEach { (key, count) -> increment(key, count) }

fun <K> MutableMap<K, Int>.incrementAll(other: Iterable<Pair<K, Int>>) =
  other.forEach { (key, count) -> increment(key, count) }

fun <K> Map<K, Long>.getCount(key: K) = getOrDefault(key, 0)

fun <K> MutableMap<K, Long>.increment(key: K, amount: Long = 1) =
  compute(key) { _, prev -> (prev ?: 0) + amount }

fun <K> MutableMap<K, Long>.decrement(key: K, amount: Long = 1) = increment(key, -amount)

@JvmName("incrementAllLong")
fun <K> MutableMap<K, Long>.incrementAll(other: MutableMap<K, Long>) =
  other.forEach { (key, count) -> increment(key, count) }

@JvmName("incrementAllLong")
fun <K> MutableMap<K, Long>.incrementAll(other: Iterable<Pair<K, Long>>) =
  other.forEach { (key, count) -> increment(key, count) }

fun <T> Iterable<T>.toCountMap(): MutableMap<T, Int> {
  val result = mutableMapOf<T, Int>()
  forEach { result.increment(it) }
  return result
}

@JvmName("pairsToCountMap")
fun <T> Iterable<Pair<T, Int>>.toCountMap(): MutableMap<T, Int> {
  val result = mutableMapOf<T, Int>()
  forEach { result.increment(it.first, it.second) }
  return result
}

fun <T> Iterable<T>.toLongCountMap(): MutableMap<T, Long> {
  val result = mutableMapOf<T, Long>()
  forEach { result.increment(it) }
  return result
}

@JvmName("pairsToLongCountMap")
fun <T> Iterable<Pair<T, Long>>.toLongCountMap(): MutableMap<T, Long> {
  val result = mutableMapOf<T, Long>()
  forEach { result.increment(it.first, it.second) }
  return result
}

fun Iterable<BigDecimal>.product(): BigDecimal = reduce { a, b -> a * b }

fun Iterable<BigInteger>.product(): BigInteger = reduce { a, b -> a * b }

fun Iterable<Double>.product(): Double = reduce { a, b -> a * b }

fun Iterable<Int>.product(): Int = reduce { a, b -> a * b }

fun Iterable<Long>.product(): Long = reduce { a, b -> a * b }

fun Iterable<String>.splitByBlank() = split { it.isBlank() }

fun <T> Iterable<T>.splitByNull() = split { it == null }

fun <T> Iterable<T>.split(vararg delimiters: T): List<List<T>> = split { it in delimiters }

fun <T> Iterable<T>.split(predicate: (T) -> Boolean): List<List<T>> {
  val result = mutableListOf<List<T>>()
  var current = mutableListOf<T>()
  forEach {
    if (predicate(it)) {
      result.add(current)
      current = mutableListOf<T>()
    } else {
      current.add(it)
    }
  }
  result.add(current)
  return result
}
