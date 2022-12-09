import com.google.common.truth.Truth.assertThat

class PuzzleY2021D18 : Puzzle {

  private lateinit var numbers: List<SnailfishNumber>

  override fun parse(input: String) {
    numbers = input.lines().map { it.toSnailfishNumber() }
  }

  override fun solve1(): Long {
    return numbers.sum().magnitude()
  }

  override fun solve2(): Long {
    return numbers.maxOf { a ->
      numbers.filter { b -> a !== b }.maxOf { b -> (a + b).magnitude() }
    }
  }

  companion object {

    sealed interface SnailfishNumber {
      fun clone(): SnailfishNumber
    }

    data class SnailfishPair(var left: SnailfishNumber, var right: SnailfishNumber) :
      SnailfishNumber {
      override fun toString() = "[$left,$right]"
      override fun clone() = SnailfishPair(left.clone(), right.clone())
    }

    data class SnailfishLiteral(var value: Long) : SnailfishNumber {
      override fun toString() = value.toString()
      override fun clone() = SnailfishLiteral(value)
    }

    private fun String.toSnailfishNumber(): SnailfishNumber {
      fun parseInternal(chars: ArrayDeque<Char>): SnailfishNumber {
        val digits = chars.removeWhile { it.isDigit() }
        return if (digits.isNotEmpty()) {
          SnailfishLiteral(digits.asString().toLong())
        } else {
          check(chars.removeFirst() == '[')
          val left = parseInternal(chars)
          check(chars.removeFirst() == ',')
          val right = parseInternal(chars)
          check(chars.removeFirst() == ']')
          SnailfishPair(left, right)
        }
      }
      return parseInternal(toDeque())
    }

    private operator fun SnailfishNumber.plus(other: SnailfishNumber): SnailfishNumber {
      return SnailfishPair(this.clone(), other.clone()).also { reduce(it) }
    }

    private fun reduce(number: SnailfishNumber) {
      while (true) {
        var lastLiteral: SnailfishLiteral? = null
        var addToNextLiteral: Long? = null
        fun explode(depth: Int, parent: SnailfishPair?, number: SnailfishNumber): Boolean {
          return when (number) {
            is SnailfishLiteral -> {
              if (addToNextLiteral != null) {
                number.value += addToNextLiteral!!
                true
              } else {
                lastLiteral = number
                false
              }
            }
            is SnailfishPair -> {
              if (depth > 3 && addToNextLiteral == null) {
                lastLiteral?.let { it.value += (number.left as SnailfishLiteral).value }
                addToNextLiteral = (number.right as SnailfishLiteral).value
                if (parent!!.left === number) {
                  parent!!.left = SnailfishLiteral(0)
                } else {
                  parent!!.right = SnailfishLiteral(0)
                }
                false
              } else {
                explode(depth + 1, number, number.left) || explode(depth + 1, number, number.right)
              }
            }
          }
        }

        if (explode(depth = 0, parent = null, number) || addToNextLiteral != null) continue

        fun split(parent: SnailfishPair?, number: SnailfishNumber): Boolean {
          return when (number) {
            is SnailfishLiteral -> {
              if (number.value > 9) {
                val split =
                  SnailfishPair(
                    SnailfishLiteral(number.value / 2),
                    SnailfishLiteral(number.value - (number.value / 2)),
                  )
                if (parent!!.left === number) {
                  parent!!.left = split
                } else {
                  parent!!.right = split
                }
                true
              } else {
                false
              }
            }
            is SnailfishPair -> split(number, number.left) || split(number, number.right)
          }
        }

        if (!split(parent = null, number)) break
      }
    }

    private fun Iterable<SnailfishNumber>.sum(): SnailfishNumber = reduce { a, b -> a + b }

    private fun SnailfishNumber.magnitude(): Long {
      return when (this) {
        is SnailfishPair -> (3 * left.magnitude()) + (2 * right.magnitude())
        is SnailfishLiteral -> value
      }
    }

    fun testAdd1() {
      val numbers = """
        [1,2]
        [[3,4],5]
        """.trimIndent().lines().map { it.toSnailfishNumber() }
      assertThat(numbers.sum().toString()).isEqualTo("[[1,2],[[3,4],5]]")
    }

    fun testAdd2() {
      val numbers = """
        [[[[4,3],4],4],[7,[[8,4],9]]]
        [1,1]
        """.trimIndent().lines().map { it.toSnailfishNumber() }
      assertThat(numbers.sum().toString()).isEqualTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
    }

    fun testAdd3() {
      val numbers = """
        [1,1]
        [2,2]
        [3,3]
        [4,4]
        """.trimIndent().lines().map { it.toSnailfishNumber() }
      assertThat(numbers.sum().toString()).isEqualTo("[[[[1,1],[2,2]],[3,3]],[4,4]]")
    }

    fun testAdd4() {
      val numbers = """
        [1,1]
        [2,2]
        [3,3]
        [4,4]
        [5,5]
        """.trimIndent().lines().map { it.toSnailfishNumber() }
      assertThat(numbers.sum().toString()).isEqualTo("[[[[3,0],[5,3]],[4,4]],[5,5]]")
    }

    fun testAdd5() {
      val numbers = """
        [1,1]
        [2,2]
        [3,3]
        [4,4]
        [5,5]
        [6,6]
        """.trimIndent().lines().map { it.toSnailfishNumber() }
      assertThat(numbers.sum().toString()).isEqualTo("[[[[5,0],[7,4]],[5,5]],[6,6]]")
    }

    fun testAdd6() {
      val numbers = """
        [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
        [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
        [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
        [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
        [7,[5,[[3,8],[1,4]]]]
        [[2,[2,2]],[8,[8,1]]]
        [2,9]
        [1,[[[9,3],9],[[9,0],[0,7]]]]
        [[[5,[7,4]],7],1]
        [[[[4,2],2],6],[8,7]]
        """.trimIndent().lines().map { it.toSnailfishNumber() }
      assertThat(numbers.sum().toString())
        .isEqualTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
    }

    fun testMagnitude1() {
      assertThat("[9,1]".toSnailfishNumber().magnitude())
        .isEqualTo(29)
    }

    fun testMagnitude2() {
      assertThat("[1,9]".toSnailfishNumber().magnitude())
        .isEqualTo(21)
    }

    fun testMagnitude3() {
      assertThat("[[9,1],[1,9]]".toSnailfishNumber().magnitude())
        .isEqualTo(129)
    }

    fun testMagnitude4() {
      assertThat("[[1,2],[[3,4],5]]".toSnailfishNumber().magnitude())
        .isEqualTo(143)
    }

    fun testMagnitude5() {
      assertThat("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]".toSnailfishNumber().magnitude())
        .isEqualTo(1384)
    }

    fun testMagnitude6() {
      assertThat("[[[[1,1],[2,2]],[3,3]],[4,4]]".toSnailfishNumber().magnitude())
        .isEqualTo(445)
    }

    fun testMagnitude7() {
      assertThat("[[[[3,0],[5,3]],[4,4]],[5,5]]".toSnailfishNumber().magnitude())
        .isEqualTo(791)
    }

    fun testMagnitude8() {
      assertThat("[[[[5,0],[7,4]],[5,5]],[6,6]]".toSnailfishNumber().magnitude())
        .isEqualTo(1137)
    }

    fun testMagnitude9() {
      assertThat(
        "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]".toSnailfishNumber().magnitude())
        .isEqualTo(3488)
    }

    val testInput1 = """
      [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
      [[[5,[2,8]],4],[5,[[9,9],0]]]
      [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
      [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
      [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
      [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
      [[[[5,4],[7,7]],8],[[8,3],8]]
      [[9,3],[[9,9],[6,[4,9]]]]
      [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
      [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
      """.trimIndent()
    val testAnswer1 = 4140

    val testInput2 = testInput1
    val testAnswer2 = 3993
  }
}
