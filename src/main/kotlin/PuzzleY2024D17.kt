class PuzzleY2024D17 : Puzzle {

  private var a = 0L
  private var b = 0L
  private var c = 0L
  private lateinit var instructions: List<Long>

  override fun parse(input: String) {
    val (a, b, c) = input.extractLongs().take(3)
    this.a = a
    this.b = b
    this.c = c
    this.instructions = input.extractLongs().drop(3)
  }

  override fun solve1(): String = execute(a, b, c, instructions).joinToString(",")

  override fun solve2(): Long {
    fun solve2(a: Long, n: Int = 1): Long? {
      if (n > instructions.size) {
        return a
      } else {
        for (i in 0..7) {
          if (execute((a shl 3) + i, b, c, instructions) == instructions.takeLast(n)) {
            val solution = solve2((a shl 3) + i, n + 1)
            if (solution != null) return solution
          }
        }
      }
      return null
    }

    return solve2(0)!!
  }

  private fun execute(a: Long, b: Long, c: Long, instructions: List<Long>): List<Long> {
    var a = a
    var b = b
    var c = c

    fun combo(operand: Long): Long {
      return when {
        operand in 0L..3L -> operand
        operand == 4L -> a
        operand == 5L -> b
        operand == 6L -> c
        else -> fail()
      }
    }

    var ip = 0
    val outputs = mutableListOf<Long>()
    while (ip in instructions.indices && ip + 1 in instructions.indices) {
      val opcode = instructions[ip]
      val operand = instructions[ip + 1]
      when (opcode) {
        0L -> a = a shr combo(operand).coerceToInt()
        1L -> b = b xor operand
        2L -> b = combo(operand) % 8
        3L -> if (a != 0L) {
          ip = operand.coerceToInt()
          continue
        }
        4L -> b = b xor c
        5L -> outputs += combo(operand) % 8
        6L -> b = a shr combo(operand).coerceToInt()
        7L -> c = a shr combo(operand).coerceToInt()
        else -> fail()
      }
      ip += 2
    }
    return outputs
  }

  companion object {
    val testInput1_1 = """
      Register A: 729
      Register B: 0
      Register C: 0

      Program: 0,1,5,4,3,0
      """.trimIndent()
    val testAnswer1_1 = "4,6,3,5,6,3,5,2,1,0"

    val testInput2_1 = """
      Register A: 2024
      Register B: 0
      Register C: 0

      Program: 0,3,5,4,3,0
      """.trimIndent()
    val testAnswer2_1 = 117440
  }
}
