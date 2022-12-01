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
}
