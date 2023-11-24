class PuzzleY2020D1 : Puzzle {

  private lateinit var numbers: List<Int>

  override fun parse(input: String) {
    numbers = input.extractInts()
  }

  override fun solve1(): Int {
    return numbers.combinations(2).first { it.sum() == 2020 }.product()
  }

  override fun solve2(): Int {
    return numbers.combinations(3).first { it.sum() == 2020 }.product()
  }

  companion object {
    val testInput1 = """
      1721
      979
      366
      299
      675
      1456
      """.trimIndent()
    val testAnswer1 = 514579

    val testInput2 = testInput1
    val testAnswer2 = 241861950
  }
}
