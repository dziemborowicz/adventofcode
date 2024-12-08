import java.util.PriorityQueue

fun <T> Iterable<T>.combinations(length: Int): Sequence<List<T>> {
  val pool = this as? List<T> ?: toList()
  return sequence {
    if (length > pool.size) return@sequence
    val indices = IntArray(length) { it }
    while (true) {
      yield(indices.map { pool[it] })
      var i = length
      do {
        i--
        if (i == -1) return@sequence
      } while (indices[i] == i + pool.size - length)
      indices[i]++
      for (j in i + 1..<length) {
        indices[j] = indices[j - 1] + 1
      }
    }
  }
}

fun <T> Iterable<T>.permutations(length: Int? = null): Sequence<List<T>> {
  val pool = this as? List<T> ?: toList()
  return sequence {
    val r = length ?: pool.size
    if (r > pool.size) return@sequence
    val indices = IntArray(pool.size) { it }
    val cycles = IntArray(r) { pool.size - it }
    yield(indices.map { pool[it] })
    if (pool.size == 0) return@sequence
    cycle@ while (true) {
      for (i in r - 1 downTo 0) {
        cycles[i]--
        if (cycles[i] == 0) {
          val temp = indices[i]
          for (j in i..<pool.size - 1) indices[j] = indices[j + 1]
          indices[pool.size - 1] = temp
          cycles[i] = pool.size - i
        } else {
          val j = pool.size - cycles[i]
          indices[i] = indices[j].also { indices[j] = indices[i] }
          yield(indices.map { pool[it] })
          continue@cycle
        }
      }
      return@sequence
    }
  }
}

inline fun <reified T> Iterable<T>.permutationsWithReplacement(length: Int? = null): Sequence<List<T>> {
  if (length == 0) return sequenceOf()
  val pool = this as? List<T> ?: toList()
  return sequence {
    val size = length ?: pool.size
    val permutation = Array(size) { pool.first() }
    val indices = IntArray(size)
    while (true) {
      indices.forEachIndexed { i, x -> permutation[i] = pool[x] }
      yield(permutation.toList())
      var i = 0
      while (true) {
        indices[i]++
        if (indices[i] < pool.size) break
        indices[i++] = 0
        if (i == size) return@sequence
      }
    }
  }
}

operator fun <T> List<T>.component6(): T = get(5)
operator fun <T> List<T>.component7(): T = get(6)
operator fun <T> List<T>.component8(): T = get(7)
operator fun <T> List<T>.component9(): T = get(8)

fun <T> List<T>.copy(): List<T> = toList()

@JvmName("copyListOfList")
fun <T> List<List<T>>.copy(): List<List<T>> = map { it.toList() }

@JvmName("copyListOfListOfList")
fun <T> List<List<List<T>>>.copy(): List<List<List<T>>> = map { it.copy() }

fun Iterable<*>.countDistinct(): Int = toSet().size

fun <T> Iterable<T>.longCount(): Long {
  var count = 0L
  for (it in this) count++
  return count
}

inline fun <T> Iterable<T>.longCount(predicate: (T) -> Boolean): Long {
  var count = 0L
  for (it in this) {
    if (predicate(it)) {
      count++
    }
  }
  return count
}

fun <T> List<T>.dropLast(): List<T> = dropLast(1)

inline fun <T> List<T>.firstIndexed(predicate: (Int, T) -> Boolean): T {
  for (i in indices) {
    if (predicate(i, this[i])) {
      return this[i]
    }
  }
  throw NoSuchElementException()
}

inline fun <T> List<T>.firstIndexedOrNull(predicate: (Int, T) -> Boolean): T? {
  for (i in indices) {
    if (predicate(i, this[i])) {
      return this[i]
    }
  }
  return null
}

fun <T> List<T>.getWrapped(index: Int): T = get(index.mod(size))

fun <T> MutableList<T>.setWrapped(index: Int, element: T): T = set(index.mod(size), element)

fun <T> Iterable<T>.indexOfOrThrow(element: T): Int =
  indexOf(element).also { if (it == -1) throw NoSuchElementException() }

fun <T> Iterable<T>.lastIndexOfOrThrow(element: T): Int =
  lastIndexOf(element).also { if (it == -1) throw NoSuchElementException() }

inline fun <T> Iterable<T>.indexOfFirstOrThrow(predicate: (T) -> Boolean): Int =
  indexOfFirst(predicate).also { if (it == -1) throw NoSuchElementException() }

inline fun <T> Iterable<T>.indexOfLastOrThrow(predicate: (T) -> Boolean): Int =
  indexOfLast(predicate).also { if (it == -1) throw NoSuchElementException() }

inline fun <T> Iterable<T>.indexOfSingle(predicate: (T) -> Boolean): Int {
  val firstIndex = indexOfFirst(predicate)
  if (firstIndex == -1) return -1
  val lastIndex = indexOfLast(predicate)
  if (firstIndex != lastIndex) return -1
  return firstIndex
}

inline fun <T> Iterable<T>.indexOfSingleOrThrow(predicate: (T) -> Boolean): Int =
  indexOfSingle(predicate).also { if (it == -1) throw NoSuchElementException() }

fun <T> List<T>.indicesOf(element: T): List<Int> = indicesOf { it == element }

inline fun <T> List<T>.indicesOf(predicate: (T) -> Boolean): List<Int> =
  indices.filter { predicate(this[it]) }

inline fun <R : Any> CharSequence.lastNotNullOf(transform: (Char) -> R?): R =
  reversed().firstNotNullOf(transform)

inline fun <R : Any> CharSequence.lastNotNullOfOrNull(transform: (Char) -> R?): R? =
  reversed().firstNotNullOfOrNull(transform)

inline fun <T, R : Any> Iterable<T>.lastNotNullOf(transform: (T) -> R?): R =
  reversed().firstNotNullOf(transform)

inline fun <T, R : Any> Iterable<T>.lastNotNullOfOrNull(transform: (T) -> R?): R? =
  reversed().firstNotNullOfOrNull(transform)

fun <T> List<T>.middle(): T = this[middleIndex()]

fun List<*>.middleIndex(): Int {
  require(size.isOdd) { "List must have an odd number of elements." }
  return size / 2
}

fun <T> List<T>.subList(indices: IntRange): List<T> {
  return if (indices.step == 1) {
    subList(indices.first, indices.last + 1)
  } else {
    indices.map { this[it] }
  }
}

fun <T> MutableSet<T>.removeFirst(): T {
  val iterator = iterator()
  return iterator.next().also { iterator.remove() }
}

fun <T> MutableSet<T>.removeFirstOrNull(): T? = if (isNotEmpty()) removeFirst() else null

fun <T> MutableSet<T>.removeFirst(element: T): T = removeFirstThat { it == element }

fun <T> MutableSet<T>.removeFirstOrNull(element: T): T? = removeFirstOrNullThat { it == element }

inline fun <T> MutableSet<T>.removeFirstThat(predicate: (T) -> Boolean): T {
  val iterator = iterator()
  while (iterator.hasNext()) {
    val element = iterator.next()
    if (predicate(element)) {
      iterator.remove()
      return element
    }
  }
  throw NoSuchElementException()
}

inline fun <T> MutableSet<T>.removeFirstOrNullThat(predicate: (T) -> Boolean): T? {
  val iterator = iterator()
  while (iterator.hasNext()) {
    val element = iterator.next()
    if (predicate(element)) {
      iterator.remove()
      return element
    }
  }
  return null
}

fun <T> MutableSet<T>.removeLast(): T {
  val iterator = iterator()
  var element: T
  do {
    element = iterator.next()
  } while (iterator.hasNext())
  iterator.remove()
  return element
}

fun <T> MutableSet<T>.removeLastOrNull(): T? {
  val iterator = iterator()
  if (!iterator.hasNext()) return null
  var element: T
  do {
    element = iterator.next()
  } while (iterator.hasNext())
  iterator.remove()
  return element
}

fun <T> MutableSet<T>.removeSingle(): T {
  return if (size == 1) {
    removeFirst()
  } else {
    error("Must contain exactly one element.")
  }
}

fun <T> MutableSet<T>.removeSingleOrNull(): T? {
  return if (size == 1) {
    removeFirst()
  } else {
    null
  }
}

inline fun <reified T> MutableSet<T>.removeSingleThat(predicate: (T) -> Boolean): T =
  single(predicate).also { remove(it) }

inline fun <reified T> MutableSet<T>.removeSingleOrNullThat(predicate: (T) -> Boolean): T? =
  singleOrNull(predicate)?.also { remove(it) }

fun <T> MutableSet<T>.removeMany(n: Int): List<T> {
  if (size < n) throw NoSuchElementException()
  return removeUpTo(n)
}

fun <T> MutableSet<T>.removeUpTo(n: Int): List<T> {
  val iterator = iterator()
  return buildList {
    while (size < n && iterator.hasNext()) {
      add(iterator.next())
      iterator.remove()
    }
  }
}

inline fun <T> MutableSet<T>.removeWhile(predicate: (T) -> Boolean): List<T> {
  val iterator = iterator()
  return buildList {
    while (iterator.hasNext()) {
      val element = iterator.next()
      if (!predicate(element)) break
      add(element)
      iterator.remove()
    }
  }
}

fun <T> MutableList<T>.removeFirst(element: T): T = removeFirstThat { it == element }

fun <T> MutableList<T>.removeFirstOrNull(element: T): T? = removeFirstOrNullThat { it == element }

inline fun <T> MutableList<T>.removeFirstThat(predicate: (T) -> Boolean): T {
  for (i in indices) {
    val element = this[i]
    if (predicate(element)) {
      removeAt(i)
      return element
    }
  }
  throw NoSuchElementException()
}

inline fun <T> MutableList<T>.removeFirstOrNullThat(predicate: (T) -> Boolean): T? {
  for (i in indices) {
    val element = this[i]
    if (predicate(element)) {
      removeAt(i)
      return element
    }
  }
  return null
}

fun <T> MutableList<T>.removeLast(element: T): T = removeLastThat { it == element }

fun <T> MutableList<T>.removeLastOrNull(element: T): T? = removeLastOrNullThat { it == element }

inline fun <T> MutableList<T>.removeLastThat(predicate: (T) -> Boolean): T {
  for (i in lastIndex downTo 0) {
    val element = this[i]
    if (predicate(element)) {
      removeAt(i)
      return element
    }
  }
  throw NoSuchElementException()
}

inline fun <T> MutableList<T>.removeLastOrNullThat(predicate: (T) -> Boolean): T? {
  for (i in lastIndex downTo 0) {
    val element = this[i]
    if (predicate(element)) {
      removeAt(i)
      return element
    }
  }
  return null
}

fun <T> MutableList<T>.removeSingle(): T {
  if (size != 1) error("Must contain exactly one element.")
  return removeFirst()
}

fun <T> MutableList<T>.removeSingleOrNull(): T? = if (size == 1) removeSingle() else null

fun <T> MutableList<T>.removeSingle(element: T): T = removeSingleThat { it == element }

fun <T> MutableList<T>.removeSingleOrNull(element: T): T? = removeSingleOrNullThat { it == element }

inline fun <T> MutableList<T>.removeSingleThat(predicate: (T) -> Boolean): T {
  val index = indexOfSingleOrThrow(predicate)
  return this[index].also { removeAt(index) }
}

inline fun <T> MutableList<T>.removeSingleOrNullThat(predicate: (T) -> Boolean): T? {
  val index = indexOfSingle(predicate)
  if (index == -1) return null
  return this[index].also { removeAt(index) }
}

fun <T> MutableList<T>.removeMany(n: Int): List<T> {
  if (size < n) throw NoSuchElementException()
  return removeUpTo(n)
}

fun <T> MutableList<T>.removeManyLast(n: Int): List<T> {
  if (size < n) throw NoSuchElementException()
  return removeUpToLast(n)
}

fun <T> MutableList<T>.removeUpTo(n: Int): List<T> {
  return take(n).also { subList(0, minOf(n, size)).clear() }
}

fun <T> MutableList<T>.removeUpToLast(n: Int): List<T> {
  return takeLast(n).also { subList(maxOf(0, size - n), size).clear() }
}

inline fun <T> MutableList<T>.removeWhile(predicate: (T) -> Boolean): List<T> {
  val index = indexOfFirst { !predicate(it) }
  if (index == -1) return toList().also { clear() }
  return removeMany(index)
}

inline fun <T> MutableList<T>.removeLastWhile(predicate: (T) -> Boolean): List<T> {
  val index = indexOfLast { !predicate(it) }
  if (index == -1) return toList().also { clear() }
  return removeManyLast(size - index - 1)
}

fun <T> MutableList<T>.rotateRight(offset: Int) {
  val effectiveOffset = offset.mod(size)
  if (effectiveOffset != 0) {
    subList(0, size - effectiveOffset).reverse()
    subList(size - effectiveOffset, size).reverse()
    reverse()
  }
}

fun <T> MutableList<T>.rotateLeft(offset: Int) = rotateRight(-offset)

fun <T> List<T>.rotatedRight(offset: Int): List<T> = toMutableList().also { it.rotateRight(offset) }

fun <T> List<T>.rotatedLeft(offset: Int): List<T> = rotatedRight(-offset)

fun <T> Iterable<T>.split(vararg delimiters: T): List<List<T>> = splitBy { it in delimiters }

fun Iterable<String>.splitByBlank() = splitBy { it.isBlank() }

fun <T> Iterable<T>.splitByNull() = splitBy { it == null }

inline fun <T> Iterable<T>.splitBy(predicate: (T) -> Boolean): List<List<T>> {
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

fun <T> Iterable<Set<T>>.intersect() = reduce { a, b -> a intersect b }

inline fun <T, U> Iterable<T>.intersectOf(selector: (T) -> Set<U>) = map(selector).intersect()

fun <T> Iterable<Set<T>>.union() = fold(setOf<T>()) { a, b -> a union b }

inline fun <T, U> Iterable<T>.unionOf(selector: (T) -> Set<U>) = map(selector).union()

fun <T> dequeOf() = ArrayDeque<T>()

fun <T> dequeOf(element: T) = ArrayDeque<T>().also { it.add(element) }

fun <T> dequeOf(vararg elements: T) = ArrayDeque(elements.toList())

fun <T> Iterable<T>.toDeque(): ArrayDeque<T> = ArrayDeque<T>().also { it.addAll(this) }

fun <T> List<T>.toDeque(): ArrayDeque<T> = ArrayDeque(this)

fun String.toDeque(): ArrayDeque<Char> = toList().toDeque()

fun <K, V> Iterable<Pair<K, V>>.toHashMap(): HashMap<K, V> = hashMapOf<K, V>().also { it.putAll(this) }

fun <T> priorityQueueOf() = PriorityQueue<T>()

fun <T> priorityQueueOf(element: T, comparator: Comparator<in T>? = null) =
  PriorityQueue(comparator).also { it.add(element) }

fun <T> priorityQueueOf(vararg elements: T) = PriorityQueue(elements.toList())

fun <T> priorityQueueWith(comparator: Comparator<in T>) = PriorityQueue(comparator)

fun <T> Iterable<T>.toPriorityQueue(comparator: Comparator<in T>? = null): PriorityQueue<T> =
  PriorityQueue(comparator).also { it.addAll(this) }

fun <T> List<T>.toPriorityQueue(comparator: Comparator<in T>? = null): PriorityQueue<T> =
  PriorityQueue(comparator).also { it.addAll(this) }

fun String.toPriorityQueue(comparator: Comparator<in Char>? = null): PriorityQueue<Char> =
  toList().toPriorityQueue(comparator)
