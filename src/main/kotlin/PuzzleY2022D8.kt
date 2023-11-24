class PuzzleY2022D8 : Puzzle {

  private lateinit var grid: Grid<Int>

  override fun parse(input: String) {
    grid = input.parseDenseIntGrid()
  }

  override fun solve1(): Int {
    return grid.indices.count { isVisible(it) }
  }

  override fun solve2(): Int {
    return grid.indices.maxOf { scenicScore(it) }
  }

  private fun isVisible(location: Index): Boolean {
    val height = grid[location]
    return (0 until location.row).all { grid[it, location.column] < height } ||
      (location.row + 1 until grid.numRows).all { grid[it, location.column] < height } ||
      (0 until location.column).all { grid[location.row, it] < height } ||
      (location.column + 1 until grid.numColumns).all { grid[location.row, it] < height }
  }

  private fun scenicScore(location: Index): Int {
    val height = grid[location.row, location.column]

    var countUp = 0
    for (it in location.row - 1 downTo 0) {
      countUp++
      if (grid[it, location.column] >= height) break
    }

    var countDown = 0
    for (it in location.row + 1 until grid.numRows) {
      countDown++
      if (grid[it, location.column] >= height) break
    }


    var countLeft = 0
    for (it in location.column - 1 downTo 0) {
      countLeft++
      if (grid[location.row, it] >= height) break
    }

    var countRight = 0
    for (it in location.column + 1 until grid.numColumns) {
      countRight++
      if (grid[location.row, it] >= height) break
    }

    return countUp * countDown * countLeft * countRight
  }

  companion object {
    val testInput1 = """
      30373
      25512
      65332
      33549
      35390
      """.trimIndent()
    val testAnswer1 = 21

    val testInput2 = testInput1
    val testAnswer2 = 8
  }
}
