fun Iterable<Char>.asString(): String = joinToString("")

fun String.indicesOf(element: Char): List<Int> = indicesOf { it == element }

inline fun String.indicesOf(predicate: (Char) -> Boolean): List<Int> =
  indices.filter { predicate(this[it]) }
