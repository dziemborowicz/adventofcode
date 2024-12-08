inline fun IntRange.binarySearch(comparison: (Int) -> Int): Int {
  var low = first
  var high = last
  while (low <= high) {
    val mid = (low + high).ushr(1)
    val cmp = comparison(mid)
    when {
      cmp < 0 -> low = mid + 1
      cmp > 0 -> high = mid - 1
      else -> return mid
    }
  }
  return (-(low + 1))
}

inline fun IntRange.binarySearchLow(comparison: (Int) -> Int): Int {
  check(!isEmpty())
  val result = binarySearch(comparison)
  return if (result < 0) {
    -result - 2
  } else {
    result
  }
}

inline fun IntRange.binarySearchHigh(comparison: (Int) -> Int): Int {
  check(!isEmpty())
  val result = binarySearch(comparison)
  return if (result < 0) {
    -result - 1
  } else {
    result
  }
}

inline fun <K : Comparable<K>> IntRange.binarySearchBy(key: K, selector: (Int) -> K): Int =
  binarySearch { selector(it).compareTo(key) }

inline fun <K : Comparable<K>> IntRange.binarySearchLowBy(key: K, selector: (Int) -> K): Int =
  binarySearchLow { selector(it).compareTo(key) }

inline fun <K : Comparable<K>> IntRange.binarySearchHighBy(key: K, selector: (Int) -> K): Int =
  binarySearchHigh { selector(it).compareTo(key) }

inline fun LongRange.binarySearch(comparison: (Long) -> Int): Long {
  var low = first
  var high = last
  while (low <= high) {
    val mid = (low + high).ushr(1)
    val cmp = comparison(mid)
    when {
      cmp < 0 -> low = mid + 1
      cmp > 0 -> high = mid - 1
      else -> return mid
    }
  }
  return (-(low + 1))
}

inline fun LongRange.binarySearchLow(comparison: (Long) -> Int): Long {
  check(!isEmpty())
  val result = binarySearch(comparison)
  return if (result < 0) {
    -result - 2
  } else {
    result
  }
}

inline fun LongRange.binarySearchHigh(comparison: (Long) -> Int): Long {
  check(!isEmpty())
  val result = binarySearch(comparison)
  return if (result < 0) {
    -result - 1
  } else {
    result
  }
}

inline fun <K : Comparable<K>> LongRange.binarySearchBy(key: K, selector: (Long) -> K): Long =
  binarySearch { selector(it).compareTo(key) }

inline fun <K : Comparable<K>> LongRange.binarySearchLowBy(key: K, selector: (Long) -> K): Long =
  binarySearchLow { selector(it).compareTo(key) }

inline fun <K : Comparable<K>> LongRange.binarySearchHighBy(key: K, selector: (Long) -> K): Long =
  binarySearchHigh { selector(it).compareTo(key) }
