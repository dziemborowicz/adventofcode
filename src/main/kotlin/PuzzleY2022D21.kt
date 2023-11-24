class PuzzleY2022D21 : Puzzle {

  private sealed interface Monkey
  private sealed interface Operation : Monkey {
    val left: Monkey
    val right: Monkey
  }

  private data class Literal(val value: Long) : Monkey
  private data class Add(override val left: Monkey, override val right: Monkey) : Operation
  private data class Subtract(override val left: Monkey, override val right: Monkey) : Operation
  private data class Multiply(override val left: Monkey, override val right: Monkey) : Operation
  private data class Divide(override val left: Monkey, override val right: Monkey) : Operation

  private lateinit var root: Operation
  private lateinit var humn: Monkey

  override fun parse(input: String) {
    val definitions = input.lines().associate { it.split(": ").toPair() }
    val monkeys = hashMapOf<String, Monkey>()
    val parseMonkey = DeepRecursiveFunction { name ->
      monkeys.computeIfAbsentInline(name) {
        val number = definitions.getValue(name).toLongOrNull()
        if (number != null) {
          Literal(number)
        } else {
          val (left, op, right) = definitions.getValue(name).split(' ')
          when (op) {
            "+" -> Add(callRecursive(left), callRecursive(right))
            "-" -> Subtract(callRecursive(left), callRecursive(right))
            "*" -> Multiply(callRecursive(left), callRecursive(right))
            "/" -> Divide(callRecursive(left), callRecursive(right))
            else -> error()
          }
        }
      }!!
    }
    root = parseMonkey("root") as Operation
    humn = parseMonkey("humn")
  }

  override fun solve1(): Long {
    return evaluate(root)
  }

  override fun solve2(): Long {
    var (left, right) =
      if (root.left.contains(humn)) {
        Pair(root.left, root.right)
      } else {
        Pair(root.right, root.left)
      }

    while (left !== humn) {
      check(left is Operation)
      if (left.left.contains(humn)) {
        right = when (left) {
          is Add -> Subtract(right, left.right)
          is Subtract -> Add(right, left.right)
          is Multiply -> Divide(right, left.right)
          is Divide -> Multiply(right, left.right)
        }
        left = left.left
      } else {
        right = when (left) {
          is Add -> Subtract(right, left.left)
          is Subtract -> Subtract(left.left, right)
          is Multiply -> Divide(right, left.left)
          is Divide -> Divide(left.left, right)
        }
        left = left.right
      }
    }

    return evaluate(right)
  }

  private val evaluate = DeepRecursiveFunction<Monkey, Long> {
    when (it) {
      is Literal -> it.value
      is Add -> callRecursive(it.left) + callRecursive(it.right)
      is Subtract -> callRecursive(it.left) - callRecursive(it.right)
      is Multiply -> callRecursive(it.left) * callRecursive(it.right)
      is Divide -> callRecursive(it.left) / callRecursive(it.right)
    }
  }

  private fun Monkey.contains(monkey: Monkey): Boolean {
    return DeepRecursiveFunction<Monkey, Boolean> {
      it === monkey || (it is Operation && (callRecursive(it.left) || callRecursive(it.right)))
    }(this)
  }

  companion object {
    val testInput1 = """
      root: pppw + sjmn
      dbpl: 5
      cczh: sllz + lgvd
      zczc: 2
      ptdq: humn - dvpt
      dvpt: 3
      lfqf: 4
      humn: 5
      ljgn: 2
      sjmn: drzm * dbpl
      sllz: 4
      pppw: cczh / lfqf
      lgvd: ljgn * ptdq
      drzm: hmdt - zczc
      hmdt: 32
      """.trimIndent()
    val testAnswer1 = 152

    val testInput2 = testInput1
    val testAnswer2 = 301
  }
}
