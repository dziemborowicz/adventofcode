fun <T> Iterable<T>.only(): T {
  require(count() == 1) { "Must contain exactly one element." }
  return first()
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
