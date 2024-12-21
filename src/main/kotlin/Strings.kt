fun Iterable<Char>.asString(): String = joinToString("")

fun String.indicesOf(element: Char): List<Int> = indicesOf { it == element }

inline fun String.indicesOf(predicate: (Char) -> Boolean): List<Int> =
  indices.filter { predicate(this[it]) }

operator fun List<String>.get(index: Index): Char = this[index.row][index.column]

fun List<String>.getOrDefault(index: Index, default: Char): Char = getOrNull(index) ?: default

fun List<String>.getOrNull(index: Index): Char? = getOrNull(index.row)?.getOrNull(index.column)

fun String.getWrapped(index: Int): Char = get(index.mod(length))

fun String.splitByBlank(): List<String> = this.split("\n\n")


operator fun CharSequence.component1(): Char = get(0)
operator fun CharSequence.component2(): Char = get(1)
operator fun CharSequence.component3(): Char = get(2)
operator fun CharSequence.component4(): Char = get(3)
operator fun CharSequence.component5(): Char = get(4)
operator fun CharSequence.component6(): Char = get(5)
operator fun CharSequence.component7(): Char = get(6)
operator fun CharSequence.component8(): Char = get(7)
operator fun CharSequence.component9(): Char = get(8)

fun CharSequence.second(): Char = get(1)
fun CharSequence.third(): Char = get(2)
fun CharSequence.fourth(): Char = get(3)
fun CharSequence.fifth(): Char = get(4)
