fun Iterable<Char>.asString(): String = joinToString("")

fun String.indicesOf(element: Char): List<Int> = indicesOf { it == element }

inline fun String.indicesOf(predicate: (Char) -> Boolean): List<Int> =
  indices.filter { predicate(this[it]) }

operator fun List<String>.get(index: Index): Char = this[index.row][index.column]

fun List<String>.getOrDefault(index: Index, default: Char): Char = getOrNull(index) ?: default

fun List<String>.getOrNull(index: Index): Char? =
  getOrNull(index.row)?.getOrNull(index.column)
