class PuzzleY2024D2 : Puzzle {

  private lateinit var reports: List<List<Int>>

  override fun parse(input: String) {
    reports = input.extractIntLists()
  }

  override fun solve1(): Int {
    return reports.count { it.isSafe() }
  }

  override fun solve2(): Int {
    return reports.count { list ->
      list.isSafe() || list.indices.any { index -> list.withoutItemAt(index).isSafe() }
    }
  }

  private fun List<Int>.isSafe(): Boolean {
    return windowed(2).all { (a, b) -> a in (b + 1..b + 3) } || windowed(2).all { (b, a) -> a in (b + 1..b + 3) }
  }

  private fun List<Int>.withoutItemAt(index: Int): List<Int> {
    return filterIndexed { i, _ -> i != index }
  }

  companion object {
    val testInput1 = """
      7 6 4 2 1
      1 2 7 8 9
      9 7 6 2 1
      1 3 2 4 5
      8 6 4 4 1
      1 3 6 7 9
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2 = testInput1
    val testAnswer2 = 4
  }
}
