import kotlin.math.abs

class PuzzleY2024D1 : Puzzle {

  private lateinit var leftList: List<Int>
  private lateinit var rightList: List<Int>

  override fun parse(input: String) {
    val grid = input.parseIntGrid()
    leftList = grid.column(0)
    rightList = grid.column(1)
  }

  override fun solve1(): Int {
    return leftList.sorted().zip(rightList.sorted()).sumOf { (a, b) -> abs(a - b) }
  }

  override fun solve2(): Int {
    val rightCounter = rightList.toCounter()
    return leftList.sumOf { it * rightCounter[it] }
  }

  companion object {
    val testInput1 = """
      3   4
      4   3
      2   5
      1   3
      3   9
      3   3
      """.trimIndent()
    val testAnswer1 = 11

    val testInput2 = testInput1
    val testAnswer2 = 31
  }
}
