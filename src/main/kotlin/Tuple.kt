import java.io.Serializable

data class Quadruple<out A, out B, out C, out D>(
  val first: A,
  val second: B,
  val third: C,
  val fourth: D,
) : Serializable {
  override fun toString(): String = "($first, $second, $third, $fourth)"
}

data class Quintuple<out A, out B, out C, out D, out E>(
  val first: A,
  val second: B,
  val third: C,
  val fourth: D,
  val fifth: E,
) : Serializable {
  override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

fun <T> Quadruple<T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth)

fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> = listOf(first, second, third, fourth, fifth)

fun <T> Iterable<T>.toPair(): Pair<T, T> {
  require(count() == 2) { "Must contain exactly two elements." }
  val it = iterator()
  return Pair(it.next(), it.next())
}

fun <T> Iterable<T>.toTriple(): Triple<T, T, T> {
  require(count() == 3) { "Must contain exactly three elements." }
  val it = iterator()
  return Triple(it.next(), it.next(), it.next())
}

fun <T> Iterable<T>.toQuadruple(): Quadruple<T, T, T, T> {
  require(count() == 4) { "Must contain exactly four elements." }
  val it = iterator()
  return Quadruple(it.next(), it.next(), it.next(), it.next())
}

fun <T> Iterable<T>.toQuintuple(): Quintuple<T, T, T, T, T> {
  require(count() == 5) { "Must contain exactly five elements." }
  val it = iterator()
  return Quintuple(it.next(), it.next(), it.next(), it.next(), it.next())
}

@JvmName("flattenIterableOfPair")
fun <T> Iterable<Pair<T, T>>.flatten(): List<T> = flatMap { it.toList() }

@JvmName("flattenIterableOfTriple")
fun <T> Iterable<Triple<T, T, T>>.flatten(): List<T> = flatMap { it.toList() }

@JvmName("flattenIterableOfQuadruple")
fun <T> Iterable<Quadruple<T, T, T, T>>.flatten(): List<T> = flatMap { it.toList() }

@JvmName("flattenIterableOfQuintuple")
fun <T> Iterable<Quintuple<T, T, T, T, T>>.flatten(): List<T> = flatMap { it.toList() }
