fun <V> cacheOf() = hashMapOf<Any?, V>()

fun cacheKey(vararg keys: Any?): Any? {
  return when (keys.size) {
    0 -> Unit
    1 -> keys[0]
    2 -> Pair(keys[0], keys[1])
    3 -> Triple(keys[0], keys[1], keys[2])
    4 -> Quadruple(keys[0], keys[1], keys[2], keys[3])
    5 -> Quintuple(keys[0], keys[1], keys[2], keys[3], keys[4])
    else -> keys.toList()
  }
}

operator fun <V> MutableMap<Any?, V>.get(
  firstKey: Any?,
  secondKey: Any?,
  vararg restOfKeys: Any?,
): V? = this[cacheKey(firstKey, secondKey, *restOfKeys)]

fun <V> MutableMap<Any?, V>.getOrDefault(
  firstKey: Any?,
  secondKey: Any?,
  vararg restOfKeys: Any?,
  defaultValue: V,
): V {
  return getOrDefault(cacheKey(firstKey, secondKey, *restOfKeys), defaultValue)
}

inline fun <V> MutableMap<Any?, V>.getOrElse(
  firstKey: Any?,
  secondKey: Any?,
  vararg restOfKeys: Any?,
  defaultValue: () -> V,
): V {
  return getOrElse(cacheKey(firstKey, secondKey, *restOfKeys), defaultValue)
}

inline fun <V> MutableMap<Any?, V>.getOrPut(
  firstKey: Any?,
  secondKey: Any?,
  vararg restOfKeys: Any?,
  defaultValue: () -> V,
): V {
  return getOrPut(cacheKey(firstKey, secondKey, *restOfKeys), defaultValue)
}

fun <V> MutableMap<Any?, V>.getValue(
  firstKey: Any?,
  secondKey: Any?,
  vararg restOfKeys: Any?,
): V {
  return getValue(cacheKey(firstKey, secondKey, *restOfKeys))
}

fun <V : Comparable<V>> MutableMap<Any?, V>.setMin(vararg keys: Any?, value: V): Boolean {
  val prevValue = this[cacheKey(*keys)]
  if (prevValue == null || value.compareTo(prevValue) < 0) {
    this[cacheKey(*keys)] = value
    return true
  }
  return false
}

fun <K, V : Comparable<V>> MutableMap<K, V>.setMin(key: K, value: V): Boolean {
  val prevValue = this[key]
  if (prevValue == null || value.compareTo(prevValue) < 0) {
    this[key] = value
    return true
  }
  return false
}

fun <V : Comparable<V>> MutableMap<Any?, V>.setMax(vararg keys: Any?, value: V): Boolean {
  val prevValue = this[cacheKey(*keys)]
  if (prevValue == null || value.compareTo(prevValue) > 0) {
    this[cacheKey(*keys)] = value
    return true
  }
  return false
}

fun <K, V : Comparable<V>> MutableMap<K, V>.setMax(key: K, value: V): Boolean {
  val prevValue = this[key]
  if (prevValue == null || value.compareTo(prevValue) > 0) {
    this[key] = value
    return true
  }
  return false
}

operator fun <V> MutableMap<Any?, V>.set(
  firstKey: Any?,
  secondKey: Any?,
  vararg restOfKeys: Any?,
  value: V,
) {
  this[cacheKey(firstKey, secondKey, *restOfKeys)] = value
}
