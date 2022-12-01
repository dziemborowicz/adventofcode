class PuzzleY2022D1 : Puzzle {

  private lateinit var caloriesByElf: List<Int>

  override fun parse(input: String) {
    caloriesByElf = input.lines().splitByBlank().map { it.sumOf(String::toInt) }
  }

  override fun solve1(): Int {
    return caloriesByElf.max()
  }

  override fun solve2(): Int {
    return caloriesByElf.sorted().takeLast(3).sum()
  }

  companion object {
    val testInput1 = """
      1000
      2000
      3000
      
      4000
      
      5000
      6000
      
      7000
      8000
      9000
      
      10000
      """.trimIndent()
    val testAnswer1 = 24000

    val testInput2 = testInput1
    val testAnswer2 = 45000
  }
}
