class PuzzleY2025D6 : Puzzle {

  private lateinit var problems: List<Grid<Char>>

  override fun parse(input: String) {
    problems = input.parseDenseCharGrid().splitByBlankColumns()
  }

  override fun solve1(): Long {
    return problems.sumOf { grid ->
      val numbers = grid.rows().dropLast().map { it.toLong() }
      when (grid.rows().last().first()) {
        '+' -> numbers.sum()
        '*' -> numbers.product()
        else -> fail()
      }
    }
  }

  override fun solve2(): Long {
    return problems.sumOf { grid ->
      val numbers = grid.columns().map { it.toLong() }
      when (grid.rows().last().first()) {
        '+' -> numbers.sum()
        '*' -> numbers.product()
        else -> fail()
      }
    }
  }

  private fun List<Char>.toLong(): Long = joinToString("").extractLongs().single()

  companion object {
    val testInput1 = """
      123 328  51 64 
       45 64  387 23 
        6 98  215 314
      *   +   *   +  
      """.trimIndent()
    val testAnswer1 = 4277556

    val testInput2 = testInput1
    val testAnswer2 = 3263827
  }
}
