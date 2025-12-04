class PuzzleY2025D4 : Puzzle {

  private lateinit var grid: Grid<Char>

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return grid.indices.count { it.canBeRemovedFrom(grid) }
  }

  override fun solve2(): Int {
    val grid = grid.copy()
    val queue = grid.indices.filter { it.canBeRemovedFrom(grid) }.toHashSet()
    while (queue.isNotEmpty()) {
      val index = queue.removeFirst()
      grid[index] = 'x'
      queue += index.neighborsWithDiagonalsIn(grid).filter { it.canBeRemovedFrom(grid) }
    }
    return grid.count { it == 'x' }
  }

  private fun Index.canBeRemovedFrom(grid: Grid<Char>): Boolean =
    grid[this] == '@' && neighborsWithDiagonalsIn(grid).count { grid[it] == '@' } < 4

  companion object {
    val testInput1 = """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.
      """.trimIndent()
    val testAnswer1 = 13

    val testInput2 = testInput1
    val testAnswer2 = 43
  }
}
