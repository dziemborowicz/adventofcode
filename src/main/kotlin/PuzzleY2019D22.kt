import com.google.common.truth.Truth.assertThat
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class PuzzleY2019D22 : Puzzle {

  private data class ModOp(val a: BigInteger, val b: BigInteger, val m: BigInteger)

  private lateinit var instructions: List<String>

  override fun parse(input: String) {
    instructions = input.lines()
  }

  override fun solve1(): Int = shuffle(numCards = 10007, instructions).indexOf(2019)

  override fun solve2(): BigInteger {
    val op = instructions.toModOp(numCards = 119315717514047)
    return op.apply(2020, times = 101741582076661)
  }

  companion object {
    private fun ModOp.apply(n: Long, times: Long): BigInteger =
      apply(n.toBigInteger(), times.toBigInteger())

    private fun ModOp.apply(n: BigInteger, times: BigInteger = ONE): BigInteger {
      var a = a
      var b = b
      var n = n
      var times = times
      while (times > ZERO) {
        if (times.isOdd) {
          n = ((a * n) + b).mod(m)
        }
        b = ((a * b) + b).mod(m)
        a = (a * a).mod(m)
        times = times.shiftRight(1)
      }
      return n
    }

    private fun List<String>.toModOp(numCards: Int): ModOp = toModOp(numCards.toBigInteger())

    private fun List<String>.toModOp(numCards: Long): ModOp = toModOp(numCards.toBigInteger())

    private fun List<String>.toModOp(numCards: BigInteger): ModOp {
      var op = ModOp(a = ONE, b = ZERO, m = numCards)
      for (instruction in reversed()) {
        op = when {
          instruction == "deal into new stack" -> {
            ModOp(
              a = (-op.a).mod(op.m),
              b = (-op.b - ONE).mod(op.m),
              m = op.m,
            )
          }

          instruction.startsWith("cut") -> {
            val n = instruction.extractBigIntegers().single()
            ModOp(
              a = op.a,
              b = (op.b + n).mod(op.m),
              m = op.m,
            )
          }

          instruction.startsWith("deal with increment") -> {
            val n = instruction.extractBigIntegers().single().modInverse(numCards)
            ModOp(
              a = (op.a * n).mod(op.m),
              b = (op.b * n).mod(op.m),
              m = op.m,
            )
          }

          else -> fail()
        }
      }
      return op
    }

    private fun shuffle(numCards: Int, instructions: List<String>): List<Int> {
      val op = instructions.toModOp(numCards)
      return (0..<numCards).map { op.apply(it.toBigInteger()).toInt() }
    }

    fun test1_1() {
      val input = """
        deal with increment 7
        deal into new stack
        deal into new stack
        """.trimIndent()
      val result = shuffle(numCards = 10, input.lines())
      assertThat(result).containsExactly(0, 3, 6, 9, 2, 5, 8, 1, 4, 7).inOrder()
    }

    fun test1_2() {
      val input = """
        cut 6
        deal with increment 7
        deal into new stack
        """.trimIndent()
      val result = shuffle(numCards = 10, input.lines())
      assertThat(result).containsExactly(3, 0, 7, 4, 1, 8, 5, 2, 9, 6).inOrder()
    }

    fun test1_3() {
      val input = """
        deal with increment 7
        deal with increment 9
        cut -2
        """.trimIndent()
      val result = shuffle(numCards = 10, input.lines())
      assertThat(result).containsExactly(6, 3, 0, 7, 4, 1, 8, 5, 2, 9).inOrder()
    }

    fun test1_4() {
      val input = """
        deal into new stack
        cut -2
        deal with increment 7
        cut 8
        cut -4
        deal with increment 7
        cut 3
        deal with increment 9
        deal with increment 3
        cut -1
        """.trimIndent()
      val result = shuffle(numCards = 10, input.lines())
      assertThat(result).containsExactly(9, 2, 5, 8, 1, 4, 7, 0, 3, 6).inOrder()
    }
  }
}
