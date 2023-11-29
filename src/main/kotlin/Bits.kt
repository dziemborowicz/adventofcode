fun Int.getBit(position: Int): Int = (this shr position) and 1

fun Int.getBitBoolean(position: Int): Boolean = getBit(position).toBoolean()

fun Int.setBit(position: Int, value: Int): Int {
  return when (value) {
    0 -> clearBit(position)
    1 -> setBit(position)
    else -> throw IllegalArgumentException("value must be 0 or 1.")
  }
}

fun Int.setBit(position: Int, value: Boolean = true): Int {
  if (!value) return clearBit(position)
  return this or (1 shl position)
}

fun Int.clearBit(position: Int): Int = this and (1 shl position).inv()

fun Long.getBit(position: Int): Long = (this shr position) and 1

fun Long.getBitBoolean(position: Int): Boolean = getBit(position).toBoolean()

fun Long.setBit(position: Int, value: Int): Long = setBit(position, value.toLong())

fun Long.setBit(position: Int, value: Long): Long {
  return when (value) {
    0L -> clearBit(position)
    1L -> setBit(position)
    else -> throw IllegalArgumentException("value must be 0 or 1.")
  }
}

fun Long.setBit(position: Int, value: Boolean = true): Long {
  if (!value) return clearBit(position)
  return this or (1L shl position)
}

fun Long.clearBit(position: Int): Long = this and (1L shl position).inv()
