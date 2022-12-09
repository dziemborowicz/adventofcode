import java.math.BigInteger

fun Boolean.toBigInteger(): BigInteger = if (this) BigInteger.ONE else BigInteger.ZERO

fun Boolean.toCharLowercase(): Char = if (this) 't' else 'f'

fun Boolean.toCharUppercase(): Char = if (this) 'T' else 'F'

fun Boolean.toInt(): Int = if (this) 1 else 0

fun Boolean.toLong(): Long = if (this) 1L else 0L

fun BigInteger.toBoolean(): Boolean = this != BigInteger.ZERO

fun Char.toBoolean(): Boolean = this == 't' || this == 'T'

fun Int.toBoolean(): Boolean = this != 0

fun Long.toBoolean(): Boolean = this != 0L
