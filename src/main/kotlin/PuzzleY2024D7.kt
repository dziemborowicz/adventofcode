class PuzzleY2024D7 : Puzzle {

  private lateinit var equations: List<Pair<Long, List<Long>>>

  override fun parse(input: String) {
    equations = input.extractLongLists().map { it.first() to it.drop(1) }
  }

  override fun solve1(): Long {
    return equations.filter { (result, operands) ->
      isPossible(result, operands, listOf(Long::plus, Long::times))
    }.sumOf { (result, _) -> result }
  }

  override fun solve2(): Long {
    return equations.filter { (result, operands) ->
      isPossible(result, operands, listOf(Long::plus, Long::times, ::concatenate))
    }.sumOf { (result, _) -> result }
  }

  private fun isPossible(
    result: Long,
    operands: List<Long>,
    operators: List<(Long, Long) -> Long>,
  ): Boolean {
    return if (operands.size < 2) {
      operands.single() == result
    } else {
      val (a, b) = operands
      val rest = operands.drop(2)
      operators.any { operator ->
        isPossible(result, listOf(operator(a, b)) + rest, operators)
      }
    }
  }

  private fun concatenate(a: Long, b: Long): Long = "$a$b".toLong()

  companion object {
    val testInput1 = """
      190: 10 19
      3267: 81 40 27
      83: 17 5
      156: 15 6
      7290: 6 8 6 15
      161011: 16 10 13
      192: 17 8 14
      21037: 9 7 18 13
      292: 11 6 16 20
      """.trimIndent()
    val testAnswer1 = 3749

    val testInput2 = testInput1
    val testAnswer2 = 11387
  }
}
