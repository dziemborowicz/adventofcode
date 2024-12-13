class PuzzleY2021D1 : Puzzle {

  private lateinit var depths: List<Int>

  override fun parse(input: String) {
    depths = input.extractInts()
  }

  override fun solve1(): Int {
    return depths.windowed(2).count { it[1] > it[0] }
  }

  override fun solve2(): Int {
    return depths.windowed(3).map { it.sum() }.windowed(2).count { it[1] > it[0] }
  }

  companion object {
    val testInput1 = """
      199
      200
      208
      210
      200
      207
      240
      269
      260
      263
      """.trimIndent()
    val testAnswer1 = 7

    val testInput2 = testInput1
    val testAnswer2 = 5
  }
}
