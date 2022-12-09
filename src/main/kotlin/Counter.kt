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
