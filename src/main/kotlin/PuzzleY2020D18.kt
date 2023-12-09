class PuzzleY2020D18 : Puzzle {

  private val functions = """
    infix fun Long.timesLow(other: Long): Long = this * other
    infix fun Long.plusLow(other: Long): Long = this + other
    """.trimIndent()

  private lateinit var expressions: List<String>

  override fun parse(input: String) {
    this.expressions = input.replace(Regex("""\d+"""), "$0L").lines()
  }

  override fun solve1(): Long {
    return evalLongs(functions, expressions.map {
      it.replace("+", "plusLow").replace("*", "timesLow")
    }).sum()
  }

  override fun solve2(): Long {
    return evalLongs(functions, expressions.map { it.replace("*", "timesLow") }).sum()
  }

  companion object {
    val testInput1_1 = "1 + 2 * 3 + 4 * 5 + 6"
    val testAnswer1_1 = 71

    val testInput1_2 = "1 + (2 * 3) + (4 * (5 + 6))"
    val testAnswer1_2 = 51

    val testInput1_3 = "2 * 3 + (4 * 5)"
    val testAnswer1_3 = 26

    val testInput1_4 = "5 + (8 * 3 + 9 + 3 * 4 * 3)"
    val testAnswer1_4 = 437

    val testInput1_5 = "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
    val testAnswer1_5 = 12240

    val testInput1_6 = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
    val testAnswer1_6 = 13632

    val testInput2_1 = "1 + 2 * 3 + 4 * 5 + 6"
    val testAnswer2_1 = 231

    val testInput2_2 = "1 + (2 * 3) + (4 * (5 + 6))"
    val testAnswer2_2 = 51

    val testInput2_3 = "2 * 3 + (4 * 5)"
    val testAnswer2_3 = 46

    val testInput2_4 = "5 + (8 * 3 + 9 + 3 * 4 * 3)"
    val testAnswer2_4 = 1445

    val testInput2_5 = "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"
    val testAnswer2_5 = 669060

    val testInput2_6 = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
    val testAnswer2_6 = 23340
  }
}
