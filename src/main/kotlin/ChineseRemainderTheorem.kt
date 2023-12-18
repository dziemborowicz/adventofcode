import java.math.BigInteger

/** Represents an `x ≡ a (mod n)` congruence. */
data class Congruence(val a: BigInteger, val n: BigInteger) {
  constructor(a: BigInteger, n: Int) : this(a, n.toBigInteger())
  constructor(a: BigInteger, n: Long) : this(a, n.toBigInteger())
  constructor(a: Int, n: BigInteger) : this(a.toBigInteger(), n)
  constructor(a: Int, n: Int) : this(a.toBigInteger(), n.toBigInteger())
  constructor(a: Int, n: Long) : this(a.toBigInteger(), n.toBigInteger())
  constructor(a: Long, n: BigInteger) : this(a.toBigInteger(), n)
  constructor(a: Long, n: Int) : this(a.toBigInteger(), n.toBigInteger())
  constructor(a: Long, n: Long) : this(a.toBigInteger(), n.toBigInteger())

  override fun toString() = "x ≡ $a (mod $n)"
}

@JvmName("IntListToCongruence")
fun List<Int>.toCongruence(): Congruence {
  check(size == 2)
  return Congruence(this[0], this[1])
}

@JvmName("LongListToCongruence")
fun List<Long>.toCongruence(): Congruence {
  check(size == 2)
  return Congruence(this[0], this[1])
}

@JvmName("BigIntegerListToCongruence")
fun List<BigInteger>.toCongruence(): Congruence {
  check(size == 2)
  return Congruence(this[0], this[1])
}

@JvmName("BigIntegerIntPairToCongruence")
fun Pair<BigInteger, Int>.toCongruence() = Congruence(first, second)

@JvmName("BigIntegerLongPairToCongruence")
fun Pair<BigInteger, Long>.toCongruence() = Congruence(first, second)

@JvmName("IntBigIntegerPairToCongruence")
fun Pair<Int, BigInteger>.toCongruence() = Congruence(first, second)

@JvmName("IntIntPairToCongruence")
fun Pair<Int, Int>.toCongruence() = Congruence(first, second)

@JvmName("IntLongPairToCongruence")
fun Pair<Int, Long>.toCongruence() = Congruence(first, second)

@JvmName("LongBigIntegerPairToCongruence")
fun Pair<Long, BigInteger>.toCongruence() = Congruence(first, second)

@JvmName("LongIntPairToCongruence")
fun Pair<Long, Int>.toCongruence() = Congruence(first, second)

@JvmName("LongLongPairToCongruence")
fun Pair<Long, Long>.toCongruence() = Congruence(first, second)

fun crt(congruences: List<Congruence>): BigInteger {
  val product = congruences.productOf { it.n }
  return congruences.sumOf {
    val n = product / it.n
    val m = n.modInverse(it.n)
    it.a * n * m
  }.mod(product)
}

@JvmName("crtOfCongruenceIterable")
fun crt(congruences: Iterable<Congruence>) = crt(congruences.toList())

@JvmName("crtOfIntListIterable")
fun crt(congruences: Iterable<List<Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongListIterable")
fun crt(congruences: Iterable<List<Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfBigIntegerListIterable")
fun crt(congruences: Iterable<List<BigInteger>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfBigIntegerIntPairIterable")
fun crt(congruences: Iterable<Pair<BigInteger, Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfBigIntegerLongPairIterable")
fun crt(congruences: Iterable<Pair<BigInteger, Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfIntBigIntegerPairIterable")
fun crt(congruences: Iterable<Pair<Int, BigInteger>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfIntIntPairIterable")
fun crt(congruences: Iterable<Pair<Int, Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfIntLongPairIterable")
fun crt(congruences: Iterable<Pair<Int, Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongBigIntegerPairIterable")
fun crt(congruences: Iterable<Pair<Long, BigInteger>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongIntPairIterable")
fun crt(congruences: Iterable<Pair<Long, Int>>) = crt(congruences.map { it.toCongruence() })

@JvmName("crtOfLongLongPairIterable")
fun crt(congruences: Iterable<Pair<Long, Long>>) = crt(congruences.map { it.toCongruence() })

@JvmName("CongruenceIterableCrt")
fun Iterable<Congruence>.crt() = crt(this)

@JvmName("IntListIterableCrt")
fun Iterable<List<Int>>.crt() = crt(this)

@JvmName("LongListIterableCrt")
fun Iterable<List<Long>>.crt() = crt(this)

@JvmName("BigIntegerListIterableCrt")
fun Iterable<List<BigInteger>>.crt() = crt(this)

@JvmName("BigIntegerIntPairIterableCrt")
fun Iterable<Pair<BigInteger, Int>>.crt() = crt(this)

@JvmName("BigIntegerLongPairIterableCrt")
fun Iterable<Pair<BigInteger, Long>>.crt() = crt(this)

@JvmName("IntBigIntegerPairIterableCrt")
fun Iterable<Pair<Int, BigInteger>>.crt() = crt(this)

@JvmName("IntIntPairIterableCrt")
fun Iterable<Pair<Int, Int>>.crt() = crt(this)

@JvmName("IntLongPairIterableCrt")
fun Iterable<Pair<Int, Long>>.crt() = crt(this)

@JvmName("LongBigIntegerPairIterableCrt")
fun Iterable<Pair<Long, BigInteger>>.crt() = crt(this)

@JvmName("LongIntPairIterableCrt")
fun Iterable<Pair<Long, Int>>.crt() = crt(this)

@JvmName("LongLongPairIterableCrt")
fun Iterable<Pair<Long, Long>>.crt() = crt(this)
