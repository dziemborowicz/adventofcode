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

fun <T> Iterable<T>.only(): T {
  require(count() == 1) { "Must contain exactly one element." }
  return first()
}

fun <T> Iterable<T>.only(predicate: (T) -> Boolean): T {
  require(count(predicate) == 1) { "Must contain exactly one matching element." }
  return first(predicate)
}

fun <T> Iterable<T>.indexOfOnly(predicate: (T) -> Boolean): Int {
  require(count(predicate) == 1) { "Must contain exactly one matching element." }
  return indexOfFirst(predicate)
}

fun <T> MutableList<T>.removeOnly(element: T): T = removeOnlyIf { it == element }

fun <T> MutableList<T>.removeOnlyIf(predicate: (T) -> Boolean): T {
  val index = indexOfOnly(predicate)
  if (index < 0) throw NoSuchElementException()
  return removeAt(index)
}

fun <T> MutableList<T>.removeFirst(element: T): T = removeFirstIf { it == element }

fun <T> MutableList<T>.removeFirstIf(predicate: (T) -> Boolean): T {
  val index = indexOfFirst(predicate)
  if (index < 0) throw NoSuchElementException()
  return removeAt(index)
}

fun <T> MutableList<T>.removeLast(element: T): T = removeLastIf { it == element }

fun <T> MutableList<T>.removeLastIf(predicate: (T) -> Boolean): T {
  val index = indexOfLast(predicate)
  if (index < 0) throw NoSuchElementException()
  return removeAt(index)
}

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
