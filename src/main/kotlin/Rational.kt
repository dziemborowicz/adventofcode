import java.math.BigDecimal
import java.math.BigInteger

class Rational(numerator: BigInteger, denominator: BigInteger) : Comparable<Any> {

  constructor(numerator: Int) : this(numerator.toBigInteger(), BigInteger.ONE)
  constructor(numerator: Long) : this(numerator.toBigInteger(), BigInteger.ONE)
  constructor(numerator: BigInteger) : this(numerator, BigInteger.ONE)

  constructor(numerator: Int, denominator: Int) : this(
    numerator.toBigInteger(), denominator.toBigInteger()
  )

  constructor(numerator: Long, denominator: Long) : this(
    numerator.toBigInteger(), denominator.toBigInteger()
  )

  val numerator = numerator / gcd(numerator, denominator)
  val denominator = denominator / gcd(numerator, denominator)

  operator fun component1(): BigInteger = denominator
  operator fun component2(): BigInteger = numerator

  override fun compareTo(other: Any): Int {
    val difference = this - when (other) {
      is Rational -> other
      is Int -> Rational(other)
      is Long -> Rational(other)
      is BigInteger -> Rational(other)
      is Float -> fail("Cannot compare $this to Float of $other.")
      is Double -> fail("Cannot compare $this to Double of $other.")
      is BigDecimal -> fail("Cannot compare $this to BigDecimal of $other.")
      else -> fail("Cannot compare $this to $other.")
    }
    return when {
      difference.numerator < BigInteger.ZERO -> -1
      difference.numerator > BigInteger.ZERO -> 1
      else -> 0
    }
  }

  override fun equals(other: Any?): Boolean {
    return when (other) {
      null -> false
      is Rational -> this.numerator == other.numerator && this.denominator == other.denominator
      is Int -> this == Rational(other)
      is Long -> this == Rational(other)
      is BigInteger -> this == Rational(other)
      is Float -> fail("Cannot compare $this to Float of $other.")
      is Double -> fail("Cannot compare $this to Double of $other.")
      is BigDecimal -> fail("Cannot compare $this to BigDecimal of $other.")
      else -> fail("Cannot compare $this to $other.")
    }
  }

  override fun hashCode(): Int = 31 * numerator.hashCode() + denominator.hashCode()

  override fun toString(): String {
    return if (denominator == BigInteger.ONE) {
      numerator.toString()
    } else {
      "$numerator/$denominator"
    }
  }

  companion object {
    val ZERO = Rational(BigInteger.ZERO)
    val ONE = Rational(BigInteger.ONE)
    val TWO = Rational(BigInteger.TWO)
    val TEN = Rational(BigInteger.TEN)
  }
}

infix fun Int.over(denominator: Int): Rational = Rational(this, denominator)
infix fun Long.over(denominator: Long): Rational = Rational(this, denominator)
infix fun BigInteger.over(denominator: BigInteger): Rational = Rational(this, denominator)

fun Int.toRational(): Rational = Rational(this)
fun Long.toRational(): Rational = Rational(this)
fun BigInteger.toRational(): Rational = Rational(this)
fun String.toRational(): Rational {
  val parts = this.split('/')
  return when {
    parts.size == 1 -> Rational(parts.single().toBigInteger())
    parts.size == 2 -> Rational(parts[0].toBigInteger(), parts[1].toBigInteger())
    else -> throw NumberFormatException("Cannot parse \"$this\" to Rational.")
  }
}

fun Rational.toInt(): Int = (this.numerator / this.denominator).toInt()
fun Rational.toLong(): Long = (this.numerator / this.denominator).toLong()
fun Rational.toBigInteger(): BigInteger = this.numerator / this.denominator

fun Rational.toIntExact(): Int {
  check(this.isIntegral()) { "Cannot convert $this to exact Int." }
  check(this.numerator in Int.MIN_VALUE.toBigInteger()..Int.MAX_VALUE.toBigInteger())
  return this.numerator.toInt()
}

fun Rational.toLongExact(): Long {
  check(this.isIntegral()) { "Cannot convert $this to exact Long." }
  check(this.numerator in Long.MIN_VALUE.toBigInteger()..Long.MAX_VALUE.toBigInteger())
  return this.numerator.toLong()
}

fun Rational.toBigIntegerExact(): BigInteger {
  check(this.isIntegral()) { "Cannot convert $this to exact BigInteger." }
  return this.numerator
}

fun Rational.isIntegral(): Boolean = this.denominator == BigInteger.ONE

fun Rational.isNotIntegral(): Boolean = !this.isIntegral()

operator fun Rational.unaryMinus(): Rational = (-this.numerator) over (this.denominator)

operator fun Rational.unaryPlus(): Rational = this

infix operator fun Rational.plus(other: Rational): Rational =
  (this.numerator * other.denominator + other.numerator * this.denominator) over (this.denominator * other.denominator)

infix operator fun Rational.minus(other: Rational): Rational =
  (this.numerator * other.denominator - other.numerator * this.denominator) over (this.denominator * other.denominator)

infix operator fun Rational.times(other: Rational): Rational =
  (this.numerator * other.numerator) over (this.denominator * other.denominator)

infix operator fun Rational.div(other: Rational): Rational =
  this * (other.denominator over other.numerator)

infix operator fun Int.plus(other: Rational): Rational = this.toRational() + other
infix operator fun Long.plus(other: Rational): Rational = this.toRational() + other
infix operator fun BigInteger.plus(other: Rational): Rational = this.toRational() + other
infix operator fun Rational.plus(other: Int): Rational = this + other.toRational()
infix operator fun Rational.plus(other: Long): Rational = this + other.toRational()
infix operator fun Rational.plus(other: BigInteger): Rational = this + other.toRational()

infix operator fun Int.minus(other: Rational): Rational = this.toRational() - other
infix operator fun Long.minus(other: Rational): Rational = this.toRational() - other
infix operator fun BigInteger.minus(other: Rational): Rational = this.toRational() - other
infix operator fun Rational.minus(other: Int): Rational = this - other.toRational()
infix operator fun Rational.minus(other: Long): Rational = this - other.toRational()
infix operator fun Rational.minus(other: BigInteger): Rational = this - other.toRational()

infix operator fun Int.times(other: Rational): Rational = this.toRational() * other
infix operator fun Long.times(other: Rational): Rational = this.toRational() * other
infix operator fun BigInteger.times(other: Rational): Rational = this.toRational() * other
infix operator fun Rational.times(other: Int): Rational = this * other.toRational()
infix operator fun Rational.times(other: Long): Rational = this * other.toRational()
infix operator fun Rational.times(other: BigInteger): Rational = this * other.toRational()

infix operator fun Int.div(other: Rational): Rational = this.toRational() / other
infix operator fun Long.div(other: Rational): Rational = this.toRational() / other
infix operator fun BigInteger.div(other: Rational): Rational = this.toRational() / other
infix operator fun Rational.div(other: Int): Rational = this / other.toRational()
infix operator fun Rational.div(other: Long): Rational = this / other.toRational()
infix operator fun Rational.div(other: BigInteger): Rational = this / other.toRational()

operator fun IntRange.contains(rational: Rational): Boolean =
  rational in (first.toRational()..last.toRational())

operator fun LongRange.contains(rational: Rational): Boolean =
  rational in (first.toRational()..last.toRational())

fun Iterable<Rational>.product(): Rational {
  var sum = Rational.ONE
  for (element in this) {
    sum *= element
  }
  return sum
}

fun Iterable<Rational>.sum(): Rational {
  var sum = Rational.ZERO
  for (element in this) {
    sum += element
  }
  return sum
}

inline fun <T> Iterable<T>.productOfRational(selector: (T) -> Rational): Rational {
  var sum = Rational.ONE
  for (element in this) {
    sum *= selector(element)
  }
  return sum
}

inline fun <T> Iterable<T>.sumOfRational(selector: (T) -> Rational): Rational {
  var sum = Rational.ZERO
  for (element in this) {
    sum += selector(element)
  }
  return sum
}
